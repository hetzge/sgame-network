package de.hetzge.sgame.network;

import org.nustaq.net.TCPObjectSocket;

public interface IF_Dispatch {

	void dispatch(Object object, TCPObjectSocket socket);

}
