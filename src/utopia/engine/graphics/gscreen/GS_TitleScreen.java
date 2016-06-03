package utopia.engine.graphics.gscreen;

import java.awt.Point;

import utopia.basic.GameState;
import utopia.basic.GameStateManager;
import utopia.basic.Launcher;
import utopia.basic.controls.MouseInput.MouseActionType;
import utopia.engine.graphics.surfaces.StaticImage;

public class GS_TitleScreen extends MGameScreen {
	private StaticImage bg = new StaticImage("res/ui_elements/deep_space.jpg");
	private StaticImage title = new StaticImage("res/ui_elements/title.png");
	private Point pointer = new Point();
	private boolean clicked = false;
	
	
	public GS_TitleScreen() {
		bg.show(0);
		title.show(0);
		
		super.surfaces.add(bg);
		super.surfaces.add(title);
	}

	
	@Override
	protected void handleMouse(MouseActionType actionType) {
		if (actionType == MouseActionType.CLICK){
			clicked = true;
			pointer = mouse.getMousePosition();
		}
		
	}
	
	@Override
	protected void handleKeyboard() {
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void updateLogic() {
		if (clicked){
			clicked = false;
		
			if (pointer.x > 319 && pointer.x < 471){
				if (pointer.y > 302 && pointer.y < 335){
					//Novo jogo
					System.out.println("Novo...");
					GameStateManager.setGameState(GameState.GAME_FIELD);
				}
				else if(pointer.y > 374 && pointer.y < 408){
					//Carregar
					System.out.println("Carregar...");
					GameStateManager.setGameState(GameState.GAME_STATISTICS);
				}
			}
			else if(pointer.y > 509 && pointer.y < 543){
				//Sobre ou Sair
				if (pointer.x > 61 && pointer.x < 158){
					//Função do sobre...
					System.out.println("Marlon Dias - 2016");
				}
				else if (pointer.x > 641 && pointer.x < 729){
					//Função do sair...
					System.out.println("Adiós!");
				}
			}
		}
		
	}

}
