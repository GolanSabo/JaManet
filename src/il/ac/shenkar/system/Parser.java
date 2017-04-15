package il.ac.shenkar.system;

import java.nio.ByteBuffer;

import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.EthernetPacket.EthernetHeader;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.UdpPacket;

import il.ac.shenkar.message.Message.ClientConfAckStruct;
import il.ac.shenkar.message.Message.ConfMsgStruct;
import il.ac.shenkar.message.Message.ElectionMsgStruct;
import il.ac.shenkar.message.Message.IpLeakMsgStruct;
import il.ac.shenkar.message.Message.IpPoolAckStruct;
import il.ac.shenkar.message.Message.IpPoolMsgStruct;
import il.ac.shenkar.message.Message.IpPoolRequestMsgStruct;
import il.ac.shenkar.message.Message.IpPoolResponseMsgStruct;
import il.ac.shenkar.message.Message.MonitorMsgStruct;
import il.ac.shenkar.message.Message.NetworkDiscoveryMsgStruct;
import il.ac.shenkar.message.Message.NewJoineeMsgStruct;
import il.ac.shenkar.message.Message.ServerConfAckStruct;
import il.ac.shenkar.message.Message.StatRequestMsgStruct;
import il.ac.shenkar.message.Message.StatResponeMsgStruct;
import il.ac.shenkar.message.ParserCommandInterface;

public class Parser implements ParserCommandInterface{
	
	private static Parser instance;
	
	private Parser(){
		
	}
	
	public static Parser getInstance(){
		if(instance == null)
			instance = new Parser();
		return instance;
	}
	
	public void receiveEvent(Packet packet){
		EthernetPacket etherPacket = packet.get(EthernetPacket.class);
		if(etherPacket == null)
			return;
		EthernetHeader header = etherPacket.getHeader();

		switch(header.getType().value()){
		case NetworkDiscoveryMsgStruct.MSG_TYPE:
			parseNetworkDiscoveryMsg(packet.getRawData());
			break;
		case ConfMsgStruct.MSG_TYPE:
			parseConfMsg(packet.getRawData());
			break;
		case ClientConfAckStruct.MSG_TYPE:
			parseClientConfAckMsg(packet.getRawData());
			break;
		case ServerConfAckStruct.MSG_TYPE:
			// no data inside
			break;
		case NewJoineeMsgStruct.MSG_TYPE:
			parseNewJoineeMsg(packet.getRawData());
			break;
		case IpPoolMsgStruct.MSG_TYPE:
			parseIpPoolMsg(packet.getRawData());
			break;
		case IpLeakMsgStruct.MSG_TYPE:
			parseIpLeakMsg(packet.getRawData());
			break;
		case IpPoolRequestMsgStruct.MSG_TYPE:
			parseIpPoolRequestMsg(packet.getRawData());
			break;
		case IpPoolResponseMsgStruct.MSG_TYPE:
			parseIpPoolResponseMsg(packet.getRawData());
			break;
		case IpPoolAckStruct.MSG_TYPE:
			parseIpPoolAck(packet.getRawData());
			break;
		case StatRequestMsgStruct.MSG_TYPE:
			parseStatRequestMsg(packet.getRawData());
			break;
		case StatResponeMsgStruct.MSG_TYPE:
			parseStatResponseMsg(packet.getRawData());
			break;
		case MonitorMsgStruct.MSG_TYPE:
			parseMonitorMsg(packet.getRawData());
			break;
		case ElectionMsgStruct.MSG_TYPE:
			parseElectionMsg(packet.getRawData());
			break;
		default:
//			TcpPacket tcpPacket = packet.get(TcpPacket.class);
			TcpPacket tcpPacket;
			UdpPacket udpPacket;
			if((tcpPacket = packet.get(TcpPacket.class)) != null){
				
			}else if((udpPacket = packet.get(UdpPacket.class)) != null){
				
			}
		}
	}

	private void parseNetworkDiscoveryMsg(byte[] rawData) {
		NetworkDiscoveryMsgParams params = new NetworkDiscoveryMsgParams();
		NetworkDiscoveryMsgStruct struct = new NetworkDiscoveryMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		params.srcMac = struct.srcMac.get();
		params.srcName = struct.srcName.get();
		params.ssId = struct.ssId.get();
	}
	
	private void parseConfMsg(byte[] rawData) {
		ConfMsgParams params = new ConfMsgParams();
		ConfMsgStruct struct = new ConfMsgStruct();

		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		params.channel = (byte) struct.channel.get();
		params.ip = struct.ip.get();
		params.netmask = struct.netmask.get();
		params.poolSize = (byte)struct.poolSize.get();
		params.routingProtocol = (byte)struct.routingProtocol.get();
		params.wifiMode = (byte)struct.wifiMode.get();
	}
	
