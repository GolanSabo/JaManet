package il.ac.shenkar.routing;

public enum MonitorMsgTypeEnum {
	MonitorReq(0x0030),MonitorRes(0x0031);
	private int value;
	 
	private MonitorMsgTypeEnum(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
