package PlanetWars;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

public class BattleScreen extends JFrame {
	
	Battle battle;
	Planet planet;
	
	private JPanel marcador = new JPanel();
	private JPanel unitgrid_player = new JPanel();
	private JPanel unitgrid_enemy = new JPanel();
	
	private JLabel tit_jugador = new JLabel("PLAYER");
	private JLabel tit_enemy = new JLabel("ENEMY");
	
	private JLabel lightHunter_player;
	private JLabel lightHunter_enemy;
	
	private JLabel heavyHunter_player;
	private JLabel heavyHunter_enemy;
	
	private JLabel battleShip_player;
	private JLabel battleShip_enemy;
	
	private JLabel armoredShip_player;
	private JLabel armoredShip_enemy;
	
	private JLabel missileLauncher_player;
	private JLabel missileLauncher_enemy;
	
	private JLabel ionCannon_player;
	private JLabel ionCannon_enemy;
	
	private JLabel plasmaCannon_player;
	private JLabel plasmaCannon_enemy;
	
	private JTextArea desc = new JTextArea();
	
	private JButton boton = new JButton("CONTINUE");
	
	
	
	
	public BattleScreen(ArrayList<MilitaryUnit>[] enemyArmy) {
		battle = new Battle(planet.getArmy(), enemyArmy);
		updateLabels();
		
		
		unitgrid_player.add(lightHunter_player);
		unitgrid_player.add(heavyHunter_player);
		unitgrid_player.add(battleShip_player);
		unitgrid_player.add(armoredShip_player);
		unitgrid_player.add(missileLauncher_player);
		unitgrid_player.add(ionCannon_player);
		unitgrid_player.add(plasmaCannon_player);
		
		unitgrid_enemy.add(lightHunter_enemy);
		unitgrid_enemy.add(heavyHunter_enemy);
		unitgrid_enemy.add(battleShip_enemy);
		unitgrid_enemy.add(armoredShip_enemy);
		unitgrid_enemy.add(missileLauncher_enemy);
		unitgrid_enemy.add(ionCannon_enemy);
		unitgrid_enemy.add(plasmaCannon_enemy);
		
		desc.setEditable(false);
		String texto = battle.startBattle();
		
		int winner = battle.getWinner();
		if (winner != 0) {
			boton.setText("END BATTLE");
			if (winner == 1) {
				texto += "\n\n" + "Planet Army won the battle!";
				planet.setMetal(planet.getMetal() + battle.getWasteMetalDeuterium()[0]);
				planet.setDeuterium(planet.getDeuterium() + battle.getWasteMetalDeuterium()[1]);
			} else if (winner == 2) {
				texto += "\n\n" + "Enemy Army won the battle!";
			}
		}
		
		desc.setText(texto);
		
		this.setLayout(new GridLayout(3, 1));
		marcador.setLayout(new GridLayout(2, 3));
		unitgrid_player.setLayout(new GridLayout(4, 2));
		unitgrid_enemy.setLayout(new GridLayout(4, 2));
		
		marcador.add(tit_jugador);
		marcador.add(tit_enemy);
		marcador.add(unitgrid_player);
		marcador.add(unitgrid_enemy);
		
		boton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateLabels();
				String texto = battle.continueBattle();
				int winner = battle.getWinner();
				if (winner != 0) {
					boton.setText("END BATTLE");
					if (winner == 1) {
						texto += "\n\n" + "Planet Army won the battle!";
						planet.setMetal(planet.getMetal() + battle.getWasteMetalDeuterium()[0]);
						planet.setDeuterium(planet.getDeuterium() + battle.getWasteMetalDeuterium()[1]);
					} else if (winner == 2) {
						texto += "\n\n" + "Enemy Army won the battle!";
					}
				}
				
				desc.setText(texto);
			}
			
		});
		
		
		add(marcador);
		add(desc);
		add(boton);
		
		setSize(200,200);
		setVisible(true);
		
	}
	
	private void updateLabels() {
		
		int[] units_planet = battle.getActualNumberUnitsPlanet();
		int[] units_enemy = battle.getActualNumberUnitsPlanet();
		
		lightHunter_player = new JLabel("Light Hunter - " + units_planet[0]);
		lightHunter_enemy = new JLabel("Light Hunter - " + units_enemy[0]);
		
		heavyHunter_player = new JLabel("Heavy Hunter - " + units_planet[1]);
		heavyHunter_enemy = new JLabel("Heavy Hunter - " + units_enemy[1]);
		
		battleShip_player = new JLabel("Battle Ship - " + units_planet[2]);
		battleShip_enemy = new JLabel("Battle Ship - " + units_enemy[2]);
		
		armoredShip_player = new JLabel("Armored Ship - " + units_planet[3]);
		armoredShip_enemy = new JLabel("Armored Ship - " + units_enemy[3]);
		
		missileLauncher_player = new JLabel("Missile Launcher - " +  units_planet[4]);
		missileLauncher_enemy = new JLabel("Missile Launcher - " +  units_enemy[4]);
		
		ionCannon_player = new JLabel("Ion Cannon - " + units_planet[5]);
		ionCannon_enemy = new JLabel("Ion Cannon - " + units_enemy[5]);
		
		plasmaCannon_player = new JLabel("Plasma Cannon - " + units_planet[6]);
		plasmaCannon_enemy= new JLabel("Plasma Cannon - " + units_enemy[6]);
		
	}
	
	
	
}
