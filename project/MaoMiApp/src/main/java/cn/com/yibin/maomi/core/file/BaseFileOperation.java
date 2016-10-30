
 /**
 * 项目名称：    系统名称
 * 包名：              com.business.model.file
 * 文件名：         BaseFileOperation.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-3-31-上午11:18:18
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.core.file;

import java.io.InputStream;
import java.io.OutputStream;


 /**
 * 类名称：     BaseFileOperation
 * 类描述：     
 * 创建人：     Administrator
 * 修改人：     Administrator
 * 修改时间：2013-3-31 上午11:18:18
 * 修改备注：
 * @version 1.0.0
 **/

public interface BaseFileOperation<T> {
	   public  static final int BUFFEREDSIZE = 1024;
	   public  void downloadFile(String downloadedFileFullPath, OutputStream out, FileProcess fileProcess)  throws Exception;
	   public  void uploadFile(String uploadedFileFullPath, InputStream in, FileProcess fileProcess)  throws Exception;
	   public  void removeFile(String uploadedFileFullPath)  throws Exception;
	   public  void readInputStreamToOutputStream(String inputFileFullPath, OutputStream out)  throws Exception;
	   public  boolean  isFileExist(String filePath) throws Exception;
}
