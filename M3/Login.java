package PlanetWars;

import org.jdatepicker.impl.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

class VentanaLogin extends JFrame {
    private JLabel titulo,nom,pwd;
    private JTextField nombre;
    private JPasswordField password;
    private JPanel panelG,panelLogin;
    private JButton login,register;
    

    VentanaLogin(Connection con){
        iniciar();
        setTitle("Inicio de sesión");
        
        panelLogin = new JPanel();
        panelLogin.setOpaque(false);

        panelG = new JPanel(new FlowLayout());
        panelG = new JPanelConFondo(new ImageIcon("space3.jpg").getImage());

        GridLayout grid = new GridLayout(3,1);
        grid.setHgap(25);
        grid.setVgap(25);

        panelLogin.setLayout(grid);
        panelLogin.setBorder(new EmptyBorder(35, 35, 35, 35));

        titulo = new JLabel("Planet Wars");
        titulo.setFont(new Font("Aharoni",Font.BOLD,40));
        titulo.setForeground(Color.white);

        nom = new JLabel("Nombre: "); nombre = new JTextField(14);
        pwd = new JLabel("Contrasenya: "); password = new JPasswordField(14);
        login = new JButton("Iniciar sesión");register = new JButton("Registrarse");

        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nombre.getText().isEmpty() || password.getPassword().length == 0){
                    JOptionPane.showMessageDialog(null, "Rellené los campos", "Inicio de sesión", JOptionPane.WARNING_MESSAGE, null);
                } else {
                    try {
                        String query = "select Username,Password from USU";
                        Statement stm = con.createStatement();
                        ResultSet rs = stm.executeQuery(query);

                        while(rs.next()){
                            if (rs.getString(1) == nombre.getText() || rs.getString(1) == nombre.getText()){
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Has iniciado sesión correctamente",
                                        "Inicio de sesión",
                                        JOptionPane.INFORMATION_MESSAGE,
                                        null);
                                new VentanaInicial(con);
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        "Error | Usuario o contrasenya incorrecto",
                                        "Inicio de sesión",
                                        JOptionPane.INFORMATION_MESSAGE,
                                        null);
                                new VentanaLogin(con);
                            }
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VentanaRegister(con);
            }
        });

        nom.setForeground(Color.white);pwd.setForeground(Color.white);

        panelLogin.add(nom);panelLogin.add(nombre);
        panelLogin.add(pwd);panelLogin.add(password);
        panelLogin.add(register);panelLogin.add(login);

        panelG.add(titulo);
        panelG.add(panelLogin);

        add(panelG,BorderLayout.CENTER);
        this.setVisible(true);
    }

    public void iniciar(){
        
        this.setIconImage(new ImageIcon("ico.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650,350);
        // this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

}

class VentanaRegister extends JFrame{

    private JLabel titulo,nom,fecha,pwd;
    private JTextField nombre;
    private JPasswordField password;
    private JPanel panelG,panelLogin;
    private JButton register,login;

    VentanaRegister(Connection con){
        iniciar();

        setTitle("Registro de usuario");

        panelLogin = new JPanel();   panelG = new JPanel(new FlowLayout());
        panelLogin.setOpaque(false); panelG = new JPanelConFondo(new ImageIcon("space3.jpg").getImage());

        // Calendario
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");p.put("text.month", "Month");p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Grid del Login
        GridLayout grid = new GridLayout(4,1);
        grid.setHgap(25);
        grid.setVgap(25);

        panelLogin.setLayout(grid);

        // Padding del panel
        panelLogin.setBorder(new EmptyBorder(35, 35, 35, 35));

        // Labels y TextFields
        titulo = new JLabel("Planet Wars");
        titulo.setFont(new Font("Aharoni",Font.BOLD,40));
        titulo.setForeground(Color.white);

        nom = new JLabel("Nombre: "); nombre = new JTextField(14);
        pwd = new JLabel("Contrasenya: "); password = new JPasswordField(14);
        fecha = new JLabel("Fecha de nacimiento: ");
        register = new JButton("Registrarse");login = new JButton("Iniciar sesión");

        // Action Listeners
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    java.util.Date selectedDate = (Date) datePicker.getModel().getValue();

                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                    if (selectedDate == null){
                        JOptionPane.showMessageDialog(null, "Rellené los campos", "Registro de usuario", JOptionPane.WARNING_MESSAGE, null);
                    } else {
                        String today = formatter.format(selectedDate);

                        if (nombre.getText().isBlank() || selectedDate == null || String.valueOf(password.getPassword()).isBlank()) {
                            JOptionPane.showMessageDialog(null, "Rellené los campos", "Registro de usuario", JOptionPane.WARNING_MESSAGE, null);
                        }

                        else {
                            Statement stm = con.createStatement();
                            String query = "select * from USU";
                            ResultSet rs = stm.executeQuery(query);

                            while (rs.next()) {
                                if (nombre.getText() == rs.getString(2)) {
                                    JOptionPane.showMessageDialog(null, "Ya existe un usuario similar", "Registro de usuario", JOptionPane.WARNING_MESSAGE, null);
                                }
                                else {
                                    String insert = "insert into USU(Username,Birth_Date,Password) values(?,?,?)";
                                    PreparedStatement pr_stm = con.prepareStatement(insert);

                                    pr_stm.setString(2, nombre.getText());
                                    pr_stm.setString(3, datePicker.getToolTipText());
                                    pr_stm.setString(4, String.valueOf(password.getPassword()));

                                    pr_stm.executeUpdate();}
                            }}
                    }


                }

                catch(SQLException ex){ex.printStackTrace();}}

        });
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new VentanaLogin(con);
            }
        });

        nom.setForeground(Color.white);pwd.setForeground(Color.white);fecha.setForeground(Color.white);


        // Añadir Objetos
        panelLogin.add(nom);panelLogin.add(nombre);
        panelLogin.add(fecha);panelLogin.add(datePicker);
        panelLogin.add(pwd);panelLogin.add(password);
        panelLogin.add(login);panelLogin.add(register);

        panelG.add(titulo);
        panelG.add(panelLogin);

        add(panelG,BorderLayout.CENTER);}

    public void iniciar(){
        this.setVisible(true);
        this.setIconImage(new ImageIcon("ico.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(650,350);
        // this.setResizable(false);
        this.setLocationRelativeTo(null);
    }
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}

