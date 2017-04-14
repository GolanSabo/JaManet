package il.ac.shenkar.Controller;

import org.pcap4j.packet.Packet;

/**
 * This module is the main module of the application. it is in charge of sniffing any packet in the network.
 * It is also in charge of accumulating statistics and the forward the packets to the relevant 
 * module for processing.
 * 
 * @author Golan Sabo
 *
 */
public class Controller {
	//Private variables
	private static Controller instance = null;
	
	//private functions
	private Controller(){}
	
	//Public functions
	
	/**
	 * Returns an instance of Controller
	 * @return Controller
	 */
	public static Controller getInstance(){
		
		if(instance == null){
			instance = new Controller();
		}
		return instance;
	}

	public void receiveEvent(Packet packet) {
		
		
	}
	
	
	
}
