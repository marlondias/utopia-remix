package utopia.engine.graphics.msurfaces;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import utopia.engine.graphics.MSurface;
import utopia.engine.graphics.MTileset;

//Superfície 2d que controla e renderiza um mapa composto por tiles
public class TiledMap extends MSurface {
    private BufferedImage fullMap; //Imagem do mapa completo
    private int mapWidth; //Dimensões do mapa (em pixels)
    private int mapHeight;

	private MTileset tileset; //Permite apenas 1 tileset para o canvas
	private int tileWidth; //Dimensões da tile (pq o dimension é bosta)
    private int tileHeight;
    
    

    private int WIDTH;
    private int HEIGHT;
    private boolean valid;
    private final int BUG_LEVEL = 20;

    private int visibleTilesX;
    private int visibleTilesY;
    
    private int xOffset; //Em pixels
    private int yOffset;
    private int xTileOffset; //Em tiles
    private int yTileOffset;
    
    private boolean needUpdate = true;

	
	public TiledMap(MTileset tileset, int width, int height) {
		super(width, height);

		//Carrega os dados do tileset (sem verificação de segurança)
		if (tileset.isValid()) this.tileset = tileset;
		this.tileWidth = (int)tileset.getTileDimensions().getWidth();
		this.tileHeight = (int)tileset.getTileDimensions().getHeight();
		
		
		//Cria uma imagem do tamanho necessário
		this.mapWidth = tileWidth * BUG_LEVEL;
		this.mapHeight = tileHeight * BUG_LEVEL;
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		this.fullMap = gc.createCompatibleImage(mapWidth, mapHeight);
		
		//Constrói o mapa completo usando o tileset (e uma matriz de IDs)
		Graphics2D g2 = fullMap.createGraphics();
		for (int fakeY=0; fakeY<BUG_LEVEL; fakeY++){
			for (int fakeX=0; fakeX<BUG_LEVEL; fakeX++){
				//preenche o mapa com qualquer tile do tileset
				byte tileID = (byte)(7 & System.currentTimeMillis());
				int xPos = fakeX * tileWidth;
				int yPos = fakeY * tileHeight;
				g2.drawImage(this.tileset.getTile(tileID), xPos, yPos, (xPos + tileWidth), (yPos + tileHeight), 0, 0, tileWidth, tileHeight, null);
			}
		}
		g2.dispose();
		
		
        //Calcula quantas tiles completas cabem na tela, +1 para caber a tile extra ao mover
        this.visibleTilesX = (width / tileWidth) + 1;
        this.visibleTilesY = (height / tileHeight) + 1;
        
        //Se houver espaço para um tile incompleto, aumenta 1 nos tiles visiveis
        if (width % tileWidth != 0) visibleTilesX++;
        if (height % tileHeight != 0) visibleTilesY++;

        this.WIDTH = width;
        this.HEIGHT = height;
        this.valid = true;
		
        super.validate();
	}

	
	//revisar tudo abaixo
    
    private void checkBorders(){
        if (valid == false) return;
        
        //Verifica se ultrapassou as extremidades da tela
        if (xTileOffset < 0){
            xTileOffset = 0;
            xOffset = 0; 
        }
        else {
            int lastTileX = (WIDTH % tileWidth != 0) ? 1 : 0; //+1 se houver incompleto no final
            lastTileX += BUG_LEVEL;
            if ((xTileOffset + visibleTilesX) > lastTileX){
                xTileOffset = lastTileX - visibleTilesX;
                xOffset = tileWidth - (WIDTH % tileWidth);
            }
        }
        
        if (yTileOffset < 0){
            yTileOffset = 0;
            yOffset = 0; 
        }
        else {
            int lastTileY = (HEIGHT % tileHeight != 0) ? 1 : 0; //+1 se houver incompleto no final
            lastTileY += BUG_LEVEL;
            if ((yTileOffset + visibleTilesY) > lastTileY){
                yTileOffset = lastTileY - visibleTilesY;
                yOffset = tileHeight - (HEIGHT % tileHeight);
            }
        }
    }
    
    public void cameraCenterOn(int xPixel, int yPixel){
        if (valid == false) return;
        
        xPixel -= WIDTH / 2; //desloca até que o ponto fique no centro do canvas
        yPixel -= HEIGHT / 2;
        
        xTileOffset = xPixel / tileWidth; //atualiza os offsets
        yTileOffset = yPixel / tileHeight;
        xOffset = xPixel % tileWidth;
        yOffset = yPixel % tileHeight;
        
        checkBorders();
    }
    
    public void moveL(){
        needUpdate = true;
        if (xOffset > 0){
            xOffset--; //não está no primeiro pixel, movimento normal
        }
        else {
            if (xTileOffset > 0){
                xOffset = tileWidth-1; //offset vai pro ultimo pixel
                xTileOffset--; //vai um tile para a esquerda
            }
        }
    }

    public void moveR(){
        needUpdate = true;
        int lastTile = (WIDTH % tileWidth != 0) ? 1 : 0; //+1 se houver incompleto no final
        lastTile += BUG_LEVEL;        
        if ((xTileOffset + visibleTilesX) < lastTile){
            //não está no ultimo tile, movimento normal
            if (xOffset < (tileWidth-1)){
                xOffset++;
            }
            else{
                xOffset = 0;
                xTileOffset++;
            }
        }
        else if ((xTileOffset + visibleTilesX) == lastTile){
            //ultimo tile, movimento apenas nos pixels
            int extraPixels = tileWidth - (WIDTH % tileWidth); //quantos pixels do ultimo tile estão ocultos
            if (xOffset < extraPixels){
                xOffset++;
            }
        }
    }

    public void moveU(){
        if (valid == false) return;
        needUpdate = true;
        if (yOffset > 0){
            yOffset--; //não está no pixel zero, movimento normal
        }
        else {
            if (yTileOffset > 0){
                yOffset = tileHeight-1; //offset vai pro ultimo pixel
                yTileOffset--; //vai um tile para cima
            }
        }
    }

    public void moveD(){
        if (valid == false) return;
        needUpdate = true;
        int lastTile = (HEIGHT % tileHeight != 0) ? 1 : 0; //+1 se houver incompleto no final
        lastTile += BUG_LEVEL;
        if ((yTileOffset + visibleTilesY) < lastTile){
            //não está no ultimo tile, movimento normal
            if (yOffset < (tileHeight-1)){
                yOffset++;
            }
            else{
                yOffset = 0;
                yTileOffset++;
            }
        }
        else if ((yTileOffset + visibleTilesY) == lastTile){
            //ultimo tile, movimento apenas nos pixels
            int extraPixels = tileHeight - (HEIGHT % tileHeight); //quantos pixels do ultimo tile estão ocultos
            if (yOffset < extraPixels){
                yOffset++;
            }
        }
    }
    
	
	
	@Override
	public void updateGraphics() {
		//Faz a atualização considerando os movimentos laterais do mapa
		if (!needUpdate) return;

		//Copia apenas a porção visível do FULLMAP
		int xPos = xOffset + (xTileOffset * tileWidth);
		int yPos = yOffset + (yTileOffset * tileHeight);
		super.drawBuffImg(fullMap, xPos, yPos, xPos+super.getWidth(), yPos+super.getHeight());
	
        needUpdate = false; //As mudanças já ocorreram
	}
	
}
