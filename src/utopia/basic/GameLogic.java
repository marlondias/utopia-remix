package utopia.basic;

import java.awt.Canvas;
import utopia.engine.graphics.MRenderer;
import utopia.engine.graphics.MRenderer2;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.canvas.MCanvasTile;


public class GameLogic {
	private final GameGraphics graphics;
    private InputHandler keyboard;
    private MCanvasTile tlCanvas;
    private MRenderer mRendr;

	
	public GameLogic(Canvas canvas, GameGraphics gg){
		//TUDO ERRADO! TIRE OS GRAFICOS DAQUI!!

		//Coisas do init()
        keyboard = new InputHandler(canvas); //associa o listener à tela
        
        graphics = gg;

        mRendr = new MRenderer(canvas.getWidth(), canvas.getHeight());
        tlCanvas = new MCanvasTile(288, 288, new MTileset(48, "res/tileset48-utopia2.png"));
        tlCanvas.loadLevelFromImage("res/utopia-terreno-teste1.bmp");
        tlCanvas.show();

	}
	
    public void update(){
        //Lógica e matemática
        
        if (keyboard.up.isPressed()) tlCanvas.moveU();
        if (keyboard.down.isPressed()) tlCanvas.moveD();
        if (keyboard.left.isPressed()) tlCanvas.moveL();
        if (keyboard.right.isPressed()) tlCanvas.moveR();
        tlCanvas.updateTiles();
        
        graphics.addPixels(mRendr.render());
    }
	
}
