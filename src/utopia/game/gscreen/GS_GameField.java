package utopia.game.gscreen;


import utopia.basic.GameGraphics;
import utopia.basic.InputHandler;
import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.msurfaces.StaticImage;
import utopia.engine.graphics.msurfaces.TiledMap;
import utopia.game.level.Planet;

public class GS_GameField extends MGameScreen {
    private TiledMap terreno;
    private StaticImage moldura;

    
    public GS_GameField(GameGraphics gg, InputHandler kbd) {
    	super(gg, kbd);
    	
    	Planet lala = new Planet("Lalah");

    	terreno = new TiledMap(new MTileset(48, "res/tileset48-utopia1.png"), 430, 430, lala.getTerrainMap());
        terreno.setPosition(255, 48);

        moldura = new StaticImage(450, 450, "res/ui_elements/GUI-frame.png");
        moldura.setPosition(246, 39);
        
        super.surfaces.add(terreno);
        super.surfaces.add(moldura);
	}

	@Override
	public void updateAll() {
    	if (super.input.up.isPressed()) terreno.moveU();
        if (super.input.down.isPressed()) terreno.moveD();
        if (super.input.left.isPressed()) terreno.moveL();
        if (super.input.right.isPressed()) terreno.moveR();
	}

}
