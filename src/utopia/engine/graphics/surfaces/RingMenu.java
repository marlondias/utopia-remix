package utopia.engine.graphics.surfaces;

import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import utopia.basic.RingOption;

//Um menu de ícones posicionados em formato de anel
public class RingMenu extends MSurface {
	private int itemCount; //Número de ícones
	private double separationAngle; //Ângulo entre dois icones consecutivos
	private int radius; //Distância entre o centro de um icone e o centro da superfície
	
	private int itemSize = 64;
	private LinkedList<RingOption> options; //Todas as opções disponíveis (e seus icones)
	
	private final double angleFix = Math.toRadians(90); //Corrige posição do primeiro item
	private double offset; //Correção de X e Y para cada item
	
	private int posSelected = -1; //Indica qual é o ícone selecionado/destacado (se houver)
	private int posHighlighted = -1;
	
	
	public RingMenu(int radius) {
		super(1,1);
		
		this.radius = radius;
		
		this.setOptions( RingOption.getMenuIcons() );
		
		int center = radius + (itemSize >> 1) + 1;
		this.offset = center - (itemSize >> 1);
		
		//Calcular tamanho necessário da superfície
		int size = center * 2;
		super.changeSize(size, size);
		super.validate();
		super.transitionOut(250);
	}
	
	
	public void setOptions(LinkedList<RingOption> opt){
		//Usar a estrutura/veículo como parametro
		
		this.options = opt;
		this.itemCount = opt.size();
		this.separationAngle = Math.toRadians(360.00 / itemCount);
	}
	
	
	private void checkHighlighted(int px, int py){
		//Verifica se o ponteiro está dentro de algum item
		double angle = -angleFix;
		double x, y;
		for (int i=0; i<itemCount; i++){
			x = (radius * Math.cos(angle) + offset); //Calcula as coordenadas do item
			y = (radius * Math.sin(angle) + offset);
			
			if ((px >= x) && (px < x+itemSize) && (py >= y) && (py < y+itemSize)){
				posHighlighted = i;
				return;
			}
			angle += separationAngle; //Atualiza para o próximo item
		}
		posHighlighted = -1; //Não está sobre um ícone
	}

	public boolean checkMouse(int px, int py){
		px -= getX();
		py -= getY();
		
		checkHighlighted(px, py);
		if (posHighlighted != -1) return true;
		
		return false;
	}

	public RingOption setClick(){
		RingOption ret = RingOption.CANCEL;
		if (posHighlighted != -1){
			posSelected = posHighlighted;
			posHighlighted = -1;
			ret = options.get(posSelected);
		}
		return ret;
	}

	
	private void fillWithNothing(){
		//Pinta tudo com transparência
		super.getDrawingSurf().setComposite(AlphaComposite.Clear);
		super.getDrawingSurf().fillRect(0, 0, super.getWidth(), super.getHeight());
		super.getDrawingSurf().setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
	}
	
	@Override
	public void updateGraphics() {
		fillWithNothing();
		if (getTransitionLevel() < 0.3){
			posHighlighted = -1;
			posSelected = -1;
		}
		
		double angleIncrement = separationAngle; //Multiplicar nesse também da um efeito legal
		double angle = (-angleFix) * getTransitionLevel();
		double r = radius * getTransitionLevel();

		BufferedImage img;
		for (int i=0; i<itemCount; i++){
			int x = (int) (r * Math.cos(angle) + offset); //Calcula as coordenadas do item
			int y = (int) (r * Math.sin(angle) + offset);

			//Escolhe o icone correto e desenha
			if (posSelected == i) img = options.get(i).getIcon(2);
			else if (posHighlighted == i) img = options.get(i).getIcon(1);
			else img = options.get(i).getIcon(0);
			super.getDrawingSurf().drawImage(img, x, y, itemSize, itemSize, null);
			
			angle += angleIncrement; //Atualiza para o próximo item
		}
	}

}
