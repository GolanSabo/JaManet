package il.ac.shenkar.data;

import java.util.HashMap;

public class DNSTable extends HashMap<String, String> {
	private static DNSTable instance = null;

	private DNSTable() {
		super();
	}
	
	public static DNSTable getInstance(){
		if(instance == null)
			instance = new DNSTable();
		return instance;
	}

	@Override
	public String put(String ip, String nickName) {
		// TODO Auto-generated method stub
		return super.put(ip, nickName);
	}
	
	@Override
	public String get(Object ip) {
		return super.get(ip);
	}

}
