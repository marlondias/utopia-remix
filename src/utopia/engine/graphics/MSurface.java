package utopia.engine.graphics;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

public abstract class MSurface {
	private int posX;
	private int posY;
	private Dimension dimension = new Dimension();
	private boolean visible;
	private BufferedImage renderImg;
	protected Graphics2D drawingSurface;
	
	
	protected MSurface(int x, int y, int width, int height){
		this.posX = x;
		this.posY = y;
		this.dimension.setSize(width, height);
		this.visible = false;
		
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		this.renderImg = gc.createCompatibleImage(width, height);
		drawingSurface = renderImg.createGraphics();
		
	}

	
	protected void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	public int getPositionX(){
		return this.posX;
	}
	public int getPositionY(){
		return this.posY;
	}

	protected void setDimension(int width, int height){
		this.dimension.setSize(width, height);
	}
	public int getWidth(){
		return (int)this.dimension.getWidth();
	}
	public int getHeight(){
		return (int)this.dimension.getHeight();
	}

	public abstract void updateGraphics(); //Atualiza posições e objetos antes de exibir
	
	public BufferedImage getRenderedImage(){
		return this.renderImg;
	}

}
