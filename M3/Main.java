package PlanetWars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.Timer;

import org.eclipse.persistence.sessions.Login;

public class Main {

	public static void main(String[] args) {
		ConnectionBDD c = new ConnectionBDD();
		
		Connection con= c.getConex();
		InfoShips info=new InfoShips();
		
		ArmoredShip a = new ArmoredShip(1, 1, con);
		System.out.println(a.getMetalCost());
		Planet p=new Planet();
		//prueba de login
		InfoPlanet pla=new InfoPlanet();
		System.out.println(pla.getInfoQuantityShips(con, 1, 0, 1, 2));
		InfoUsers usu=new InfoUsers();
		System.out.println(usu.compruebaUser(con, "prueba", "P@ssw0rd"));
		CallableStatement cst=usu.getInfoPlanetId(con, "prueba");
		try {
			cst.execute();
			System.out.println(cst.getInt(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//
		//VentanaLogin v = new VentanaLogin(con);
		VentanaLogin l=new VentanaLogin(con);
//		Timer timer = new Timer (1000, new ActionListener ()
//		{
//		    public void actionPerformed(ActionEvent e)
//		    {
//		        // Aqu el cdigo que queramos ejecutar.
//		    	p.setDeuterium(p.getDeuterium()+10);
//		    	p.setMetal(p.getMetal()+10);
//		    	ventanita.aumentadorRecursos(p.getMetal(), p.getDeuterium());
//		     }
//		});
//		timer.start();
		//p.upgradeTechnologyDefense();
	}
}
