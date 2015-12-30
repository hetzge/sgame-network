package de.hetzge.sgame.network;

import java.io.Serializable;

public class Network implements IF_Network {

	private IF_Network network = null;

	public void connect() {
		NetworkSettings networkSettings = NetworkModule.settings;
		E_NetworkRole networkRole = networkSettings.getNetworkRole();
		switch (networkRole) {
		case CLIENT:
			this.network = new TcpClient().connect(networkSettings);
			break;
		case HOST:
			this.network = new TcpServer().start();
			break;
		}
	}

	@Override
	public void send(Serializable object) {
		this.network.send(object);
	}

	public void sendAndSelf(Serializable object) {
		send(object);
		IF_Dispatch dispatcher = NetworkModule.setup.getDispatcher();
		dispatcher.dispatch(object, null);
	}

	public void sendOrSelf(Serializable object) {
		NetworkSettings networkSettings = NetworkModule.settings;
		E_NetworkRole networkRole = networkSettings.getNetworkRole();
		if (networkRole == E_NetworkRole.CLIENT) {
			send(object);
		} else {
			IF_Dispatch dispatcher = NetworkModule.setup.getDispatcher();
			dispatcher.dispatch(object, null);
		}
	}

	@Override
	public void disconnect() {
		this.network.disconnect();
		this.network = null;
	}

	public boolean isConnected() {
		return this.network != null;
	}

}
