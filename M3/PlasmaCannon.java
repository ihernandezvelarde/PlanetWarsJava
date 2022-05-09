package PlanetWars;

public class PlasmaCannon extends Defense implements MilitaryUnit{

    PlasmaCannon() {
        super.setInitialArmor(ARMOR_PLASMACANNON);
        super.setBaseDamage(BASE_DAMAGE_PLASMACANNON);
        super.setArmor(ARMOR_PLASMACANNON);
    }

    @Override
    public int attack() {return this.getBaseDamage();}

    @Override
    public void takeDamage(int receivedDamage) {this.setArmor(this.getActualArmor() - receivedDamage);}

    @Override
    public int getActualArmor() {return this.getArmor();}

    @Override
    public int getMetalCost() {return this.METAL_COST_PLASMACANNON;}

    @Override
    public int getDeuteriumCost() {return this.DEUTERIUM_COST_PLASMACANNON;}

    @Override
    public int getChanceGeneratinWaste() {return this.CHANCE_GENERATNG_WASTE_PLASMACANNON;}

    @Override
    public int getChanceAttackAgain() {return this.CHANCE_ATTACK_AGAIN_PLASMACANNON;}

    public void changeStatsByTech(int tech){
        this.setBaseDamage(BASE_DAMAGE_PLASMACANNON + (tech * PLUS_ATTACK_PLASMACANNON_BY_TECHNOLOGY)*BASE_DAMAGE_PLASMACANNON/100);
        this.setInitialArmor(ARMOR_PLASMACANNON + (tech * PLUS_ARMOR_PLASMACANNON_BY_TECHNOLOGY)*ARMOR_PLASMACANNON/100);
    }
}
