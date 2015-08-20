package com.nearbuy.merchant.update;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;

import com.nearbuy.dao.MerchantDAO;
import com.nearbuy.merchant.model.Merchant;

public class UpdateServiceActor extends UntypedActor {

	private ActorRef mediator;
	// private Timeout timeout;
	private ExecutionContext ec;

	UpdateServiceActor() {
		ec = getContext().system().dispatcher();
		mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
		// timeout = new Timeout(Duration.create(40, "seconds"));
	}

	@Override
	public void onReceive(Object msg) throws Exception {

		System.out.println("Inside onRecieve...");
		if (msg instanceof Merchant) {
			final Merchant merchant = (Merchant) msg;
			Future<Merchant> updateMerchantFuture = updateMerchant(merchant);

			updateMerchantFuture.onSuccess(new OnSuccess<Merchant>() {
				public void onSuccess(Merchant result) {
					mediator.tell(new DistributedPubSubMediator.Send("/user/QUERY_MODEL_SERVICE", merchant, true), getSelf());
					System.out.println("Data update to Command Model.");
				}
			}, ec);
			
		} else {
			unhandled(msg);
		}
	}

	private Future<Merchant> updateMerchant(Merchant merchant) {
		// Get the Spring Context
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");

		// Get the EmployeeDAO Bean
		MerchantDAO merchantDao = (MerchantDAO) ctx.getBean("merchantDAO");

		merchantDao.save(merchant);

		return Futures.successful(merchant);

	}
}
