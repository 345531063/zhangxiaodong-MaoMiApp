package cn.com.yibin.maomi.core.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import cn.com.yibin.maomi.core.file.BaseFileOperation;

public class FileUtil 
{
	   private static BaseFileOperation<? extends Object> baseFileOperation    ;
	   private static String                              uploadsStoreType     ;
	   private static String                              uploadsStoreCharset  ;
	   private static String                              uploadsStoreHost     ;
	   private static Integer                             uploadsStorePort     ;
	   private static String                              uploadsStoreUsername ;
	   private static String                              uploadsStorePassword ;
	   
//	   public  static BaseFileOperation<? extends Object> getFileUploadOperation()
//	   {
//		   if(null == uploadsStoreHost){
//			   uploadsStoreType     = ResourceUtil.getUploadsStoreType();
//			   uploadsStoreCharset  = ResourceUtil.getUploadsStoreCharset();
//			   uploadsStoreHost     = ResourceUtil.getUploadsStoreHost();
//			   uploadsStorePort     = ResourceUtil.getUploadsStorePort();
//			   uploadsStoreUsername = ResourceUtil.getUploadsStoreUsername();
//			   uploadsStorePassword = ResourceUtil.getUploadsStorePassword();
//		   }
//		   if(null == baseFileOperation)
//		   {
//
//			   switch(FileStoreType.valueOf(uploadsStoreType)){
//			   		case LOCAL :{
//			   			baseFileOperation = new LocalFileOperationImpl();
//			   			break;
//			   		}
//			   		case FTP :{
//			   			baseFileOperation = new FtpFileOperationImpl(uploadsStoreHost,uploadsStorePort,uploadsStoreUsername,uploadsStorePassword,uploadsStoreCharset);
//			   			break;
//			   		}
//			   		case SMB :{
//			   			baseFileOperation = new SmbFileOperationImpl(uploadsStoreHost,uploadsStoreUsername,uploadsStorePassword);
//			   			break;
//			   		}
//			   }
//		   }
//		   return baseFileOperation;
//	   }
	   
