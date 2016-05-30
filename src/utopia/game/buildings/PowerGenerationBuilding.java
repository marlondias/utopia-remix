package utopia.game.buildings;

//Gera energia constantemente (SolarPanel, SolarGenerator, PowerBuilding)
public class PowerGenerationBuilding extends Building {

	public PowerGenerationBuilding(BuildingType bt) {
		super(bt);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateDuty() {
		System.out.println("To gerando energia! E gastando " + super.getPowerDemand());
		
	}

}
