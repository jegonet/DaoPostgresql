package datos;

import java.util.ArrayList;
import java.sql.*;
import javax.servlet.*;

public class Perfil {
	
	public int id = 0;
	public String nombre = "";
	
	private ServletContext context;
	
	public Perfil(ServletContext context)
	{
		this.context = context;
	}
	
	public ArrayList<Perfil> cosultarPerfiles() throws Exception
	{
		ArrayList<Perfil> listadoPerfiles = new ArrayList<Perfil>();
		
		ResultSet resultado = null;
		
		try
		{
			resultado = new BaseDatos(context).consultar("select * from perfil");
			
			while(resultado.next())
			{
				Perfil estado = new Perfil(null);
				estado.id = resultado.getInt("id");
				estado.nombre = resultado.getString("nombre");
				
				listadoPerfiles.add(estado);
			}
			
			resultado.close();
		}
		catch(SQLException sqlEx)
		{
			resultado.close();
			
			throw new Exception("Error SQL: " +  sqlEx.getMessage());
		}
		
		return listadoPerfiles;
	}
}