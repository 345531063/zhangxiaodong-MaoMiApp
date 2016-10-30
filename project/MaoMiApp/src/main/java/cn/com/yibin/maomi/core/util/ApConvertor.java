package cn.com.yibin.maomi.core.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import cn.com.yibin.maomi.core.annotation.ApTable;
import cn.com.yibin.maomi.core.tx.service.TxService;

public class ApConvertor{
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private  static <T> T copyPropertiesByMap(TxService txService,Map<String,String> sourceMapModel, T targetObject,Map<String, String> classFieldMapping, boolean withUserInfo, String... entityIdentifier) throws Exception
	{
		Class<?> targetClass = targetObject.getClass();
		String targetClassName = targetClass.getName();
		PropertyDescriptor[] propertyDescriptors_target = BeanUtils.getPropertyDescriptors(targetObject.getClass());
		Set<String> overrideFieldsName = (Set<String>) sourceMapModel.keySet();
		Set<String> notOverrideFieldsName = new HashSet<String>();
		StringBuffer sb = new StringBuffer();
		if (entityIdentifier.length > 0) {
			for (String ei : entityIdentifier)
			{
				sb.append(ei + ".");
			}
		}
		String entityIdentifierStr = sb.toString();
		for (PropertyDescriptor propertyDescriptor_target : propertyDescriptors_target)
		{
			String name_target = propertyDescriptor_target.getName();

			if ("class".equals(name_target))continue;
			Method readMethod = propertyDescriptor_target.getReadMethod();
			if(null == readMethod){
				LogUtil.warn(ApConvertor.class,propertyDescriptor_target.getName());
				continue;
			}
			Class targetFieldClass = readMethod.getReturnType();
			
			String targetFieldClassName = targetFieldClass.getSimpleName();
			boolean isSuccessField = false;
			if ("boolean".equals(targetFieldClassName))
			{
				targetFieldClass = Boolean.class;
			}
			if ("short".equals(targetFieldClassName))
			{
				targetFieldClass = Short.class;
			}
			if ("long".equals(targetFieldClassName))
			{
				targetFieldClass = Long.class;
			}
			if ("int".equals(targetFieldClassName))
			{
				targetFieldClass = Integer.class;
			}
			if ("float".equals(targetFieldClassName))
			{
				targetFieldClass = Float.class;
			}
			if ("double".equals(targetFieldClassName))
			{
				targetFieldClass = Double.class;
			}

			targetFieldClassName = targetFieldClass.getSimpleName();
			// if(!targetFieldClass.isPrimitive())
			{
				name_target = (entityIdentifierStr + name_target);
				for (String overrideFieldName : overrideFieldsName)
				{
					if (overrideFieldName.equalsIgnoreCase(name_target))
					{
						Object valueObj = sourceMapModel.get(overrideFieldName);
						if (overrideFieldName.equals(entityIdentifierStr + "id") && (new Integer(0).equals(valueObj))) {
							break;
						}
						String value   = StringUtil.emptyOpt(valueObj);
						// 获取写set方法
						Method writeMethod = propertyDescriptor_target.getWriteMethod();
						// 调用(set)方法
						if ((null == writeMethod)) {
							writeMethod = targetClass.getMethod("set"+ StringUtils.capitalize(name_target), targetFieldClass);
							if (null == writeMethod) {
								LogUtil.error(ApConvertor.class,"类型：" + targetFieldClassName + ",【" + targetClassName + "." + name_target+ "没有对应的set方法】");
								break;
							}
						}
						try
						{
							if (StringUtils.isBlank(value)) {
								value = null;
								if("position".equalsIgnoreCase(name_target)){
									writeMethod.invoke(targetObject, 0);
									break;
								}
								writeMethod.invoke(targetObject, value);
								isSuccessField = true;
								LogUtil.warn(ApConvertor.class,"【 " + targetObject.getClass().getSimpleName() + "." + name_target+ " 】被强制赋予了空值");
								break;
							}
							Object castValue = null;

							// 判断是否为实体
							
							if (targetFieldClass.isAnnotationPresent(ApTable.class))
							{
								try {

									String fieldName = null;
									if (null != classFieldMapping) {
										fieldName = classFieldMapping.get(targetFieldClass.getSimpleName());
									}

									if (StringUtils.isBlank(fieldName)) {
										castValue = txService.findEntityById(targetFieldClass, Long.parseLong(value));
												//this.getHibernateTemplate().get(targetFieldClass,StringUtil.nullToString(value));
									}
									else {
										LinkedHashMap<String, Object> filterMap = new LinkedHashMap<String, Object>();
										filterMap.put(fieldName, StringUtil.emptyOpt(value));
										List l = txService.findEntitiesByFilterMap(targetFieldClass, filterMap);
										if (l.size() > 0) {
											castValue = l.get(0);
										}
									}

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							else
							{
								try {
									if (targetFieldClass.isEnum()) {//对Enum类型进行特殊处理
										Class<? extends Enum> clazz= (Class<? extends Enum>) targetFieldClass;
										castValue = Enum.valueOf(clazz, value.toString());
									} else {
										if (value.getClass().equals(targetFieldClass))
										{
											castValue = value;
										}
										else
										{
											if("Boolean".equals(targetFieldClassName)){
												value = ("1".equals(value) ? "true" : "false");
											}
											castValue = targetFieldClass.getConstructor(value.getClass()).newInstance(value);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
										LogUtil.error(ApConvertor.class,"【" + targetObject.getClass() + "." + name_target + "[类型"+targetFieldClass+"],必须具有public("
												+ value.getClass() + "){//方法体}】");
										throw new Exception();
								}
							}

							writeMethod.invoke(targetObject, castValue);
							isSuccessField = true;
						} catch (Exception e)
						{
							e.printStackTrace();
							throw new Exception("name_target:"+name_target);
						}
						break;
					}
				}
			}
			if (!isSuccessField)
				notOverrideFieldsName.add(name_target);
		}
		if (ResourceUtil.isDebug()) {
			LogUtil.warn(ApConvertor.class,"对象【" + targetClassName + "】不能赋值的属性：");
			notOverrideFieldsName.remove(entityIdentifierStr + "creator");
			notOverrideFieldsName.remove(entityIdentifierStr + "modificator");
			notOverrideFieldsName.remove(entityIdentifierStr + "create_date");
			notOverrideFieldsName.remove(entityIdentifierStr + "modify_date");
			for (String name : notOverrideFieldsName) {
				LogUtil.warn(ApConvertor.class,">>>>>> " + name);
			}
		}
		return targetObject;
	}
	public static <T>  void  putMapEntryByBean(Map<String, String> map,T beanObj) throws Exception{
		getValueMap(map,beanObj);
	}
	public static <T> Map<String, String>  getMapByBean(T beanObj) throws Exception{
		Map<String, String>   map  = new HashMap<String, String>();
		putMapEntryByBean(map,beanObj);
		return map;
	}
	public  static <T> T getBeanByMap(TxService txService ,Map<String,String> sourceMapModel,Class<T> beanClazz,String... entityIdentifier) throws Exception{
		T beanObject   =  beanClazz.newInstance();
		copyPropertiesByMap(txService, sourceMapModel, beanObject, null, false);
		return beanObject;
	}
	public  static <T> T copyPropertiesByMap(TxService txService ,Map<String,String> sourceMapModel,T beanObject,String... entityIdentifier) throws Exception{
		copyPropertiesByMap(txService, sourceMapModel, beanObject, null, false);
		return beanObject;
	}
	private static <T> Map<String, String> getValueMap(Map<String, String> map,T obj) {  
        // 获取f对象对应类中的所有属性域  
        Field[] fields = obj.getClass().getDeclaredFields();  
        for (int i = 0, len = fields.length; i < len; i++) {  
            String varName = fields[i].getName();  
            try {  
                // 获取原来的访问控制权限  
                boolean accessFlag = fields[i].isAccessible();  
                // 修改访问控制权限  
                fields[i].setAccessible(true);  
                // 获取在对象f中属性fields[i]对应的对象中的变量  
                Object o = fields[i].get(obj);  
                if (o != null)  
                    map.put(varName, o.toString());  
                // 恢复访问控制权限  
                fields[i].setAccessible(accessFlag);  
            } catch (IllegalArgumentException ex) {  
                ex.printStackTrace();  
            } catch (IllegalAccessException ex) {  
                ex.printStackTrace();  
            }  
        }  
        return map;  
  
    }  
}