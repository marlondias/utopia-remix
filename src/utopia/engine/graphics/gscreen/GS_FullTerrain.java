package utopia.engine.graphics.gscreen;

import java.awt.Font;
import java.awt.Point;
import utopia.basic.GameSettings;
import utopia.basic.MouseInput.MouseActionType;
import utopia.engine.graphics.MAnimationSheet;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.surfaces.AnimatedImage;
import utopia.engine.graphics.surfaces.RingMenu;
import utopia.engine.graphics.surfaces.TextLine;
import utopia.engine.graphics.surfaces.TiledMap;
import utopia.game.planet.Planet;

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
    
    
    public GS_FullTerrain(Planet p) {
		int maxW = GameSettings.getResolution().width;
		int maxH = GameSettings.getResolution().height;
		
    	terrain = new TiledMap(new MTileset(48, "res/tilesets/tileset48-terrain.png"), maxW, maxH, p.getTerrainMap());
    	terrain.setPosition(0, 0);
    	terrain.transitionIn(200);
    	
    	halfTileW = terrain.getTileWidth() >> 1;
    	halfTileH = terrain.getTileHeight() >> 1;
    	
    	resourcesLayer = new TiledMap(new MTileset(48, "res/tilesets/tileset48-resources.png"), maxW, maxH, p.getResourceMap());
    	resourcesLayer.setPosition(0, 0);
    	
    	planetName = new TextLine("Planet " + p.getName(), new Font("Monospaced", Font.PLAIN, 19));
    	int nameX = (maxW / 2) - (planetName.getWidth() / 2);
    	int nameY = maxH - planetName.getHeight() - 24;
    	planetName.setPosition(nameX, nameY);
    	planetName.transitionIn(200);
    	
    	pointer = new AnimatedImage(34, 34, new MAnimationSheet(34, 34, 10, 1000, "res/cursors/anim_drag-marker.png"));
    	
    	super.surfaces.add(terrain);
    	super.surfaces.add(resourcesLayer);
    	super.surfaces.add(pointer);
    	super.surfaces.add(planetName);
	}


	@Override
	protected void handleMouse(MouseActionType actionType) {
		pointer.show();
		
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
    		terrain.moveU();
    		resourcesLayer.moveU();
        	pointer.hide();
    	}
    	if (super.input.down.isPressed()){
    		terrain.moveD();
    		resourcesLayer.moveD();
        	pointer.hide();
    	}
    	if (super.input.left.isPressed()){
    		terrain.moveL();
    		resourcesLayer.moveL();
        	pointer.hide();
    	}
    	if (super.input.right.isPressed()){
    		terrain.moveR();
    		resourcesLayer.moveR();
        	pointer.hide();
    	}
    	
	}

	@Override
	protected void updateLogic() {
		
		if (false && isDragging){
			//Verifica a direção do retângulo de seleção (ou quadrante)
			int quadrante = 2;
			if ((dragPos1.x < dragPos2.x) && (dragPos1.y > dragPos2.y)) quadrante = 1;
			if ((dragPos1.x > dragPos2.x) && (dragPos1.y > dragPos2.y)) quadrante = 2;
			if ((dragPos1.x > dragPos2.x) && (dragPos1.y < dragPos2.y)) quadrante = 3;
			if ((dragPos1.x < dragPos2.x) && (dragPos1.y < dragPos2.y)) quadrante = 4;
			
			//Criar retangulo de seleção entre os pontos..
		}
		
		if (clicked){
			System.out.println("Click!");
			clicked = false;
		}
		
		pointer.setCenterAt(cursorPos.x + halfTileW, cursorPos.y + halfTileH);

	}

}
