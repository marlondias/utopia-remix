package utopia.engine.graphics.surfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class TextLine extends MSurface {
	private int lineWidth; //Largura do texto (pixels)
	private int lineHeight; //Altura do texto
	private int baseline; //Posição a ser deslocada para posicionar o texto
	private String text; //Palavra ou frase
	private Font font; //A fonte a ser usada
	private Color color = Color.WHITE; //Cor do texto
	
	
	public TextLine(String txt, Font fnt) {
		super(1, 1);

		//Define uma fonte padrão para evitar nullPointer
		this.font = (fnt == null) ? new Font("SansSerif", Font.PLAIN, 10) : fnt;

		this.setString(txt);
		super.validate();
	}
	
	
	public void setString(String txt){
		this.text = (txt == null) ? "(null)" : txt;
		this.adaptSize();
	}

	public void setFont(Font f){
		if (f == null) return;
		this.font = f;
		this.adaptSize();
	}

	public void setFontSize(float size){
		if (size < 1) return;
		font = font.deriveFont(size);
		this.adaptSize();
	}

	public void setColor(Color c){
		if (c == null) return;
		this.color = c;
	}
	
	private void adaptSize(){
		//Muda tamanho da superfície em caso de mudança na fonte ou texto
		FontMetrics fm = super.getDrawingSurf().getFontMetrics(this.font);
		lineWidth = fm.stringWidth(this.text);
		lineHeight = fm.getHeight();
		baseline = fm.getAscent();
		super.changeSize(lineWidth, lineHeight);
	}
	
	@Override
	public void updateGraphics() {
		super.getDrawingSurf().setFont(font);
		super.getDrawingSurf().setColor(color);
		super.getDrawingSurf().drawString(this.text, 0, baseline);
	}

}
