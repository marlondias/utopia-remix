package utopia.game.gscreen;

import utopia.basic.GameGraphics;
import utopia.basic.InputHandler;
import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.msurfaces.StaticImage;

public class GS_Background extends MGameScreen {
	private StaticImage bg;
	
	public GS_Background(GameGraphics gg, InputHandler ih) {
		super(gg, ih);
		
        bg = new StaticImage(800, 600, "res/ui_elements/deep_space.jpg");
        bg.setPosition(0, 0);

        super.surfaces.add(bg);
	}

	@Override
	public void updateAll() {
		return;
	}


}
