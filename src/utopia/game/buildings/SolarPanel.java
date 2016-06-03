package utopia.game.buildings;

public class SolarPanel extends Building implements PowerGenerator {
	private int output;

	public SolarPanel() {
		super(BuildingType.SOLAR_PANEL);
		
		//Verificar tech level para saber o tipo de painel e a geração
		output = 5;
	}

	@Override
	public void generatePower() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getPowerOutput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void updateDuty() {
		// TODO Auto-generated method stub
		System.out.println("To gerando " + output + " MW");
		
	}

}
