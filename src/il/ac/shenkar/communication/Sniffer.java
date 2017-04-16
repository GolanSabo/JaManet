package il.ac.shenkar.communication;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;

import il.ac.shenkar.system.Dispatcher;
import il.ac.shenkar.system.Parser;
import il.ac.shenkar.system.QueueObject;

/**
 * This module is in charge of capturing packets and forward them to the controller for processing
 * 
 * @author Golan
 *
 */
public class Sniffer {
	// Instance of Sniffer (Sniffer implements singleton design pattern
	private static Sniffer instance = null;

	// The address of the network interface
	private InetAddress addr;

	// An instance of the network interface
	private PcapNetworkInterface nif;

	// Mode of the network packet capturing
	private final PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;

	// Maximum size of data (at a time)
	private final int snapLen = 65536;

	// The packet handler (send, receive etc...)
	private PcapHandle handleInput;
	private PcapHandle handleOuput;

	//private constructor
	private Sniffer(){
		//TODO: get data from conf file
		try {
			addr = InetAddress.getByName("192.168.1.4");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns an instance of Sniffer
	 * @return Sniffer
	 */
	public static Sniffer getInstance(){
		if(instance == null)
			instance = new Sniffer();
		return instance;
	}

	/**
	 * Start function is in charge of initiating the needed parameters and start the Sniffer's job
	 */
	public void start(){
		try {
			nif = Pcaps.getDevByAddress(addr);
			sniff();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The main function of this class - it's in charge of sniffing packets in the network.
	 * @throws PcapNativeException - if an error occurs in the pcap native library.
	 * @throws NotOpenException - if this PcapHandle is not open.
	 */
	private void sniff() throws PcapNativeException, NotOpenException{
		handleInput = nif.openLive(snapLen, mode, 0);
		while(true){
			Packet packet = handleInput.getNextPacket();
			if (packet == null) {
				continue;
			}
			Dispatcher.getInstance().insert(new PacketQueueObject(packet));
		}

	}
	
	public void send(Packet packet) throws PcapNativeException, NotOpenException{
		handleOuput.sendPacket(packet);
	}


	protected class PacketQueueObject implements QueueObject{
		private Packet packet;

		public PacketQueueObject(Packet pack){
			packet = pack;
		}

		@Override
		public void dispatch() {
			Parser.getInstance().receiveEvent(packet);
		}
	}
}
