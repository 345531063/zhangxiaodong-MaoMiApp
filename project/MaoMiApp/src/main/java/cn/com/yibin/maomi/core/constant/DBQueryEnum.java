package cn.com.yibin.maomi.core.constant;

public enum DBQueryEnum {
   EQUALS("=","等于"),
   CASE_INSENSITIVE ("case-insensitive","大写"),
   LIKE("like","模糊查询"),
   INSTR("instr","模糊查询");
   private String value;
   private String description;

   private DBQueryEnum(String value,String description) {
		this.setDescription(description);
  }
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
