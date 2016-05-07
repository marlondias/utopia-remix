package utopia.basic;

import java.awt.Canvas;
import utopia.engine.graphics.MRenderer;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.TiledMap;
import utopia.sobras.MCanvasTile;
import utopia.sobras.MRenderer2;


public class GameLogic {
	private GameGraphics graphics;
    private InputHandler keyboard;

    private TiledMap terreno;

	
	public GameLogic(Canvas canvas, GameGraphics gg){
		//TUDO ERRADO! TIRE OS GRAFICOS DAQUI!!

		//Coisas do init()
        keyboard = new InputHandler(canvas); //associa o listener à tela
        
        graphics = gg;
        terreno = new TiledMap(new MTileset(48, "res/tileset48-utopia2.png"), 50, 50, 450, 450);

	}
	
    public void update(){
        //Lógica e matemática
        
        if (keyboard.up.isPressed()) terreno.moveU();
        if (keyboard.down.isPressed()) terreno.moveD();
        if (keyboard.left.isPressed()) terreno.moveL();
        if (keyboard.right.isPressed()) terreno.moveR();

        terreno.updateGraphics();
        graphics.customDraw(terreno.getRenderedImage());

    }
	
}
