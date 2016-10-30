package cn.com.yibin.maomi.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MathUtil 
{
	   public static boolean isEmptyNumber(String value)
	   {
		   return "0".equals(StringUtil.emptyOpt(value,"0"));
	   }
	   public static boolean isNumberic(String numStr)
	   {
		  return numStr.matches("^[0-9]+\\.?[0-9]*$");
	   }
	   public static double parseDecimal(double number,int scale)
	   {
		   return new Double(decimal(number,scale)).doubleValue();
	   }
	   public static double parseDecimal(String numberStr,int scale) throws Exception
	   {
		   NumberFormat  nf = NumberFormat.getNumberInstance();
		   nf.setRoundingMode(RoundingMode.HALF_UP);
		   nf.setMaximumFractionDigits(scale);
		   return new Double(decimal(new Double(numberStr).doubleValue(),2)).doubleValue();
	   }
	   public static String decimal(double number,int scale)
	   {
		   NumberFormat  nf = NumberFormat.getNumberInstance();
		   nf.setMaximumFractionDigits(scale);
		   nf.setRoundingMode(RoundingMode.HALF_UP);
		   return nf.format(number).replaceAll(",","");
	   }
	   public static BigDecimal max(BigDecimal bd1 , BigDecimal bd2){
		   if(null == bd1){
			   return bd2;
		   }
		   if(null == bd2){
			   return bd1;
		   }
		   return ( 0 < bd1.compareTo(bd2) ) ?   bd1 : bd2 ;
	   }
	   public static BigDecimal min(BigDecimal bd1 , BigDecimal bd2){
		   if(null == bd1){
			   return bd2;
		   }
		   if(null == bd2){
			   return bd1;
		   }
		   return ( 0 < bd1.compareTo(bd2) ) ?   bd2 : bd1 ;
	   }
	   public static Integer max(Integer bd1 , Integer bd2){
		   if(null == bd1){
			   return bd2;
		   }
		   if(null == bd2){
			   return bd1;
		   }
		   return ( 0 < bd1.compareTo(bd2) ) ?   bd1 : bd2 ;
	   }
	   public static Integer min(Integer bd1 , Integer bd2){
		   if(null == bd1){
			   return bd2;
		   }
		   if(null == bd2){
			   return bd1;
		   }
		   return ( 0 < bd1.compareTo(bd2) ) ?   bd2 : bd1 ;
	   }
	   public static String fillBeforeZero(int number,int scale)
	   {
		   String str = String.format("%0"+scale+"d", number);      
		   return str;
	   }
	   private static BigDecimal recursionXNPV(String[] values,String[] dates,double rate,String dateFormat,int index,StringBuffer fx,StringBuffer dx) throws Exception
	   {
		   if(index == values.length)
		   {
			   return new BigDecimal("0.000");
		   }
		   else
		   {
			  SimpleDateFormat sdf = new  SimpleDateFormat(dateFormat);
			  Date firstDate = sdf.parse(dates[0]);
			  Date currentDate = sdf.parse(dates[index]);
			  long milSeconds = (currentDate.getTime()-firstDate.getTime());
			  BigDecimal intervalDays = new BigDecimal(milSeconds).divide(new BigDecimal((1000*60*60*24)),20,BigDecimal.ROUND_HALF_EVEN);
			  BigDecimal currentValue = null;
			  currentValue = intervalDays.divide(new BigDecimal(365),20,BigDecimal.ROUND_HALF_EVEN);
			  currentValue = new BigDecimal(values[index]).divide(new BigDecimal(Math.pow((1+rate),currentValue.doubleValue())),20,BigDecimal.ROUND_HALF_EVEN);
			  if(milSeconds == 0)
			  {
				  fx.append(values[index]);
				  dx.append(0);
			  }
			  else
			  {
				  fx.append("+"+values[index]+"*Math.pow(1+rate,-1.00*"+intervalDays+"/365)");
				  dx.append("+"+values[index]+"*(-1.00*"+intervalDays+"/365)*Math.pow(1+rate,-1.00*"+intervalDays+"/365-1)");
			  }
			  return  currentValue.add(recursionXNPV(values,dates,rate,dateFormat,++index,fx,dx));
		   }
	   }
	   private static void recursionXIRR(String[] values,String[] dates,double rate,String dateFormat,int index,BigDecimal[] rs_values) throws Exception
	   {
		   if(index == values.length)
		   {
			   return;
		   }
		   else
		   {
			  try {
				  SimpleDateFormat sdf = new  SimpleDateFormat(dateFormat);
				  Date firstDate = sdf.parse(dates[0]);
				  Date currentDate = sdf.parse(dates[index]);
				  long milSeconds = (currentDate.getTime()-firstDate.getTime());
				  BigDecimal intervalDays = new BigDecimal(milSeconds).divide(new BigDecimal((1000*60*60*24)),20,BigDecimal.ROUND_HALF_EVEN);
				  BigDecimal currentValue = null;
				  currentValue = intervalDays.divide(new BigDecimal(365),20,BigDecimal.ROUND_HALF_EVEN);
				  currentValue = new BigDecimal(values[index]).divide(new BigDecimal(Math.pow((1+rate),currentValue.doubleValue())),20,BigDecimal.ROUND_HALF_EVEN);
				  if(milSeconds == 0)
				  {
					  rs_values[0] = rs_values[0].add(new BigDecimal(values[index]));
					  rs_values[1] = rs_values[1].add(new BigDecimal("0"));
				  }
				  else
				  {
					  rs_values[0] = rs_values[0].add(new BigDecimal(values[index]).multiply(new BigDecimal(Math.pow(1+rate,-1.00*intervalDays.doubleValue()/365))));
					  rs_values[1] = rs_values[1].add(new BigDecimal(values[index]).multiply(new BigDecimal(-1.00*intervalDays.doubleValue()/365).multiply(new BigDecimal(Math.pow(1+rate,-1.00*intervalDays.doubleValue()/365-1)))));
				  }
				  recursionXIRR(values,dates,rate,dateFormat,++index,rs_values);
			} catch (Exception e) {
				return ;
			}
		   }
	   }
	   public static String XNPV(String[] values,String[] dates,double rate,String dateFormat) throws Exception
	   {
		   StringBuffer fx = new StringBuffer("");
		   StringBuffer dx = new StringBuffer("");
		   BigDecimal recursion = recursionXNPV(values,dates,rate,dateFormat,0,fx,dx);
		   BigDecimal result = recursion;
		   System.out.println("fx(原函数):"+fx);
		   System.out.println("dx(导函数):"+dx);
		   return result.toString();
	   }
	   public static double XIRR(String[] values,String[] dates,double rate,String dateFormat) throws Exception
	   {
		   BigDecimal[] rs_values = new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
		   double x1 = rate;
		   double dr = 1;
		   double x2;
		   int k = 0;
		   double H = 0.00001/100;
		   double fx,fpx;
		   do
		   {
			   if(++k>200){break;};   //%记录迭代次数
			   rs_values = new BigDecimal[]{new BigDecimal("0.00"),new BigDecimal("0.00")};
			   recursionXIRR(values,dates,x1,dateFormat,0,rs_values);
			   fx= rs_values[0].doubleValue();//函数
			   fpx= rs_values[1].doubleValue();//导函数
			   if((Math.abs(fx) < H ))
		       {
				  System.out.println("满足条件");
		    	  break;
		       }
			   if((Double.isNaN(fx))||(Double.isInfinite(fx)))
			   {
				  System.out.println("数据异常");
				  x1=0;
				  break;
			   }
			   if((fpx == 0)||(Double.isNaN(fpx))||(Double.isInfinite(fx)))
			   {
				   System.out.println("数据异常");
				   x1=0;
				   break;
			   }
		       x2=x1; // %保留原上一次迭代值
		       x1=x1-fx/fpx; //%牛顿法 
		       dr=x1-x2;   // %两次迭代值差
		   }while(Math.abs(dr)>=H); 
		   return x1;
	   }
	   public static void main(String []args) throws Exception
	   {
//		   String values[] = new String[]{"-10000","5000","7000"};
//		   String dates[] = new String[]{"2008-01-01","2008-02-01","2008-03-01"};
//		   System.out.println("结果:"+XNPV(values,dates,0.1,"yyyy-MM-dd"));
//		   System.out.println("结果:"+XIRR(values,dates,0.1,"yyyy-MM-dd"));
		   System.out.println(MathUtil.fillBeforeZero(13,6));
	   } 
}
