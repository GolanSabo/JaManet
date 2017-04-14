package il.ac.shenkar.demo;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.Packet.Header;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.TcpPacket.Builder;

public class Demo {

	public static void main(String[] args) {
		InetAddress addr;
		try {
			addr = InetAddress.getByName("192.168.1.4");
			PcapNetworkInterface nif  = Pcaps.getDevByAddress(addr);
			int snapLen = 65536;
			PromiscuousMode mode = PromiscuousMode.PROMISCUOUS;
			PcapHandle handle = nif.openLive(snapLen, mode, 0);

			while(true){
				Packet packet = handle.getNextPacket();
				if (packet == null) {
					continue;
				}
//				Header head = packet.getHeader();
				Builder bb = new Builder();
				bb.dstAddr(InetAddress.getByName("192.168.1.7"));
				bb.srcAddr(addr);
				bb.fin(true);
				handle.sendPacket(bb.build());
//				Header head = IpV4Header
//				handle.sendPacket(packet1);
//				EthernetHeader ethHead = (EthernetHeader)head;
//				EtherType t = ethHead.getType();
				System.out.println(packet.toString());

				//				org.pcap4j.packet.Packet.Builder
				//				IpV4Packet ipV4Packet = packet.get(IpV4Packet.class);
				//				if(ipV4Packet != null){
				//					Inet4Address srcAddr = ipV4Packet.getHeader().getSrcAddr();
				//					System.out.println(ipV4Packet.toString());
				//				}
			}

		} catch (PcapNativeException | NotOpenException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
