package semantics.java.utils;
import java.security.MessageDigest;

public class SecurityUtils {
	public static byte[] getMD5(String str){
		MessageDigest md = null;
		try
		{
			md = MessageDigest.getInstance("MD5");
		}
		catch(Exception e) {
			//do nothing, MD5 is built in, no Exception can occur
		}
		md.update(str.getBytes());
		return md.digest();
	}
}
