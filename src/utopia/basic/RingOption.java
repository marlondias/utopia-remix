package utopia.basic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import utopia.game.buildings.Building;

//Icones possíveis em um RingMenu
public enum RingOption {
	//OPCAO(código, "icone", isTerrain, isTank, isShip, isBuilding)
	CANCEL   ("cancel.png"),
	INFO     ("info.png"),
	REPAIR   ("repair.png"),
	DEMOLISH ("demolition.png"),
	ENABLE   ("power.png"),
	DISABLE  ("forbid.png"),
	LAUNCH   ("rocket.png"),
	GOTO     ("go-to.png"),
	SCAN     ("radar.png"),
	TAKE_OFF ("flight-up.png"),
	LAND     ("flight-down.png"),
	REFUEL   ("fuel.png");
	

	private final int ICON_COUNT = 3;
	private int iconSize = 64; //O ícone é sempre quadrado
	private BufferedImage icon; //A imagem guarda 3 versões do mesmo icone
	
	private RingOption(String fileName){
		//Cria imagem com espaço suficiente
		icon = new BufferedImage((iconSize * ICON_COUNT), iconSize, BufferedImage.TYPE_INT_ARGB);
		
		//Acessa a imagem do ícone
		BufferedImage img = Helper.openImageFile("res/ring-icons/" + fileName);
		
		Graphics2D g2 = icon.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Assets.ICON_NORMAL_COLOR);
		
		for (int i=0; i<ICON_COUNT; i++){
			if (i == 1) g2.setColor(Assets.ICON_HIGHLIGHT_COLOR);
			else if (i == 2) g2.setColor(Assets.ICON_SELECTED_COLOR);
			
			//Monta o ícone sobre o círculo colorido
			g2.fillOval(0 + (iconSize * i), 0, iconSize, iconSize);
			if (img != null) g2.drawImage(img, (iconSize * i), 0, img.getWidth(), img.getHeight(), null);
		}

	}
	
	
	public BufferedImage getIcon(int pos){
		if (pos < 0 || pos >= ICON_COUNT) pos = 0;
		return icon.getSubimage(0 + (iconSize * pos), 0, iconSize, iconSize);
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

	private static LinkedList<RingOption> getMenu(int vehi){
		//Para tanque ou nave
		//Verifica as opções que este veículo oferece e vai enchendo a lista de acordo
		
		//Info, Go to, Scan e Cancel estão presentes em todos
		
		//Retorna a lista
		return null;
	}

	
}
