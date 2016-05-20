package utopia.engine.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class MSurface {
	private int width = 1; //Dimensões da superfície
	private int height = 1;
	private int posX = 0; //Posição relativa
	private int posY = 0;
	private boolean valid = false; //Indica se a superfície pode ser usada com segurança
	private boolean visible = true; //Indica se a imagem será renderizada
	private BufferedImage renderImg; //Guarda o conteúdo a ser renderizado
	private Graphics2D drawingSurface; //Permite alterar o conteúdo
	private AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC);

	private float opacity = 1.0F; //Nível do canal alpha
	private boolean fadingIn = false;
	private boolean fadingOut = false;
	private long fadeStep; //Tempo para atualizar a opacidade (ms)
	private long lastFadeStep; //Ultima atualização de opacidade (ms)

	
	protected MSurface(int width, int height){
		if (width > 0) this.width  = width;
		if (height > 0) this.height = height;
		this.renderImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		this.drawingSurface = renderImg.createGraphics();
		this.drawingSurface.setComposite(ac);
	}
	
	
	protected void validate(){
		this.valid = true;
	}
	protected void invalidate(){
		this.valid = false;
	}

	public void show(){
		this.visible = true;
	}
	public void hide(){
		this.visible = false;
	}
	
	protected void drawBuffImg(BufferedImage img, int x1, int y1, int x2, int y2){
		//Desenha parte da imagem IMG
		if (drawingSurface == null){
			drawingSurface = renderImg.createGraphics();
			drawingSurface.setComposite(ac);
		}
		drawingSurface.drawImage(img, 0, 0, this.width, this.height, x1, y1, x2, y2, null);
	}
	protected void drawBuffImg(BufferedImage img){
		//Desenha toda a imagem IMG
		this.drawBuffImg(img, 0, 0, img.getWidth(), img.getHeight());
	}
	
	protected Graphics2D getDrawingSurf(){
		if (drawingSurface == null){
			drawingSurface = renderImg.createGraphics();
			drawingSurface.setComposite(ac);
		}
		return drawingSurface;
	}
	
	protected void changeSize(int w, int h){
		if (w<1 || h<1) return; //Tamanho inválido
		this.width = w;
		this.height = h;
		this.renderImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		if (drawingSurface != null) drawingSurface.dispose();
		drawingSurface = renderImg.createGraphics();
		drawingSurface.setComposite(ac);
	}

	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	
	public int getX(){
		return this.posX;
	}
	public int getY(){
		return this.posY;
	}

	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}

	public void setCenterAt(int x, int y){
		this.posX = x - (width >> 1);
		this.posY = y - (width >> 1);
	}

	public void fadeIn(int durationMS){
		if (durationMS < 100) return; //Erro
		if (opacity == 1.0F) return; //Não precisa
		fadeStep = durationMS / (long)((1.0F - opacity) * 100);
		fadingOut = false;
		fadingIn = true;
	}
	public void fadeOut(int durationMS){
		if (durationMS < 100) return; //Erro
		if (opacity == 0) return; //Não precisa
		fadeStep = durationMS / (long)(opacity * 100);
		fadingOut = true;
		fadingIn = false;
	}

	protected void updateOpacity(){
		if ((System.currentTimeMillis() - lastFadeStep) >= fadeStep){
			//Atualiza a opacidade em 1%
			if (fadingIn) opacity += 0.01F;
			if (fadingOut) opacity -= 0.01F;
			lastFadeStep = System.currentTimeMillis();
		}
		//Se ultrapassar algum extremo, interrompe o efeito
		if (opacity < 0){
			opacity = 0;
			fadingIn = false;
			fadingOut = false;
		}
		if (opacity > 1.0F){
			opacity = 1.0F;
			fadingIn = false;
			fadingOut = false;
		}
		
		//Aplicar opacidade na imagem
		//NAO APLICA ESSA CACETA!!
	}
	
	public abstract void updateGraphics(); //Atualiza posições e objetos antes de exibir
	
	public BufferedImage getRenderedImage(){
		if (!valid) return null;
		updateGraphics();

		//if (fadingIn || fadingOut) updateOpacity();
		if (!visible) return null;

		return this.renderImg;
	}

}
