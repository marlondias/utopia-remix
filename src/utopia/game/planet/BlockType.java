package utopia.game.planet;

public enum BlockType {
	//TIPO (tile, walk, hover, build),
	NULL (0, false, false, false),
	
	LAND (1, true, true, true),
	LAND_WATER(4, false, true, false),
	WATER (3, false, true, false),
	MOUNTAIN (8, false, false, false),
	MOUNTAIN_TOP (9, false, false, false), //TODO: Não pode sobrevoar o topo
	
	MOSS (0, false, true, false),
	HOLE (0, false, false, false);
	
	
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
		return tilesetID;
	}
	
	public boolean allowBuilding(){
		return canBuild;
	}
	
	public boolean allowMovement(){
		return canWalk;
	}
	
	public boolean allowMovementHover(){
		return canHover;
	}
	
	public static int getBorderTileID(BlockType target, BlockType left, BlockType right, BlockType top, BlockType bottom){
		//Compara TARGET com seus vizinhos diretos, retornando a tile que se encaixa graficamente
		
		//TODO: Verificar se TARGET é um tipo fronteira
		if (target == null) return 0; //Erro: target não existe
		
		BlockType typeA = null;
		if (target == BlockType.LAND_WATER) typeA = LAND;
		
		boolean blendTo_L = false, blendTo_R = false, blendTo_T = false, blendTo_B = false;
		int blendCount = 0;

		//Verifica quem cola em quem
		if (target.equals(top)){
			blendTo_T = true;
			blendCount++;
		}
		if (target.equals(bottom)){
			blendTo_B = true;
			blendCount++;
		}
		if (target.equals(left)){
			blendTo_L = true;
			blendCount++;
		}
		if (target.equals(right)){
			blendTo_R = true;
			blendCount++;
		}
		
		if (blendCount < 2){
			//Não existe tile correspondente
			return target.getTileID();
		}
		else if (blendCount == 2){
			int offset = 0;
			
			if (blendTo_L && blendTo_R){
				//É reto horizontal
				if (typeA.equals(top)) offset = 2; //Orientado para baixo
				else offset = 18; //Orientado para cima
			}
			else if (blendTo_T && blendTo_B){
				//É reto vertical
				if (typeA.equals(left)) offset = 9; //Orientado para a direita
				else offset = 11;//Orientado para a esquerda
			}
			else if (blendTo_R && blendTo_B){
				//É canto NW
				if (typeA.equals(left) || typeA.equals(top)) offset = 1; //Fechado
				else offset = 7; //Aberto
			}
			else if (blendTo_L && blendTo_B){
				//É canto NE
				if (typeA.equals(right) || typeA.equals(top)) offset = 3; //Fechado
				else offset = 8; //Aberto
			}
			else if (blendTo_R && blendTo_T){
				//É canto SW
				if (typeA.equals(left) || typeA.equals(bottom)) offset = 17; //Fechado
				else offset = 15; //Aberto
			}
			else if (blendTo_L && blendTo_T){
				//É canto SE
				if (typeA.equals(right) || typeA.equals(bottom)) offset = 19; //Fechado
				else offset = 16; //Aberto
			}
			
			return target.getTileID() + offset;
		}
		else {
			return target.getTileID() - 1;
		}
		
	}

}
