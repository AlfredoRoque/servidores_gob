package SQL;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

	private final static String url = "jdbc:mysql://127.0.0.1:3306/serv_web";
	private final static String usuario = "root";
	private final static String contrasena = "";
	
	//conexion a la BD
	public static Connection conectar() {
		Connection conexion = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexion = DriverManager.getConnection(url, usuario, contrasena);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error " + e);
		}
		
		return conexion;
	}
	
}
