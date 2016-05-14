package utopia.basic;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Marlon Dias
 */
public class InputHandler implements KeyListener {
    //Responsável pelo teclado

	public class Key {
        //Representa uma tecla
        private boolean pressed; //indica se está pressionada
        
        public Key(){
            pressed = false;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }    
    }
    
    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    
    public Key num1 = new Key();
    public Key num2 = new Key();
    public Key num3 = new Key();
    public Key num4 = new Key();
    public Key num5 = new Key();
    
    
    public InputHandler(Canvas tela){
        tela.addKeyListener(this); //coloca esta classe como manipulador de teclas
    }
    
    
    public void keyPressed(KeyEvent e) {
    	//alguma tecla está sendo pressionada, descubra qual e ative o "toggle"
        toggleKey(e.getKeyCode(), true);
    }
    
    public void keyReleased(KeyEvent e) {
    	//alguma tecla deixou de ser pressionada, descubra qual e libere o "toggle"
        toggleKey(e.getKeyCode(), false);
    }
    
    public void keyTyped(KeyEvent e) {
    	//função obrigatória, mas não utilizada
        //throw new UnsupportedOperationException("Não implementado");
    }
    
    private void toggleKey(int keyCode, boolean isPressed){
    	//Considera WASD e setas como direções
        if ((keyCode == KeyEvent.VK_W) || (keyCode == KeyEvent.VK_UP)){
            up.setPressed(isPressed);
        }
        if ((keyCode == KeyEvent.VK_A) || (keyCode == KeyEvent.VK_LEFT)){
            left.setPressed(isPressed);
        }
        if ((keyCode == KeyEvent.VK_S) || (keyCode == KeyEvent.VK_DOWN)){
            down.setPressed(isPressed);
        }
        if ((keyCode == KeyEvent.VK_D) || (keyCode == KeyEvent.VK_RIGHT)){
            right.setPressed(isPressed);
        }

        //Números 1 e 2
        if (keyCode == KeyEvent.VK_1){
            num1.setPressed(isPressed);
        }
        if (keyCode == KeyEvent.VK_2){
            num2.setPressed(isPressed);
        }
    }

}
