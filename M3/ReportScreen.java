package PlanetWars;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class ReportScreen extends JPanel {
	
	Connection con;
	
	JPanel panel1, panel2, panel3;
	JLabel label_bat = new JLabel("Battle ID: ");
	JTextField id_bat;
	JTextArea terminal;
	JButton boton_resumen, boton_completo;
	
	int total_starting_units_planet, total_final_units_planet, total_starting_units_enemy, total_final_units_enemy,
	planet_metal_costs, planet_deuterium_costs, enemy_metal_costs, enemy_deuterium_costs, planet_metal_losses, 
	planet_deuterium_losses, enemy_metal_losses, enemy_deuterium_losses;
	
	String complete_rep;
	
	int[] starting_units_planet = new int[7];
	int[] final_units_planet = new int[7];
	
	int[] starting_units_enemy = new int[4];
	int[] final_units_enemy = new int[4];
	
	public ReportScreen(Connection con) {
		
		// guarda la connexio a la bbdd com a un atribut
		this.con = con;
		
		// panel principal que conte tots els elements i els altres panels
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(2, 1));
		
		// panel que conte la JLabel de id de batalla, un textfield per a insertar la id de batalla i un panel amb dos botons per a mostrar els reports
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1, 3));
		
		// panel que conte els botons per a mostrar els reports
		panel3 = new JPanel();
		panel3.setLayout(new GridLayout(2, 1));
		
		// textfield per a insertar la id de batalla
		id_bat = new JTextField();
		id_bat.setSize(100, 100);
		
		// textarea per a mostrar els reports
		terminal = new JTextArea();
		terminal.setEditable(false);
		
		// botons per a mostrar els reports i els seus respectius listeners
		boton_resumen = new JButton("Show Battle Summary");
		boton_resumen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// primer es comprova que la ID sigui correcta
					CallableStatement cst = con.prepareCall("{call check_battle (?, ?)");
					cst.setInt(1, Integer.parseInt(id_bat.getText()));
					cst.registerOutParameter(2, java.sql.Types.INTEGER);
					if (cst.getInt(2) == 0) {
						JOptionPane.showMessageDialog(null, "ERROR: battle ID does not exist");;
					} else {
						// si existeix la batalla es genera el report resumit
						setVariables(Integer.parseInt(id_bat.getText()));
						terminal.setText(getSummary(Integer.parseInt(id_bat.getText())));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "ERROR: battle ID must be a number");
				}
			}
			
		});
		
		boton_completo = new JButton("Show Battle Development");
		boton_completo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CallableStatement cst = con.prepareCall("{call check_battle (?, ?)");
					cst.setInt(1, Integer.parseInt(id_bat.getText()));
					cst.registerOutParameter(2, java.sql.Types.INTEGER);
					if (cst.getInt(2) == 0) {
						JOptionPane.showMessageDialog(null, "ERROR: battle ID does not exist");;
					} else {
						setVariables(Integer.parseInt(id_bat.getText()));
						setCompleteReport(Integer.parseInt(id_bat.getText()));
						terminal.setText(complete_rep);
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "ERROR: battle ID must be a number");
				}
			}
			
		});
		
		// s'aniden els panels i s'afageixen a la finestra
		panel1.add(panel2);
		panel1.add(terminal);
		panel2.add(label_bat);
		panel2.add(id_bat);
		panel2.add(panel3);
		panel3.add(boton_resumen);
		panel3.add(boton_completo);
		
		add(panel1);
		

	}
	
	private String getSummary(int id_battle) {
		String msg = (
				"BATTLE ID: " + id_battle + "\n" + 
				"BATTLE STATISTICS" + "\n\n" + 
				"Planet Army" + "          " /*10*/ + "Starting Units" + "     " /*5*/ + "Remaining Units" + "     " /*5*/ + "Enemy Army" + "          " /*10*/ + "Units" + "     " /*5*/ + "Cost" + "\n" +
				"Light Hunter" + "          " + starting_units_planet[0] + "     " + final_units_planet[0] + "     " + "Light Hunter" + "          " + starting_units_enemy[0] + "     " + final_units_enemy[0] + "\n" +
				"Heavy Hunter" + "          " + starting_units_planet[1] + "     " + final_units_planet[1] + "     " + "Heavy Hunter" + "          " + starting_units_enemy[1] + "     " + final_units_enemy[1] + "\n" +
				"Battle Ship" + "          " + starting_units_planet[2] + "     " + final_units_planet[2] + "     " + "Battle Ship" + "          " + starting_units_enemy[2] + "     " + final_units_enemy[2] + "\n" +
				"Armored Ship" + "          " + starting_units_planet[3] + "     " + final_units_planet[3] + "     " + "Armored Ship" + "          " + starting_units_enemy[3] + "     " + final_units_enemy[3] + "\n" +
				"Missile Launcher" + "          " + starting_units_planet[4] + "     " + final_units_planet[4] + "     " + "Missile Launcher" + "          " + starting_units_enemy[4] + "     " + final_units_enemy[4] + "\n" +
				"Ion Cannon" + "          " + starting_units_planet[5] + "     " + final_units_planet[5] + "     " + "Plasma Hunter" + "          " + starting_units_enemy[5] + "     " + final_units_enemy[5] + "\n" +
				"Plasma Cannon" + "          " + starting_units_planet[6] + "     " + final_units_planet[6] + "     " + "Plasma Hunter" + "          " + starting_units_enemy[6] + "     " + final_units_enemy[6] + "\n" +
				"TOTAL" + "          " + total_starting_units_planet + "     " + total_final_units_planet + "     " + "TOTAL" + "          " + total_starting_units_enemy + "     " + total_final_units_enemy + "\n" +
				"******************************************************************************************************************************************************************************" + "\n" +
				"Planet Army's Costs" + "                    " /*20*/ + "Enemy Army's Costs" + "\n" +
				"Metal: " + planet_metal_costs + "                    " + "Metal: " + enemy_metal_costs + "\n" +
				"Deuterium: " + planet_deuterium_costs + "                    " + "Deuterium: " + enemy_deuterium_costs +
				"******************************************************************************************************************************************************************************" + "\n" +
				"Planet Army's Losses" + "                    " + "Enemy Army's Losses" + "\n" + 
				"Metal: " + planet_metal_losses + "                    " + "Metal: " + enemy_metal_losses + "\n" +
				"Deuterium: " + planet_deuterium_losses + "                    " + "Deuterium: " + enemy_deuterium_losses
				);
		return msg;
	}
	
	private void setUnits(int id_battle, int index) {
		try {
			CallableStatement cst1 = con.prepareCall("{call get_ship_battle (?, ?, ?, ?, ?)}");
			CallableStatement cst2 = con.prepareCall("{call get_defense_battle (?, ?, ?, ?, ?)}");
			
			cst1.setInt(1, id_battle);
			
			cst1.registerOutParameter(4, java.sql.Types.INTEGER);
			cst1.registerOutParameter(5, java.sql.Types.INTEGER);
			
			cst1.execute();
			
			cst1.setInt(2, index+1);
			cst1.setInt(3, 0);
			
			cst1.execute();
			
			starting_units_planet[index] = cst1.getInt(4);
			final_units_planet[index] = cst1.getInt(5);
			
			cst1.setInt(3, 1);
			
			cst1.execute();
			
			starting_units_enemy[index] = cst1.getInt(4);
			final_units_enemy[index] = cst1.getInt(5);
			
			if (index < 3) {
				cst2.setInt(1, id_battle);
				cst2.setInt(2, index+1);
				cst2.setInt(3, 0);
				starting_units_planet[index+4] = cst2.getInt(4);
				final_units_planet[index+4] = cst2.getInt(4);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setVariables(int id_batalla) {
		try {
			CallableStatement cst = con.prepareCall("{call SHOW_REPORT (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cst.setInt(1, id_batalla);
			
			for (int i = 2; i < 14; i++) {
				cst.registerOutParameter(i, java.sql.Types.INTEGER);
			}
			
			cst.execute();
			
			total_starting_units_planet = cst.getInt(2);
			total_final_units_planet = cst.getInt(3);
			total_starting_units_enemy = cst.getInt(4);
			total_final_units_enemy = cst.getInt(5);
			planet_metal_costs = cst.getInt(6);
			planet_deuterium_costs = cst.getInt(7);
			enemy_metal_costs = cst.getInt(8);
			enemy_deuterium_costs = cst.getInt(9);
			planet_metal_losses = cst.getInt(10);
			planet_deuterium_losses = cst.getInt(11);
			enemy_metal_losses = cst.getInt(12);
			enemy_deuterium_losses = cst.getInt(13);
			
			for (int i = 0; i < 4; i++) {
				setUnits(id_batalla, i);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void setCompleteReport(int id_batalla) {
		try {
			CallableStatement cst = con.prepareCall("{call GET_STEP_AMOUNT (?, ?)}");
			
			cst.setInt(1, id_batalla);
			
			cst.execute();
			cst.registerOutParameter(2, java.sql.Types.INTEGER);
			
			String msg = "";
			
			int num_steps = cst.getInt(2);
			for (int i = 1; i <= i; i++) {
				cst = con.prepareCall("{call GETBATTLESTEP (?, ?, ?)}");
				cst.setInt(1, id_batalla);
				cst.setInt(2, i);
				
				cst.registerOutParameter(3, java.sql.Types.VARCHAR);
				
				msg += "\n" + cst.getString(3);
			}
			
			complete_rep = msg;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}






