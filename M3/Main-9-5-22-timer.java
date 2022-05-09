package PlanetWars;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Main {

	public static void main(String[] args) {
		
		Planet p=new Planet();
		VentanaInicial ventanita=new VentanaInicial();
		Timer timer = new Timer (1000, new ActionListener ()
		{
		    public void actionPerformed(ActionEvent e)
		    {
		        // Aquí el código que queramos ejecutar.
		    	p.setDeuterium(p.getDeuterium()+10);
		    	p.setMetal(p.getMetal()+10);
		    	ventanita.aumentadorRecursos(p.getMetal(), p.getDeuterium());
		     }
		});
		timer.start();
		//p.upgradeTechnologyDefense();
		
	}

}
