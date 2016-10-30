package cn.com.yibin.maomi.core.constant;

public enum DBUpdateTypeEnum {
   INSERT("插入"),
   MODIFY("更新"),
   INSERTORMODIFY("更新"),
   DELETE("删除");
   private String description;

   private DBUpdateTypeEnum(String description) {
		this.setDescription(description);
  }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
