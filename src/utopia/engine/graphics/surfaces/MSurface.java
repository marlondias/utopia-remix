package utopia.engine.graphics.surfaces;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import utopia.engine.graphics.TransitionController;

public abstract class MSurface {
	private int width = 1; //Dimensões da superfície
	private int height = 1;
	private int posX = 0; //Posição relativa
	private int posY = 0;
	private boolean valid = false; //Indica se a superfície pode ser usada com segurança
	private BufferedImage renderImg; //Guarda o conteúdo a ser renderizado
	private Graphics2D drawingSurface; //Permite alterar o conteúdo
	private AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);
	private TransitionController transition = new TransitionController();
	private double currentTransitionLevel; //Guarda valor temporário (por eficiência)

	
	protected MSurface(int width, int height){
		if (width > 0) this.width = width;
		if (height > 0) this.height = height;
		this.renderImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		this.drawingSurface = renderImg.createGraphics();
		this.drawingSurface.setComposite(ac);
	}
	
	
	protected double getTransitionLevel(){
		//Informa o valor para que a subclasse crie o efeito
		return currentTransitionLevel;
	}
	
	protected void validate(){
		valid = true;
	}
	
	protected void invalidate(){
		valid = false;
	}
	
	protected boolean isValid(){
		return valid;
	}

	protected void changeSize(int w, int h){
		if (w<1 || h<1) return; //Tamanho inválido
		width = w;
		height = h;
		renderImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		if (drawingSurface != null) drawingSurface.dispose();
		drawingSurface = renderImg.createGraphics();
		drawingSurface.setComposite(ac);
	}

	protected Graphics2D getDrawingSurf(){
		if (drawingSurface == null){
			drawingSurface = renderImg.createGraphics();
			drawingSurface.setComposite(ac);
		}
		return drawingSurface;
	}

	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getX(){
		return posX;
	}
	
	public int getY(){
		return posY;
	}

	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}

	public void setCenterAt(int x, int y){
		posX = x - (width >> 1);
		posY = y - (width >> 1);
	}

	public void show(int durationMS){
		transition.transitionIn(durationMS);
	}
	
	public void hide(int durationMS){
		transition.transitionOut(durationMS);
	}

	
	public boolean isInside(int x, int y){
		//Verifica se o ponto informado está dentro da superfície
		return ((x >= posX) && (x < posX+width) && (y >= posY) && (y < posY + height));
	}
	
	
	public abstract void updateGraphics(); //Atualiza posições e objetos antes de exibir
	
	public void selfDraw(Graphics2D g2d){
		if (!valid) return;
		
		currentTransitionLevel = transition.getTransitionLevel();
		updateGraphics();
		
		//Desenha, se estiver visível
		if (currentTransitionLevel > 0) g2d.drawImage(renderImg, posX, posY, width, height, null);
	}

}
