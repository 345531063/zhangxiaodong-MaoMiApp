package cn.com.yibin.maomi.core.constant;

public enum JsonReturnResultTypeEnum {
   SUCCESS("操作成功"),
   FAILURE("操作失败");
   private String description;

   private JsonReturnResultTypeEnum(String description) {
		this.setDescription(description);
  }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
