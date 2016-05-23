package utopia.engine.graphics.gscreen;

import utopia.basic.MouseInput.MouseActionType;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.surfaces.StaticImage;
import utopia.engine.graphics.surfaces.TiledMap;
import utopia.game.planet.Planet;

public class GS_GameField extends MGameScreen {
    private TiledMap terreno;
    private TiledMap resOverlay;
    private StaticImage moldura;

    
    public GS_GameField(Planet planet) {
    	terreno = new TiledMap(new MTileset(48, "res/tileset48-utopia1.png"), 430, 430, planet.getTerrainMap());
        terreno.setPosition(255, 48);

    	resOverlay = new TiledMap(new MTileset(48, "res/tileset48-utopia1.png"), 430, 430, planet.getResourceMap());
        resOverlay.setPosition(255, 48);

        moldura = new StaticImage(450, 450, "res/ui_elements/GUI-frame.png");
        moldura.setPosition(246, 39);
        
        super.surfaces.add(terreno);
        super.surfaces.add(resOverlay);
        super.surfaces.add(moldura);
	}
    

	@Override
	protected void handleMouse(MouseActionType actionType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleKeyboard() {
    	if (super.input.up.isPressed()){
    		terreno.moveU();
    		resOverlay.moveU();
    	}
        if (super.input.down.isPressed()){
        	terreno.moveD();
        	resOverlay.moveD();
        }
        if (super.input.left.isPressed()){
        	terreno.moveL();
        	resOverlay.moveL();
        }
        if (super.input.right.isPressed()){
        	terreno.moveR();
        	resOverlay.moveR();
        }
        
	}

	@Override
	protected void updateLogic() {
		// TODO Auto-generated method stub
		
	}

}
