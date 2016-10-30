package cn.com.yibin.maomi.core.repository.dao;

import cn.com.yibin.maomi.core.model.Table;

public interface TableDao  extends RepositoryDao
{
	public void getTableInfo(final Table table, final boolean isLoadTreeData) throws Exception;
	public void getTableExportExcelInfo(final Table table, final boolean isLoadTreeData) throws Exception;
}
