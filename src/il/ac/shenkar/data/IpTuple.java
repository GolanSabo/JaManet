package il.ac.shenkar.data;

import il.ac.shenkar.system.managers.IpManager;
import il.ac.shenkar.system.scheduler.ScheduleInterface;
import il.ac.shenkar.system.scheduler.ScheduleTtl;

public class IpTuple implements ScheduleInterface, Comparable<IpTuple>{

	public enum Status{VALID, NONVALID, LEAKED}
	private String ip;
	private short poolSize;
	private Status status;
	private ScheduleTtl ttl = null;
	private final int TTLTime = 5 * 1000;
	
	public IpTuple(String ip, short poolSize) {
		this.ip = ip;
		this.poolSize = poolSize;
		status = Status.VALID;
		ttl = new ScheduleTtl(null, TTLTime, this);
	}

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

	public void renewTtl() {
		ttl.cancel();
		status = Status.VALID;
		ttl = new ScheduleTtl(null, TTLTime, this);
	}

	@Override
	public void timerEvent(Object id) {
		if(status.equals(Status.VALID))
			status = Status.NONVALID;
		else if(status.equals(Status.NONVALID)){
			status = Status.LEAKED;
			new ScheduleTtl(this, 0, IpManager.getInstance());
		}
	}

	@Override
	public int compareTo(IpTuple tuple) {
        return this.ip.compareTo(tuple.ip);
	}

}
