package utopia.engine.graphics;

import java.util.LinkedList;

import utopia.basic.GameGraphics;
import utopia.basic.GameSettings;
import utopia.basic.InputHandler;
import utopia.basic.MouseInput;
import utopia.basic.MouseInput.MouseActionType;

// Guarda e organiza todos os MSurfaces e suas posições
public abstract class MGameScreen {
	protected GameGraphics graphics = GameSettings.getGameGraphics();
	protected InputHandler input = GameSettings.getInputHandler();
	protected MouseInput mouse = GameSettings.getMouseInput();
	protected LinkedList<MSurface> surfaces = new LinkedList<MSurface>();
	private boolean onScreen; //Se está visível e pode receber interação
	//private int transition; //Nivel da transição para exibir/ocultar (0% - 100%)
	private boolean hasInputFocus = true;
	
	
	protected MGameScreen(){
		//Nothing......
	}
	
	
	public void show(){
		this.onScreen = true;
	}
	public void hide(){
		this.onScreen = false;
	}
	
	protected abstract void handleMouse(MouseActionType actionType); //Lógica do mouse

	protected abstract void handleKeyboard(); //Lógica do teclado
	
	protected abstract void updateLogic();

	public void updateAll(){
		//Atualiza interação (se estiver no foco)
		if (hasInputFocus){
			handleKeyboard();
			MouseActionType mat = mouse.getMouseAction();
			if (mat != null) handleMouse(mat);
		}
		updateLogic();
	}
	
	public LinkedList<MSurface> getGraphics(){
		return (onScreen) ? this.surfaces : null;
	}

}
