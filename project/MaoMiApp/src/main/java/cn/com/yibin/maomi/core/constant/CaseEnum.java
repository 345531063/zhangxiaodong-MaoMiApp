package cn.com.yibin.maomi.core.constant;

public enum CaseEnum {
   UPPERCASE("大写"),
   LOWERCASE("小写"),
   NORMAL("正常");
   private String description;

   private CaseEnum(String description) {
		this.setDescription(description);
  }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
