package utopia.basic;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.MSurface;

public class GameGraphics {
	private final Canvas targetCanvas;
	private BufferStrategy bs;
	private BufferedImage buffImg;
	private Graphics graphics;
    private Graphics2D g2d;
	private LinkedList<MGameScreen> layers = new LinkedList<MGameScreen>();

	
	public GameGraphics(Canvas canvas){
		this.targetCanvas = canvas;
		this.buffImg = GameSettings.getDefaultGC().createCompatibleImage(GameSettings.getResolution().width, GameSettings.getResolution().height);
	}
	
	
    public void render(){
    	/* 
    	 * Cria uma imagem do tamanho de CANVAS e desenha tudo o que estiver
    	 * na pilha LAYERS, se possível.
    	 * Finaliza copiando a imagem para o buffer e libera os recursos.
    	 */
        bs = targetCanvas.getBufferStrategy(); //associa um buffer, ou cria se necessário
    	if (bs == null){
            targetCanvas.createBufferStrategy(2);
            return;
        }

        try {
            g2d = buffImg.createGraphics();
            
            //Para cada camada, desenha o conteúdo das subcamadas (se possível)
            BufferedImage tmp;
            for (MGameScreen gScr : layers){
            	if (gScr.getGraphics() == null) continue; //Camada não está visivel
            	for (MSurface surf : gScr.getGraphics()){
            		tmp = surf.getRenderedImage();
                	if (tmp != null){
                		g2d.drawImage(tmp, surf.getX(), surf.getY(), tmp.getWidth(), tmp.getHeight(), null);
                	}
            	}
            }
            
            graphics = bs.getDrawGraphics();
            graphics.drawImage(buffImg, 0, 0, null);
        	if(!bs.contentsLost()) bs.show();
       	}
    	finally {
    		if(graphics != null) graphics.dispose(); //libera manualmente os recursos
    		if(g2d != null) g2d.dispose();
    	}
    	
    }

    public void addLayer(MGameScreen gs){
    	if (gs != null) layers.add(gs);
    }

}
