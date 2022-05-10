package PlanetWars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;



public class ConnectionBDD {
	private Connection conex;
	String url;
	String user;
	String password;
	public ConnectionBDD() {
		try {
			url = "lalal";
			user= "lol";
			password= "lel";
			Class.forName("com.mysql.cj.jdbc.Driver");
			// establecer conex a base datos NO OLVIDEMOS EL .JAR!!!!!!! (libreria)
			this.conex=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("ERROR conexion");
		}
		
	}
	public Connection getConex() {
		return conex;
	}
	
}