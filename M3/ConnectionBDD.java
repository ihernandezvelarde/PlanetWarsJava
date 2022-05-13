package PlanetWars;

import java.sql.*;

public class ConnectionBDD {
	private Connection conex;
	String url;
	String user;
	String password;
	public ConnectionBDD() {
		// Carga el driver de oracle
		try {
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

			// Conecta con la base de datos orcl con el usuario system y la contrasea password
			conex = DriverManager.getConnection("jdbc:oracle:thin:@192.168.40.2:1521:orcl", "alumnoAMS15", "alumnoAMS15");
			System.out.println("Conectado MAQUINAAA");
		}

		catch (SQLException e) {System.out.println("Error de conexion");}
	}
	public Connection getConex() {
		return conex;
	}

}

class InfoShips  {
	CallableStatement getInfoShips(Connection con, int id_nave) {
		CallableStatement cst = null;
		try {
			cst = con.prepareCall("{call getship (?,?,?,?,?,?,?,?,?,?)}");

			// Parametro 1 del procedimiento almacenado
			cst.setInt(1, id_nave);

			// Definimos los tipos de los parametros de salida del procedimiento almacenado
			cst.registerOutParameter(2, java.sql.Types.VARCHAR);//nombre
			cst.registerOutParameter(3, java.sql.Types.INTEGER);//metal cost
			cst.registerOutParameter(4, java.sql.Types.INTEGER);//cristal
			cst.registerOutParameter(5, java.sql.Types.INTEGER);//deuterium
			cst.registerOutParameter(6, java.sql.Types.INTEGER);//initial armor
			cst.registerOutParameter(7, java.sql.Types.INTEGER);//armor
			cst.registerOutParameter(8, java.sql.Types.INTEGER);//base dmg
			cst.registerOutParameter(9, java.sql.Types.INTEGER);// speed
			cst.registerOutParameter(10, java.sql.Types.INTEGER); // wastings
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return cst;
	}

}
class InfoPlanet {
	CallableStatement getInfoPlanet(Connection con, int id_planet) {
		CallableStatement cst = null;
		try {
			cst = con.prepareCall("{call get_planet (?,?,?,?,?,?,?,?)}");
			// Parametro 1 del procedimiento almacenado
			cst.setInt(1, id_planet);

			// Definimos los tipos de los parametros de salida del procedimiento almacenado
			cst.registerOutParameter(2, java.sql.Types.VARCHAR);//name
			cst.registerOutParameter(3, java.sql.Types.INTEGER);//tech atk
			cst.registerOutParameter(4, java.sql.Types.INTEGER);//tech def
			cst.registerOutParameter(5, java.sql.Types.INTEGER);//cristal
			cst.registerOutParameter(6, java.sql.Types.INTEGER);//metal
			cst.registerOutParameter(7, java.sql.Types.INTEGER);//deuterio
			cst.registerOutParameter(8, java.sql.Types.INTEGER);//id user
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cst;

	}
}
class InfoDefense {
	CallableStatement getInfoDefense(Connection con, int id_nave) {
		CallableStatement cst = null;
		try {
			cst = con.prepareCall("{call getdefense (?,?,?,?,?,?,?,?,?,?)}");
			// Parametro 1 del procedimiento almacenado
			cst.setInt(1, id_nave);

			// Definimos los tipos de los parametros de salida del procedimiento almacenado
			cst.registerOutParameter(2, java.sql.Types.VARCHAR);
			cst.registerOutParameter(3, java.sql.Types.INTEGER);
			cst.registerOutParameter(4, java.sql.Types.INTEGER);
			cst.registerOutParameter(5, java.sql.Types.INTEGER);
			cst.registerOutParameter(6, java.sql.Types.INTEGER);
			cst.registerOutParameter(7, java.sql.Types.INTEGER);
			cst.registerOutParameter(8, java.sql.Types.INTEGER);
			cst.registerOutParameter(9, java.sql.Types.INTEGER);
			cst.registerOutParameter(10, java.sql.Types.INTEGER);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cst;
	}
}

class InfoUsers{
	int compruebaUser(Connection con, String name, String password) {
		CallableStatement cst = null;

		try {
			cst = con.prepareCall("{? = call login (?,?)}");

			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, name);
			cst.setString(3, password);

			cst.execute();
		}
		catch (SQLException e) {e.printStackTrace();}

		try {return cst.getInt(1);}
		catch (SQLException e){e.printStackTrace();}

		return 0;
	}
	int existeUser(Connection con, String name) {
		CallableStatement cst = null;

		try {
			cst = con.prepareCall("{? = call comprovar_usuari (?) }");
			cst.registerOutParameter(1, Types.INTEGER);
			cst.setString(2, name);
			cst.execute();
		}
		catch (SQLException e) {e.printStackTrace();}

		try {return cst.getInt(1);}
		catch (SQLException e){e.printStackTrace();}

		return 0;
	}
	void anyadeUser(Connection con, String name, java.sql.Date fecha,String password) {
		CallableStatement cst = null;

		try {
			cst = con.prepareCall("{call add_user (?,?,?) }");
			cst.setString(1,name);
			cst.setDate(2,fecha);
			cst.setString(3, password);
			cst.execute();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	int getIdUser(Connection con, String name){

		CallableStatement cst = null;

		try {
			cst = con.prepareCall("{? = call getid (?) }");
			cst.registerOutParameter(1,Types.INTEGER);
			cst.setString(2,name);
			cst.execute();
		}
		catch (SQLException e) {e.printStackTrace();}

		try {return cst.getInt(1);}
		catch (SQLException e){e.printStackTrace();}
		return 0;
	}

	void anyadePlaneta(Connection con, String name,int id_user) {
		CallableStatement cst = null;

		try {
			cst = con.prepareCall("{call add_planet (?,?,?,?,?,?,?) }");
			cst.setString(1,name);
			cst.setInt(2,0);
			cst.setInt(3,0);
			cst.setInt(4,0);
			cst.setInt(5,0);
			cst.setInt(6,0);
			cst.setInt(7, id_user);
			cst.execute();
		}
		catch (SQLException e) {e.printStackTrace();}
	}
	CallableStatement getInfoPlanetId(Connection con, String usuario) {
		CallableStatement cst = null;
		try {
			cst = con.prepareCall("{call get_planetusu (?,?) }");

			// Parametro 1 del procedimiento almacenado
			cst.setString(1, usuario);

			// Definimos los tipos de los parametros de salida del procedimiento almacenado

			cst.registerOutParameter(2, java.sql.Types.INTEGER);


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return cst;

	}
}
