package PlanetWars;

//extends Ship
public class LightHunter extends Ship implements Variables, MilitaryUnit{

	public LightHunter() {
		super.setInitialArmor(ARMOR_LIGTHHUNTER);
		super.setArmor(ARMOR_LIGTHHUNTER);
		super.setBaseDamage(BASE_DAMAGE_LIGTHHUNTER);
	}
	@Override
	public void takeDamage(int receivedDamage) {
		this.setArmor(this.getArmor()-receivedDamage);		
	}
	@Override
	public int getMetalCost() {
		// TODO Auto-generated method stub
		 return this.METAL_COST_LIGTHHUNTER;	
	}
	@Override
	public int getDeuteriumCost() {
		// TODO Auto-generated method stub
		return DEUTERIUM_COST_LIGTHHUNTER;
	}
	@Override
	public int getChanceGeneratinWaste() {
		// TODO Auto-generated method stub
		if (this.getArmor()<=0) {
            return this.CHANCE_GENERATNG_WASTE_LIGTHHUNTER;
        }
        return 0;
    }
	@Override
	public int getChanceAttackAgain() {
		// TODO Auto-generated method stub
	return this.CHANCE_ATTACK_AGAIN_LIGTHHUNTER;	
	}
	public void cambiarArmadura(int tecnologia) {
		this.setInitialArmor(ARMOR_LIGTHHUNTER + (tecnologia*PLUS_ARMOR_LIGTHHUNTER_BY_TECHNOLOGY)*ARMOR_LIGTHHUNTER/100);
		this.setBaseDamage(BASE_DAMAGE_LIGTHHUNTER+(tecnologia*PLUS_ATTACK_LIGTHHUNTER_BY_TECHNOLOGY)*BASE_DAMAGE_LIGTHHUNTER/100);
	}
   
}
