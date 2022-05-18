package PlanetWars;

import java.sql.Connection;

public interface MilitaryUnit {
    abstract int attack();
    abstract void takeDamage(int receivedDamage);
    abstract int getActualArmor();
    abstract int getMetalCost();
    abstract int getDeuteriumCost();
    abstract int getChanceGeneratinWaste();
    abstract int getChanceAttackAgain();
    abstract int getQuantity();
    abstract void setQuantity(int quantity);
    abstract void resetArmor();
//take damage
}
