/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utopia.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Marlon Dias
 */
public class MTileset {
    //Minha versão da classe SpriteSheet, mais segura e flexivel
    
    private String filePath; //caminho do arquivo de imagem
    private int width; //largura do tileset (em pixels)
    private int height; //altura do tileset (em pixels)
    private int tileWidth; //largura do tile
    private int tileHeight; //altura do tile
    private int numberOfTilesX; //quantidade de tiles por linha
    private int numberOfTilesY; //quantidade de tiles por coluna
    private int numberOfTiles; //quantidade de tiles no tileset
    private int[] pixels; //contém os pixels da imagem carregada
    private boolean valid; //indica se o tileset pode ser utilizado
    
    
    public MTileset(String path, int tileWidthPixels, int tileHeightPixels) {
        if ((tileWidthPixels <= 0) || (tileHeightPixels <= 0)) return; //evita divisão por zero ou tamanho negativo
        
        BufferedImage img = null;
        
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException ex) {
            Logger.getLogger(MTileset.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (img == null) return;
        
        //se carregou a imagem, começa a extrair os dados
        this.filePath = path;
        this.width = img.getWidth();
        this.height = img.getHeight();

        //verifica se as dimensões do tile cabem na imagem carregada
        if ((this.width < tileWidthPixels) || (this.height < tileHeightPixels)) return;
        this.tileWidth = tileWidthPixels;
        this.tileHeight = tileHeightPixels;
        
        //verifica se a imagem contém apenas tiles inteiros (as dimensões são multiplos do tileSize)
        if (this.width % tileWidthPixels != 0) return; //largura contém tiles incompletos
        if (this.height % tileHeightPixels != 0) return; //altura contém tiles incompletos
        
        //marca quantos tiles existem (horizontal e vertical)
        this.numberOfTilesX = this.width / tileWidthPixels;
        this.numberOfTilesY = this.height / tileHeightPixels;
        this.numberOfTiles = numberOfTilesY * numberOfTilesX;
        
        //guarda os pixels da imagem num vetor
        this.pixels = img.getRGB(0, 0, this.width, this.height, null, 0, this.width);
        
        this.valid = true; //a imagem pode ser usada como tileset
    }
    
    public MTileset(String path, int tileSize){
        //Em caso de tile quadrado
        this(path, tileSize, tileSize);
    }
    
    
    public boolean isValid(){
        return valid;
    }
    
    public int getNumberOfTilesX(){
        return numberOfTilesX;
    }
    
    public int getNumberOfTilesY(){
        return numberOfTilesY;
    }
    
    public int getNumberOfTiles(){
        return numberOfTiles;
    }
    
    public int getTileWidth(){
        return tileWidth;
    }
    
    public int getTileHeight(){
        return tileHeight;
    }
    
    public String getFilePath(){
        return filePath;
    }
    
    public int getTilePixel(int tileID, int xPixel, int yPixel){        
        //verifica se os argumentos são válidos (retorna BLACK em caso de erro)
        if ((xPixel < 0) || (yPixel < 0) || (xPixel >= tileWidth) || (yPixel > tileHeight)) return 0;
        if ((tileID < 0) || (tileID >= numberOfTiles)) return 0;
        
        int xPos = tileID % numberOfTilesX; //deslocamento X dentro no tileset
        int yPos = tileID / numberOfTilesX; //deslocamento Y dentro no tileset
        
        int yPix = ((yPos * tileHeight) + yPixel) * width; //y do pixel desejado
        int xPix = (xPos * tileWidth) + xPixel; //x do pixel desejado
        
        return pixels[yPix + xPix]; //pixel(xPixel, yPixel) dentro do tile(xPos, yPos)
    }

}
