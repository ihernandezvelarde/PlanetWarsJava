package PlanetWars;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
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
	planet_deuterium_losses, enemy_metal_losses, enemy_deuterium_losses, metal_wastings, deuterium_wastings, winner;
	
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
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		
		// panel que conte la JLabel de id de batalla, un textfield per a insertar la id de batalla i un panel amb dos botons per a mostrar els reports
		panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));

		// panel que conte els botons per a mostrar els reports
		panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		
		label_bat.setForeground(Color.WHITE);
		
		// textfield per a insertar la id de batalla
		id_bat = new JTextField();
		id_bat.setPreferredSize(new Dimension(1, 1));
		
		// textarea per a mostrar els reports
		terminal = new JTextArea();
		terminal.setEditable(false);
		terminal.setPreferredSize(new Dimension(750, 500));
		
		this.setBackground(new Color(0, 0, 0, 0));
		panel1.setBackground(new Color(0, 0, 0, 0));
		panel2.setBackground(new Color(0, 0, 0, 0));
		panel3.setBackground(new Color(0, 0, 0, 0));
		this.setPreferredSize(new Dimension(800, 600));
		
		// botons per a mostrar els reports i els seus respectius listeners
		boton_resumen = new JButton("Show Battle Summary");
		boton_resumen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// primer es comprova que la ID sigui correcta
					CallableStatement cst = con.prepareCall("{call check_battle (?, ?)}");
					cst.setInt(1, Integer.parseInt(id_bat.getText()));
					cst.registerOutParameter(2, java.sql.Types.INTEGER);
					cst.execute();
					System.out.println("comprobacion");
					int existe = cst.getInt(2);
					if (existe == 0) {
						JOptionPane.showMessageDialog(null, "ERROR: battle ID does not exist");;
					} else {
						System.out.println("a");
						// si existeix la batalla es genera el report resumit
						setVariables(Integer.parseInt(id_bat.getText()));
						terminal.setText(getSummary(Integer.parseInt(id_bat.getText())));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR");
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
					cst.execute();
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
				"Missile Launcher" + "          " + starting_units_planet[4] + "     " + final_units_planet[4] + "\n" +
				"Ion Cannon" + "          " + starting_units_planet[5] + "     " + final_units_planet[5] + "\n" +
				"Plasma Cannon" + "          " + starting_units_planet[6] + "     " + final_units_planet[6] + "\n" +
				"TOTAL" + "          " + total_starting_units_planet + "     " + total_final_units_planet + "     " + "TOTAL" + "          " + total_starting_units_enemy + "     " + total_final_units_enemy + "\n" +
				"****************************************************************************************************************" + "\n" +
				"Planet Army's Costs" + "                    " /*20*/ + "Enemy Army's Costs" + "\n" +
				"Metal: " + planet_metal_costs + "                    " + "Metal: " + enemy_metal_costs + "\n" +
				"Deuterium: " + planet_deuterium_costs + "                    " + "Deuterium: " + enemy_deuterium_costs + "\n" +
				"****************************************************************************************************************" + "\n" +
				"Planet Army's Losses" + "                    " + "Enemy Army's Losses" + "\n" + 
				"Metal: " + planet_metal_losses + "                    " + "Metal: " + enemy_metal_losses + "\n" +
				"Deuterium: " + planet_deuterium_losses + "                    " + "Deuterium: " + enemy_deuterium_losses + "\n" +
				"Weighted: " + (planet_metal_losses + planet_deuterium_losses*5) + "                    " + "Weighted: " + (enemy_metal_losses + enemy_deuterium_losses*5) + "\n" + 
				"****************************************************************************************************************" + "\n" +
				"Wastings Generated" + "\n" +
				"Metal: " + metal_wastings + "\n" +
				"Deuterium: " + deuterium_wastings + "\n\n"
				);
		if (winner == 0) {
			msg += "Battle won by Planet Army";
		} else {
			msg += "Battle won by Enemy Army";
		}
		return msg;
	}
	
	private void setUnits(int id_battle, int index) {
		try {
			CallableStatement cst1 = con.prepareCall("{call get_ship_battle (?, ?, ?, ?, ?)}");
			CallableStatement cst2 = con.prepareCall("{call get_defense_battle (?, ?, ?, ?, ?)}");
			
			cst1.setInt(1, id_battle);
			
			cst1.registerOutParameter(4, java.sql.Types.INTEGER);
			cst1.registerOutParameter(5, java.sql.Types.INTEGER);
			
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
				cst2.registerOutParameter(4, java.sql.Types.INTEGER);
				cst2.registerOutParameter(5, java.sql.Types.INTEGER);
				cst2.execute();
				starting_units_planet[index+3] = cst2.getInt(4);
				final_units_planet[index+3] = cst2.getInt(5);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setVariables(int id_batalla) {
		try {
			CallableStatement cst = con.prepareCall("{call SHOW_REPORT (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
			cst.setInt(1, id_batalla);
			
			for (int i = 1; i < 18; i++) {
				cst.registerOutParameter(i, java.sql.Types.INTEGER);
			}
			System.out.println("parametros variable");
			
			cst.execute();
			
			
			total_starting_units_planet = cst.getInt(3);
			total_final_units_planet = cst.getInt(4);
			total_starting_units_enemy = cst.getInt(5);
			total_final_units_enemy = cst.getInt(6);
			planet_metal_costs = cst.getInt(7);
			planet_deuterium_costs = cst.getInt(8);
			enemy_metal_costs = cst.getInt(9);
			enemy_deuterium_costs = cst.getInt(10);
			planet_metal_losses = cst.getInt(11);
			planet_deuterium_losses = cst.getInt(12);
			enemy_metal_losses = cst.getInt(13);
			enemy_deuterium_losses = cst.getInt(14);
			metal_wastings = cst.getInt(15);
			deuterium_wastings = cst.getInt(16);
			winner = cst.getInt(17);
			
			System.out.println("iniciando units");
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
			
			cst.registerOutParameter(2, java.sql.Types.INTEGER);
			
			cst.execute();
			System.out.println("a");
			String msg = "";
			
			int num_steps = cst.getInt(2);
			for (int i = 1; i <= num_steps; i++) {
				cst = con.prepareCall("{call GETBATTLESTEP (?, ?, ?)}");
				cst.setInt(1, id_batalla);
				cst.setInt(2, i);
				
				cst.registerOutParameter(3, java.sql.Types.VARCHAR);
				cst.execute();
				
				msg += "\n" + cst.getString(3);
			}
			
			complete_rep = msg;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}






