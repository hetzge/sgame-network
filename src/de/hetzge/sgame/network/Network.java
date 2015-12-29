package de.hetzge.sgame.network;

import java.io.Serializable;

public class Network implements IF_Network {

	private IF_Network network = null;

	public void connect() {
		NetworkSettings networkSettings = NetworkModule.networkSettings;
		E_NetworkRole networkRole = networkSettings.getNetworkRole();
		switch (networkRole) {
		case CLIENT:
			network = new TcpClient().connect(networkSettings);
			break;
		case HOST:
			network = new TcpServer().start();
			break;
		}
	}

	@Override
	public void send(Serializable object) {
		network.send(object);
	}

	public void sendAndSelf(Serializable object) {
		send(object);
		NetworkModule.dispatcher.dispatch(object, null);
	}

	public void sendOrSelf(Serializable object) {
		NetworkSettings networkSettings = NetworkModule.networkSettings;
		E_NetworkRole networkRole = networkSettings.getNetworkRole();
		if (networkRole == E_NetworkRole.CLIENT) {
			send(object);
		} else {
			NetworkModule.dispatcher.dispatch(object, null);
		}
	}

	@Override
	public void disconnect() {
		network.disconnect();
		network = null;
	}

	public boolean isConnected() {
		return network != null;
	}

}
