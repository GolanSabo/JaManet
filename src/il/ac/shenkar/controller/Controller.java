package il.ac.shenkar.controller;

import il.ac.shenkar.Monitor.Monitor;
import il.ac.shenkar.Monitor.MonitorRequestMessage;
import il.ac.shenkar.Monitor.MonitorResponseMessage;
import il.ac.shenkar.data.DNSTable;
import il.ac.shenkar.data.IpTuple;
import il.ac.shenkar.message.Message.ClientConfAckStruct;
import il.ac.shenkar.message.Message.ConfMsgStruct;
import il.ac.shenkar.message.Message.MessageHeader;
import il.ac.shenkar.message.Message.NewJoineeMsgStruct;
import il.ac.shenkar.message.Message.ServerConfAckStruct;
import il.ac.shenkar.message.ParserCommandInterface.ClientConfAckParams;
import il.ac.shenkar.message.ParserCommandInterface.ConfMsgParams;
import il.ac.shenkar.message.ParserCommandInterface.MonitorMsgParams;
import il.ac.shenkar.message.ParserCommandInterface.NetworkDiscoveryMsgParams;
import il.ac.shenkar.message.ParserCommandInterface.NewJoineeMsgParams;
import il.ac.shenkar.message.ParserCommandInterface.StatRequestMsgParams;
import il.ac.shenkar.message.ParserCommandInterface.StatResponeMsgParams;
import il.ac.shenkar.routing.RoutingProtocolEnum;
import il.ac.shenkar.system.Assembler;
import il.ac.shenkar.system.managers.IpManager;

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
	private DNSTable dnsTable = null;
	private String ssId = null;
	private IpManager ipManager = null;
	private RoutingProtocolEnum protocol = null;
	private String myMac = null;
	//private functions
	private Controller(){
		ipManager = IpManager.getInstance();
		dnsTable = DNSTable.getInstance();
	}
	
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

	public void receivedNetworkDiscoveryEvent(NetworkDiscoveryMsgParams param) {
		
		if(param.ssId == null || param.ssId == ssId){
			
			ConfMsgStruct struct = new ConfMsgStruct();
			IpTuple ipTuple = IpManager.getInstance().allocateIp();
			//TODO: get real channel value
			struct.channel.set((byte)0x01);
			struct.ip.set(ipTuple.getIp());
			struct.netmask.set(ipManager.getNetMask());
			struct.poolSize.set(ipTuple.getPoolSize());
			struct.routingProtocol.set((short)protocol.ordinal());
			
			MessageHeader header = new MessageHeader(struct);
			header.dstMac.set(param.srcMac);
			header.srcMac.set(myMac);
			
			Assembler.getInstance().assemble(struct.MSG_TYPE, header);
		}
	}

	public void receivedConfMsgEvent(ConfMsgParams param) {
		try {
			ipManager.setIp(param.ip);
			//TODO: Configure network
			ipManager.setPoolSize(param.poolSize);
			if(param.routingProtocol == 0)
				protocol = RoutingProtocolEnum.AODV;
			else
				protocol = RoutingProtocolEnum.OLSR;
			
			ClientConfAckStruct struct = new ClientConfAckStruct();
			struct.ip.set(ipManager.getIp());
			//TODO: put real name from conf
			struct.srcName.set("Golan");

			MessageHeader header = new MessageHeader(struct);
			header.dstMac.set(param.srcMac);
			header.srcMac.set(myMac);
			
			Assembler.getInstance().assemble(struct.MSG_TYPE, header);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void receivedClientConfAckParamsEvent(ClientConfAckParams param) {
		dnsTable.put(param.ip, param.srcName);
		ipManager.updateMyPoolForAllocation(param.ip);
		
		ServerConfAckStruct struct = new ServerConfAckStruct();
		MessageHeader header = new MessageHeader(struct);
		header.dstMac.set(param.srcMac);
		header.srcMac.set(myMac);
		
		Assembler.getInstance().assemble(struct.MSG_TYPE, header);
		
		NewJoineeMsgStruct struct1 = new NewJoineeMsgStruct();
		struct1.ip.set(ipManager.getIp());
		struct1.newJoineeIp.set(param.ip);
		struct1.poolSize.set((short) ipManager.getPoolSize());
		
		//TODO: give real number to sequence number
		struct1.seqNum.set(1);
		
		Assembler.getInstance().assemble(struct1.MSG_TYPE, header);
	}

	public void receivedNewJoineeMsgEvent(NewJoineeMsgParams param) {
		//TODO: check sequence number
		
		ipManager.updateOrAddTuple(param.ip, param.poolSize);
		ipManager.updateOrAddTuple(param.newJoineeIp, param.poolSize);
		
		NewJoineeMsgStruct struct = new NewJoineeMsgStruct();
		struct.ip.set(param.ip);
		struct.newJoineeIp.set(param.ip);
		struct.poolSize.set(param.poolSize);
		
		//TODO: give real number to sequence number
		struct.seqNum.set(++param.seqNum);
		
		MessageHeader header = new MessageHeader(struct);
		header.dstMac.set(param.srcMac);
		header.srcMac.set(myMac);
		
		Assembler.getInstance().assemble(struct.MSG_TYPE, header);
	}

	public void receivedMonitorMsgEvent(MonitorMsgParams param) {
		
		if(param.routingProtocol != protocol.ordinal()){
			//TODO : switch routing protocol
		}
		
			
		
	}
	
	/*added my Sagi Maday*/
	
	public void receiveStatRequestMsgParams(StatRequestMsgParams param){
		int tempNumOfHops = param.numOfHops & 0xff;
		String pathAsString = "";
		for(String s : param.path)
		{
			pathAsString+=s;
		}
		if(param.numOfHops == 0)
		{
			MonitorResponseMessage monitorResponseMessage = Monitor.getInstance()
					.StartMonitorResponse(pathAsString, param.path.length);
		}
		else
		{
			MonitorRequestMessage monitorRequestMessage = Monitor.getInstance()
					.UpdateMonitorRequest(pathAsString, param.path.length
							, String.valueOf(param.seqNum), tempNumOfHops);
		}
	}
	
	//TO-DO: add "String path" to the message structure, Optional: add "int pathLength" and numOfHops
	public void receiveStatResponeMsgParams(StatResponeMsgParams param){
		/*need to add reverse path to StatResponeMsgParams structure*/
		String[] reversePath = {"I","love","Manchester","United"};
		String pathAsString = "";
		for(String s : /*param.*/reversePath)
		{
			pathAsString+=s;
		}
		if(/*param.*/reversePath[0] == IpManager.getInstance().getIp())
		{
			Monitor.getInstance().DecideProtocolByStatistics(/*give all statistics values*/);
		}
		else
		{
			MonitorResponseMessage monitorResponseMessage = Monitor.getInstance().UpdateMonitorResponse(pathAsString, /*param.*/reversePath.length
					,param.numOfError, param.totalMsgSent, param.dataMsg
					,param.controlMsg, param.pathLengh, param.routingTableSize);
		}
	}

}
