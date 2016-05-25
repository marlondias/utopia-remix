package utopia.basic.controls;

import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class MouseInput implements MouseInputListener {
	public enum MouseActionType{
		MOVE, CLICK, DRAG, LONG_PRESS;
	}
	
	private MouseActionType state = MouseActionType.MOVE; //Tipos de ação do mouse
	//private boolean scrollWheel;
	private boolean leftButton; //Botões aceitos
	private boolean midButton;
	private boolean rightButton;
	private Point position = new Point(); //Posições do ponteiro
	private Point positionDrag = new Point();
	private boolean hasUpdate = false; //Indica se houve ação recente no mouse
	private final int LP_DURATION = 1000; //1 segundo
	private long time_longPress; //Usado para verificar longpress
	
	
	public MouseInput(Canvas cnv){
		cnv.addMouseListener(this);
		cnv.addMouseMotionListener(this);
	}
	
	
	private void setButton(MouseEvent e){
		//Verifica com qual botão a ação foi gerada
		int btnCode = e.getButton();
		leftButton = false;
		midButton = false;
		rightButton = false;
		if (btnCode == MouseEvent.BUTTON1) leftButton = true;
		else if (btnCode == MouseEvent.BUTTON2) midButton = true;
		else if (btnCode == MouseEvent.BUTTON3) rightButton = true;
	}
	
	private void setPosition(MouseEvent e){
		//Posição do ponteiro no momento da ação (ou início da ação)
		position.x = e.getX();
		position.y = e.getY();
	}

	private void setReleasePosition(MouseEvent e){
		//Posição do ponteiro ao final da ação (DRAG)
		positionDrag.x = e.getX();
		positionDrag.y = e.getY();
	}
	
	private boolean isLongPress(MouseEvent e){
		return (e.getWhen() > time_longPress);
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		//Click único ou duplo
		//setButton(e);
		//setPosition(e);
		//state = (e.getClickCount() > 1) ? MouseActionType.DBL_CLICK : MouseActionType.CLICK;
		//hasUpdate = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Pode terminar em CLICK, DRAG ou LONG_PRESS
		setButton(e);
		setPosition(e);
		time_longPress = e.getWhen() + LP_DURATION;
		state = MouseActionType.CLICK;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setReleasePosition(e);
		if (state == MouseActionType.CLICK && isLongPress(e)) state = MouseActionType.LONG_PRESS;
		hasUpdate = true;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//É um movimento de arrasta pé
		setButton(e);
		setReleasePosition(e);
		state = MouseActionType.DRAG;
		hasUpdate = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//É um movimento normal
		setPosition(e);
		state = MouseActionType.MOVE;
		hasUpdate = true;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	
	public Point getMousePosition(){
		return position;
	}

	public Point getMouseDestination(){
		return positionDrag;
	}
	
	public MouseActionType getMouseAction(){
		//Informa a ação apenas uma vez (pq?)
		if (hasUpdate){
			hasUpdate = false;
			return state;
		}
		else return null;
	}

	public void getAction(){
		if (state == MouseActionType.MOVE){
			System.out.println("Moveu " + position.x + ", " + position.y);
			return;
		}
		
		switch (state) {
			case CLICK:
				System.out.print("Click: ");
				break;
			case DRAG:
				System.out.print("Arrasto: ");
				break;
			case LONG_PRESS:
				System.out.print("Long press: ");
				break;
			default:
				return;
		}
		System.out.print(position.x +", "+ position.y);
		if (state == MouseActionType.DRAG) System.out.print(positionDrag.x + ", " + positionDrag.y);
		System.out.println();
	}
}
