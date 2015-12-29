package de.hetzge.sgame.network;

import org.nustaq.serialization.FSTConfiguration;

public final class NetworkModule {

	public static final Network instance = new Network();
	public static final NetworkFunction function = new NetworkFunction();
	public static final NetworkSettings networkSettings = new NetworkSettings();

	public static IF_Dispatch dispatcher = (x, y) -> System.out.println("empty dispatch");
	public static FSTConfiguration fstConfiguration = FSTConfiguration.getDefaultConfiguration();

}
