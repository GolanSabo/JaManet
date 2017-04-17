package il.ac.shenkar.routing;

public enum RoutingProtocolEnum {
	AODV((byte)1),
	OLSR((byte)2);
	
    private final byte protocol;       

    private RoutingProtocolEnum(byte protocol) {
    	this.protocol = protocol;
    }


}
