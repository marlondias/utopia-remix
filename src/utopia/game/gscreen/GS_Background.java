package utopia.game.gscreen;

import utopia.basic.MouseInput.MouseActionType;
import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.msurfaces.StaticImage;

public class GS_Background extends MGameScreen {
	private StaticImage bg;
	
	public GS_Background() {
		super();
		
        bg = new StaticImage(800, 600, "res/ui_elements/deep_space.jpg");
        bg.setPosition(0, 0);

        super.surfaces.add(bg);
	}

	@Override
	protected void handleMouse(MouseActionType actionType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleKeyboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateLogic() {
		// TODO Auto-generated method stub
		
	}

}
