package PlanetWars;

public class HeavyHunter extends Ship implements Variables,MilitaryUnit{
	public HeavyHunter() {
		super.setInitialArmor(ARMOR_HEAVYHUNTER);
		super.setArmor(ARMOR_HEAVYHUNTER);
		super.setBaseDamage(BASE_DAMAGE_HEAVYHUNTER);

	}
	@Override
	public void takeDamage(int receivedDamage) {
		this.setArmor(this.getArmor()-receivedDamage);		
	}
	@Override
	public int getMetalCost() {
		// TODO Auto-generated method stub
		 return this.METAL_COST_HEAVYHUNTER;
	}
	@Override
	public int getDeuteriumCost() {
		// TODO Auto-generated method stub
		return this.DEUTERIUM_COST_HEAVYHUNTER;
	}
	@Override
	public int getChanceGeneratinWaste() {
		// TODO Auto-generated method stub
		return this.CHANCE_GENERATNG_WASTE_HEAVYHUNTER;
	}
	@Override
	public int getChanceAttackAgain() {
		// TODO Auto-generated method stub
		return this.CHANCE_ATTACK_AGAIN_HEAVYHUNTER;
	}
	
	public void cambiarArmadura(int tecnologia) {
		this.setInitialArmor(ARMOR_HEAVYHUNTER + (tecnologia*PLUS_ARMOR_HEAVYHUNTER_BY_TECHNOLOGY)*ARMOR_HEAVYHUNTER/100);     
		this.setBaseDamage(BASE_DAMAGE_HEAVYHUNTER+(tecnologia*PLUS_ATTACK_HEAVYHUNTER_BY_TECHNOLOGY)*BASE_DAMAGE_HEAVYHUNTER/100);
	}
}
