package datos;
import java.sql.*;
import java.util.Properties;
import javax.servlet.*;

public class BaseDatos {
	
	private String dbPathServer;
	private Properties props;
	
	public BaseDatos(String dbPathServer, String dbUsuario, String dbPassword, boolean dbSSL)
	{
		this.dbPathServer = "jdbc:postgresql:" +  dbPathServer;
		
		props = new Properties();
		props.setProperty("user", dbUsuario);
		props.setProperty("password", dbPassword);
		
		if(dbSSL)
			props.setProperty("ssl","true");
		
	}
	
	public BaseDatos(ServletContext context)
	{
		this.dbPathServer = "jdbc:postgresql:" +  context.getInitParameter("pg-url");
		
		props = new Properties();
		props.setProperty("user", context.getInitParameter("pg-user"));
		props.setProperty("password", context.getInitParameter("pg-password"));
		
		if(context.getInitParameter("pg-ssl").equals("true"))
			props.setProperty("ssl","true");
		
	}
	
	public ResultSet consultar(String consulta) throws SQLException
	{
		Connection conn = DriverManager.getConnection(dbPathServer, props);
			
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(consulta);
		
		return rs;
	}
	
	public void actualizar(String sentencia) throws SQLException
	{
		Connection conn = DriverManager.getConnection(dbPathServer, props);
			
		Statement st = conn.createStatement();
		st.executeUpdate(sentencia);
		
		conn.close();
	}
	
	public static String limpiarParametro(String valor)
	{
		return valor.replace('\'', '"').replace(';', ',');
	}
}