package utopia.basic;

import java.awt.Canvas;

import utopia.game.gscreen.GS_Background;
import utopia.game.gscreen.GS_GameField;
import utopia.game.gscreen.GS_Info;
import utopia.game.gscreen.GS_MainUI;

public class GameLogic {
	private GameGraphics graphics;
    private InputHandler keyboard;

    private GS_Background fundo;
    private GS_GameField campo1;
    private GS_MainUI gui1;
    private GS_Info info;

	
	public GameLogic(Canvas canvas, GameGraphics gg){
		//Coisas do init()
        keyboard = new InputHandler(canvas); //associa o listener à tela
        graphics = gg;

        campo1 = new GS_GameField(graphics, keyboard);
        fundo = new GS_Background(graphics, keyboard);
        gui1 = new GS_MainUI(graphics, keyboard);
        info = new GS_Info (graphics, keyboard);

        campo1.show();
        fundo.show();
        gui1.show();
        info.show();
        
        graphics.addLayer(fundo);
        graphics.addLayer(campo1);
        graphics.addLayer(gui1);
        graphics.addLayer(info);
	}
	
    public void update(){
        //Lógica e matemática
    	fundo.updateAll();
    	campo1.updateAll();
    	gui1.updateAll();
    	info.updateAll();
    }
	
}
