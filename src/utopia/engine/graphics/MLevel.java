/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utopia.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Marlon Dias
 */
public class MLevel {
    private int width; //tiles de largura
    private int height; //tiles de altura
    private int[][] tiles;
    private boolean loaded = false;
    
    
    public MLevel(String terrainImgPath){
        if (terrainImgPath == null) return;
        loadLevelFromImage(terrainImgPath);    
    }
    
    
    private void loadLevelFromImage(String filePath){
        //recebe caminho em string e tenta carregar o mapa        
        BufferedImage imagem = null;
        
        try {
            imagem = ImageIO.read(new File(filePath));
        }
        catch (IOException e){
            return; //não abriu o arquivo
        }
        
        if (imagem == null) return; //evita nullptrex
        
        this.height = imagem.getHeight();
        this.width = imagem.getWidth();
        
        if (height < 15 || height > 150){
            return; //altura invalida
        }
        if (width < 15 || width > 150){
            return; //largura invalida
        }
        
        tiles = new int[width][height];
        
        int rgb, red, green, blue;
        
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                //pega um numero enorme de RGB e extrai 3 valores
                rgb = imagem.getRGB(x, y); 
                red = (rgb >> 16 ) & 0x000000FF;
                green = (rgb >> 8 ) & 0x000000FF;
                blue = (rgb) & 0x000000FF;
                
                //compara os RGB e obtem um numero pra cada tipo de terreno
                if (red == 0 && green == 255 && blue == 0){
                    tiles[x][y] = 1; //verde = livre
                }
                else if (red == 0 && green == 0 && blue == 255){
                    tiles[x][y] = 2; //azul = liquido
                }
                else if (red == 0 && green == 0 && blue == 0){
                    tiles[x][y] = 3; //preto = buraco
                }
                else if (red == 255 && green == 0 && blue == 0){
                    tiles[x][y] = 4; //vermelho = montanha
                }
                else {
                    tiles[x][y] = 0; //erro
                }
            }
        }
        loaded = true; //carregou perfeitamente
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public int[][] getLevelTiles(int xFirstTile, int yFirstTile, int matrixWidth, int matrixHeight){
        //checar erros depois
        
        int[][] retorno = new int[matrixWidth][matrixHeight]; //cria a matriz de retorno
        
        //percorre a matriz de retorno
        for (int y=0; y<matrixHeight; y++){
            for (int x=0; x<matrixWidth; x++){
                if ( ((x+xFirstTile) < width) && ((y+yFirstTile) < height) ){
                    //se existirem ainda estiver dentro dos limites do level, copia um tile
                    retorno[x][y] = tiles[x+xFirstTile][y+yFirstTile];
                }
                else{
                    //se ultrapassou os limites, copia o tile 0
                    retorno[x][y] = 0;
                }
            }
        }
        
        return retorno;
    }

}
