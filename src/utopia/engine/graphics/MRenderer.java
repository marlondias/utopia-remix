/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utopia.engine.graphics;

import utopia.basic.GameGraphics;
import utopia.engine.graphics.canvas.MCanvas;
import utopia.engine.graphics.canvas.MCanvas2;
import utopia.engine.graphics.canvas.MCanvasTile;

/**
 * Contém uma pilha de MCanvas e o método para renderizar os canvas visíveis.
 * 
 * @author Marlon Dias
 */
public class MRenderer {
    private final int WIDTH;
    private final int HEIGHT;
    private final int vetSIZE;
    

    private int[][] pixelMatrix; //Local intermediario para manipulação dos pixels
    private int[] pixels; //Vetor com os pixels da tela
    
    public MCanvas camada1; //quadrado
    public MCanvas2 camada2; //retangulo deitado
    public MCanvas camada3; //retandulo em pe
    public MCanvas camada4;
    
    
    public MRenderer(int screenWidth, int screenHeight){
        if (screenWidth > 0) WIDTH = screenWidth;
        else WIDTH = 1;
        
        if (screenHeight > 0) HEIGHT = screenHeight;
        else HEIGHT = 1;
                
        pixelMatrix = new int[WIDTH][HEIGHT];
        
        vetSIZE = WIDTH * HEIGHT;
        pixels = new int[vetSIZE];
        
        for (int y=0; y<HEIGHT; y++){
            for (int x=0; x<WIDTH; x++){
                pixelMatrix[x][y] = (int)(System.nanoTime() + x + y); //Preenche com randomness :)
            }
        }
        
        //testes
        camada1 = new MCanvas(216, 252);
        //camada2 = new MCanvas(496, 378);
        camada2 = new MCanvas2(216, 252);
        camada3 = new MCanvas(100, 100);
        camada4 = new MCanvas(216, 252);

        camada1.loadImage("res/yeah_bitch1.png");
        camada2.loadImage("res/yeah_bitch3.png");
        camada3.loadImage("res/yeah_bitch3.png");
        camada4.loadImage("res/yeah_bitch3.png");
        
        //camada1.setPosition(300, 30);
        //camada2.setPosition((WIDTH >> 1) - (camada2.getWidth() >> 1), (HEIGHT >> 1) - (camada2.getHeight() >> 1));
        camada4.setPosition((WIDTH >> 1) - (camada4.getWidth() >> 1), (HEIGHT >> 1) - (camada4.getHeight() >> 1));
        
        camada1.show();
        camada2.show();
        camada3.show();
        camada4.show();
    }
    
    
    private int blend(int newPixel, int oldPixel){
        int newPixAlpha = newPixel & 0xff000000;
        
        if (newPixAlpha == 0xff000000) return newPixel; //pixel é opaco, substuir
        
        else if (newPixAlpha == 0) return oldPixel; //pixel é invisivel, ignorar
        
        else {
            //pixel é translúcido, aplicar AlphaBlend
            int newB = newPixel & 0xff;
            newPixel >>= 8;
            int newG = newPixel & 0xff;
            newPixel >>= 8;
            int newR = newPixel & 0xff;
            newPixel >>= 8;

            int alpha = (newPixel) & 0xff;        
            int invAlpha = 256 - alpha;
            int result = 0xff; //Novo pixel continuará opaco
            alpha++;

            result <<= 8;
            result |= (( alpha * newR + invAlpha * ((oldPixel >> 16) & 0xff)) >> 8); //red
            result <<= 8;
            result |= (( alpha * newG + invAlpha * ((oldPixel >> 8) & 0xff)) >> 8); //green
            result <<= 8;
            result |= (( alpha * newB + invAlpha * ((oldPixel) & 0xff)) >> 8); //blue

            return result;
        }
    }
    
    private void drawCanvas(MCanvas canvas){
        if (canvas == null) return;
        if (canvas.isVisible() == false) return; //ignorar MCanvas invisíveis
        if ((canvas.getPositionX() <= -canvas.getWidth()) || (canvas.getPositionY() <= -canvas.getHeight()) || (canvas.getPositionX() >= WIDTH) || (canvas.getPositionY() >= HEIGHT)) return; //ja está fora da tela
        
        for (int y=0; y<canvas.getHeight(); y++){
            for (int x=0; x<canvas.getWidth(); x++){
                int xPix = x + canvas.getPositionX(); //coordenadas dentro do RENDERER
                int yPix = y + canvas.getPositionY();
                if ((xPix >= 0) && (yPix >= 0) && (xPix < WIDTH) && (yPix < HEIGHT)){
                    pixelMatrix[xPix][yPix] = blend(canvas.getPixelColor(x, y), pixelMatrix[xPix][yPix]);
                }
            }
        }
    }
    
