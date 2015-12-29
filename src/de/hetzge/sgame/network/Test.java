package de.hetzge.sgame.network;

public class Test {

	public static void main(String[] args) {
		NetworkSettings networkSettings = NetworkModule.networkSettings;
		networkSettings.setNetworkRole(E_NetworkRole.valueOf(args[0]));
		NetworkModule.dispatcher = (message, socket) -> {
			System.out.println(message);
		};

		NetworkModule.instance.connect();
		NetworkModule.instance.send("Hello World");
	}

}
