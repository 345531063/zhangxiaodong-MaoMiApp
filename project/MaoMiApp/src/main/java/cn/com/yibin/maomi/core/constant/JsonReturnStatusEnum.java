package cn.com.yibin.maomi.core.constant;


public enum JsonReturnStatusEnum {
   SUCCESS(0  ,                     "SUCCESS",                       "操作成功"),
   FAILURE(-1 ,                     "FAILURE",                       "操作失败"),
   IRREMOTENOTLEARN(-2 ,            "IRREMOTENOTLEARN",              "红外未学习"),
   IRREMOTERBUSY(-4,               "IRREMOTERBUSY",                 "红外设备忙"),
   UNACTIVATED(-10 ,			    "UNACTIVATED",				     "网关未激活"),
   IREMOTELEARNFAILD(-11,		    "IREMOTELEARNFAILD",			 "红外学习指令失败,校验失败"),
   IREMOTELEARNSUCCESS(12,			"IREMOTELEARNSUCCESS",			"红外学习成功"),
   IREMOTELEARNOVERTIME(-13,		"IREMOTELEARNOVERTIME",			"红外学习超时"),
   NOTSUPPORTTEMPMODEN(-14 ,		"NOTSUPPORTTEMPMODEN",			"模式不支持"),
   IRREMOTERNOTLEARN(-15 ,			"IRREMOTERNOTLEARN",			"执行为学习");
  
   private final int    status;
   private final String code;
   private final String description;
   
   private JsonReturnStatusEnum(int status,String code,String description) {
	   this.status       = status ;
	   this.code         = code;
	   this.description  = description ;
   }

	public int getStatus() {
		return status;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	public static JsonReturnStatusEnum getJsonReturnStatusEnumByCode(String irRemoteTypeStatusCode) {
		for(JsonReturnStatusEnum irRemoteEnum : JsonReturnStatusEnum.values()){
			String code = irRemoteEnum.getCode();
			if(irRemoteTypeStatusCode.equalsIgnoreCase(code)){
				return irRemoteEnum;
			}
		}
		return FAILURE;
	}
	public static JsonReturnStatusEnum getJsonReturnStatusEnumByStatus(Integer irRemoteTypeStatus) {
		for(JsonReturnStatusEnum irRemoteEnum : JsonReturnStatusEnum.values()){
			Integer irRemoteTypeStatusDb = irRemoteEnum.getStatus();
			if(irRemoteTypeStatusDb.equals(irRemoteTypeStatus)){
				return irRemoteEnum;
			}
		}
		return FAILURE;
	}
}
