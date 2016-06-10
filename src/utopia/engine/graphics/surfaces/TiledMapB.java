package utopia.engine.graphics.surfaces;

import java.awt.image.BufferedImage;
import utopia.engine.graphics.MTileset;
import utopia.game.planet.Block;
import utopia.game.planet.BlockType;

public class TiledMapB extends MSurface {
    private int[][] tileID; //Matriz de tiles
    private int width, height; //Dimensão da matriz de tiles
    
	private MTileset tileset;
	private int tileWidth, tileHeight; //Dimensões da tile (pixels)
    
    private BufferedImage fullMap; //Imagem do mapa completo
    private int xSmallOffset = 0; //Deslocamento (px)
    private int ySmallOffset = 0;
    private boolean needUpdate = true;
    
    
	public TiledMapB(int width, int height, MTileset ts) {
		super(width, height);
		
		tileset = ts;
		tileWidth = tileset.getTileWidth();
		tileWidth = tileset.getTileHeight();
		
		validate();
	}
	
	
	public void setTiles(BlockType[][] tiles){
		//Lê os blocos, converte, relaciona os cantos e armazena na matriz de IDs
		
		width = tiles.length;
		height = tiles[0].length;
		tileID = new int[width][height];
		
		for (int x=0; x<width; x++){
			for (int y=0; y<height; y++){
				if (tiles[x][y] == BlockType.LAND_WATER){
					//Tratar fronteira
					
					BlockType top = null, bottom = null, left = null, right = null;
					if (x > 0) left = tiles[x-1][y];
					if (x < width-1) right = tiles[x+1][y];
					if (y > 0) top = tiles[x][y-1];
					if (y < height-1) bottom = tiles[x][y+1];
					tileID[x][y] = BlockType.getBorderTileID(tiles[x][y], left, right, top, bottom);
					
				}
				else tileID[x][y] = tiles[x][y].getTileID();
			}
		}
		
		fullMap = tileset.drawTiledMap(tileID, width, height);
	}
	
    private void verticalMovement(int units){
    	//Calcula movimentos no eixo Y
    	if (units == 0) return;
    	int oldOffset = ySmallOffset;
    	
		ySmallOffset += units;
		if (ySmallOffset < 0) ySmallOffset = 0;
		int mapLimit = fullMap.getHeight() - super.getHeight();
		if (ySmallOffset > mapLimit) ySmallOffset = mapLimit;
		
    	needUpdate = (oldOffset == ySmallOffset);
    }

    private void horizontalMovement(int units){
    	//Calcula movimentos no eixo X
    	if (units == 0) return;
    	int oldOffset = xSmallOffset;
    	
		xSmallOffset += units;
		if (xSmallOffset < 0) xSmallOffset = 0;
		int mapLimit = fullMap.getWidth() - super.getWidth();
		if (xSmallOffset > mapLimit) xSmallOffset = mapLimit;

    	needUpdate = (oldOffset == xSmallOffset);
    }

    private void centerAtTile(int x, int y){
    	//Calcula o ponto que corresponde ao centro dessa tile
		int xDest = (x * tileWidth) + (tileWidth / 2);
		int yDest = (y * tileHeight) + (tileHeight / 2);
		
		//Verifica se esse ponto existe no mapa
		if (xDest < 0 || yDest < 0 || xDest >= fullMap.getWidth() || yDest >= fullMap.getHeight()) return;
		
		//Centraliza a tela nesse ponto
		xDest -= super.getWidth() / 2;
		yDest -= super.getHeight() / 2;
		
		//Corrige limites
		int mapLimit = fullMap.getWidth() - super.getWidth();
		if (xDest > mapLimit) xDest = mapLimit;
		mapLimit = fullMap.getHeight() - super.getHeight();
		if (yDest > mapLimit) yDest = mapLimit;
		if (xDest < 0) xDest = 0;
		if (yDest < 0) yDest = 0;
		
		//Atualiza apenas se o destino for diferente da posiçao atual
		if ((xDest != xSmallOffset) || (yDest != ySmallOffset)){
			xSmallOffset = xDest;
			ySmallOffset = yDest;
			needUpdate = true;
		}
    }

    public void moveU(int speed){
    	if (speed <= 0) return;
        if (isValid()) verticalMovement(-1 * speed);
    }
    
    public void moveD(int speed){
    	if (speed <= 0) return;
        if (isValid()) verticalMovement(1 * speed);
    }
    
    public void moveL(int speed){
    	if (speed <= 0) return;
        if (isValid()) horizontalMovement(-1 * speed);
    }
    
    public void moveR(int speed){
    	if (speed <= 0) return;
        if (isValid()) horizontalMovement(1 * speed);
    }

	public void goToTile(int x, int y){
		//Implementar smooth scrolling
		this.centerAtTile(x, y);
	}

	public void autoMove(int speed){
		//Move o mapa automaticamente no sentido horário
		
		if (!needUpdate) moveR(speed);
		if (!needUpdate) moveD(speed);
		if (!needUpdate) moveL(speed);
		if (!needUpdate) moveU(speed);
		
	}

	@Override
	public void updateGraphics() {
		//Faz a atualização considerando os movimentos laterais do mapa
		if (!needUpdate) return;

		//Copia apenas a parte visível do FULLMAP
		int xPos = xSmallOffset;
		int yPos = ySmallOffset;
		
		BufferedImage sub = fullMap.getSubimage(xPos, yPos, getWidth(), getHeight());
		super.getDrawingSurf().drawImage(sub, 0, 0, sub.getWidth(), sub.getHeight(), null);
		
        needUpdate = false; //As mudanças já ocorreram
	}

}
