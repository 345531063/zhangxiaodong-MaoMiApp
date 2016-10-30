package cn.com.yibin.maomi.core.constant;

public enum OrderByEnum {
   ASC("升序"),
   DESC("降序");
   
   private String description;

   private OrderByEnum(String description) {
		this.setDescription(description);
  }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
   
}
