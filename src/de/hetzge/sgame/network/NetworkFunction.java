package de.hetzge.sgame.network;

import java.io.Serializable;

import org.nustaq.net.TCPObjectSocket;
import org.pmw.tinylog.Logger;

public class NetworkFunction {

	public void send(TCPObjectSocket tcpObjectSocket, Serializable object) {
		try {
			tcpObjectSocket.writeObject(object);
			tcpObjectSocket.flush();
		} catch (Exception e) {
			Logger.error(e, "error while sending object");
		}
	}

}
