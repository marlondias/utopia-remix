package utopia.basic;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

//Guarda todas as preferências, configurações e OBJETOS COMUNS (melhor ideia ever!)
public class GameSettings {
	//TBD: Idioma, mapeamento de teclas, mouse, fullscreen...

	private static final GraphicsEnvironment G_ENV = GraphicsEnvironment.getLocalGraphicsEnvironment(); //GE fixo
	private static final Dimension RESOLUTION = new Dimension(800, 600); //Resolução da tela de jogo (pixels)
	private static final GameGraphics GG = new GameGraphics(); //Faz toda a parte gráfica
	
    private static Canvas canvas; //tela para renderização
    private static InputHandler INPUT; //Monitora o teclado
    private static final int SCALE = 1; //multiplica o tamanho da tela

    
	public static void initCanvas(){
		//Será ativada na criação do GameGraphics
		int WIDTH = RESOLUTION.width;
		int HEIGHT = RESOLUTION.height;
		
    	canvas = new Canvas();
    	canvas.setIgnoreRepaint(true); //evita renderização passiva
    	canvas.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE)); //mantem o tamanho da tela fixo
        canvas.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        canvas.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        
        INPUT = new InputHandler(canvas);
	}
    
	public static Canvas getCanvas(){
		return canvas;
	}

	public static InputHandler getInputHandler(){
		return INPUT;
	}
	
	public static GameGraphics getGameGraphics(){
		return GG;
	}
	
	public static GraphicsDevice getDefaultGD(){
		return G_ENV.getDefaultScreenDevice();
	}
	
	public static GraphicsConfiguration getDefaultGC(){
		return G_ENV.getDefaultScreenDevice().getDefaultConfiguration();
	}
	
	public static Dimension getResolution(){
		return RESOLUTION;
	}
	
}
