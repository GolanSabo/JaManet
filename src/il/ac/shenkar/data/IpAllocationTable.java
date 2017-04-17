package il.ac.shenkar.data;

import java.util.HashMap;

public class IpAllocationTable extends HashMap<String,IpTuple>{
	
	private static IpAllocationTable instance = null;

	private IpAllocationTable() {
		super();
	}
	
	public static IpAllocationTable getInstance(){
		if(instance == null)
			instance = new IpAllocationTable();
		return instance;
	}
	
//	@Override
//	public IpTuple get(Object mac) {
//		return super.get(mac);
//	}
	
	
	@Override
	public IpTuple get(Object ip) {
		return super.get(ip);
	}
}
