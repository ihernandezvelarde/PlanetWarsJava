package PlanetWars;

import java.util.ArrayList;

public class MissileLauncher extends Defense implements MilitaryUnit{

    MissileLauncher() {
        super.setInitialArmor(ARMOR_MISSILELAUNCHER);
        super.setBaseDamage(BASE_DAMAGE_MISSILELAUNCHER);
        super.setArmor(ARMOR_MISSILELAUNCHER);
    }

    @Override
    public int attack() {return this.getBaseDamage();}

    @Override
    public void takeDamage(int receivedDamage) {
        this.setArmor(this.getActualArmor() - receivedDamage);
    }

    @Override
    public int getActualArmor() {return this.getArmor();}

    @Override
    public int getMetalCost() {return this.METAL_COST_MISSILELAUNCHER;}

    @Override
    public int getDeuteriumCost() {return this.DEUTERIUM_COST_MISSILELAUNCHER;}

    @Override
    public int getChanceGeneratinWaste() {return this.CHANCE_GENERATNG_WASTE_MISSILELAUNCHER;}

    @Override
    public int getChanceAttackAgain() {return this.CHANCE_ATTACK_AGAIN_MISSILELAUNCHER;}

    public void changeStatsByTech(int techArmor,int techDamage){
        this.setBaseDamage(BASE_DAMAGE_MISSILELAUNCHER + (techDamage * PLUS_ATTACK_MISSILELAUNCHER_BY_TECHNOLOGY)*BASE_DAMAGE_MISSILELAUNCHER/100);
        this.setInitialArmor(ARMOR_MISSILELAUNCHER + (techArmor * PLUS_ARMOR_MISSILELAUNCHER_BY_TECHNOLOGY)*ARMOR_MISSILELAUNCHER/100);
    }
}


