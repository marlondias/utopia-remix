package utopia.engine.graphics.gscreen;

import java.util.LinkedList;

import utopia.basic.GameGraphics;
import utopia.basic.GameSettings;
import utopia.basic.InputHandler;
import utopia.basic.MouseInput;
import utopia.basic.MouseInput.MouseActionType;
import utopia.engine.graphics.surfaces.MSurface;

// Guarda e organiza todos os MSurfaces e suas posições
public abstract class MGameScreen {
	protected GameGraphics graphics = GameSettings.getGameGraphics();
	protected InputHandler input = GameSettings.getInputHandler();
	protected MouseInput mouse = GameSettings.getMouseInput();
	
	protected LinkedList<MSurface> surfaces = new LinkedList<MSurface>();
	private boolean onScreen; //Se está visível e pode receber interação
	private boolean hasInputFocus = true;
	
	
	protected MGameScreen(){
		//Nothing......
	}
	
	protected abstract void handleMouse(MouseActionType actionType); //Interação do mouse

	protected abstract void handleKeyboard(); //Interação do teclado
	
	protected abstract void updateLogic(); //Atualiza
	
	public void show(){
		onScreen = true;
	}
	
	public void hide(){
		onScreen = false;
	}

	public void updateAll(){
		//Atualiza interação (se estiver no foco)
		if (onScreen && hasInputFocus){
			handleKeyboard();
			MouseActionType mat = mouse.getMouseAction();
			if (mat != null) handleMouse(mat);
		}
		updateLogic();
	}
	
	public LinkedList<MSurface> getGraphics(){
		return (onScreen) ? surfaces : null;
	}

}
