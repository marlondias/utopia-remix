package utopia.engine.graphics.surfaces;

import java.awt.Point;
import java.awt.image.BufferedImage;

import utopia.engine.graphics.MTileset;

//Superfície 2d que controla e renderiza um mapa composto por tiles
public class TiledMap extends MSurface {
    private BufferedImage fullMap; //Imagem do mapa completo
	private MTileset tileset; //Permite apenas 1 tileset
	private int tileWidth; //Dimensões da tile (px)
    private int tileHeight;
    private int xSmallOffset; //Deslocamento (px)
    private int ySmallOffset;
    private boolean needUpdate = true;
    
	
	public TiledMap(MTileset tileset, int width, int height, int[][] map) {
		super(width, height);

		if (tileset.isValid()) this.tileset = tileset;
		tileWidth = tileset.getTileWidth();
		tileHeight = tileset.getTileHeight();
		
		fullMap = tileset.drawTiledMap(map, 35, 35); //Cria o mapa completo
		
        super.validate();
	}


    private void verticalMovement(int units){
    	//Calcula movimentos no eixo Y
    	if (units == 0) return;
    	needUpdate = true;
		ySmallOffset += units;
		if (ySmallOffset < 0) ySmallOffset = 0;
		int mapLimit = fullMap.getHeight() - super.getHeight();
		if (ySmallOffset > mapLimit) ySmallOffset = mapLimit;
    }

    private void horizontalMovement(int units){
    	//Calcula movimentos no eixo X
    	if (units == 0) return;
    	needUpdate = true;
		xSmallOffset += units;
		if (xSmallOffset < 0) xSmallOffset = 0;
		int mapLimit = fullMap.getWidth() - super.getWidth();
		if (xSmallOffset > mapLimit) xSmallOffset = mapLimit;
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
	
	public Point getSnap(int x, int y){
		if (x < 0 || x >= super.getWidth() || y < 0 || y >= super.getHeight()) return null; //Não está dentro da superfície

		int offsetX = -(xSmallOffset % tileWidth); //Deslocamentos em relação ao grid inicial
		int offsetY = -(ySmallOffset % tileHeight);
		
		int snappedX = (((x-offsetX) / tileWidth) * tileWidth) + offsetX; //Novos valores do ponto obedecendo o grid inicial
		int snappedY = (((y-offsetY) / tileHeight) * tileHeight) + offsetY;
		
		return new Point(snappedX, snappedY);
	}
	
	public int getTileWidth(){
		return tileWidth;
	}

	public int getTileHeight(){
		return tileHeight;
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