	private void parseClientConfAckMsg(byte[] rawData) {
		ClientConfAckParams params = new ClientConfAckParams();
		ClientConfAckStruct struct = new ClientConfAckStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		params.ip = struct.ip.get();
	}
	
	private void parseNewJoineeMsg(byte[] rawData) {
		NewJoineeMsgParams params = new NewJoineeMsgParams();
		NewJoineeMsgStruct struct = new NewJoineeMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		params.ip = struct.ip.get();
		params.newJoineeIp = struct.newJoineeIp.get();
		params.newJoineePoolSize = (byte) struct.newJoineePoolSize.get();
		params.seqNum = (short) struct.seqNum.get();
		params.poolSize = (byte) struct.poolSize.get();
	}
	
	private void parseIpPoolMsg(byte[] rawData) {
		if(rawData.length < IpPoolMsgStruct.headerSize){
			//the msg is not in the requeired size
			return;
		}
		
		IpPoolMsgParams params = new IpPoolMsgParams();
		IpPoolMsgStruct struct = new IpPoolMsgStruct(rawData.length - IpPoolMsgStruct.headerSize);
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		params.seqNum = (short) struct.seqNum.get();
		params.poolTable = new String[struct.poolTable.length];
	
		for(int i = 0; i < struct.poolTable.length; ++i){
			params.poolTable[i] = struct.poolTable[i].get();
		}
	}
	
	private void parseIpLeakMsg(byte[] rawData) {
		IpLeakMsgParams params = new IpLeakMsgParams();
		IpLeakMsgStruct struct = new IpLeakMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.firstIp = struct.firstIp.get();
		params.poolSize = (byte) struct.poolSize.get();
		params.seqNum = (short) struct.seqNum.get();
	}
	
	private void parseIpPoolRequestMsg(byte[] rawData) {
		IpPoolRequestMsgParams params = new IpPoolRequestMsgParams();
		IpPoolRequestMsgStruct struct = new IpPoolRequestMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.ip = struct.ip.get();
		params.seqNum = (short) struct.seqNum.get();
	}

	private void parseIpPoolResponseMsg(byte[] rawData) {
		IpPoolResponseMsgParams params = new IpPoolResponseMsgParams();
		IpPoolResponseMsgStruct struct = new IpPoolResponseMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.destIp = struct.destIp.get();
		params.firstIp = struct.firstIp.get();
		params.poolSize = (byte) struct.poolSize.get();
	}
	
	private void parseIpPoolAck(byte[] rawData) {
		IpPoolAckParams params = new IpPoolAckParams();
		IpPoolAckStruct struct = new IpPoolAckStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.firstIp = struct.firstIp.get();
		params.poolSize = (byte) struct.poolSize.get();
	}
	
	private void parseStatRequestMsg(byte[] rawData) {
		
		if(rawData.length < StatRequestMsgStruct.headerSize){
			//the msg is not in the requeired size
			return;
		}
		
		StatRequestMsgParams params = new StatRequestMsgParams();
		StatRequestMsgStruct struct = new StatRequestMsgStruct(rawData.length - StatRequestMsgStruct.headerSize);
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.numOfHops = (byte) struct.numOfHops.get();
		params.seqNum = (short) struct.seqNum.get();
		
		for(int i = 0; i < struct.path.length; ++i){
			params.path[i] = struct.path[i].get();
		}
	}
	
	private void parseStatResponseMsg(byte[] rawData) {
		StatResponeMsgParams params = new StatResponeMsgParams();
		StatResponeMsgStruct struct = new StatResponeMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.controlMsg = (short) struct.controlMsg.get();
		params.dataMsg = (short) struct.dataMsg.get();
		params.numOfError = (short) struct.numOfError.get();
		params.pathLengh = (byte) struct.pathLengh.get();
		params.routingTableSize = (byte) struct.routingTableSize.get();
		params.totalMsgSent = (short) struct.totalMsgSent.get();
	}
	
	private void parseMonitorMsg(byte[] rawData) {
		MonitorMsgParams params = new MonitorMsgParams();
		MonitorMsgStruct struct = new MonitorMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.hour = (byte) struct.hour.get();
		params.minute = (byte) struct.minute.get();
		params.second = (byte) struct.second.get();
		params.routingProtocol = (byte) struct.routingProtocol.get();
		params.seqNum = (short) struct.seqNum.get();
	}
	
	private void parseElectionMsg(byte[] rawData) {
		ElectionMsgParams params = new ElectionMsgParams();
		ElectionMsgStruct struct = new ElectionMsgStruct();
		
		struct.setByteBuffer(ByteBuffer.wrap(rawData),0);
		
		params.electionNum = (byte) struct.electionNum.get();
		params.ip = struct.ip.get();
		params.seqNum = (short) struct.seqNum.get();
		
	}
}
