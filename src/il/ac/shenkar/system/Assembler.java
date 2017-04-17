package il.ac.shenkar.system;

import java.nio.ByteBuffer;

import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.packet.EthernetPacket;
import org.pcap4j.packet.Packet;
import org.pcap4j.packet.UnknownPacket;
import org.pcap4j.packet.namednumber.EtherType;
import org.pcap4j.util.MacAddress;

import il.ac.shenkar.communication.Sniffer;
import il.ac.shenkar.message.Message.MessageHeader;

public class Assembler {
	private static Assembler instance = null;
	
	private Assembler(){

	}
	
	public static Assembler getInstance(){
		if(instance == null)
			instance = new Assembler();
		return instance;
	}
	
	public void assemble(short msgType, MessageHeader header){
		
		EthernetPacket.Builder eb = new EthernetPacket.Builder();
		EtherType type = new EtherType(msgType, "My Protocol Message");
		
        byte[] data = new byte[header.struct.size()];
        ByteBuffer buffer = header.struct.getByteBuffer();

        buffer.rewind();
        buffer.get(data);

		eb.type(type)
		.srcAddr(MacAddress.getByName(header.srcMac.get(), ":"))
		.dstAddr(MacAddress.getByName(header.dstMac.get(), ":"))
		.payloadBuilder(new UnknownPacket.Builder().rawData(data))
		.paddingAtBuild(true);
		
		try {
			Sniffer.getInstance().send(eb.build());
			
		} catch (PcapNativeException | NotOpenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	

}
