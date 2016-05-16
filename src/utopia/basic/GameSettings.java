package utopia.basic;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

//Guarda todas as preferências, configurações e objetos comuns
public class GameSettings {
	private static final GraphicsEnvironment G_ENV = GraphicsEnvironment.getLocalGraphicsEnvironment(); //GE fixo
	private static final Dimension RESOLUTION = new Dimension(800, 600); //Resolução da tela de jogo (pixels)
	//Idioma a ser usado (0 = eng)
	//Mapeamento de teclas

	private static GameGraphics GG; //Faz toda a parte gráfica
    private static InputHandler INPUT; //Monitora o teclado

    //Coisas do game loop
    public static final int WIDTH = 800; //largura da tela
    public static final int HEIGHT = 600;//(WIDTH / 16) * 9; //altura da tela (proporcão 16:9)
    public static final int SCALE = 1; //multiplica o tamanho da tela
    private JFrame frame; //janela para exibir o jogo
    private Canvas canvas; //tela para renderização

    
    
	
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
