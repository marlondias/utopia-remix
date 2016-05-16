package utopia.game.gscreen;

import java.awt.Color;
import java.awt.Font;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import utopia.engine.graphics.MGameScreen;
import utopia.engine.graphics.msurfaces.TextLine;

public class GS_Info extends MGameScreen{
	private Font labelFont = new Font("SansSerif", Font.PLAIN, 11);
	private Font valueFont = new Font("Serif", Font.PLAIN, 19);
	private TextLine labelPop;
	private TextLine labelCash;
	private TextLine labelQoL;
	private TextLine valuePop;
	private TextLine valueCash;
	private TextLine valueQoL;
	private TextLine valueDate;
	
	private int pop = 10000;
	private int cash = 1000000;
	private int qol = 45;
	private Date date = new Date();
	
	private NumberFormat nf = NumberFormat.getNumberInstance();
	private DateFormat df = new SimpleDateFormat("dd MMM yyyy");

	
	public GS_Info() {
		super();
		
		labelPop = new TextLine("Population", labelFont);
		labelCash = new TextLine("Money", labelFont);
		labelQoL = new TextLine("Quality", labelFont);
		valuePop = new TextLine(null, valueFont);
		valueCash = new TextLine(null, valueFont);
		valueQoL = new TextLine(null, valueFont);
		valueDate = new TextLine("01 Jan 2090", new Font("SansSerif", Font.PLAIN, 16));
		
		labelPop.setPosition(18, 200);
		valuePop.setPosition(18, 216);
		labelCash.setPosition(18, 257);
		valueCash.setPosition(18, 273);
		labelQoL.setPosition(18, 314);
		valueQoL.setPosition(18, 330);
		valueDate.setPosition((370 - valueDate.getWidth()), 53);
		
		labelPop.setColor(Color.decode("#d0d0d0"));
		labelCash.setColor(Color.decode("#d0d0d0"));
		labelQoL.setColor(Color.decode("#d0d0d0"));
		valueDate.setColor(Color.decode("#d0d0d0"));
		valuePop.setColor(Color.decode("#F0F0c0"));
		valueCash.setColor(Color.decode("#c0F0c0"));
		valueQoL.setColor(Color.decode("#c0F0F0"));

		super.surfaces.add(labelPop);
		super.surfaces.add(valuePop);
		super.surfaces.add(labelCash);
		super.surfaces.add(valueCash);
		super.surfaces.add(labelQoL);
		super.surfaces.add(valueQoL);
		super.surfaces.add(valueDate);
	}

	@Override
	public void updateAll() {
		valuePop.setString(nf.format(pop).toString());
		valueCash.setString(nf.format(cash).toString());
		valueQoL.setString(qol + "%");
		valueDate.setString(df.format(date));
		valueDate.setPosition((370 - valueDate.getWidth()), 53);
		
		pop += 1;
		cash += 10;
		date.setTime(date.getTime() + (1000 * 60 * 60));
	}

}
