package utopia.basic;

import utopia.game.gscreen.GS_Background;
import utopia.game.gscreen.GS_FullTerrain;
import utopia.game.gscreen.GS_GameField;
import utopia.game.gscreen.GS_Info;
import utopia.game.gscreen.GS_MainUI;
import utopia.game.planet.Planet;

public class GameLogic {
	private GameGraphics graphics = GameSettings.getGameGraphics();
    private MouseInput mi = GameSettings.getMouseInput();

    private GS_Background fundo;
    private GS_GameField campo1;
    private GS_FullTerrain campo2;
    private GS_MainUI gui1;
    private GS_Info info;
    
    private Planet planeta = new Planet("Hempa");

	
	public GameLogic(){
        campo1 = new GS_GameField(planeta);
        campo2 = new GS_FullTerrain(planeta);
        fundo = new GS_Background();
        gui1 = new GS_MainUI();
        info = new GS_Info ();

        campo2.show();
        fundo.show();
        gui1.show();
        info.show();
        
        //graphics.addLayer(fundo);
        graphics.addLayer(campo2);
        //graphics.addLayer(gui1);
        //graphics.addLayer(info);


	}
	
    public void update(){
        //Lógica e matemática
    	campo2.updateAll();
    	//mi.getAction();
    }
	
}
