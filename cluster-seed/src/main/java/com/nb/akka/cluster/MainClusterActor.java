package com.nb.akka.cluster;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class MainClusterActor extends UntypedActor {

	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	Cluster cluster = Cluster.get(getContext().system());

	public MainClusterActor() {

	}

	@Override
	public void preStart() throws Exception {
		cluster.subscribe(getSelf(), MemberEvent.class);
	};

	@Override
	public void onReceive(Object message) throws Exception {

		if (message instanceof MemberUp) {
			MemberUp mUp = (MemberUp) message;
			log.info("Member is Up: {} ", mUp.member());

		} else if (message instanceof UnreachableMember) {

			UnreachableMember mUnreachable = (UnreachableMember) message;
			log.info("Member detected as unreachable: {} ", mUnreachable.member());

		} else if (message instanceof MemberRemoved) {
			MemberRemoved mRemoved = (MemberRemoved) message;
			log.info("Member is Removed: {} ", mRemoved.member());

		} else {
			unhandled(message);

		}
	}

}