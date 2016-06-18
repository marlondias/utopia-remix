package utopia.engine.graphics.gscreen;

import utopia.basic.controls.MouseInput.MouseActionType;
import utopia.basic.helpers.Assets;
import utopia.basic.helpers.GameSettings;
import utopia.basic.helpers.PerlinNoise;
import utopia.basic.helpers.TerrainGenerator;
import utopia.engine.graphics.surfaces.HeightMap;
import utopia.engine.graphics.surfaces.TiledMapB;

public class GS_AreaTeste extends MGameScreen {
    private TiledMapB terrain;
    private HeightMap hmap = new HeightMap(500, 500);
    
    
    public GS_AreaTeste() {
		int maxW = GameSettings.getResolution().width;
		int maxH = GameSettings.getResolution().height;
		
		terrain = new TiledMapB(maxW, maxH, Assets.TILESET_TERRAIN_MINI);
    	terrain.show(0);
		terrain.setTiles(TerrainGenerator.generateTiles(100, 100, 5));
		
		double[][] pn = PerlinNoise.generatePerlinNoise(1, 100, 100, 5); 
		for (int x=0; x<100; x++){
			for (int y=0; y<100; y++){
				if (pn[x][y] < 0.5) pn[x][y] = 0;
			}
		}
		
		hmap.setMap(pn);
		hmap.show(0);
    	
    	surfaces.add(hmap);
//    	surfaces.add(terrain);
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
