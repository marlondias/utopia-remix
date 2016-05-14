package utopia.basic;

import java.awt.Canvas;
import java.awt.Font;
import utopia.engine.graphics.MAnimationSheet;
import utopia.engine.graphics.MTileset;
import utopia.engine.graphics.msurfaces.AnimatedImage;
import utopia.engine.graphics.msurfaces.StaticImage;
import utopia.engine.graphics.msurfaces.TextLine;
import utopia.engine.graphics.msurfaces.TiledMap;


public class GameLogic {
	private GameGraphics graphics;
    private InputHandler keyboard;

    private StaticImage fotoBG;
    private StaticImage hud1;
    private StaticImage hud2;
    private StaticImage hud3;
    private StaticImage moldura;
    private AnimatedImage animacao;
    private TiledMap terreno;
    private TextLine texto;
    private int var;

	
	public GameLogic(Canvas canvas, GameGraphics gg){
		//Coisas do init()
        keyboard = new InputHandler(canvas); //associa o listener à tela
        graphics = gg;

        //TESTES
        fotoBG = new StaticImage(800, 600, "res/ui_elements/deep_space.jpg");
        hud1 = new StaticImage(149, 150, "res/ui_elements/GUI-azul_status2.png");
        hud2 = new StaticImage(70, 420, "res/ui_elements/GUI-azul_lateral.png");
        hud3 = new StaticImage(491, 57, "res/ui_elements/GUI-azul_botoes.png");

        terreno = new TiledMap(new MTileset(48, "res/tileset48-utopia1.png"), 430, 430);
        moldura = new StaticImage(450, 450, "res/ui_elements/GUI-frame.png");
        animacao = new AnimatedImage(140, 140, new MAnimationSheet(160, 160, 12, 1500, "res/anim_planet.png"));
        
        texto = new TextLine(null, new Font("Lucidia", Font.PLAIN, 16));
        
        fotoBG.show();
        terreno.setPosition(255, 48);
        terreno.show();
        moldura.setPosition(246, 39);
        moldura.show();
        animacao.setPosition(48, 14);
        animacao.show();

        hud1.setPosition(44, 10);
        hud2.setPosition(730, 164);
        hud3.setPosition(294, 527);
        
        texto.setPosition(50, 50);
        

        //graphics.addLayer(fotoBG);
        graphics.addLayer(terreno);
        //graphics.addLayer(moldura);
        graphics.addLayer(animacao);
        graphics.addLayer(hud1);
        graphics.addLayer(hud3);
        graphics.addLayer(hud2);
        graphics.addLayer(texto);
	}
	
	
    public void update(){
        //Lógica e matemática

    	if (keyboard.up.isPressed()) terreno.moveU();
        if (keyboard.down.isPressed()) terreno.moveD();
        if (keyboard.left.isPressed()) terreno.moveL();
        if (keyboard.right.isPressed()) terreno.moveR();
        if (keyboard.num1.isPressed()){
        	var--;
        	System.out.println("Var: " + var);
        }
        if (keyboard.num2.isPressed()){
        	var++;
        	System.out.println("Var: " + var);
        }
        
        texto.setString("Number " + var);

    }
	
}
