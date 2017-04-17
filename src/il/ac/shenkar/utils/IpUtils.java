package il.ac.shenkar.utils;

import java.net.InetAddress;

public class IpUtils {

	private static int getLast3Digit(String ip) throws NumberFormatException{
		String strIp = ip.substring(ip.lastIndexOf(".") + 1, ip.length());
		return Integer.parseInt(strIp);
	}

	public static String getNewIp(String ip, int addressToAdd)throws NumberFormatException{
		int digits;

		digits = getLast3Digit(ip);
		String newIp = ip.substring(0,ip.lastIndexOf(".") + 1);
		digits += addressToAdd;
		if(digits > 255)
			throw new NumberFormatException("After adding address range IP too large!");
		
		newIp += digits;

		return newIp;
	}
}