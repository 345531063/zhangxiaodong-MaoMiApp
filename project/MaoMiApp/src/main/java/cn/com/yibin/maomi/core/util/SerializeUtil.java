/**
 * 项目名称：    系统名称
 * 包名：              com.kernal.utils
 * 文件名：         SerializeUtil.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-9-8-上午10:04:12
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	// 序列化
	public static byte[] serialize(Object object) {
		ObjectOutputStream objectOutputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			byte[] bytes = byteArrayOutputStream.toByteArray();
			return bytes;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != byteArrayOutputStream){
				try {
					byteArrayOutputStream.close();
					byteArrayOutputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != objectOutputStream){
				try {
					objectOutputStream.close();
					objectOutputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	// 反序列化
	@SuppressWarnings("unchecked")
	public static <TYPE> TYPE deserialize(byte[] bytes,Class<TYPE> clazz) {
		ByteArrayInputStream byteArrayOutputStream = null;
		ObjectInputStream objectInputStream  = null;
		try {
			byteArrayOutputStream = new ByteArrayInputStream(bytes);
			objectInputStream = new ObjectInputStream(
					byteArrayOutputStream);
			return (TYPE)objectInputStream.readObject();
		} catch (Exception e) {
			LogUtil.error(SerializeUtil.class,"deserialize exception");
		}finally{
			if(null != objectInputStream){
				try {
					objectInputStream.close();
					objectInputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != byteArrayOutputStream){
				try {
					byteArrayOutputStream.close();
					byteArrayOutputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	// 反序列化
	public static Object deserialize(byte[] bytes) {
		ByteArrayInputStream byteArrayOutputStream = null;
		ObjectInputStream objectInputStream  = null;
		try {
			byteArrayOutputStream = new ByteArrayInputStream(bytes);
		    objectInputStream = new ObjectInputStream(
					byteArrayOutputStream);
			return objectInputStream.readObject();
		} catch (Exception e) {
			LogUtil.error(SerializeUtil.class,"deserialize exception");
		}finally{
			if(null != objectInputStream){
				try {
					objectInputStream.close();
					objectInputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null != byteArrayOutputStream){
				try {
					byteArrayOutputStream.close();
					byteArrayOutputStream = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}