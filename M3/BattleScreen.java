package PlanetWars;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

public class BattleScreen extends JFrame {
	
	private Battle battle;
	
	private JPanel marcador = new JPanel();
	private JPanel unitgrid_player = new JPanel();
	private JPanel unitgrid_enemy = new JPanel();
	
	private JLabel tit_jugador = new JLabel("PLAYER");
	private JLabel tit_enemy = new JLabel("ENEMY");
	
	private JLabel lightHunter_player = new JLabel();
	private JLabel lightHunter_enemy = new JLabel();
	
	private JLabel heavyHunter_player = new JLabel();
	private JLabel heavyHunter_enemy = new JLabel();
	
	private JLabel battleShip_player = new JLabel();
	private JLabel battleShip_enemy = new JLabel();
	
	private JLabel armoredShip_player = new JLabel();
	private JLabel armoredShip_enemy = new JLabel();
	
	private JLabel missileLauncher_player = new JLabel();
	
	private JLabel ionCannon_player = new JLabel();
	
	private JLabel plasmaCannon_player = new JLabel();
	
	private JTextArea desc = new JTextArea();
	
	private JButton boton = new JButton("CONTINUE");
	
	private ActionListener continuar, acabar;
	
	
	
	
	public BattleScreen(Planet planet, ArrayList<MilitaryUnit>[] enemyArmy, Connection con, int idUser,String username) {
		
		
		battle = new Battle(planet.getArmy(), enemyArmy);
		
		// assignacio de colors de font i de fons
		marcador.setBackground(Color.BLACK);
		unitgrid_player.setBackground(Color.BLACK);
		unitgrid_enemy.setBackground(Color.BLACK);
		this.setBackground(Color.BLACK);
		desc.setBackground(Color.BLACK);
		desc.setForeground(Color.GREEN);
		tit_jugador.setForeground(Color.GREEN);
		tit_enemy.setForeground(Color.GREEN);
		lightHunter_player.setForeground(Color.GREEN);
		lightHunter_enemy.setForeground(Color.GREEN);
		heavyHunter_player.setForeground(Color.GREEN);
		heavyHunter_enemy.setForeground(Color.GREEN);
		battleShip_player.setForeground(Color.GREEN);
		battleShip_enemy.setForeground(Color.GREEN);
		armoredShip_player.setForeground(Color.GREEN);
		armoredShip_enemy.setForeground(Color.GREEN);
		missileLauncher_player.setForeground(Color.GREEN);
		ionCannon_player.setForeground(Color.GREEN);
		plasmaCannon_player.setForeground(Color.GREEN);
		
		// assignacio d'estils de font
		desc.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		tit_jugador.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		tit_enemy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		lightHunter_player.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		lightHunter_enemy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		heavyHunter_player.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		heavyHunter_enemy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		battleShip_player.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		battleShip_enemy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		armoredShip_player.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		armoredShip_enemy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		missileLauncher_player.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		ionCannon_player.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		plasmaCannon_player.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		
		updateLabels();
		
		// s'afageixen les dades als panels corresponents
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
		
	    this.setTitle("PLANET WARS");
        this.setResizable(false);
        //arriba para evitar redimensionar ventana , permitido por defecto

        Toolkit pantalla=Toolkit.getDefaultToolkit();
        Image img=pantalla.getImage("ico.png");
        this.setIconImage(img);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // arriba para terminar programa al cerrar ventana
		
		desc.setEditable(false);
		desc.setPreferredSize(new Dimension(500, 500));
		
		String texto = battle.startBattle();
		
		// primer torn comprovacio de que es pot continuar la batalla
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
			planet.setArmy(enemyArmy);
		}
		
		desc.setText(texto);
		
		// panels de marcador amb les seves labels
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		marcador.setLayout(new GridLayout(2, 3));
		unitgrid_player.setLayout(new GridLayout(4, 2));
		unitgrid_enemy.setLayout(new GridLayout(4, 2));
		
		marcador.add(tit_jugador);
		marcador.add(tit_enemy);
		marcador.add(unitgrid_player);
		marcador.add(unitgrid_enemy);
		
		// ActionListener per a finalitzar la batalla i sortir
		acabar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				battle.saveBattleData(con, idUser);
				dispose();
				//Falta guardado
				new VentanaInicial(con, username);
			}
		};
		
		// ActionListener per a continuar la batalla
		continuar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateLabels();
				String texto = battle.continueBattle();
				int winner = battle.getWinner();
				
				// si s'acaba la batalla canvia l'actionlistener
				if (winner != 0) {
					boton.setText("END BATTLE");
					boton.addActionListener(acabar);
					boton.removeActionListener(continuar);
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
			
		};
		
		boton.addActionListener(continuar);
		
		
		add(marcador);
		add(desc);
		add(boton);
		
		setSize(900,700);
		setVisible(true);
		
	}
	
	// metode que assigna i actualitza les quantitats de tropes
	private void updateLabels() {
		
		// Actualitza les JLabels de les dades de cada exercit
		int[] units_planet = battle.getActualNumberUnitsPlanet();
		int[] units_enemy = battle.getActualNumberUnitsEnemy();
		
		lightHunter_player.setText("Light Hunter - " + units_planet[0]);
		lightHunter_enemy.setText("Light Hunter - " + units_enemy[0]);
		
		heavyHunter_player.setText("Heavy Hunter - " + units_planet[1]);
		heavyHunter_enemy.setText("Heavy Hunter - " + units_enemy[1]);
		
		battleShip_player.setText("Battle Ship - " + units_planet[2]);
		battleShip_enemy.setText("Battle Ship - " + units_enemy[2]);
		
		armoredShip_player.setText("Armored Ship - " + units_planet[3]);
		armoredShip_enemy.setText("Armored Ship - " + units_enemy[3]);
		
		missileLauncher_player.setText("Missile Launcher - " +  units_planet[4]);
		
		ionCannon_player.setText("Ion Cannon - " + units_planet[5]);
		
		plasmaCannon_player.setText("Plasma Cannon - " + units_planet[6]);
		
	}
}