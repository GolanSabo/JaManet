package il.ac.shenkar.message;

public interface ParserCommandInterface {
	
	public class NetworkDiscoveryMsgParams{
		public String dstMac;
		public String srcMac;
		public String ssId;
	}
	
	public class ConfMsgParams{
		public String dstMac;
		public String srcMac;
		public byte wifiMode;
		public byte channel;
		public byte routingProtocol;
		public String ip;
		public byte poolSize;
		public String netmask;
	}
	
	public class ClientConfAckParams{
		public String dstMac;
		public String srcMac;
		public String ip;
		public String srcName;
	}
	
	public class NewJoineeMsgParams{
		public short seqNum;
		public String ip;	
		public byte poolSize;	
		public String newJoineeIp;	
//		public byte newJoineePoolSize;
	}
	
	public class IpPoolMsgParams{
		public short seqNum;
		public String[] poolTable;
	}
	
	public class IpLeakMsgParams{
		public short seqNum;
		public String firstIp;
		public byte poolSize;
	}
	
	public class IpPoolRequestMsgParams{
		public short seqNum;
		public String ip;
	}
	
	public class IpPoolResponseMsgParams{
		public String destIp;
		public String firstIp;
		public byte poolSize;
	}
	
	public class IpPoolAckParams{
		public String firstIp;
		public byte poolSize;
	}
	
	public class StatRequestMsgParams{
		public short seqNum;
		public byte numOfHops;
		public String[] path;
	}
	
	public class StatResponeMsgParams{
		public short totalMsgSent;
		public short numOfError;
		public short dataMsg;
		public short controlMsg;
		public byte routingTableSize;
		public byte pathLengh;
	}
	
	public static class MonitorMsgParams{
		public short seqNum;
		public byte routingProtocol;
		public byte hour;
		public byte minute;
		public byte second;
	}
	
	public class ElectionMsgParams{
		public short seqNum;
		public byte electionNum;
		public String ip;
	}
	
	
}
