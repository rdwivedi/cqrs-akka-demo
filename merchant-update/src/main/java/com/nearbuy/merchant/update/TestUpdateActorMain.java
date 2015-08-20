package com.nearbuy.merchant.update;

import java.util.Random;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.util.Timeout;

import com.nearbuy.merchant.model.Merchant;

public class TestUpdateActorMain {

	public static void main(String[] args) throws Exception {

		ActorSystem actorSystem = ActorSystem.create("nearbuy-actor-system");
		Timeout timeout = new Timeout(Duration.create(10, "seconds"));

		ActorRef mediator = DistributedPubSubExtension.get(actorSystem).mediator();

		Thread.sleep(20000);

		for (int i = 0; i < 10; i++) {
			mediator.tell(new DistributedPubSubMediator.Send("/user/UPDATE_SERVICE", getMerchant(i), true), ActorRef.noSender());
		}

	}

	private static Merchant getMerchant(int i) {
		Merchant merchant = new Merchant();
		merchant.setId(new Random().nextInt(100000));
		merchant.setName("Merchant - " + i);
		merchant.setVertical("Music - " + i);

		return merchant;
	}
}
