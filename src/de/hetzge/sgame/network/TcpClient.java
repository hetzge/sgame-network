package de.hetzge.sgame.network;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

import org.nustaq.net.TCPObjectSocket;
import org.nustaq.serialization.FSTConfiguration;
import org.pmw.tinylog.Logger;

class TcpClient implements IF_Network {

	private TCPObjectSocket tcpObjectSocket;

	public TcpClient connect(NetworkSettings clientSettings) {
		try {
			String host = clientSettings.getHost();
			short port = clientSettings.getPort();
			Socket socket = new Socket(host, port);
			FSTConfiguration fstConfiguration = NetworkModule.setup.getFstConfiguration();
			this.tcpObjectSocket = new TCPObjectSocket(socket, fstConfiguration);
			new AcceptMessageThread(this.tcpObjectSocket).start();
			Logger.info("Connected as client");
		} catch (IOException e) {
			Logger.error(e, "error while connecting to server");
		}
		return this;
	}

	@Override
	public void send(Serializable object) {
		NetworkModule.function.send(this.tcpObjectSocket, object);
	}

	@Override
	public void disconnect() {
		try {
			this.tcpObjectSocket.close();
		} catch (IOException e) {
			Logger.error(e, "error while disconnect");
		}
	}

}
