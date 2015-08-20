package read.com.nearbuy.merchant.read;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.dispatch.OnSuccess;

import com.nearbuy.dao.MerchantDAO;
import com.nearbuy.merchant.model.Merchant;

public class QueryModelServiceActor extends UntypedActor {

	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-qm.xml");

	MerchantDAO merchantDaoQM = (MerchantDAO) ctx.getBean("merchantDAO-QM");
	MerchantDAO merchantDaoCM = (MerchantDAO) ctx.getBean("merchantDAO-CM");

	@Override
	public void onReceive(Object message) throws Exception {
		System.out.println("inside Onrecieve of QueryModelServiceActor");
		ExecutionContext ec = getContext().system().dispatcher();
		if (message instanceof Merchant) {
			Merchant merchant = (Merchant) message;
			System.out.println("Merchen Recieved : " + merchant);
			Future<Merchant> merchantFuture = this.getMerchant(merchant.getId());
			merchantFuture.flatMap(new Mapper<Merchant, Future<Integer>>() {
				public Future<Integer> apply(final Merchant merchant) {
					return updateMerchantToQueryModel(merchant);
				}
			}, ec).onSuccess(new OnSuccess<Integer>() {
				public void onSuccess(Integer result) {
					System.out.println("Merchant Id : " + result + "added to QueryModel");;
				}
			}, ec);
		} else {
			unhandled(message);
		}
	}

	private Future<Merchant> getMerchant(int id) {

		Merchant merchant = merchantDaoCM.findByID(id);
		return Futures.successful(merchant);
	}

	private Future<Integer> updateMerchantToQueryModel(Merchant merchant) {

		merchantDaoQM.save(merchant);

		return Futures.successful(merchant.getId());

	}
}
