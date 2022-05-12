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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class VentanaLogin extends JFrame {
    private JLabel titulo,nom,pwd;
    private JTextField nombre;
    private JPasswordField password;
    private JPanel panelG,panelLogin;
    private JButton login,register;
    InfoUsers infoU;
    CallableStatement cst;

    VentanaLogin(Connection con){
        iniciar();
        setTitle("Inicio de sesión");

        infoU = new InfoUsers();

        panelLogin = new JPanel();
        panelLogin.setOpaque(false);

        panelG = new JPanel(new FlowLayout());
        panelG = new JPanelConFondo(new ImageIcon("space2.gif").getImage());

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

                    int correcto = infoU.compruebaUser(con,nombre.getText(),String.valueOf(password.getPassword()));


                    if (correcto == 1){
                        JOptionPane.showOptionDialog(null, "Has iniciado sesión correctamente","Inicio de sesión", JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, new Object[]{}, null);
                        dispose();
                        new VentanaInicial(con);
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Error | Usuario o contrasenya incorrecto",
                                "Inicio de sesión",
                                JOptionPane.INFORMATION_MESSAGE,
                                null);
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
    InfoUsers infoU = new InfoUsers();
    CallableStatement cst;

    VentanaRegister(Connection con){
        iniciar();

        setTitle("Registro de usuario");

        panelLogin = new JPanel();   panelG = new JPanel(new FlowLayout());
        panelLogin.setOpaque(false); panelG = new JPanelConFondo(new ImageIcon("space2.gif").getImage());

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


                java.util.Date selectedDate = (Date) datePicker.getModel().getValue();
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                if (selectedDate == null) {
                    JOptionPane.showMessageDialog(null, "Rellené los campos", "Registro de usuario", JOptionPane.WARNING_MESSAGE, null);
                } else {
                    String today = formatter.format(selectedDate);

                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date parsed = format.parse(today);
                        java.sql.Date sql = new java.sql.Date(parsed.getTime());

                        if (nombre.getText().isBlank() || selectedDate == null || String.valueOf(password.getPassword()).isBlank()) {
                            JOptionPane.showMessageDialog(null, "Rellené los campos", "Registro de usuario", JOptionPane.WARNING_MESSAGE, null);
                        }
                        else {
                            int existe = infoU.existeUser(con, nombre.getText());

                            if (existe == 1) {
                                JOptionPane.showMessageDialog(null, "El usuario ya existe", "Registro de usuario", JOptionPane.WARNING_MESSAGE, null);
                            } else {
                                infoU.anyadeUser(con, nombre.getText(), sql, String.valueOf(password.getPassword()));

                                JOptionPane.showMessageDialog(null, "Te has registrado correctamente | BIENVENIDO/A", "Registro de usuario", JOptionPane.WARNING_MESSAGE, null);
                                dispose();
                                new VentanaInicial(con);
                            }
                        }
                    }
                    catch (ParseException ex) {ex.printStackTrace();}
                }
            }

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

        add(panelG,BorderLayout.CENTER);

        this.setVisible(true);}

    public void iniciar(){
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
