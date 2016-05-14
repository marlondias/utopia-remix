package utopia.basic;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import utopia.engine.graphics.MSurface;

public class GameGraphics {
	private final Canvas targetCanvas;
	private BufferStrategy bs;
	private BufferedImage buffImg;
	private Graphics graphics;
    private Graphics2D g2d;
	private LinkedList<MSurface> layers;

	
	public GameGraphics(Canvas canvas){
		this.targetCanvas = canvas;
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		buffImg = gc.createCompatibleImage(canvas.getWidth(), canvas.getHeight());
		layers = new LinkedList<MSurface>();
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
            //g2d.setComposite(comp);


            
            //A magia acontece aqui..
            int px, py;
            BufferedImage tmp;
            for (int i=0; i<layers.size(); i++){
            	tmp = layers.get(i).getRenderedImage();
            	px = layers.get(i).getX();
            	py = layers.get(i).getY();
            	if (tmp != null){
            		g2d.drawImage(tmp, px, py, tmp.getWidth(), tmp.getHeight(), null);
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

    public void addLayer(MSurface surface){
    	layers.add(surface);
    }

}
