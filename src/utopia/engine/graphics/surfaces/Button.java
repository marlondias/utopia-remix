package utopia.engine.graphics.surfaces;

import java.awt.Color;

public class Button extends MSurface {
	public enum ButtonState{
		DISABLED, //Não recebe interação
		NORMAL, //Ativo e esperando click
		HOVER, //Mouse está sobre ele
		ACTIVE //Foi clicado
	}
	
	private ButtonState state;
	private int mouseX, mouseY;
	private boolean clicked;
	private long time_releaseClick;
	private boolean enabled = true;
	
	private Color colorDisabled = Color.GRAY;
	private Color colorNormal = Color.WHITE;
	private Color colorHover = Color.CYAN;
	private Color colorActive = Color.BLUE;

	
	public Button(int width, int height) {
		super(width, height);
		
		//Calcular tamanho da fonte e centralizar texto/icone
		validate();
	}
	
	
	private void checkState(){
		//Muda o estado, dependendo da interação
		
		if (!enabled){
			state = ButtonState.DISABLED;
		}
		else if (mouseX >= 0 && mouseX < getWidth() && mouseY >= 0 && mouseY < getHeight()){
			//Mouse está sobre o botão
			if (clicked) state = ButtonState.ACTIVE;
			else state = ButtonState.HOVER;
		}
		else state = ButtonState.NORMAL;
		
	}
	
	private void drawButton(){
		switch (state) {
		case DISABLED:
			getDrawingSurf().setColor(colorDisabled);
			break;
		case NORMAL:
			getDrawingSurf().setColor(colorNormal);
			break;
		case HOVER:
			getDrawingSurf().setColor(colorHover);
			break;
		case ACTIVE:
			getDrawingSurf().setColor(colorActive);
			break;
		default:
			break;
		}
		
		getDrawingSurf().fillRect(0, 0, getWidth(), getHeight());
		
	}
	
	public void setMousePosition(int x, int y){
		//Informa a posição do ponteiro
		mouseX = x - getX();
		mouseY = y - getY();
	}

	public void setMouseClick(){
		//Informa que ocorreu um click
		clicked = true;
		time_releaseClick = System.currentTimeMillis() + 90;
	}
	
	public boolean isActive(){
		return (state == ButtonState.ACTIVE);
	}

	@Override
	public void updateGraphics() {
		checkState();
		if (clicked && System.currentTimeMillis() >= time_releaseClick) clicked = false;
		drawButton();
	}
	
}
