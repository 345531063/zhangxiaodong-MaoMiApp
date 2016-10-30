
 /**
 * 项目名称：    系统名称
 * 包名：              com.business.dao
 * 文件名：         JedisDataType.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-9-8-上午10:22:00
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.cache.redis;


 /**
 * 类名称：     JedisDataType
 * 类描述：     
 * 创建人：     Administrator
 * 修改人：     Administrator
 * 修改时间：2013-9-8 上午10:22:00
 * 修改备注：
 * @version 1.0.0
 **/

public enum JedisDataType {
	
	SESSION_REDIS_CACHE("session","会话设置");
	
	private  final  String name;
	private  final  String code;
	
	private JedisDataType(String name, String code) {
		this.name = name;
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	
}
