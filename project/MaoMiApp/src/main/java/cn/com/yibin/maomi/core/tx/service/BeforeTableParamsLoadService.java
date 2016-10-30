package cn.com.yibin.maomi.core.tx.service;

import java.util.Map;

import cn.com.yibin.maomi.core.model.Table;

public interface BeforeTableParamsLoadService {
  public void beforeTableParamsLoad(Table table, Map<String, String> beforeParamsMap) throws Exception;
}
