package utopia.basic;

import java.awt.Canvas;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.TiledMap;


public class GameLogic {
	private GameGraphics graphics;
    private InputHandler keyboard;

    private TiledMap terreno;
    private TiledMap terreno2;

	
	public GameLogic(Canvas canvas, GameGraphics gg){

		//Coisas do init()
        keyboard = new InputHandler(canvas); //associa o listener à tela
        
        graphics = gg;
        terreno = new TiledMap(new MTileset(48, "res/tileset48-utopia1.png"), 450, 450);
        terreno2 = new TiledMap(new MTileset(32, "res/sprite_sheet32b.png"), 800, 600);

        graphics.addLayer(0, 0, terreno2);
        graphics.addLayer(0, 0, terreno);

	}
	
    public void update(){
        //Lógica e matemática
        
        if (keyboard.up.isPressed()) terreno.moveU();
        if (keyboard.down.isPressed()) terreno.moveD();
        if (keyboard.left.isPressed()) terreno.moveL();
        if (keyboard.right.isPressed()) terreno.moveR();


    }
	
}
