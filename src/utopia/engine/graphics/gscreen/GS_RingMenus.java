package utopia.engine.graphics.gscreen;

import java.awt.Point;

import utopia.basic.MouseInput.MouseActionType;
import utopia.engine.graphics.surfaces.RingMenu;
import utopia.game.planet.Block;

public class GS_RingMenus extends MGameScreen {
	private RingMenu rm_building;
	private RingMenu rm_vehicle;
	private RingMenu rm_terrain;
	
	private Point pointer = new Point();
	private boolean clicked = false;
	private Block target;
	
	
	public GS_RingMenus(){
		rm_building = new RingMenu(120);
		rm_terrain = new RingMenu(60);
		rm_vehicle = new RingMenu(70);
		
		rm_building.setCenterAt(400, 200);
		
		super.surfaces.add(rm_building);
	}
	

	@Override
	protected void handleMouse(MouseActionType actionType) {
		
		switch (actionType) {
		case MOVE:
			//Highlighted
			pointer.x = super.mouse.getMousePosition().x;
			pointer.y = super.mouse.getMousePosition().y;
			break;
		case CLICK:
			//Selected
			clicked = true;
			break;
		default:
			break;
		}
		
	}

	@Override
	protected void handleKeyboard() {
    	if (super.input.num1.isPressed()){
    		rm_building.transitionOut(300);
    	}
    	if (super.input.num2.isPressed()){
    		rm_building.transitionIn(300);
    	}
		
	}

	@Override
	protected void updateLogic() {
		//Mostra o highlight de algum RM
		rm_building.checkMouse(pointer.x, pointer.y);
		
		//Trata o click
		if (clicked){
			clicked = false;
			rm_building.setClick(); //Aqui ele retorna o Enum escolhido (tratar depois)
			rm_building.transitionOut(300);
			
			//Perde o foco do mouse e teclado
		}
		
	}
	
}
