package utopia.engine.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import utopia.basic.Assets;
import utopia.game.buildings.Building;

//Icones possíveis em um RingMenu
public enum RingOption {
	//OPCAO(código, "icone", isTerrain, isTank, isShip, isBuilding)
	CANCEL   (0),
	INFO     (1),
	REPAIR   (2),
	DEMOLISH (3),
	ENABLE   (4),
	DISABLE  (5),
	LAUNCH   (6),
	GOTO     (7),
	SCAN     (8),
	TAKE_OFF (9),
	LAND     (10),
	REFUEL   (11);
	

	private final int ICON_SIZE = 48; //O ícone é sempre quadrado
	private final int ICON_COUNT = 3;
	private BufferedImage icon; //A imagem guarda 3 versões do mesmo icone
	
	private RingOption(int pos){
		//Cria uma imagem com espaço suficiente
		icon = new BufferedImage((ICON_SIZE * ICON_COUNT), ICON_SIZE, BufferedImage.TYPE_INT_ARGB);
		
		//Copia as duas versões do ícone desejado
		BufferedImage img1 = Assets.RINGICONS_B48.getSubimage(pos * ICON_SIZE, 0, ICON_SIZE, ICON_SIZE);
		BufferedImage img2 = Assets.RINGICONS_W48.getSubimage(pos * ICON_SIZE, 0, ICON_SIZE, ICON_SIZE);
		BufferedImage image = null;
		
		Graphics2D g2 = icon.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for (int i=0; i<ICON_COUNT; i++){
			//Escolhe a cor e desenha um círculo
			if (i == 0) g2.setColor(Assets.ICON_NORMAL_COLOR);
			else if (i == 1) g2.setColor(Assets.ICON_HIGHLIGHT_COLOR);
			else if (i == 2) g2.setColor(Assets.ICON_SELECTED_COLOR);
			g2.fillOval((ICON_SIZE * i), 0, ICON_SIZE, ICON_SIZE);

			//Escolhe a versão correta do ícone
			if (i == 0) image = img1;
			else image = img2;
			
			//Coloca o ícone sobre o círculo (ou não..)
			if (image != null) g2.drawImage(image, (ICON_SIZE * i), 0, image.getWidth(), image.getHeight(), null);
		}

	}
	
	
	public BufferedImage getIcon(int pos){
		if (pos < 0 || pos >= ICON_COUNT) pos = 0;
		return icon.getSubimage(0 + (ICON_SIZE * pos), 0, ICON_SIZE, ICON_SIZE);
	}

	public int getSize(){
		return ICON_SIZE;
	}

	public static LinkedList<RingOption> getMenuIcons(){
		//Verifica as opções que esta estrutura oferece e vai enchendo a lista de acordo
		//Info, Demolish e Cancel estão presentes em todas
		
		LinkedList<RingOption> list = new LinkedList<>();
		list.add(INFO);
		list.add(REPAIR);
		list.add(DEMOLISH);
		list.add(ENABLE);
		list.add(LAUNCH);
		
		//Retorna a lista
		return list;
	}


	
}
