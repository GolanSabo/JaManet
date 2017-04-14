package il.ac.shenkar.Protocol;

import java.util.HashMap;
import java.util.Map;

import org.pcap4j.packet.namednumber.NamedNumber;

public class MyProtocol extends NamedNumber<Byte, MyProtocol>{
	
	public static final MyProtocol Monitor
	= new MyProtocol((byte)1, "Monitor");
	
	public static final MyProtocol OLSR_PACKET
	= new MyProtocol((byte)1, "OLSR PACKET");

	private static final long serialVersionUID = 3155818580398801532L;

	public MyProtocol(Byte value, String name) {
		super(value, name);
	}

	private static final Map<Byte, MyProtocol> registry
	= new HashMap<Byte, MyProtocol>();

	static {
		registry.put(Monitor.value(), Monitor);
	}

	public static MyProtocol getInstance(Byte value) {
		if (registry.containsKey(value)) {
			return registry.get(value);
		}
		else {
			return new MyProtocol(value, "unknown");
		}
	}

	public static MyProtocol register(MyProtocol version) {
		return registry.put(version.value(), version);
	}

	@Override
	public int compareTo(MyProtocol o) {
		return value().compareTo(o.value());
	}
}
