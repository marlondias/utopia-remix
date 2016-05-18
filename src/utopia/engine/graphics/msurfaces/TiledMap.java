package utopia.engine.graphics.msurfaces;

import java.awt.image.BufferedImage;
import utopia.engine.graphics.MSurface;
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
    private boolean valid;
    
	
	public TiledMap(MTileset tileset, int width, int height, int[][] map) {
		super(width, height);

		if (tileset.isValid()) this.tileset = tileset;
		tileWidth = tileset.getTileWidth();
		tileHeight = tileset.getTileHeight();
		
		fullMap = tileset.drawTiledMap(map, 35, 35); //Cria o mapa completo
		
        this.valid = true;
        
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

    public void moveU(){
        if (valid == false) return;
        verticalMovement(-1);
    }
    
    public void moveD(){
        if (valid == false) return;
        verticalMovement(1);
    }
    
    public void moveL(){
        if (valid == false) return;
        horizontalMovement(-1);
    }
    
    public void moveR(){
        if (valid == false) return;
        horizontalMovement(1);
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

	public void goToTile(int x, int y){
		//Implementar smooth scrolling
		this.centerAtTile(x, y);
	}
    
	@Override
	public void updateGraphics() {
		//Faz a atualização considerando os movimentos laterais do mapa
		if (!needUpdate) return;

		//Copia apenas a porção visível do FULLMAP
		int xPos = xSmallOffset;
		int yPos = ySmallOffset;
		super.drawBuffImg(fullMap, xPos, yPos, xPos+super.getWidth(), yPos+super.getHeight());
		
        needUpdate = false; //As mudanças já ocorreram
	}
	
}
