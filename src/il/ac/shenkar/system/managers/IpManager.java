package il.ac.shenkar.system.managers;

import il.ac.shenkar.data.IpAllocationTable;
import il.ac.shenkar.data.IpTuple;
import il.ac.shenkar.system.scheduler.ScheduleInterface;
import il.ac.shenkar.utils.IpUtils;
import il.ac.shenkar.utils.Utils;

public class IpManager implements ScheduleInterface{
	//Private variables
	private static IpManager instance = null;
	private IpAllocationTable ipTable = null;
	private String myIp = null;
	private String netMask = null;
	private boolean isInAllocationProcess = false;

	private short poolSize = 0;


	private IpManager(){
	}
	/**
	 * Returns an instance of IpManager
	 * @return IpManager
	 */
	public static IpManager getInstance(){

		if(instance == null){
			instance = new IpManager();
		}
		return instance;
	}
	/**
	 * This function should be called if the node is a server who creates the server
	 */
	public void start(short poolSize, String ip){
		ipTable = IpAllocationTable.getInstance();
		this.poolSize = poolSize;
		myIp = ip;
	}
	/**
	 * This function should be called if the node is a client who's looking for network
	 */
	public void start(){
		ipTable = IpAllocationTable.getInstance();
	}

//	public IpAllocationTable getIpTable() {
//		return ipTable;
//	}
//
//	public void setIpTable(IpAllocationTable ipTable) {
//		this.ipTable = ipTable;
//	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(short poolSize) {
		this.poolSize = poolSize;
	}
	
	public String getIp() {
		return myIp;
	}
	
	public void setIp(String ip) throws Exception {
		if(ip != null)
			throw new Exception("IP already configured!");
		this.myIp = ip;
	}
	
	public String getNetMask() {
		return netMask;
	}
	
	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public IpTuple allocateIp(){
		short newPoolSize;
		String firstIp;

		newPoolSize = (short) (poolSize / 2);
		firstIp = IpUtils.getNewIp(myIp, newPoolSize);
		
		return new IpTuple(firstIp, newPoolSize);
	}
	
	public void updateMyPoolForAllocation(String ip){
		short newPoolSize = (short) (poolSize / 2);
		
		IpTuple ipTuple = new IpTuple(ip, newPoolSize);
		ipTable.put(ip, ipTuple);
		
		IpTuple myIpTuple = ipTable.get(myIp);
		myIpTuple.setPoolSize((short)(myIpTuple.getPoolSize() - newPoolSize));
		ipTable.put(ip,myIpTuple);
	}
	
	public void updateOrAddTuple(String ip, short poolSize){
		IpTuple ipTuple = new IpTuple(ip, poolSize);
		ipTable.put(ip, ipTuple);
	}
	
	
	@Override
	public void timerEvent(Object id) {
		IpTuple leakedTuple = (IpTuple) id;
		
		ipTable = (IpAllocationTable) Utils.sortByValue(ipTable);
		//TODO: get the buddy by sorting the map
		
	}
	
	
}
