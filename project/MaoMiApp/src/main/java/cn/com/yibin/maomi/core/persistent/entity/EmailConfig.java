
 /**
 * 项目名称：    系统名称
 * 包名：              com.business.email
 * 文件名：         EmailBase.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-12-2-上午11:52:27
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.core.persistent.entity;

import javax.xml.bind.annotation.XmlRootElement;

import cn.com.yibin.maomi.core.annotation.ApPrimaryKey;
import cn.com.yibin.maomi.core.annotation.ApTable;
import cn.com.yibin.maomi.core.annotation.ApTableColumn;


 /**
 * 类名称：     EmailBase
 * 类描述：     
 * 创建人：     Administrator
 * 修改人：     Administrator
 * 修改时间：2013-12-2 上午11:52:27
 * 修改备注：
 * @version 1.0.0
 **/

@XmlRootElement(name="emailConfig")
@ApTable(name="T_EMAIL_CONFIG",comment="邮件配置表",sequenceName="SEQ_T_EMAIL_CONFIG")
public class EmailConfig {
	
	@ApTableColumn(name="ID_",comment="主键编号")
	@ApPrimaryKey
    private Long id ;
	
	@ApTableColumn(name="HOST_",comment="邮箱主机")
	private String  host;
	
	@ApTableColumn(name="PORT_",comment="邮箱端口号")
	private Integer port;
	
	@ApTableColumn(name="FROM_USER_",comment="邮箱发送者")
	private String  fromUser;
	
	@ApTableColumn(name="FROM_USER_PASSWORD_",comment="邮箱发送者密码")
	private String  fromUserPassword;
	
	@ApTableColumn(name="FROM_USER_EMAIL_ADDRESS_",comment="邮箱发送者邮件地址")
	private String  fromUserEmailAddress;
	
	@ApTableColumn(name="FROM_USER_REAL_NAME_",comment="邮箱发送者真实身份")
	private String  fromUserRealName;
	
	public Long getId() {
		return id;
	}
	public String getHost() {
		return host;
	}
	public Integer getPort() {
		return port;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getFromUser() {
		return fromUser;
	}
	public String getFromUserPassword() {
		return fromUserPassword;
	}
	public String getFromUserEmailAddress() {
		return fromUserEmailAddress;
	}
	public String getFromUserRealName() {
		return fromUserRealName;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public void setFromUserPassword(String fromUserPassword) {
		this.fromUserPassword = fromUserPassword;
	}
	public void setFromUserEmailAddress(String fromUserEmailAddress) {
		this.fromUserEmailAddress = fromUserEmailAddress;
	}
	public void setFromUserRealName(String fromUserRealName) {
		this.fromUserRealName = fromUserRealName;
	}
}
