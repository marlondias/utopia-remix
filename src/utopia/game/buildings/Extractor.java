package utopia.game.buildings;

//Gera novos recursos por extração (OreMine, Hydroponics, ChemicalPlant, MossConverter, LifeSuport...)
public interface Extractor {
	//Especificar tipo de recurso extraido
	
	public int getExtractionOutput();
	
}
