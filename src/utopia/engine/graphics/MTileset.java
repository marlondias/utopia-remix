package utopia.engine.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utopia.basic.helpers.Helper;

//Minha versão da classe SpriteSheet, mais segura e flexivel
public class MTileset {
    private final BufferedImage img; //Imagem contendo o tileset
    private final int tileWidth; //Dimensões da tile (pixels)
    private final int tileHeight;
    private final int columns; //Quantidade de tiles na horizontal
    private final int rows; //Quantidade de tiles na vertical
    private final int totalOfTiles;
    private boolean valid; //indica se o tileset pode ser utilizado
    
    
    public MTileset(int tileWidthPixels, int tileHeightPixels, String path) {
    	tileWidth = (tileWidthPixels > 0) ? tileWidthPixels : 0;
    	tileHeight = (tileHeightPixels > 0) ? tileHeightPixels : 0;
    	
        img = Helper.openImageFile(path); //Carrega o arquivo no endereço PATH

        columns = img.getWidth() / tileWidth;
        rows = img.getHeight() / tileHeight;
        totalOfTiles = columns * rows;

    	//Verificação de erros...
    	if (tileWidth <= 0 || tileHeight <= 0) return; //Erro: tamanho inválido da tile
    	if (img == null) return; //Erro: imagem não carregou
        if ((img.getWidth() % tileWidth != 0) || (img.getHeight() % tileHeight != 0)) return; //Erro: tiles parciais
        
        valid = true; //Esse tileset pode ser acessado sem erros
    }
    
    public MTileset(int tileSize, String path){
        //Em caso de tile quadrado
        this(tileSize, tileSize, path);
    }
    
    
    public boolean isValid(){
        return valid;
    }
        
    public int getTileWidth(){
    	return tileWidth;
    }
    
    public int getTileHeight(){
    	return tileHeight;
    }
    
    public BufferedImage getTile(int tileID){
    	//Retorna uma imagem contendo apenas a tile
    	if (!valid) return null;
    	if ((tileID < 0) || (tileID >= totalOfTiles)) tileID = 0; //ID não corresponde a algo válido
    	
    	int posY = (tileID / columns) * tileHeight;
    	int posX = (tileID % columns) * tileWidth;
    	return img.getSubimage(posX, posY, tileWidth, tileHeight);
    }
    
    public BufferedImage drawTiledMap(int[][] tiles, int width, int height){
    	//Cria um mapa completo usando esse tileset e uma matriz de IDs
    	if (!valid) return null;
    	
    	BufferedImage finalMap = new BufferedImage(width * this.tileWidth, height * this.tileHeight, BufferedImage.TYPE_INT_ARGB);
    	Graphics2D g2 = finalMap.createGraphics();

    	int yPos = 0;
    	for (int y=0; y<height; y++){
        	int xPos = 0;
    		for (int x=0; x<width; x++){
    			g2.drawImage(this.getTile(tiles[x][y]), xPos, yPos, (xPos + tileWidth), (yPos + tileHeight), 0, 0, tileWidth, tileHeight, null);
    			xPos += tileWidth;
    		}
    		yPos += tileHeight;
    	}
    	
    	g2.dispose();
    	return finalMap;
    }
    
}
