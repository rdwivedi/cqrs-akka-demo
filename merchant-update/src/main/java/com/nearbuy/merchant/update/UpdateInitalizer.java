package com.nearbuy.merchant.update;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.routing.FromConfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class UpdateInitalizer {

	private static final String UPDATE_SERVICE = "UPDATE_SERVICE";

	public static void main(String[] args) {

		Config config = ConfigFactory.load();
		
		String systemName = config.getString("akka.systemName");

		ActorSystem actorSystem = ActorSystem.create(systemName, config);

		//final LoggingAdapter logger = Logging.getLogger(actorSystem, UpdateInitalizer.class);
		
		ActorRef mediator = DistributedPubSubExtension.get(actorSystem).mediator();

		ActorRef updateServiceRef = actorSystem.actorOf(FromConfig.getInstance().props(Props.create(UpdateServiceActor.class)), UPDATE_SERVICE);
		mediator.tell(new DistributedPubSubMediator.Put(updateServiceRef), updateServiceRef);
	}

}
