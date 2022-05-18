package PlanetWars;

public abstract class Defense implements Variables,MilitaryUnit{
    private int armor;
    private int initialArmor;
    private int baseDamage;
    private int quantity;
    


    public int attack() {return this.getBaseDamage();}
    public void takeDamage(int receivedDamage) {this.setArmor(this.getActualArmor() - receivedDamage);}
    public int getActualArmor() {return this.getArmor();}
    public void resetArmor() {armor = initialArmor;}
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    


    public int getArmor() {return this.armor;}
    public void setArmor(int armor) {this.armor = armor;}
    public int getInitialArmor() {return initialArmor;}
    public void setInitialArmor(int initialArmor) {this.initialArmor = initialArmor;}
    public int getBaseDamage() {return this.baseDamage;}
    public void setBaseDamage(int baseDamage) {this.baseDamage = baseDamage;}
    

}  
