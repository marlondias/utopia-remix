package utopia.basic;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GameGraphics {
	private final Canvas targetCanvas;
	private BufferStrategy bs;
	private BufferedImage buffImg;
	private int[] imgPixels;

	
	public GameGraphics(Canvas canvas){
		this.targetCanvas = canvas;
		buffImg = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_RGB);
	    imgPixels = ((DataBufferInt)buffImg.getRaster().getDataBuffer()).getData(); //macumba para acessar os pixels
	}
	
    public void render(){
    	bs = targetCanvas.getBufferStrategy(); //associa um buffer que faz "coisas"
    	if (bs == null){
            //se for nulo, crie um novo buffer
            targetCanvas.createBufferStrategy(3); //tripleBuffering (reduz defeitos de renderização)
            return;
        }
        
    	this.pixelDrawing();
        
        Graphics gfx = bs.getDrawGraphics();
        //gfx.drawRect(0, 0, getWidth(), getHeight()); //cria um retangulo do tamanho exato de CANVAS
        gfx.drawImage(buffImg, 0, 0, targetCanvas.getWidth(), targetCanvas.getHeight(), null); //desenha o que estiver em BUFFIMG
        gfx.dispose(); //libera manualmente os recursos usados
        bs.show(); //exibe o que está no buffer
    }

    private void pixelDrawing(){
    	//Placeholder
    	if (imgPixels[0] % 5 == 0){
        	for (int i=0; i<imgPixels.length; i++){
                imgPixels[i] = 0xff000000 | (int)(System.nanoTime() >> 8);
        	}    	
    	}
    	else{
        	for (int i=0; i<imgPixels.length; i++){
                imgPixels[i] += 13;
        	}
    	}

    }

}
