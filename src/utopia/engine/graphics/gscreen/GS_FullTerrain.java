package utopia.engine.graphics.gscreen;

import java.awt.Font;
import java.awt.Point;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import utopia.basic.GameStateManager;
import utopia.basic.controls.MouseInput.MouseActionType;
import utopia.basic.helpers.GameSettings;
import utopia.engine.graphics.MAnimationSheet;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.surfaces.AnimatedImage;
import utopia.engine.graphics.surfaces.TextLine;
import utopia.engine.graphics.surfaces.TiledMap;
import utopia.game.colony.Colony;
import utopia.game.planet.Planet;
import utopia.game.planet.Terrain;

public class GS_FullTerrain extends MGameScreen {
    private TiledMap terrain;
    private TiledMap resourcesLayer;
    private TextLine planetName;
    private AnimatedImage pointer;

    private Point cursorPos = new Point();
    private Point dragPos1 = new Point();
    private Point dragPos2 = new Point();
    private boolean isDragging = false;
    private boolean clicked = false;
    private int halfTileW, halfTileH;
    private int moveSpeed = 2;
    
	private DateFormat df = new SimpleDateFormat("dd MMM yyyy");
    
    
    public GS_FullTerrain(Planet p) {
		int maxW = GameSettings.getResolution().width;
		int maxH = GameSettings.getResolution().height;

		Terrain terr = p.getTerrain();
		
    	terrain = new TiledMap(new MTileset(48, "res/tilesets/tileset48-terrain.png"), maxW, maxH, terr.getTerrainMap());
    	terrain.setPosition(0, 0);
    	terrain.show(0);
    	
    	halfTileW = terrain.getTileWidth() >> 1;
    	halfTileH = terrain.getTileHeight() >> 1;
    	
    	resourcesLayer = new TiledMap(new MTileset(48, "res/tilesets/tileset48-resources.png"), maxW, maxH, terr.getResourceMap());
    	resourcesLayer.setPosition(0, 0);
    	resourcesLayer.hide(0);
    	
    	planetName = new TextLine("Planet " + p.getName(), new Font("Monospaced", Font.PLAIN, 19));
    	int nameX = (maxW / 2) - (planetName.getWidth() / 2);
    	int nameY = maxH - planetName.getHeight() - 24;
    	planetName.setPosition(nameX, nameY);
    	planetName.show(0);
    	
    	pointer = new AnimatedImage(34, 34, new MAnimationSheet(34, 34, 10, 1000, "res/cursors/anim_drag-marker.png"));
    	
    	super.surfaces.add(terrain);
    	super.surfaces.add(resourcesLayer);
    	super.surfaces.add(pointer);
    	super.surfaces.add(planetName);
	}


	@Override
	protected void handleMouse(MouseActionType actionType) {
		pointer.show(0);
		
		switch (actionType) {
		case MOVE:
			//Durante o DRAG, o movimento é ignorado
			if (!isDragging) cursorPos = terrain.getSnap(mouse.getMousePosition().x, mouse.getMousePosition().y);
			break;
		case DRAG:
			//Marca os pontos e sinaliza que está ocorrendo um DRAG
			isDragging = true;
			dragPos1 = terrain.getSnap(mouse.getMousePosition().x, mouse.getMousePosition().y);
			dragPos2 = terrain.getSnap(mouse.getMouseDestination().x, mouse.getMouseDestination().y);
			break;
		case CLICK:
			//Se está com DRAG, o click serve apenas para desmarcar. Se não, o click é sinalizado normalmente.
			if (isDragging) isDragging = false;
			else clicked = true;
			cursorPos = terrain.getSnap(mouse.getMousePosition().x, mouse.getMousePosition().y);
			break;
		case LONG_PRESS:
			//TBD
			break;
		default:
			break;
		}
		
	}

	@Override
	protected void handleKeyboard() {
    	if (super.input.up.isPressed()){
    		terrain.moveU(moveSpeed);
    		resourcesLayer.moveU(moveSpeed);
        	pointer.hide(0);
    	}
    	if (super.input.down.isPressed()){
    		terrain.moveD(moveSpeed);
    		resourcesLayer.moveD(moveSpeed);
        	pointer.hide(0);
    	}
    	if (super.input.left.isPressed()){
    		terrain.moveL(moveSpeed);
    		resourcesLayer.moveL(moveSpeed);
        	pointer.hide(0);
    	}
    	if (super.input.right.isPressed()){
    		terrain.moveR(moveSpeed);
    		resourcesLayer.moveR(moveSpeed);
        	pointer.hide(0);
    	}
    	
	}

	@Override
	protected void updateLogic() {
		
		if (clicked){
			clicked = false;
			
			System.out.println("Click!");
			Point p = terrain.getSnap(cursorPos.x, cursorPos.y);
			GameStateManager.getPlanet().interactionAt(p.x / terrain.getTileWidth(), p.y / terrain.getTileHeight());
		}
		
		pointer.setCenterAt(cursorPos.x + halfTileW, cursorPos.y + halfTileH);
		
		if (GameStateManager.getDate() != null){
		planetName.setString("Hempa - "
				+ df.format(GameStateManager.getDate())
				+ " - "
				+ GameStateManager.getTemperature() + "ºC");
		}
		
	}

}