	   public static void deleteFile(File file)
	   { 
		   if(file.exists()){                    //判断文件是否存在
		    if(file.isFile()){                    //判断是否是文件
		     file.delete();                       //delete()方法 你应该知道 是删除的意思;
		    }else if(file.isDirectory()){              //否则如果它是一个目录
		     File files[] = file.listFiles();               //声明目录下所有的文件 files[];
		     for(int i=0;i<files.length;i++){            //遍历目录下所有的文件
		      deleteFile(files[i]);             //把每个文件 用这个方法进行迭代
		     } 
		    } 
		    file.delete(); 
		   }else{ 
		    System.out.println("所删除的文件不存在！"+'\n'); 
		   } 
		} 
	   public static String getFilePathString(String str)
	   {
           String reg0 = "\\\\+"; 
           String reg = "\\\\+|/+"; 
           String temp = str.trim().replaceAll(reg0, "/"); 
           temp = temp.replaceAll(reg, "/"); 
           if (temp.length() > 1 && temp.endsWith("/")) { 
                   temp = temp.substring(0, temp.length() - 1); 
           } 
           return temp; 
	   }
	   public static void deleteFile(String filePath)
	   {
		   deleteFile(new File(getFilePathString(filePath)));
	   }
	   public static String getChildrenFileNameJson(String filepath)
		{
			filepath = getFilePathString(filepath);
			StringBuffer json_sb = new StringBuffer("{");
			File f = new File(filepath);
			File []files = f.listFiles();
			int index = 0;
			for(File file : files)
			{
				if(file.isFile())
				{
					if(file.getName().indexOf(".svn")>-1)
					{
						continue;
					}
					if(1!=(++index))
					{
						json_sb.append(",");
					}
					json_sb.append("\"")
					        .append(file.getName())
					        .append("\":")
					        .append("\"")
					        .append(file.getName())
					        .append("\"");
				}
			}
			json_sb.append("}");
			return json_sb.toString();
		}
		/**
		 * @param directory  目录名
		 * @param filename   文件名
		 * @return  如果存在则返回绝地路径，不存在则返回null
		 */
	   public static String searchFileFromDirectory(File fileOrdirectory,String filename,AbstractFileSearchDeal adsd)
	   {
		  StringBuffer sb = new StringBuffer("");
		  searchFileFromDirectory(sb,fileOrdirectory,filename,adsd);   
		  return sb.toString();
	   }
	   /**
		 * @param directory  目录名
		 * @param filename   文件名
		 * @return  如果存在则返回绝地路径，不存在则返回null
		 */
	  public static void searchFileFromDirectory(StringBuffer sb,File fileOrdirectory,String filename,AbstractFileSearchDeal adsd)
	  {
		   if(!fileOrdirectory.exists())
		   {
			   return ;
		   }
		   if(sb.length()>=1)
		   {
			   return;
		   }
		   if(fileOrdirectory.isDirectory())
		   {
			   if(adsd.fileSearchCallBack(fileOrdirectory))
			   {
			      File[] files = fileOrdirectory.listFiles();
			      for(int index=0;index<files.length;index++)
			      {
			    	  searchFileFromDirectory(sb,files[index],filename,adsd);
			      }
			   }
		   }
		   else if(fileOrdirectory.isFile())
		   {
			   if(adsd.fileSearchCallBack(fileOrdirectory,filename))
			   {
				   sb.append(fileOrdirectory.getAbsolutePath());
			   }
		   }
	  }
	  /**
	 * @param allFileMap 同名覆盖前一个文件信息   key:文件名  value:对应文件
	 * @param filePath   Map<String,File> 
	 */
	public static void allFileToMap(Map<String,File> allFileMap,File searchFile)
	  {
		  if(searchFile.exists())
		  {
			  if(searchFile.isDirectory())
			  {
				  File []files = searchFile.listFiles();
				  for(int fIndex = 0 ; fIndex < files.length;fIndex++)
				  {
					  File file = files[fIndex];
					  allFileToMap(allFileMap,file);
				  }
			  }
			  else if(searchFile.isFile())
			  {
				  allFileMap.put(searchFile.getName(), searchFile);
			  }
		  }
	  }
	   public static void saveAllFilePathToMap(File rootFile,Map<String,String> filesMap)
	   {
		   if(rootFile.isFile())
		   {
			   String fileName = rootFile.getName();
			   String filePath = rootFile.getAbsolutePath();
			   if(fileName.indexOf(".svn")>-1)
			   {
				   return;
			   }
			   filesMap.put(fileName, filePath);
		   }
		   else if(rootFile.isDirectory())
		   {
			   File []files = rootFile.listFiles();
			   for(int i=0;i<files.length;i++)
			   {
				   File file = files[i];
				   if(file.getName().indexOf(".svn")>-1)
				   {
					 continue;   
				   }
				   saveAllFilePathToMap(file,filesMap); 
			   }
		   }
	   }
	   abstract class AbstractFileSearchDeal 
	   {
	      public abstract boolean fileSearchCallBack(File currentSearchingDirectory);
	      public abstract boolean fileSearchCallBack(File currentSearchingFile,String findFileName);
	   }
	   
	
	/** 
	     * 从包package中获取所有的Class 
	     *  
	     * @param pack 
	     * @return 
	     */  
	    public static Set<Class<?>> getClasses(String pack) {  
	  
	        // 第一个class类的集合  
	        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();  
	        // 是否循环迭代  
	        boolean recursive = true;  
	        // 获取包的名字 并进行替换  
	        String packageName = pack;  
	        String packageDirName = packageName.replace('.', '/');  
	        // 定义一个枚举的集合 并进行循环来处理这个目录下的things  
	        Enumeration<URL> dirs;  
	        try {  
	            dirs = Thread.currentThread().getContextClassLoader().getResources(  
	                    packageDirName);  
	            // 循环迭代下去  
	            while (dirs.hasMoreElements()) {  
	                // 获取下一个元素  
	                URL url = dirs.nextElement();  
	                // 得到协议的名称  
	                String protocol = url.getProtocol();  
	                // 如果是以文件的形式保存在服务器上  
	                if ("file".equals(protocol)) {  
	                    //System.err.println("file类型的扫描");  
	                    // 获取包的物理路径  
	                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
	                    // 以文件的方式扫描整个包下的文件 并添加到集合中  
	                    findAndAddClassesInPackageByFile(packageName, filePath,  
	                            recursive, classes);  
	                } else if ("jar".equals(protocol)) {  
	                    // 如果是jar包文件  
	                    // 定义一个JarFile  
	                    //System.err.println("jar类型的扫描");  
	                    JarFile jar;  
	                    try {  
	                        // 获取jar  
	                        jar = ((JarURLConnection) url.openConnection())  
	                                .getJarFile();  
	                        // 从此jar包 得到一个枚举类  
	                        Enumeration<JarEntry> entries = jar.entries();  
	                        // 同样的进行循环迭代  
	                        while (entries.hasMoreElements()) {  
	                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
	                            JarEntry entry = entries.nextElement();  
	                            String name = entry.getName();  
	                            // 如果是以/开头的  
	                            if (name.charAt(0) == '/') {  
	                                // 获取后面的字符串  
	                                name = name.substring(1);  
	                            }  
	                            // 如果前半部分和定义的包名相同  
	                            if (name.startsWith(packageDirName)) {  
	                                int idx = name.lastIndexOf('/');  
	                                // 如果以"/"结尾 是一个包  
	                                if (idx != -1) {  
	                                    // 获取包名 把"/"替换成"."  
	                                    packageName = name.substring(0, idx)  
	                                            .replace('/', '.');  
	                                }  
	                                // 如果可以迭代下去 并且是一个包  
	                                if ((idx != -1) || recursive) {  
	                                    // 如果是一个.class文件 而且不是目录  
	                                    if (name.endsWith(".class")  
	                                            && !entry.isDirectory()) {  
	                                        // 去掉后面的".class" 获取真正的类名  
	                                        String className = name.substring(  
	                                                packageName.length() + 1, name  
	                                                        .length() - 6);  
	                                        try {  
	                                            // 添加到classes  
	                                            /*classes.add(Class.forName(packageName + '.'  
	                                                            + className));  */
	                                        	 classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));    
	                                        } catch (ClassNotFoundException e) {  
	                                            // log.error("添加用户自定义视图类错误 找不到此类的.class文件");  
	                                            e.printStackTrace();  
	                                        }  
	                                    }  
	                                }  
	                            }  
	                        }  
	                    } catch (IOException e) {  
	                        // log.error("在扫描用户定义视图时从jar包获取文件出错");  
	                        e.printStackTrace();  
	                    }  
	                }  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return classes;  
	    }  
	    /** 
	         * 以文件的形式来获取包下的所有Class 
	         *  
	         * @param packageName 
	         * @param packagePath 
	         * @param recursive 
	         * @param classes 
	         */  
	        public static void findAndAddClassesInPackageByFile(String packageName,  
	                String packagePath, final boolean recursive, Set<Class<?>> classes) {  
	            // 获取此包的目录 建立一个File  
	            File dir = new File(packagePath);  
	            // 如果不存在或者 也不是目录就直接返回  
	            if (!dir.exists() || !dir.isDirectory()) {  
	                // log.warn("用户定义包名 " + packageName + " 下没有任何文件");  
	                return;  
	            }  
	            // 如果存在 就获取包下的所有文件 包括目录  
	            File[] dirfiles = dir.listFiles(new FileFilter() {  
	                // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
	                public boolean accept(File file) {  
	                    return (recursive && file.isDirectory())  
	                            || (file.getName().endsWith(".class"));  
	                }  
	            });  
	            // 循环所有文件  
	            for (File file : dirfiles) {  
	                // 如果是目录 则继续扫描  
	                if (file.isDirectory()) {  
	                    findAndAddClassesInPackageByFile(packageName + "."  
	                            + file.getName(), file.getAbsolutePath(), recursive,  
	                            classes);  
	                } else {  
	                    // 如果是java类文件 去掉后面的.class 只留下类名  
	                    String className = file.getName().substring(0,  
	                            file.getName().length() - 6);  
	                    try {  
	                        // 添加到集合中去  
	                        //classes.add(Class.forName(packageName + '.' + className));  
	                                             //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净  
	                                            classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));    
	                                    } catch (ClassNotFoundException e) {  
	                        // log.error("添加用户自定义视图类错误 找不到此类的.class文件");  
	                        e.printStackTrace();  
	                    }  
	                }  
	            }  
	        }
	        
	    	public void saveFileFromInputStream(InputStream stream, String path, String filename) throws IOException {
	    		FileOutputStream fs = null;
	    		try{
		    		fs = new FileOutputStream(path + "/" + filename);
		    		byte[] buffer = new byte[1024 * 1024];
		    		//int bytesum = 0;
		    		int byteread = 0;
		    		while ((byteread = stream.read(buffer)) != -1) {
		    			//bytesum += byteread;
		    			fs.write(buffer, 0, byteread);
		    			fs.flush();
		    		}
	    		}
	    		finally{
	    			if(null != fs){
	    				fs.close();
	    				fs = null;
	    			}
	    			if(null != stream){
	    				stream.close();
	    				stream = null;
	    			}
	    			
	    		}
	    		
	    	}    
	    	

	        /**
	         * 以行为单位读取文件
	         * @throws IOException 
	         */
	        public static String readFileByLines(String fileName, String encoding) throws IOException {
	            File file = new File(fileName);
	            InputStreamReader read = null;
	            BufferedReader reader = null;
	            StringBuffer fileString = new StringBuffer("");
	            try {
	            	read = new InputStreamReader(new FileInputStream(file), encoding);
	            	reader= new BufferedReader(read);
	                String tempString = null;
	                // 一次读入一行，直到读入null为文件结束
	                while ((tempString = reader.readLine()) != null) {
	                	fileString.append(tempString);
	                }
	                reader.close();
	            } catch (IOException e) {
	            	throw e;
	            } finally {
	                if (reader != null) {
	                    try {
	                        reader.close();
	                    } catch (IOException e1) {
	                    }
	                    reader = null;
	                }
	                if (read != null) {
	                    try {
	                        read.close();
	                    } catch (IOException e1) {
	                    }
	                    read = null;
	                }
	            }
	    		return fileString.toString();
	        }
	        
	        
	    	/**
	    	 * Write content to a fileName with the destEncoding 写文件。如果此文件不存在就创建一个。
	    	 * 
	    	 * @param content
	    	 *            String
	    	 * @param fileName
	    	 *            String
	    	 * @param destEncoding
	    	 *            String
	    	 * @throws FileNotFoundException
	    	 * @throws IOException
	    	 */
	    	public static void writeFile(String content, String fileName, String destEncoding) throws FileNotFoundException, IOException {
	    		File file = null;
	    		try {
	    			file = new File(fileName);
	    			if (!file.exists()) {
	    				if (file.createNewFile() == false) {
	    					throw new IOException("create file '" + fileName + "' failure.");
	    				}
	    			}
	    			if (file.isFile() == false) {
	    				throw new IOException("'" + fileName + "' is not a file.");
	    			}
	    			if (file.canWrite() == false) {
	    				throw new IOException("'" + fileName + "' is a read-only file.");
	    			}
	    		} finally {
	    			// we dont have to close File here
	    		}
	    		BufferedWriter out = null;
	    		try {
	    			FileOutputStream fos = new FileOutputStream(fileName);
	    			out = new BufferedWriter(new OutputStreamWriter(fos, destEncoding));
	    			out.write(content);
	    			out.flush();
	    		} catch (FileNotFoundException fe) {
	    			throw fe;
	    		} catch (IOException e) {
	    			throw e;
	    		} finally {
	    			try {
	    				if (out != null)
	    					out.close();
	    			} catch (IOException ex) {
	    			}
	    		}
	    	}
	    	
	    	public static Set<File> getAllFiles(String path) {
	    		Set<File> set = new HashSet<File>();
	    		readDirFile(path, set);
	    		return set;

	    	}
	    	
	    	
	    	public static Set<File> readDirFile(String path, Set<File> set) {
	    		File file = new File(path);
	    		if (!file.isDirectory()) {
	    			set.add(file);
	    		} else if (file.isDirectory()) {
	    			String[] filelist = file.list();
	    			for (int i = 0; i < filelist.length; i++) {
	    				File readfile = new File(path + "\\" + filelist[i]);
	    				if (!readfile.isDirectory()) {
	    					set.add(readfile);
	    				} else if (readfile.isDirectory()) {
	    					readDirFile(path + "\\" + filelist[i], set);
	    				}
	    			}
	    		}
	    		return set;

	    	}       
	    	public static String getYearMonthDayPathByString(String dateStr,String dateFormat){
	    		Date date = DateUtil.getDateByFormat(dateStr, dateFormat);
	    		Calendar currentDate = Calendar.getInstance();
	    		currentDate.setTime(date);
	    		NumberFormat nf = NumberFormat.getInstance();
	    		nf.setMinimumIntegerDigits(2);
	    		return currentDate.get(Calendar.YEAR)+"/"+nf.format(currentDate.get(Calendar.MONTH)+1)+"/"+nf.format(currentDate.get(Calendar.DAY_OF_MONTH));
	    	}
	    	public static InputStream getInputStream(String sourceString){
	    		ByteArrayInputStream stream = new ByteArrayInputStream(sourceString.getBytes());
	    		return stream;
	    	}
	    	public static void mkdirs(String filePath){
	    		File f  = new File(filePath);
	    		File pf = f.getParentFile();
	    		if(!pf.exists()){
	    			pf.mkdirs();
	    		}
	    	}
	    	public static void main(String[] args) throws Exception{
//	    		ResourceUtil.init();
//	    		//long currentMili    = System.currentTimeMillis();
//	    		BaseFileOperation<? extends Object> baserOperation= FileUtil.getFileUploadOperation();
//	    		
//	    		for(int i =0 ;i<300;i++){
//	    			String commandUrl   = "/test/test"+(i+1)+".jpg";
//	    			try{
//			    		///ByteArrayOutputStream out     = new ByteArrayOutputStream();
//			    		//baserOperation.uploadFile(ResourceUtil.getFileUploadDataPath()+commandUrl,new FileInputStream("F:\\智能家居服务器逻辑设计.jpg") ,null);
//			    		//baserOperation.downloadFile(ResourceUtil.getFileUploadDataPath()+commandUrl,out ,null);
//	    				baserOperation.removeFile(ResourceUtil.getFileUploadDataPath()+commandUrl);
//	    				System.out.println("正常次数：：："+i);
//	    			}catch(Exception e){
//	    				System.out.println("出现异常次数：：："+(i+1));
//	    			}
	    	//	}
	    	}
	     
}
