package il.ac.shenkar.data;

public class IpTuple {
//	private String mac;
	private String ip;
	private short poolSize;
	
//	public IpTuple(String mac, String ip, short poolSize) {
//		this.mac = mac;
//		this.ip = ip;
//		this.poolSize = poolSize;
//	}
	
	public IpTuple(String ip, short poolSize) {
		this.ip = ip;
		this.poolSize = poolSize;
	}

//	public String getMac() {
//		return mac;
//	}
//
//	public void setMac(String mac) {
//		this.mac = mac;
//	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public short getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(short poolSize) {
		this.poolSize = poolSize;
	}
	
	
	
	
}
