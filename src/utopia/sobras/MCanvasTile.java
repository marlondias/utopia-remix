/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utopia.sobras;

import utopia.engine.graphics.MTileset;

/**
 *
 * @author Marlon Dias
 */
public class MCanvasTile extends MCanvas {
    //Substitui a MTileScreen
    
    private MTileset tileset; //Permite apenas 1 tileset para o canvas
    private int tileWidth;
    private int tileHeight;
    
    private int visibleTilesX;
    private int visibleTilesY;
    private int[][] tileID;
    
    private int xOffset; //Em pixels
    private int yOffset;
    private int xTileOffset; //Em tiles
    private int yTileOffset;
    
    private boolean needUpdate = true;
    private MLevel level = new MLevel("res/utopia-terreno-teste1.bmp"); //testes
    
    
    public MCanvasTile(int width, int height, MTileset tileset){
        super(width, height);
        if (valid == false) return; //Ja havia um erro
        
        valid = false;
        
        if (tileset == null) return;
        if (tileset.isValid() == false) return;
        this.tileset = tileset;
        
        tileWidth = tileset.getTileWidth();
        tileHeight = tileset.getTileHeight();
        
        //Calcula quantos tiles completos cabem na tela, mais 1 para caber o tile com offset
        this.visibleTilesX = (width / tileWidth) + 1;
        this.visibleTilesY = (height / tileHeight) + 1;
        
        //Se houver espaço para um tile incompleto, aumenta 1 nos tiles visiveis
        if (width % tileWidth != 0) visibleTilesX++;
        if (height % tileHeight != 0) visibleTilesY++;
        
        this.tileID = new int[visibleTilesX][visibleTilesY];
        
        valid = true;
    }
    
    
    public void loadLevelFromImage(String filePath){
        if (valid == false) return;
        if (filePath == null) return;
        
        valid = false; //carregar level pode causar erros
        level = new MLevel(filePath);
        if (level == null) return; //não carregou
        
        valid = true; //canvas ok
    }
    
    private void checkBorders(){
        if (valid == false) return;
        
        //Verifica se ultrapassou as extremidades da tela
        if (xTileOffset < 0){
            xTileOffset = 0;
            xOffset = 0; 
        }
        else {
            int lastTileX = (WIDTH % tileWidth != 0) ? 1 : 0; //+1 se houver incompleto no final
            lastTileX += level.getWidth();
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
            lastTileY += level.getHeight();
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
        if (valid == false) return;
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
        if (valid == false) return;
        needUpdate = true;
        int lastTile = (WIDTH % tileWidth != 0) ? 1 : 0; //+1 se houver incompleto no final
        lastTile += level.getWidth();        
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
        lastTile += level.getHeight();
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
    
    public void updateTiles(){
        if (valid == false) return;
        if (needUpdate == false) return; //não houve mudança
        
        needUpdate = false; //as mudança ja estão sendo aplicadas
        tileID = level.getLevelTiles(xTileOffset, yTileOffset, (visibleTilesX + xTileOffset), (visibleTilesY + yTileOffset)); //copia os tiles do Level para a matriz
        
        int yTilePos = 0;
        int yPixelPos = yOffset;
        for (int y=0; y<HEIGHT; y++){
            int xTilePos = 0;
            int xPixelPos = xOffset;
            for (int x=0; x<WIDTH; x++){
                pixelMatrix[x][y] = tileset.getTilePixel(tileID[xTilePos][yTilePos], xPixelPos, yPixelPos);
                xPixelPos++;
                if (xPixelPos >= tileWidth){
                    //Ultrapassou o tile, vai para o inicio do proximo (em X)
                    xPixelPos = 0;
                    xTilePos++;
                }
            }
            yPixelPos++;
            if (yPixelPos >= tileHeight){
                //Ultrapassou o tile, vai para o inicio do proximo (em Y)
                yPixelPos = 0;
                yTilePos++;
            }
        }
    }
    
}
