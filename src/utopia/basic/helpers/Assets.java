package utopia.basic.helpers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import utopia.engine.graphics.MTileset;

//Carrega previamente todo o conteúdo do jogo (imagens, sons, textos e cores)
public class Assets {
	public static final BufferedImage RINGICONS_W48 = openImageFile("res/ring-icons/ri-white-48px.png");
	public static final BufferedImage RINGICONS_B48 = openImageFile("res/ring-icons/ri-black-48px.png");
	public static final Color ICON_NORMAL_COLOR = Color.decode("#5a5a5a");
	public static final Color ICON_HIGHLIGHT_COLOR = Color.decode("#2b2b2b");
	public static final Color ICON_SELECTED_COLOR = Color.decode("#FF8B43");
	
	public static final MTileset TILESET_TERRAIN = new MTileset(48, 48, "res/tilesets/tileset48-terrain.png");
	public static final MTileset TILESET_RESOURCES = new MTileset(48, 48, "res/tilesets/tileset48-resources.png");
	
	
	/**
	 * Carrega um arquivo de imagem do local informado, pode retornar NULL
	 * 
	 * @param file o caminho completo do arquivo
	 * @return BufferedImage com o conteúdo visual da imagem
	 */
	public static BufferedImage openImageFile(String file){
		File f = new File(file);
		BufferedImage img = null;
        try {
			img = ImageIO.read(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return img;
	}

}
