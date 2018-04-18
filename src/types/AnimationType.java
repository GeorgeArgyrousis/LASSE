package types;

public enum AnimationType {

	/** */
	BLOB("graphics/enemy1walking.png", 14),
	PORTAL("graphics/hero.png", 11),
	ATTACK("graphics/attack_1.png", 5),
	PIDLE("graphics/player/idle.png", 8),
	PWALK("graphics/player/walk.png", 9),
	PJUMP("graphics/player/jump.png", 5),
	PAIR("graphics/player/air.png", 6),
	PFALL("graphics/player/fall.png", 4),
	PATTACK("graphics/attacking.png", 5);

	private String path;
	private int frames;

	AnimationType(String path, int frames) {
		this.path = path;
		this.frames = frames;
	}

	public String getPath() {
		return this.path;
	}

	public int getFrames() {
		if(frames == 1){
			return 1;
		}
		return this.frames;
	}
}
