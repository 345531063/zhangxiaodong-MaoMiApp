package cn.com.yibin.maomi.core.constant;

public enum OperTypeEnum {
   ADD("新增成功"),
   EDIT("修改操作"),
   REMOVE("删除操作"),
   COPY("复制操作");
   
   private String description;

   private OperTypeEnum(String description) {
		this.setDescription(description);
  }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
