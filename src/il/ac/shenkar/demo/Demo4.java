package il.ac.shenkar.demo;

import java.net.Inet4Address;
import java.net.InetAddress;

import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;

public class Demo4 {
	public static void main(String[] args) throws Exception {
		try{
//			String strAddress = "192.168.1.7";
//			final Inet4Address address;
//			final Inet4Address address1;
//			try {
//				address = (Inet4Address)InetAddress.getByName(strAddress);
//				address1 = (Inet4Address)InetAddress.getByName("192.168.1.4");
//			} catch (UnknownHostException e1) {
//				throw new IllegalArgumentException("args[0]: " + strAddress);
//			}
//			UdpPacket.Builder UdpBuilder = new UdpPacket.Builder();
//			UdpBuilder.dstAddr(address)
//			.dstPort(UdpPort.ACI)
//			.payloadBuilder(new UnknownPacket.Builder().rawData(new byte[]{1,2,3,4,2,123,2,13,32,21,2,3}))
//			.correctChecksumAtBuild(true)
//			.srcPort(UdpPort.ACI)
//			.srcAddr(address1);
//			System.out.println("UDP PACKET " + UdpBuilder.build());
//
//			IpV4Packet.Builder ipv4b = new IpV4Packet.Builder();
//			ipv4b.version(IpVersion.IPV4)
//			.tos(IpV4Rfc791Tos.newInstance((byte)0))
//			.identification((short)100)
//			.ttl((byte)100)
//			.protocol(IpNumber.UDP)
//			.payloadBuilder(UdpBuilder)
//			.correctChecksumAtBuild(true)
//			.correctLengthAtBuild(true)
//			.srcAddr(address1)
//			.dstAddr(address);

//			System.out.println("IPV4 PACKET " + ipv4b.build());
			EthernetPacket.Builder eb = new EthernetPacket.Builder();
			EtherType type = new EtherType((short) 0x1111, "OLSR");
			EtherType.register(type);
			eb.type(type)
			.srcAddr(MacAddress.getByName("08:00:27:21:d4:ae", ":"))
			.dstAddr(MacAddress.getByName("08:00:27:7b:e6:bf", ":"))
			.payloadBuilder(new UnknownPacket.Builder().rawData(new byte[]{1,2,3,4,2,123,2,13,32,21,2,3}))
			.paddingAtBuild(true);
			System.out.println("ETHERNET BUILDER" + eb.toString());
			EthernetPacket pack = eb.build();
			System.out.println(pack.toString());

			PcapHandle handle4send;

			InetAddress addr = Inet4Address.getByName("192.168.1.4");
			PcapNetworkInterface nif  = Pcaps.getDevByAddress(addr);
			//		int snapLen = 65536;
			//		PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
			//		PcapHandle handle = nif.openLive(snapLen, mode, 0);
			handle4send = nif.openLive(65536, PromiscuousMode.PROMISCUOUS, 10);
			handle4send.sendPacket(eb.build());
			handle4send.close();
		}catch(Exception e){
			System.out.println("Exception " + e);
		}
	}
}
