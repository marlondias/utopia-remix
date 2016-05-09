package utopia.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StaticImage extends MSurface {

	
	public StaticImage(int width, int height, String path) {
		super(width, height);
		this.loadImage(path);
	}

	
	public void loadImage(String path){
		//Tenta carregar usando PATH como arquivo, depois desenha na imagem principal
		BufferedImage img = null;
        try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (img == null) return;
        
		super.drawingSurface.drawImage(img, 0, 0, super.getWidth(), super.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
	}
	
	@Override
	public void updateGraphics() {
		return; //Nesse caso não há o que atualizar
	}

}
