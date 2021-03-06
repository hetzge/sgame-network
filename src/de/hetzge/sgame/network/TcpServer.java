package de.hetzge.sgame.network;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import org.nustaq.net.TCPObjectSocket;
import org.nustaq.serialization.FSTConfiguration;
import org.pmw.tinylog.Logger;

class TcpServer implements IF_Network {

	private final List<TCPObjectSocket> clients = new LinkedList<>();

	public TcpServer start() {
		try {
			ServerSocket serverSocket = new ServerSocket(NetworkModule.settings.getPort());
			new WaitForClientsThreads(serverSocket).start();
		} catch (IOException e) {
			Logger.error(e, "error while starting server socket");
		}
		return this;
	}

	@Override
	public void send(Serializable object) {
		for (TCPObjectSocket tcpObjectSocket : this.clients) {
			NetworkModule.function.send(tcpObjectSocket, object);
		}
	}

	@Override
	public void disconnect() {
		for (TCPObjectSocket tcpObjectSocket : this.clients) {
			try {
				tcpObjectSocket.close();
			} catch (IOException e) {
				Logger.error(e, "error while disconnecting from client");
			}
		}
	}

	private class WaitForClientsThreads extends Thread {

		private final ServerSocket serverSocket;

		public WaitForClientsThreads(ServerSocket serverSocket) {
			super("WaitForClients");
			this.serverSocket = serverSocket;
		}

		@Override
		public void run() {
			boolean running = true;
			while (running) {
				try {
					Logger.info("Wait for client connection ...");
					Socket socket = this.serverSocket.accept();
					FSTConfiguration fstConfiguration = NetworkModule.setup.getFstConfiguration();
					TCPObjectSocket tcpObjectSocket = new TCPObjectSocket(socket, fstConfiguration);
					TcpServer.this.clients.add(tcpObjectSocket);
					new AcceptMessageThread(tcpObjectSocket).start();
					Logger.info("Client connected");
				} catch (IOException e) {
					Logger.error(e, "error while accepting new connection");
				}
			}
		}

	}

}
