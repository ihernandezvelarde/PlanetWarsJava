package PlanetWars;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

public class IonCannon extends Defense implements MilitaryUnit{
    CallableStatement cst;

    IonCannon(int tecnoDefense, int tecnoAtack,Connection con) {
        InfoDefense def=new InfoDefense();

        cst = def.getInfoDefense(con,2);

        try {
            cst.execute();
            super.setInitialArmor(cst.getInt(6));
            super.setBaseDamage(cst.getInt(8));
            this.setArmor(this.getInitialArmor() + (tecnoDefense*PLUS_ARMOR_IONCANNON_BY_TECHNOLOGY)*this.getInitialArmor()/100);
            this.setBaseDamage(this.getBaseDamage()+(tecnoAtack*PLUS_ATTACK_IONCANNON_BY_TECHNOLOGY)*this.getBaseDamage()/100);
            cst.close();
        }

        catch (SQLException e) {e.printStackTrace();}
    }

    @Override
    public int attack() {return this.getBaseDamage();}

    @Override
    public void takeDamage(int receivedDamage) {this.setArmor(this.getActualArmor() - receivedDamage);}

    @Override
    public int getActualArmor() {return this.getArmor();}

    @Override
    public int getChanceAttackAgain() {return this.CHANCE_ATTACK_AGAIN_IONCANNON;}

    @Override
    public int getMetalCost() {
        try {return cst.getInt(3);}
        catch (SQLException e) {return 0;}
    }

    @Override
    public int getDeuteriumCost() {
        try {return cst.getInt(5);}
        catch (SQLException e) {return 0;}
    }

    @Override
    public int getChanceGeneratinWaste() {return CHANCE_GENERATNG_WASTE_IONCANNON;}


}
