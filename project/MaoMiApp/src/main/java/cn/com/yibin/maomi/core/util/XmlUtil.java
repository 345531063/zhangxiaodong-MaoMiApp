package cn.com.yibin.maomi.core.util;//package cn.com.yibin.maomi.core.util;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.StringReader;
//import java.util.List;
//
//import org.jdom.Attribute;
//import org.jdom.CDATA;
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.input.SAXBuilder;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;
//import org.xml.sax.InputSource;
//
///**
// * @author tracywindy
// *
// */
//public class XmlUtil 
//{  
//   /**********************************************************************/
//	//本地资源线程
//   private static final ThreadLocal<XMLOutputter> tl_xop  = new ThreadLocal<XMLOutputter>();
//   private static final ThreadLocal<Element>      tl_el   = new ThreadLocal<Element>();
//   private static final ThreadLocal<CDATA>        tl_cdel = new ThreadLocal<CDATA>();
//   private static final ThreadLocal<SAXBuilder>   tl_saxb = new ThreadLocal<SAXBuilder>();
//   private static final ThreadLocal<Document>     tl_doc  = new ThreadLocal<Document>();
//   /*********************************************************************/
//   /*********************************************************************/
//   //本地轻量级资源
//   private static Element orgin=null;
//   private static CDATA  cd_orgin = null;
//   //本地重量级资源
//   private static  XMLOutputter outp =null;
//   private static  SAXBuilder saxb =null; 
//   private static  Document doc=null; 
//   /********************************************************************/
//   //关闭本地所有资源
//   public static void closeLocalResources()
//   {
//	   tl_xop.remove();
//	   tl_el.remove();
//	   tl_doc.remove();
//	   tl_saxb.remove();
//   }
//   /************************************************************/
//   //根据相应的Document实例生成XML文档（采用jdom生成方式）
//   /**
// * @param doc
// * @param xmlFilePath
// */
//public static void generateXML(Document doc,String xmlFilePath)
//   {
//	   outp=getXMLOutputterInstance();
//	   try {
//		     FileOutputStream fos=new FileOutputStream(xmlFilePath);
//		     outp.output(doc,fos);
//		     fos.close();
//	     } catch (IOException e) 
//	     {
//		     e.printStackTrace();
//	     }
//   }  
//   //读取XML文档，生成并返回相应的Document实例（采用jdom解析方式）
//   /**
// * @param xmlFilePath
// * @return
// * @throws Exception
// */
//public synchronized static Document readXML(String xmlStringContent,boolean isStringContent) throws Exception
//   {
//	   saxb=getSAXBuilderInstance();
//	   StringReader sr =  new StringReader(xmlStringContent);
//	   Document doc = isStringContent ? saxb.build(new InputSource(sr)) : getDocumentInstance() ;
//	   sr.close();
//	 return  doc;     
//   }
//
////读取XML文档，生成并返回相应的Document实例（采用jdom解析方式）
///**
//* @param xmlFilePath
//* @return
//* @throws Exception
//*/
//public synchronized static Document readXML(String xmlFilePath) throws Exception
//{
//   saxb=getSAXBuilderInstance();
//   FileInputStream fis = new FileInputStream(xmlFilePath);
//   Document doc = saxb.build(xmlFilePath);
//   fis.close();
//  return  doc;     
//}
///**
// * 读取XML文档，生成并返回相应的Document实例（采用jdom解析方式）
// * @param is 输入流
// * @return
// * @throws Exception
// */
//public synchronized static Document readXML(InputStream is) throws Exception
//{
//	 saxb=getSAXBuilderInstance();
//	 Document doc = saxb.build(is);
//	 is.close();
//	 return  doc;      	
//}
//   /*****************************************************/
//   //获得本地Document实例
//   /**
// * @return
// */
//public static Document getDocumentInstance()
//   {
//	   doc=(Document)tl_doc.get();
//	   if(doc==null)
//	   {
//		   doc=new Document();
//	   }
//	   doc.removeContent();
//	 return doc;	   
//   }
//   //获得本地ELement的clone实例
//   /**
// * @param name
// * @return
// */
//public static Element getElement(String name)
//   {
//	   orgin=(Element)tl_el.get();
//	   if(orgin==null)
//	   {
//		   orgin=new Element("orgin");
//		   tl_el.set(orgin);
//	   }
//	   Element el=(Element)orgin.clone();
//	   el.setName(name);
//	   return el;
//   }
//
//public static CDATA getCDATAElement(String content)
//{
//	   cd_orgin=(CDATA)tl_cdel.get();
//	   if(cd_orgin==null)
//	   {
//		   cd_orgin=new CDATA("cd_orgin");
//		   tl_cdel.set(cd_orgin);
//	   }
//	   CDATA cd_el=(CDATA)cd_orgin.clone();
//	   cd_el.setText(content);
//	   return cd_el;
//}
//   //获得本地XMLOutputter实例
//   /**
// * @return
// */
//public static XMLOutputter getXMLOutputterInstance()
//   {
//	   outp=(XMLOutputter)tl_xop.get();
//	   if(outp==null)
//	   {
//		   outp=new XMLOutputter();// jdom
//		   tl_xop.set(outp);
//	   }
//	   return outp;
//   }
//public static XMLOutputter getXMLOutputterInstance(Format f)
//{
//	   outp=(XMLOutputter)tl_xop.get();
//	   if(outp==null)
//	   {
//		   outp=new XMLOutputter(f);// jdom
//		   tl_xop.set(outp);
//	   }
//	   return outp;
//}
//  //获得本地SAXBuilder实例
//   /**
// * @return
// */
//public static SAXBuilder getSAXBuilderInstance()
//   {
//	   saxb=(SAXBuilder)tl_saxb.get();
//	   if(saxb==null)
//	   {
//		   saxb=new SAXBuilder(false);
//		   tl_saxb.set(saxb);
//	   }
//	   return saxb;
//   }
//@SuppressWarnings("unchecked") 
//   /****************************************************/
//public static String getAttributesString(Element node)
//{
//	List<Attribute> attributes = node.getAttributes();
//	StringBuffer sb = new StringBuffer();
//	for(int index = 0;index<attributes.size();index++)
//	{
//		Attribute p = (Attribute)attributes.get(index);
//		sb.append(" "+p.getName()+"=\""+p.getValue()+"\" ");
//	}
//	return sb.toString();
//}
// /*********************************************************************/
//}
