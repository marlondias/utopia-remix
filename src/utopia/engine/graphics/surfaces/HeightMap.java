package utopia.engine.graphics.surfaces;

import java.awt.image.BufferedImage;

import utopia.basic.helpers.Helper;

public class HeightMap extends MSurface {
	private double heightMap[];
	private int mapWidth;
	private int mapHeight;
	private int mapSize;
	
	
	public HeightMap(int width, int height) {
		super(width, height);

		setMap(Helper.generateNoiseMap(width, height));
		
		getDrawingSurf().fillRect(0, 0, width, height);
		validate();
	}
	
	
	public void setMap(double map[][]){
		//Copia o mapa informado, convertendo para array 1D
		if (map == null) return;
		
		mapWidth = map.length;
		mapHeight = map[0].length;
		mapSize = mapWidth * mapHeight;
		heightMap = new double[mapSize];

		int i=0;
		for (int y=0; y<mapHeight; y++){
			for (int x=0; x<mapWidth; x++){
				heightMap[i++] = map[x][y];
			}
		}
	}

	private BufferedImage convertToImage(){
		//Transforma o mapa de altura em uma imagem
		
		BufferedImage img = new BufferedImage(mapWidth, mapHeight, BufferedImage.TYPE_INT_ARGB);
		
		int i = 0;
		for (int y=0; y<mapHeight; y++){
			for (int x=0; x<mapWidth; x++){
				double value = heightMap[i];
				int pixel,r,g,b;

				r = 0;
				g = (int) (value * 255);
				b = (int) ((1.0 - value) * 255);
				
				pixel = 0xFF; //Alpha
				pixel = (pixel << 8) | (0xFF & r); //Red
				pixel = (pixel << 8) | (0xFF & g); //Green
				pixel = (pixel << 8) | (0xFF & b); //Blue
				
				img.setRGB(x, y, pixel);
				i++;
			}
		}
		
		return img;
	}
	
	@Override
	public void updateGraphics() {
		getDrawingSurf().drawImage(convertToImage(), 0, 0, getWidth(), getHeight(), null);
	}

}
