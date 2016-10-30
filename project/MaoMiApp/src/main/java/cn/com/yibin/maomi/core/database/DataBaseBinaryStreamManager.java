
 /**
 * 项目名称：    系统名称
 * 包名：              com.business.model.database
 * 文件名：         DataBaseBinaryStreamManager.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-1-30-下午04:00:55
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.core.database;

import java.io.InputStream;


 /**
 * 类名称：     DataBaseBinaryStreamManager
 * 类描述：     
 * 创建人：     Administrator
 * 修改人：     Administrator
 * 修改时间：2013-1-30 下午04:00:55
 * 修改备注：
 * @version 1.0.0
 **/

public interface DataBaseBinaryStreamManager 
{
	public  void saveOrUpdateBinaryStream(final String prepareSql, final Object[] values, final InputStream in) throws Exception;
}
