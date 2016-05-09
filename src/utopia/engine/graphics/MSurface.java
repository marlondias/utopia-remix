package utopia.engine.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class MSurface {
	private int width; //Dimensões da superfície
	private int height;
	private final BufferedImage renderImg; //Guarda o conteúdo visual a ser renderizado
	protected final Graphics2D drawingSurface; //Permite alterar o conteúdo
	private AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0F);
	private boolean visible; //Indica se a imagem será renderizada

	private float opacity; //Nível do canal alpha
	public int posX;
	public int posY;

	
	protected MSurface(int width, int height){
		this.width = width;
		this.height = height;
		this.renderImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		drawingSurface = renderImg.createGraphics();
		drawingSurface.setComposite(ac);
		this.visible = true; //mudar?
		this.opacity = 1.0F;
		this.posX = 0;
		this.posY = 0;
	}


	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	public void show(){
		this.visible = true;
	}
	public void hide(){
		this.visible = false;
	}
	
	public void setOpacity(float valor){
		this.opacity = valor;
		drawingSurface.setComposite(ac.derive(valor));
	}
	
	public abstract void updateGraphics(); //Atualiza posições e objetos antes de exibir
	
	public BufferedImage getRenderedImage(){
		if (!visible) return null;
		return this.renderImg;
	}

}
