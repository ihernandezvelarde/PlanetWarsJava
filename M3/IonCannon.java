package PlanetWars;

public class IonCannon extends Defense implements MilitaryUnit{

    IonCannon() {
        super.setInitialArmor(ARMOR_IONCANNON);
        super.setBaseDamage(BASE_DAMAGE_IONCANNON);
        super.setArmor(ARMOR_IONCANNON);
    }

    @Override
    public int attack() {return this.getBaseDamage();}

    @Override
    public void takeDamage(int receivedDamage) {this.setArmor(this.getActualArmor() - receivedDamage);}

    @Override
    public int getActualArmor() {return this.getArmor();}

    @Override
    public int getMetalCost() {return this.METAL_COST_IONCANNON;}

    @Override
    public int getDeuteriumCost() {return this.DEUTERIUM_COST_IONCANNON;}

    @Override
    public int getChanceGeneratinWaste() {return this.CHANCE_GENERATNG_WASTE_IONCANNON;}

    @Override
    public int getChanceAttackAgain() {return this.CHANCE_ATTACK_AGAIN_IONCANNON;}

    public void changeStatsByTech(int techArmor,int techDamage){
        this.setBaseDamage(BASE_DAMAGE_IONCANNON + (techDamage * PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY)*BASE_DAMAGE_IONCANNON/100);
        this.setInitialArmor(ARMOR_IONCANNON + (techArmor * PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY)*ARMOR_IONCANNON/100);
    }
}
