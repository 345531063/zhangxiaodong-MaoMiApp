package cn.com.yibin.maomi.core.util;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolUtil {
	//private static ThreadPoolExecutor executorService = null;
	private static ThreadPoolExecutor executorService     = null;
	private static ThreadPoolExecutor minaExecutorService = null;
	
	public static void threadExecute(Runnable runnable){
		getThreadPool();
		executorService.execute(runnable);
	}
	
//	private static void minaThreadExecute(Runnable runnable){
//		getMinaThreadPool();
//		minaExecutorService.execute(runnable);
//	}
//	
//	private static void shutDownThreadPool(){
//		if(null != executorService){
//			executorService.shutdown();
//		}
//	}
//	private static void shutDownMinaThreadPool(){
//		if(null != minaExecutorService){
//			minaExecutorService.shutdown();
//		}
//	}
	
	private static ExecutorService getThreadPool(){
		if(null == executorService){
			synchronized(ThreadPoolUtil.class){
				if(null == executorService){
			      //executorService  = new ThreadPoolExecutor( 500 , 20000 , 60L , TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
			      //executorService  = new ThreadPoolExecutor( 2500 , 2500 , 0L , TimeUnit.MILLISECONDS , new LinkedBlockingQueue<Runnable>(2500)); 
			      executorService    = new ThreadPoolExecutor(150, 20000, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new ThreadPoolExecutor.DiscardOldestPolicy());
			      //executorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
				}
			}
		}
		return executorService;
	}
	
	public static ExecutorService getMinaThreadPool(){
		if(null == minaExecutorService){
			synchronized(ThreadPoolUtil.class){
				if(null == minaExecutorService){
					//minaExecutorService  = new ThreadPoolExecutor( 500 , 20000 , 60L , TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
					//minaExecutorService  = new ThreadPoolExecutor( 2500 , 2500 , 0L , TimeUnit.MILLISECONDS , new LinkedBlockingQueue<Runnable>(2500)); 
					minaExecutorService    = new ThreadPoolExecutor(500, 500, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
					//minaExecutorService.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
				}
			}
		}
		return minaExecutorService;
	}
}
