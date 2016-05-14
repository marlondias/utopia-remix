package utopia.engine.graphics;

import java.awt.image.BufferedImage;

// Guarda e organiza todos os MSurfaces e suas posições
public abstract class MGameScreen {
	private int transicao; //Nivel da transição para exibir/ocultar (0% - 100%)
	private int onScreen; //Se está visível e pode receber interação

	
	protected abstract void checkKeyboard(); //Verifica as teclas e ativa coisas..
	
	protected abstract void checkMouse(); //One day..
	
	protected abstract void updateAll(); //Chama update em todos os objetos
	
	public BufferedImage getGraphics(){
		//Passa por todos os objetos e cria a imagem final, com a tela completa
		return null;
	}

}
