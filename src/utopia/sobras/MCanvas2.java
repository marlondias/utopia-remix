/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utopia.sobras;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * 
 * Define uma área retangular, onde ficarão informações de imagem (pixels)
 * Manipulações de imagem devem ser feitas no canvas, para depois serem renderizadas.
 * 
 * @author Marlon Dias
 */
public class MCanvas2 {
    protected final int WIDTH; //Dimensões do canvas (em pixels)
    protected final int HEIGHT;
    private final int SIZE; //Tamanho do vetor (w * h)
    protected final int[] pixels; //Matriz com os pixels
    protected boolean valid; //Indica se pode ser utilizado
    private boolean visible; //Indica se deve renderizar o conteúdo do canvas
    public int xPosition; //Usado para posicionar o canvas na tela (renderer)
    public int yPosition;
    
    
    public MCanvas2 (int width, int height){
        this.valid = false;
        this.visible = false;
        
        this.WIDTH = (width < 1) ? 1 : width;
        this.HEIGHT = (height < 1) ? 1 : height;
        
        this.SIZE = width * height;
        this.pixels = new int[SIZE];

        if ((width < 1) || (height < 1)) return;
        
        int pixelIndex = 0;
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                pixels[pixelIndex++] = (0xff << 24) | (y-x << 16) | (x-y << 8) | (x+y); //deixa o canvas degrade
            }
        }
        
        this.valid = true;
    }
    
    
    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
    
    public void setPosition(int x, int y){
        xPosition = x;
        yPosition = y;
    }

    public int getPositionX() {
        return xPosition;
    }

    public int getPositionY() {
        return yPosition;
    }
    
    public void show(){
        if (valid == true){
            visible = true;
        }
    }
    
    public void hide(){
        visible = false;
    }
    
    public boolean isVisible(){
        return visible;
    }
    
    
    public void setPixelColor(int x, int y, int colorValue){
        if (valid == false) return; //invalido
        if ((x < 0) || (y < 0) || (x >= WIDTH) || (y >= HEIGHT)) return; //out of bounds
        pixels[y * WIDTH + x] = colorValue;
    }
    
    public void setPixelColor(int index, int colorValue){
        if (valid == false) return; //invalido
        if ((index < 0) || (index >= SIZE)) return; //out of bounds
        pixels[index] = colorValue;
    }

    public int getPixelColor(int x, int y){
        if (valid == false) return 0xffff0000; //erro = vermelho
        if ((x < 0) || (y < 0) || (x >= WIDTH) || (y >= HEIGHT)) return 0xffff0000; //out of bounds
        return pixels[y * WIDTH + x];
    }
    
    public int getPixelColor(int index){
        if (valid == false) return 0xffff0000; //erro = vermelho
        if ((index < 0) || (index >= SIZE)) return 0xffff0000; //out of bounds
        return pixels[index];
    }
        
    public void setCanvasToColor(int colorValue){
        if (valid == false) return;
        for (int i=0; i<SIZE; i++){
            pixels[i] = colorValue;
        }
    }
    
    
    public void loadImage(String filePath){
        if (valid == false) return; //Se for inválido, nem precisa carregar
        if (filePath == null) return;
        
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            Logger.getLogger(MCanvas2.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (img == null) return;
        
        int yMax = HEIGHT;
        int xMax = WIDTH;
        if (img.getHeight() < HEIGHT) yMax = img.getHeight();
        if (img.getWidth() < WIDTH) xMax = img.getWidth();

        for (int y=0; y<yMax; y++){
            for (int x=0; x<xMax; x++){
                pixels[y * WIDTH + x] = img.getRGB(x, y); //Copia a imagem para o canvas
            }
        }
    }
    
}
