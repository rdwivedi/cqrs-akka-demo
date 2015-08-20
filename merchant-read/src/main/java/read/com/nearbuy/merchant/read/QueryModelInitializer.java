package read.com.nearbuy.merchant.read;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.routing.FromConfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;


public class QueryModelInitializer {

	private static final String QUERY_MODEL_SERVICE = "QUERY_MODEL_SERVICE";

	public static void main(String[] args) {

		Config config = ConfigFactory.load();

		String systemName = config.getString("akka.systemName");

		ActorSystem actorSystem = ActorSystem.create(systemName, config);

		ActorRef mediator = DistributedPubSubExtension.get(actorSystem).mediator();

		ActorRef queryModelRef = actorSystem.actorOf(FromConfig.getInstance().props(Props.create(QueryModelServiceActor.class)), QUERY_MODEL_SERVICE);
		mediator.tell(new DistributedPubSubMediator.Put(queryModelRef), queryModelRef);
	}

}
