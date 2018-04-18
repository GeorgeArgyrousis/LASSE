package types;

public enum StateType {

	MENU(0, ""),
	ROOMONE(1, "maps/roomone.txt"),
	ROOMTWO(2, "maps/roomtwo.txt"),
	ROOMTHREE(3, "maps/roomthree.txt"),
	ROOMFOUR(4, "maps/roomfour.txt"),
	LEVELONE(5, "maps/levelone.txt");
	
	private int state;
	private String path;
	
	StateType(int state, String path){
		this.state = state;
		this.path = path;
	}
	
	public int getState(){
		return this.state;
	}
	
	public String getPath(){
		return this.path;
	}
}
