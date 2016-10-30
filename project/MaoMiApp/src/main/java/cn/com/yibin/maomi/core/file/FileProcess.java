
 /**
 * 项目名称：    系统名称
 * 包名：              com.business.model
 * 文件名：         FileProcess.java
 * 版本信息：    1.0.0
 * 创建日期：     2013-3-13-下午12:24:16
 * Copyright：2013XX公司-版权所有
 **/

package cn.com.yibin.maomi.core.file;

import cn.com.yibin.maomi.core.util.MathUtil;


 /**
 * 类名称：     FileProcess
 * 类描述：     
 * 创建人：     Administrator
 * 修改人：     Administrator
 * 修改时间：2013-3-13 下午12:24:16
 * 修改备注：
 * @version 1.0.0
 **/

public class FileProcess 
{
    private  long totalSize = 1L; 
    private  long uploadedSize = 0L;
    private  long downloadedSize = 0L;
    private  boolean uploadedFinish = false;
    
    //private  long uploadedMiliSeconds;
    // private  long downloadedMiliSeconds;
   
    public String getUploadedPercent(){
    	try{
    		return MathUtil.decimal(uploadedSize*1.0/totalSize*100, 2);
    	}catch(Exception e){
    		return "0";
    	}
    }
    public String getDownloadedPercent(){
		try{
    		return MathUtil.decimal(downloadedSize*1.0/totalSize*100, 2);
    	}catch(Exception e){
    		return "0";
    	}
    }
	public long getTotalSize() {
		return totalSize;
	}
	public long getUploadedSize() {
		return uploadedSize;
	}
	public long getDownloadedSize() {
		return downloadedSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public void setUploadedSize(long uploadedSize) {
		this.uploadedSize = uploadedSize;
	}
	public void setDownloadedSize(long downloadedSize) {
		this.downloadedSize = downloadedSize;
	}
	public boolean getUploadedFinish() {
		return uploadedFinish;
	}
	public void setUploadedFinish(boolean uploadedFinish) {
		this.uploadedFinish = uploadedFinish;
	}
   
}
