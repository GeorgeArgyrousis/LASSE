package types;

public enum BackgroundType {

	TEST("graphics/bg_2.png");
	
	private String path;
	
	BackgroundType(String path){
		this.path = path;
	}
	
	public String getPath(){
		return this.path;
	}
}
