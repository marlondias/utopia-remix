package utopia.game.level;

public enum BlockType {
	//TIPO (tile, walk, hover, build),
	NULL (0, false, false, false),
	LAND (1, true, true, true),
	LAKE (2, false, true, false),
	MOSS (3, false, true, false),
	HOLE (4, false, false, false),
	MOUNTAIN (5, false, false, false);
	
	
	private boolean canWalk;
	private boolean canHover;
	private boolean canBuild;
	private int tilesetID;

	private BlockType(int tile, boolean walk, boolean hover, boolean build) {
		this.canWalk = walk;
		this.canHover = hover;
		this.canBuild = build;
		this.tilesetID = tile;
	}
	
	public int getTileID(){
		return this.tilesetID;
	}
	
	public boolean allowBuilding(){
		return this.canBuild;
	}
	
	public boolean allowMovement(){
		return this.canWalk;
	}
	
	public boolean allowMovementHover(){
		return this.canHover;
	}

}
