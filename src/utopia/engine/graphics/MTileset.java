package utopia.engine.graphics;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

//Minha versão da classe SpriteSheet, mais segura e flexivel
public class MTileset {
    private final int tileWidth; //Largura da tile (em pixels)
    private final int tileHeight; //Altura da tile (em pixels)
    private int columns; //Quantidade de tiles na horizontal
    private int rows; //Quantidade de tiles na vertical
    private int totalOfTiles;
    private BufferedImage img; //Copia da imagem carregada para o tileset
    private boolean valid; //indica se o tileset pode ser utilizado
    
    
    public MTileset(int tileWidthPixels, int tileHeightPixels, String path) {
    	this.tileWidth = (tileWidthPixels > 0) ? tileWidthPixels : 0;
    	this.tileHeight = (tileHeightPixels > 0) ? tileHeightPixels : 0;
    	if (this.tileWidth <= 0 || this.tileHeight <= 0) return; //Erro nas dimensões da tile
    	
    	//Tenta o carregamento usando PATH como endereço da imagem
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(MTileset.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (img == null) return; //Erro de carregamento
        
        //Verifica se a imagem contém apenas tiles inteiros (e se cabe no minimo uma tile)
        if (img.getWidth() % this.tileWidth != 0) return; //Erro na largura
        if (img.getHeight() % this.tileHeight != 0) return; //Erro na altura
        
        //Marca quantos tiles existem (horizontal e vertical)
        this.columns = img.getWidth() / this.tileWidth;
        this.rows = img.getHeight() / this.tileHeight;
        this.totalOfTiles = this.columns * this.rows;
        
        this.valid = true; //O tileset pode ser acessado com segurança
    }
    
    public MTileset(int tileSize, String path){
        //Em caso de tile quadrado
        this(tileSize, tileSize, path);
    }
    
    
    public boolean isValid(){
        return valid;
    }
        
    public Dimension getTileDimensions(){
    	return (new Dimension(tileWidth, tileHeight));
    }
    
    public BufferedImage getTile(int tileID){
    	//Retorna uma imagem contendo apenas a tile

    	if (!valid) return null;
    	if ((tileID < 0) || (tileID >= totalOfTiles)) return null; //ID não corresponde a algo válido

    	int posY = (tileID / columns) * tileHeight;
    	int posX = (tileID % columns) * tileWidth;
    	BufferedImage tile = img.getSubimage(posX, posY, tileWidth, tileHeight);

    	return tile;
    }

}