    private void drawCanvas(MCanvas2 canvas){
        if (canvas == null) return;
        if (canvas.isVisible() == false) return; //ignorar MCanvas invisíveis
        if ((canvas.getPositionX() <= -canvas.getWidth()) || (canvas.getPositionY() <= -canvas.getHeight()) || (canvas.getPositionX() >= WIDTH) || (canvas.getPositionY() >= HEIGHT)) return; //ja está fora da tela
        
        for (int y=0; y<canvas.getHeight(); y++){
            for (int x=0; x<canvas.getWidth(); x++){
                int xPix = x + canvas.getPositionX(); //coordenadas dentro do RENDERER
                int yPix = y + canvas.getPositionY();
                if ((xPix >= 0) && (yPix >= 0) && (xPix < WIDTH) && (yPix < HEIGHT)){
                    int pixel = canvas.getPixelColor(x, y);
                    if ((pixel == 0) || ((pixel >> 24 & 0xff) == 0)){
                        //não renderizar
                    }
                    else {
                        pixelMatrix[xPix][yPix] = blend(pixel, pixelMatrix[xPix][yPix]);
                    }
                }
            }
        }
    }
    
    private void drawCanvas(MCanvasTile canvas){
        if (canvas == null) return;
        if (canvas.isVisible() == false) return; //ignorar MCanvas invisíveis
        if ((canvas.getPositionX() <= -canvas.getWidth()) || (canvas.getPositionY() <= -canvas.getHeight()) || (canvas.getPositionX() >= WIDTH) || (canvas.getPositionY() >= HEIGHT)) return; //ja está fora da tela
        
        canvas.updateTiles();
        for (int y=0; y<canvas.getHeight(); y++){
            for (int x=0; x<canvas.getWidth(); x++){
                int xPix = x + canvas.getPositionX(); //coordenadas dentro do RENDERER
                int yPix = y + canvas.getPositionY();
                
                if ((xPix >= 0) && (yPix >= 0) && (xPix < WIDTH) && (yPix < HEIGHT)){
                    int pixel = canvas.getPixelColor(x, y);
                    if (pixelMatrix[xPix][yPix] != pixel){
                        pixelMatrix[xPix][yPix] = blend(pixel, pixelMatrix[xPix][yPix]);
                    }
                }
            }
        }
    }
    
    public void addCanvas(int xPos, int yPos, MCanvas canvas){
        canvas.show();
        canvas.setPosition(xPos, yPos);
        drawCanvas(canvas);
    }

    public void addCanvas(int xPos, int yPos, MCanvasTile canvas){
        canvas.show();
        canvas.setPosition(xPos, yPos);
        drawCanvas(canvas);
    }
    
    
    
    
    private void moveThings(){
        if ((camada1.xPosition -camada1.getWidth()) < WIDTH) camada1.xPosition++;
        else camada1.xPosition = -camada1.getWidth();

        if (camada1.yPosition > 0) camada1.yPosition--;
        else camada1.yPosition = HEIGHT;

        if (camada2.xPosition < WIDTH) camada2.xPosition++;
        else camada2.xPosition = -camada2.getWidth();

        if (camada2.yPosition < HEIGHT) camada2.yPosition++;
        else camada2.yPosition = -camada2.getHeight();

        if (camada3.xPosition < WIDTH) camada3.xPosition++;
        else camada3.xPosition = -camada3.getWidth();

        if (camada3.yPosition < HEIGHT) camada3.yPosition++;
        else camada3.yPosition = -camada3.getHeight();
        
    }
    
    private void preRender(){
        
        //Deve seguir a ordem de empilhamento (fundo primeiro, depois os de cima, topo por ultimo).
        moveThings();
        drawCanvas(camada1);        
        drawCanvas(camada2);
        drawCanvas(camada3);
        if (pixelMatrix[0][0] < 0xff606060) drawCanvas(camada4);
    }

    public int[] render(){
    	int[] pixels = new int[WIDTH * HEIGHT];
    	pixelMatrix = new int[WIDTH][HEIGHT]; //limpa a matriz
        
        preRender(); //Desenha todos os MCanvas na matriz
        
        //Copia os pixels da matriz para o vetor
        int pixelIndex = 0;
        for (int y=0; y<HEIGHT; y++){
            for (int x=0; x<WIDTH; x++){
                pixels[pixelIndex] = pixelMatrix[x][y];
                pixelIndex++;
            }
        }
        
        return pixels;
    }

}
