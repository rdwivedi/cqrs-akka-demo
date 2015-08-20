package com.nb.akka.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class AkkaClusterLauncher {

	public static void main(String[] args) throws InterruptedException {

		Config config = ConfigFactory.load();
		String systemName = config.getString("akka.systemName");
		ActorSystem system = ActorSystem.create(systemName, config);
		ActorRef actorRef = system.actorOf(Props.create(MainClusterActor.class), "cluster");
		ActorRef mediator = DistributedPubSubExtension.get(system).mediator();
		mediator.tell(new DistributedPubSubMediator.Put(actorRef), actorRef);
	}

}
