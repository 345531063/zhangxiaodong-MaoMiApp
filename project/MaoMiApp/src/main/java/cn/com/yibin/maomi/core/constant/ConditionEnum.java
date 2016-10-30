package cn.com.yibin.maomi.core.constant;

public enum ConditionEnum {
   OR("或者"),
   AND("并且");
   private String description;

   private ConditionEnum(String description) {
		this.setDescription(description);
  }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
