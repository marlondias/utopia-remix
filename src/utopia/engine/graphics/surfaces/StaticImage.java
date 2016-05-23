package utopia.engine.graphics.surfaces;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class StaticImage extends MSurface {

	
	public StaticImage(int width, int height, String path) {
		super(width, height);
		if (path == null) return;
		this.loadImage(path);
		super.validate();
	}

	
	public void loadImage(String path){
		//Tenta carregar usando PATH como arquivo, depois desenha na imagem principal
		BufferedImage img = null;
        try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
        if (img == null) return;
        super.drawBuffImg(img);
	}
	
	@Override
	public void updateGraphics() {
		return; //Nesse caso não há o que atualizar
	}

}
