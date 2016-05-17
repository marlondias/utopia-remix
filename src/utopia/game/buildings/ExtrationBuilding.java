package utopia.game.buildings;

//Gera novos recursos por extração (OreMine, Hydroponics, ChemicalPlant, MossConverter, LifeSuport...)
public class ExtrationBuilding extends Building {

	protected ExtrationBuilding(BuildingType bt) {
		super(bt);
	}

	@Override
	protected void updateDuty() {
		//Extrair fuel, ore, gem, food, air ou seja lá o que precisar.
	}

}
