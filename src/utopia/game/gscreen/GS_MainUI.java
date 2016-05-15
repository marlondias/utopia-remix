package utopia.game.gscreen;

import utopia.basic.GameGraphics;
import utopia.basic.InputHandler;
import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.msurfaces.StaticImage;

public class GS_MainUI extends MGameScreen {
	private StaticImage hud1;
    private StaticImage hud2;
    private StaticImage hud3;

    
    public GS_MainUI(GameGraphics gg, InputHandler ih) {
		super(gg, ih);

		hud1 = new StaticImage(149, 150, "res/ui_elements/GUI-azul_status2.png");
        hud1.setPosition(44, 10);

        hud2 = new StaticImage(70, 420, "res/ui_elements/GUI-azul_lateral.png");
        hud2.setPosition(730, 164);

        hud3 = new StaticImage(491, 57, "res/ui_elements/GUI-azul_botoes.png");
        hud3.setPosition(294, 527);

        super.surfaces.add(hud1);
        super.surfaces.add(hud2);
        super.surfaces.add(hud3);
	}
    
	@Override
	public void updateAll() {
		return;
	}
    
}