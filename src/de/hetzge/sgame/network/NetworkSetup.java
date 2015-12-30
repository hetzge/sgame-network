package de.hetzge.sgame.network;

import org.nustaq.serialization.FSTConfiguration;

public class NetworkSetup {
	private IF_Dispatch dispatcher = (x, y) -> System.out.println("empty dispatch");
	private FSTConfiguration fstConfiguration = FSTConfiguration.getDefaultConfiguration();

	public IF_Dispatch getDispatcher() {
		return this.dispatcher;
	}

	public void setDispatcher(IF_Dispatch dispatcher) {
		this.dispatcher = dispatcher;
	}

	public FSTConfiguration getFstConfiguration() {
		return this.fstConfiguration;
	}

	public void setFstConfiguration(FSTConfiguration fstConfiguration) {
		this.fstConfiguration = fstConfiguration;
	}
}
