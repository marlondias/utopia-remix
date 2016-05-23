package utopia.basic;

import utopia.engine.graphics.gscreen.GS_Background;
import utopia.engine.graphics.gscreen.GS_FullTerrain;
import utopia.engine.graphics.gscreen.GS_GameField;
import utopia.engine.graphics.gscreen.GS_Info;
import utopia.engine.graphics.gscreen.GS_MainUI;
import utopia.engine.graphics.gscreen.GS_RingMenus;
import utopia.game.planet.Planet;

public class GameLogic {
	private GameGraphics graphics = GameSettings.getGameGraphics();
    private MouseInput mi = GameSettings.getMouseInput();

    private GS_Background fundo;
    private GS_GameField campo1;
    private GS_FullTerrain campo2;
    private GS_MainUI gui1;
    private GS_Info info;
    private GS_RingMenus rings;
    
    private Planet planeta = new Planet("Hempa");

	
	public GameLogic(){
        campo1 = new GS_GameField(planeta);
        campo2 = new GS_FullTerrain(planeta);
        fundo = new GS_Background();
        gui1 = new GS_MainUI();
        info = new GS_Info ();
        rings = new GS_RingMenus();

        campo2.show();
        fundo.show();
        gui1.show();
        info.show();
        rings.show();
        
        //graphics.addLayer(fundo);
        graphics.addLayer(campo2);
        graphics.addLayer(rings);
        //graphics.addLayer(gui1);
        //graphics.addLayer(info);


	}
	
    public void update(){
        //Lógica e matemática
    	//campo2.updateAll();
    	rings.updateAll();
    	//mi.getAction();
    }
	
}
