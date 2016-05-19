package utopia.game.gscreen;

import java.awt.Font;

import utopia.basic.GameSettings;
import utopia.basic.MouseInput.MouseActionType;
import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.msurfaces.StaticImage;
import utopia.engine.graphics.msurfaces.TextLine;
import utopia.engine.graphics.msurfaces.TiledMap;
import utopia.game.planet.Planet;

public class GS_FullTerrain extends MGameScreen {
    private TiledMap terrain;
    private TiledMap resourcesLayer;
    private TextLine planetName;
    private StaticImage cursor;
    
    
    public GS_FullTerrain(Planet p) {
		int maxW = GameSettings.getResolution().width;
		int maxH = GameSettings.getResolution().height;
		
    	terrain = new TiledMap(new MTileset(48, "res/tilesets/tileset48-terrain.png"), maxW, maxH, p.getTerrainMap());
    	terrain.setPosition(0, 0);
    	
    	resourcesLayer = new TiledMap(new MTileset(48, "res/tilesets/tileset48-resources.png"), maxW, maxH, p.getResourceMap());
    	resourcesLayer.setPosition(0, 0);
    	
    	planetName = new TextLine("Planet " + p.getName(), new Font("Monospaced", Font.PLAIN, 19));
    	int nameX = (maxW / 2) - (planetName.getWidth() / 2);
    	int nameY = maxH - planetName.getHeight() - 24;
    	planetName.setPosition(nameX, nameY);
    	
    	cursor = new StaticImage(48, 48, "res/cursor48.png");
    	
    	super.surfaces.add(terrain);
    	super.surfaces.add(resourcesLayer);
    	super.surfaces.add(cursor);
    	super.surfaces.add(planetName);
	}


	@Override
	protected void handleMouse(MouseActionType actionType) {
		switch (actionType) {
		case MOVE:
			cursor.setPosition(mouse.getMousePosition().x, mouse.getMousePosition().y);
			break;
		default:
			break;
		}
		
	}

	@Override
	protected void handleKeyboard() {
    	if (super.input.up.isPressed()){
    		terrain.moveU();
    		resourcesLayer.moveU();
    	}
    	if (super.input.down.isPressed()){
    		terrain.moveD();
    		resourcesLayer.moveD();
    	}
    	if (super.input.left.isPressed()){
    		terrain.moveL();
    		resourcesLayer.moveL();
    	}
    	if (super.input.right.isPressed()){
    		terrain.moveR();
    		resourcesLayer.moveR();
    	}
		
	}

	@Override
	protected void updateLogic() {
		// TODO Auto-generated method stub
		
	}

}
