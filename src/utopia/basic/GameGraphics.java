package utopia.basic;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GameGraphics {
	private final Canvas targetCanvas;
	private BufferStrategy bs;
	private BufferedImage buffImg;
	private Graphics graphics;
    private Graphics2D g2d;
	private int[] imgPixels;

	
	public GameGraphics(Canvas canvas){
		this.targetCanvas = canvas;

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    GraphicsConfiguration gc = gd.getDefaultConfiguration();

		buffImg = gc.createCompatibleImage(canvas.getWidth(), canvas.getHeight());
	    imgPixels = ((DataBufferInt)buffImg.getRaster().getDataBuffer()).getData(); //macumba para acessar os pixels
	}
	
    public void render(){
        bs = targetCanvas.getBufferStrategy(); //associa um buffer, ou cria se necessário
    	if (bs == null){
            targetCanvas.createBufferStrategy(2);
            return;
        }

        try {
            g2d = buffImg.createGraphics();

            //coisas aqui
 
            graphics = bs.getDrawGraphics();
            graphics.drawImage(buffImg, 0, 0, null);
        	if(!bs.contentsLost()) bs.show();
       	}
    	finally {
    		if(graphics != null) graphics.dispose(); //libera manualmente os recursos
    		if(g2d != null) g2d.dispose();
    	}
    	
    }

    public void renderOld(){
    	bs = targetCanvas.getBufferStrategy(); //associa um buffer que faz "coisas"
    	if (bs == null){
            //se for nulo, crie um novo buffer
            targetCanvas.createBufferStrategy(2); //tripleBuffering (reduz defeitos de renderização)
            return;
        }

        //coisas aqui
        	
        Graphics gfx = bs.getDrawGraphics();
        //gfx.drawRect(0, 0, getWidth(), getHeight()); //cria um retangulo do tamanho exato de CANVAS
        gfx.drawImage(buffImg, 0, 0, targetCanvas.getWidth(), targetCanvas.getHeight(), null); //desenha o que estiver em BUFFIMG
        gfx.dispose(); //libera manualmente os recursos usados
        bs.show(); //exibe o que está no buffer

    }

    private int blend(int newPixel, int oldPixel){
    	//Sobrepõe dois pixels ARGB
        int newAlpha = newPixel & 0xff000000;
        if (newAlpha == 0xff000000) return newPixel; //pixel é opaco, substituir
        else if (newAlpha == 0) return oldPixel; //pixel é transparente, ignorar
        else {
            //pixel é semi-transparente, aplicar AlphaBlend
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

    public void addPixels(int[] pixels){
    	//Adiciona pixels sobre os já existentes
    	int i = 0;
    	for(int y=0; y<(GameLoop.HEIGHT); y++){
    		for(int x=0; x<(GameLoop.WIDTH); x++){
    			imgPixels[i] = blend(pixels[i], imgPixels[i]);
    			i++;
    		}
    	}
    }
    
}
