package cn.com.yibin.maomi.core.jpa;

public enum SQLFilterEnum {
	FILTER_NOT_EMPTY_RECORDS("FILTER_NOT_EMPTY_RECORDS"), 
	FILTER_EMPTY_RECORDS("FILTER_EMPTY_RECORDS");
	
	private String value;

	private SQLFilterEnum(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
