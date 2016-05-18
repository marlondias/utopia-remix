package utopia.game.gscreen;

import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.msurfaces.StaticImage;
import utopia.engine.graphics.msurfaces.TiledMap;
import utopia.game.planet.Planet;

public class GS_GameField extends MGameScreen {
    private TiledMap terreno;
    private TiledMap resOverlay;
    private StaticImage moldura;

    
    public GS_GameField(Planet planet) {
    	super();
    	
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
	public void updateAll() {
    	if (super.input.up.isPressed()) terreno.moveU();
        if (super.input.down.isPressed()) terreno.moveD();
        if (super.input.left.isPressed()) terreno.moveL();
        if (super.input.right.isPressed()) terreno.moveR();

    	if (super.input.up.isPressed()) resOverlay.moveU();
        if (super.input.down.isPressed()) resOverlay.moveD();
        if (super.input.left.isPressed()) resOverlay.moveL();
        if (super.input.right.isPressed()) resOverlay.moveR();
        if (super.input.num1.isPressed()) resOverlay.hide();
        if (super.input.num2.isPressed()) resOverlay.show();

	}

}
