package utopia.engine.graphics;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public abstract class MSurface {
	private int width;
	private int height;
	private BufferedImage renderImg;
	protected Graphics2D drawingSurface;
	
	
	protected MSurface(int width, int height){
		this.width = width;
		this.height = height;
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		this.renderImg = gc.createCompatibleImage(width, height);
		drawingSurface = renderImg.createGraphics();
	}


	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}

	public abstract void updateGraphics(); //Atualiza posições e objetos antes de exibir
	
	public BufferedImage getRenderedImage(){
		return this.renderImg;
	}

}
