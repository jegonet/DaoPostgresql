package datos;

import java.util.ArrayList;
import java.sql.*;

import javax.servlet.*;

public class Estado {

	public int idEstado = 0;
	public String nombreEstado = "";
	
	private ServletContext context;
	
	public Estado(ServletContext context)
	{
		this.context = context;
	}
	
	public ArrayList<Estado> cosultarEstados() throws Exception
	{
		ArrayList<Estado> listadoEstados = new ArrayList<Estado>();
		
		ResultSet resultado  = null;
		
		try
		{
			resultado = new BaseDatos(context).consultar("select * from estado");
			
			while(resultado.next())
			{
				Estado estado = new Estado(null);
				estado.idEstado = resultado.getInt("id");
				estado.nombreEstado = resultado.getString("nombre");
				
				listadoEstados.add(estado);
			}
			
			resultado.close();
		}
		catch(SQLException sqlEx)
		{
			resultado.close();
			
			throw new Exception("Error SQL: " +  sqlEx.getMessage());
		}
		
		return listadoEstados;
	}
}