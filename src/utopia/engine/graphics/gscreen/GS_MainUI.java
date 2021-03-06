package utopia.engine.graphics.gscreen;

import utopia.basic.controls.MouseInput.MouseActionType;
import utopia.engine.graphics.surfaces.StaticImage;

public class GS_MainUI extends MGameScreen {
	private StaticImage hud1;
    private StaticImage hud2;
    private StaticImage hud3;

    
    public GS_MainUI() {
		hud1 = new StaticImage("res/ui_elements/GUI-azul_status2.png");
        hud1.setPosition(44, 10);

        hud2 = new StaticImage("res/ui_elements/GUI-azul_lateral.png");
        hud2.setPosition(730, 164);

        hud3 = new StaticImage("res/ui_elements/GUI-azul_botoes.png");
        hud3.setPosition(294, 527);

        super.surfaces.add(hud1);
        super.surfaces.add(hud2);
        super.surfaces.add(hud3);
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
