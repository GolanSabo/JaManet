package il.ac.shenkar.message;

import javolution.io.Struct;

public class Message {

	private static final int MAC_ADDR_LEN = 17;
	private static final int NAME_LENGTH = 16;
	private static final int IP_SIZE = 15;
	
	public static class MessageHeader extends Struct{
		public UTF8String srcMac = new UTF8String(MAC_ADDR_LEN);
		public UTF8String dstMac = new UTF8String(MAC_ADDR_LEN);
		public Struct struct = null;
		
		public MessageHeader(Struct struct){
			this.struct = struct;
		}
	}
	
	public static class NetworkDiscoveryMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0010;
		
		public UTF8String srcMac = new UTF8String(MAC_ADDR_LEN);
		public UTF8String ssId = new UTF8String(NAME_LENGTH);

	}
	
	public static class ConfMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0011;
		
		public Unsigned8 wifiMode = new Unsigned8(1);
		public Unsigned8 channel = new Unsigned8(1);
		public Unsigned8 routingProtocol = new Unsigned8(1);
		public UTF8String ip = new UTF8String(IP_SIZE);
		public Unsigned8 poolSize = new Unsigned8(1);
		public UTF8String netmask = new UTF8String(IP_SIZE);
	}
	
	public static class ClientConfAckStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0012;
		
		public UTF8String ip = new UTF8String(IP_SIZE);	
		public UTF8String srcName = new UTF8String(NAME_LENGTH);
	}
	
	public static class ServerConfAckStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0013;
	}
	
	public static class NewJoineeMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0014;
		
		public Unsigned16 seqNum = new Unsigned16(1);
		public UTF8String ip = new UTF8String(IP_SIZE);	
		public Unsigned8 poolSize = new Unsigned8(1);	
		public UTF8String newJoineeIp = new UTF8String(IP_SIZE);	
//		public Unsigned8 newJoineePoolSize = new Unsigned8(1);		
	}
	
	public static class IpPoolMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0020;
		public static final byte headerSize = 2;
		
		public Unsigned16 seqNum = new Unsigned16(1);
		public UTF8String[] poolTable = null;
		
		public IpPoolMsgStruct(int size){
			poolTable = new UTF8String[size];
			
			for(int i = 0; i < size; ++i)
				poolTable[i] = new UTF8String(2);
		}
		
	}
	
	public static class IpLeakMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0021;
	
		public Unsigned16 seqNum = new Unsigned16(1);
		public UTF8String firstIp = new UTF8String(IP_SIZE);
		public Unsigned8 poolSize = new Unsigned8(1);

	}
	
	public static class IpPoolRequestMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0022;
	
		public Unsigned16 seqNum = new Unsigned16(1);
		public UTF8String ip = new UTF8String(IP_SIZE);

	}
	
	public static class IpPoolResponseMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0023;
		
		public UTF8String destIp = new UTF8String(IP_SIZE);
		public UTF8String firstIp = new UTF8String(IP_SIZE);
		public Unsigned8 poolSize = new Unsigned8(1);
	}
	
	public static class IpPoolAckStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0024;
	
		public UTF8String firstIp = new UTF8String(IP_SIZE);
		public Unsigned8 poolSize = new Unsigned8(1);
		
	}
	
	public static class StatRequestMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0030;
		public static final byte headerSize = 3;
		
		public Unsigned16 seqNum = new Unsigned16(1);
		public Unsigned8 numOfHops = new Unsigned8(1);
		public UTF8String[] path = null;
		
		public StatRequestMsgStruct(int size){
			path = new UTF8String[size];
			for(int i = 0; i < size; ++i){
				path[i] = new UTF8String(IP_SIZE);
			}
		}
	}
	
	public static class StatResponeMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0031;
		
		public Unsigned16 totalMsgSent = new Unsigned16(1);
		public Unsigned16 numOfError = new Unsigned16(1);
		public Unsigned16 dataMsg = new Unsigned16(1);
		public Unsigned16 controlMsg = new Unsigned16(1);
		public Unsigned8 routingTableSize = new Unsigned8(1);
		public Unsigned8 pathLengh = new Unsigned8(1);
		
	}
	
	public static class MonitorMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0032;
		
		public Unsigned16 seqNum = new Unsigned16(1);
		public Unsigned8 routingProtocol = new Unsigned8(1);
		public Unsigned8 hour = new Unsigned8(1);
		public Unsigned8 minute = new Unsigned8(1);
		public Unsigned8 second = new Unsigned8(1);
		
	}
	
	public static class ElectionMsgStruct extends javolution.io.Struct{
		public static final short MSG_TYPE = 0x0033;
		
		public Unsigned16 seqNum = new Unsigned16(1);
		public Unsigned8 electionNum = new Unsigned8(1);
		public UTF8String ip = new UTF8String(IP_SIZE);
	}
	
//	public static class aodvRouteRequestStruct extends javolution.io.Struct{
//		public static final short MSG_TYPE = 0x0041;
//		
//		public Unsigned8  hopCount = new Unsigned8(1);
//		public Unsigned16 rreqId = new Unsigned16(2);
//		public UTF8String destIp = new UTF8String(IP_SIZE);
//		public Unsigned16 destSeqNum = new Unsigned16(1);
//		public UTF8String srcIp = new UTF8String(IP_SIZE);
//		public Unsigned16 srcSeqNum = new Unsigned16(1);
//	}
//
//	public static class aodvRouteResponseStruct extends javolution.io.Struct{
//		public static final short MSG_TYPE = 0x0042;
//		
//		public Unsigned8  hopCount = new Unsigned8(1);
//		public UTF8String destIp = new UTF8String(IP_SIZE);
//		public Unsigned16 destSeqNum = new Unsigned16(1);
//		public UTF8String srcIp = new UTF8String(IP_SIZE);
//		public Unsigned16 lifeTime = new Unsigned16(1);
//	}
//
//	public static class aodvRouteErrorStruct extends javolution.io.Struct{
//		public static final short MSG_TYPE = 0x0043;
//		
//		public UTF8String destIp = new UTF8String(IP_SIZE);
//		public Unsigned16 destSeqNum = new Unsigned16(1);
//	}
//	
//	public static class aodvRouteReplyAckStruct extends javolution.io.Struct{
//		public static final short MSG_TYPE = 0x0044;
//	}
	
}
