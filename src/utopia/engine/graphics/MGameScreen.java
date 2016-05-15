package utopia.engine.graphics;

import java.util.LinkedList;

import utopia.basic.GameGraphics;
import utopia.basic.InputHandler;

// Guarda e organiza todos os MSurfaces e suas posições
public abstract class MGameScreen {
	protected GameGraphics graphics;
	protected InputHandler input;
	protected LinkedList<MSurface> surfaces = new LinkedList<MSurface>();
	private boolean onScreen; //Se está visível e pode receber interação
	//private int transition; //Nivel da transição para exibir/ocultar (0% - 100%)
	
	
	protected MGameScreen(GameGraphics gg, InputHandler ih){
		this.graphics = gg;
		this.input = ih;
	}
	
	
	public void show(){
		this.onScreen = true;
	}
	public void hide(){
		this.onScreen = false;
	}
	
	public abstract void updateAll(); //Verifica as teclas e ativa coisas..
	
	public LinkedList<MSurface> getGraphics(){
		return (onScreen) ? this.surfaces : null;
	}

}
