package types;

public enum MenuType {

	START(1),
	ABOUT(2),
	OPTIONS(3),
	QUIT(4);
	
	private int type;
	
	private final String paths[] = {"menu/play_1.png",
							  "menu/about_1.png",
							  "menu/options_1.png",
							  "menu/quit_1.png"};
	
	private final String names[] = {"START",
			   						"ABOUT",
			   						"OPTIONS",
			   						"QUIT"}; 
	
	MenuType(int type){
		this.type = type;
	}
	
	public int getType(){
		return this.type;
	}
	
	public String getPath(){
		return paths[this.type - 1];
	}
	
	public String getName(){
		return names[this.type - 1];
	}
}
