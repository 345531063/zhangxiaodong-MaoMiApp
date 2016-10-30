
 /**
 * 项目名称：    系统名称
 * 包名：              com.business.model.database
 * 文件名：         MySqlDBBinaryStreamManagerImpl.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-1-30-下午04:06:08
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.core.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;

import cn.com.yibin.maomi.core.util.ExceptionUtil;
import cn.com.yibin.maomi.core.util.WebUtil;


 /**
 * 类名称：     MySqlDBBinaryStreamManagerImpl
 * 类描述：     
 * 创建人：    zxd
 * 修改人：     zxd
 * 修改时间：2015-12-23 下午04:06:08
 * 修改备注：
 * @version 1.0.0
 **/

public class MySqlDBBinaryStreamManagerImpl implements
		DataBaseBinaryStreamManager {
    public static JdbcTemplate jdbcTemplate = null;
    public static JdbcTemplate getJdbcTemplate(){
    	return (JdbcTemplate)WebUtil.getApplicationContext().getBean("jdbcTemplate");
    }
    @Override
	public  void saveOrUpdateBinaryStream(final String prepareSql,final Object[] values,final InputStream in) throws Exception {   
		jdbcTemplate.execute(new PreparedStatementCreator(){
			@Override
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				PreparedStatement ps = conn.prepareStatement(prepareSql);
				for(int i=1;i<=values.length;i++)
				{
					ps.setObject(i, values[i]);
				}
				return ps;
			}
		}, new  PreparedStatementCallback<Integer>(){
			@Override
			public Integer doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				try {
					ps.setBinaryStream(values.length+1, in, in.available());
				} catch (IOException e) {
					e.printStackTrace();
					throw new SQLException(ExceptionUtil.getExceptionPrintInfo(e));
				}
				return ps.executeUpdate();
			}
		});
	}   
}
