package utopia.engine.graphics.surfaces;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.util.LinkedList;

public class DotGraph extends MSurface {
	private final int DOT_SIZE;
	private final float GAP_X;
	private final LinkedList<Float> list = new LinkedList<>(); //Guarda os 30 pontos do gráfico
	private float maxLimit; //Limites verticais
	private float minLimit;
	private final Font fontValue = new Font("Sans Serif", Font.PLAIN, 9);
	private final Font fontLabel = new Font("Sans Serif", Font.PLAIN, 10);
	private boolean showLines = true;
	

	public DotGraph(int width, int height, int dotSize, int items) {
		super(width, height);
		
		DOT_SIZE = dotSize;
		GAP_X = getWidth() / items;
		
		validate();
		
		getDrawingSurf().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	

	private void drawLabels(){
		getDrawingSurf().setColor(Color.WHITE);
		getDrawingSurf().setFont(fontLabel);
		FontMetrics fm = getDrawingSurf().getFontMetrics();
		
		int x = getWidth() - fm.stringWidth("-99");
		int topY = fm.getAscent();
		int bottomY = getHeight() - fm.getHeight();
		int middleY = (topY + bottomY) / 2;
		
		getDrawingSurf().drawString(""+(int)(maxLimit), x, topY);
		getDrawingSurf().drawString(""+(int)(minLimit), x, bottomY);
		getDrawingSurf().drawLine(fm.stringWidth("-99"), middleY, x, middleY);
		
	}
	
	private void drawDot(float temp, int px, int py){
		//Define a cor mais apropriada
		if (temp == maxLimit) getDrawingSurf().setColor(Color.RED);
		else if (temp == minLimit) getDrawingSurf().setColor(Color.CYAN);
		else getDrawingSurf().setColor(Color.YELLOW);

		//Desenha o ponto
		getDrawingSurf().fillOval(px, py-(DOT_SIZE >> 1), DOT_SIZE, DOT_SIZE);
	}

	private void drawCurrentDot(float temp, int px, int py){
		getDrawingSurf().setColor(Color.GREEN);
		
		//Desenha o ponto
		int size = DOT_SIZE + 2;
		py -= (size >> 1);
		getDrawingSurf().fillOval(px, py, size, size);
		
		getDrawingSurf().setColor(Color.ORANGE);
		getDrawingSurf().setFont(fontValue);

		String str = "" + (int)temp;
		px = px - (getDrawingSurf().getFontMetrics().stringWidth(str) / 2) + (size >> 1);
		
		//Desenha o valor numérico
		if (py < (getHeight() / 3)) getDrawingSurf().drawString(str, px, py + 2 + (size << 1));
		else getDrawingSurf().drawString(str, px, py - 2);
	}

	public void addValue(float value){
		//Atualiza os limites se necessário
		if (list.isEmpty()){
			minLimit = value;
			maxLimit = value + 1;
		}
		if (value < minLimit) minLimit = value;
		if (value > maxLimit) maxLimit = value;
		
		//Adiciona no fim da lista e elimina algum se passar de 30 valores
		if (list.size() >= 30) list.removeFirst();
		list.add(Float.valueOf(value));
	}
	
	public void clear(){
		list.clear();
	}

	public void setLines(boolean value){
		showLines = value;
	}
	
	@Override
	public void updateGraphics() {
		getDrawingSurf().setComposite(AlphaComposite.Clear);
		getDrawingSurf().fillRect(0, 0, getWidth(), getHeight());
		getDrawingSurf().setComposite(AlphaComposite.SrcOver);
		
		if (list.isEmpty()) return;
		drawLabels();
		
		int oldX = 0, oldY = 0; //Apenas para linhas
		
		float gapY = getHeight() / (maxLimit - minLimit);
		int px = (int)(GAP_X / 2);
		for (int i=0; i<list.size(); i++){
			float temp = list.get(i).floatValue();
			int py = getHeight() - (int)((temp - minLimit) * gapY);
			
			
			//Desenha o ponto do modo mais conveniente
			if (i == list.size()-1) drawCurrentDot(temp, px, py);
			else if (!showLines) drawDot(temp, px, py);
			
			//Desenha uma linha ligando o ponto atual ao anterior (exceto no primeiro)
			if (showLines && i > 0){
				getDrawingSurf().setColor(Color.YELLOW);
				int offset = DOT_SIZE >> 1;
				getDrawingSurf().drawLine(oldX + offset, oldY - offset, px + offset, py - offset);
			}
			oldX = px;
			oldY = py;
			
			px += GAP_X;
		}
		
	}
	
}
