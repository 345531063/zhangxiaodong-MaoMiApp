package cn.com.yibin.maomi.core.util;

/**
 *@author zxd
 *@createDate 2016-3-25 下午05:23:51 
 **/
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {

	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	protected static MessageDigest messagedigest = null;
	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			System.err.println(MD5Util.class.getName()
					+ "初始化失败，MessageDigest不支持MD5Util。");
			nsaex.printStackTrace();
		}
	}

	public static String getFileMD5String(File file) throws IOException {
		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			FileChannel ch = in.getChannel();
			MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
					file.length());
			messagedigest.update(byteBuffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(null != in){
				in.close();
				in = null;
			}
		}
		return bufferToHex(messagedigest.digest());
	}

	private static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	private static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}
    
	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
   
	public static boolean checkPasswordNoSalt(String password, String md5PwdStr) {
		return getMD5EncodedPasswordNoSalt(password).equals(md5PwdStr);
	}	
	public static boolean checkPasswordWithSalt(String password,String md5PwdStr,Object salt) {
		return getMD5EncodedPasswordWithSalt(password,salt).equals(md5PwdStr);
	}	
	
	public static String getMD5EncodedPasswordNoSalt(String str) {
		return getMD5String(str);
	}
	public static String getMD5EncodedPasswordWithSalt(String str,Object salt) {
		String optSalt = StringUtil.emptyOpt(salt);
		if(!optSalt.isEmpty()){
			str+="["+salt+"]";
		}
		return getMD5String(str);
	}
	public static void main(String []args){//123456[18612187220]
		System.out.println(":::"+MD5Util.getMD5EncodedPasswordWithSalt("123456", 43L));
	}
}