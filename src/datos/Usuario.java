package datos;

import java.util.ArrayList;
import java.sql.*;

import javax.servlet.*;

public class Usuario {

	public String usuario;
	public String nombre;
	public String contrasena;
	public int id_perfil;
	public String perfil;
	public int id_estado;
	public String estado;
	public Date fecha_ultima_contrasena;
	
	private ServletContext context;
	
	public Usuario(ServletContext context)
	{
		this.context = context;
	}

	public void ingresarUsuario(String usuario, String nombre, String contrasena, int id_perfil,
			int id_estado) throws SQLException
	{
		
		new BaseDatos(context).actualizar("insert into usuario (usuario, nombre, contrasena, id_perfil, id_estado, fecha_ultima_contrasena) values ('" + 
				BaseDatos.limpiarParametro(usuario) + "', '" + BaseDatos.limpiarParametro(nombre) + "', " +
				"encode('" + BaseDatos.limpiarParametro(contrasena) + "', 'hex'), " + String.valueOf(id_perfil) + 
				", " + String.valueOf(id_estado) + ", now())");
	}
	
	public void actualizarUsuario(String usuario, String nombre, int id_perfil, int id_estado) throws SQLException
	{
		
		new BaseDatos(context).actualizar("update usuario set nombre = '" + BaseDatos.limpiarParametro(nombre) + "', " + 
				"id_perfil=" + String.valueOf(id_perfil) + ", id_estado=" + String.valueOf(id_estado) + " where " +
				"usuario = '" + BaseDatos.limpiarParametro(usuario) + "'");
	}
	
	public void cambiarContrasena(String usuario, String contrasena) throws SQLException
	{
		
		new BaseDatos(context).actualizar("update usuario set contrasena = encode('" + BaseDatos.limpiarParametro(contrasena) + "'), fecha_ultima_contrasena=now() " +
				"where usuario = '" + BaseDatos.limpiarParametro(usuario) + "'");
	}
	
	public ArrayList<Usuario> consultarUsuarios() throws Exception
	{
		ArrayList<Usuario> listadoUsuarios = new ArrayList<Usuario>();
		
		ResultSet resultado = null;
		
		try
		{
			resultado = new BaseDatos(context).consultar("select u.usuario, u.nombre, decode(u.contrasena, 'hex') as contrasena, " +
					"u.id_perfil, p.nombre as perfil, u.id_estado, e.nombre as estado, u.fecha_ultima_contrasena " +
					"from usuario u inner join perfil p on u.id_perfil = p.id " +
					"inner join estado e on e.id = u.id_estado");
			
			while(resultado.next())
			{
				Usuario objUsuario = new Usuario(null);
				objUsuario.usuario = resultado.getString ("usuario");
				objUsuario.nombre = resultado.getString("nombre");
				objUsuario.contrasena = resultado.getString("contrasena");
				objUsuario.id_perfil = resultado.getInt("id_perfil");
				objUsuario.perfil = resultado.getString("perfil");
				objUsuario.id_estado = resultado.getInt("id_estado");
				objUsuario.estado = resultado.getString("estado");
				objUsuario.fecha_ultima_contrasena = resultado.getDate("fecha_ultima_contrasena");
				
				listadoUsuarios.add(objUsuario);
			}
			
			resultado.close();
		}
		catch(SQLException sqlEx)
		{
			resultado.close();
			
			throw new Exception("Error SQL: " +  sqlEx.getMessage());
		}
		
		return listadoUsuarios;
	}
	
	public Usuario consultarUsuario(String usuario) throws Exception
	{
		Usuario objUsuario = null;
		
		ResultSet resultado = null;
		
		try
		{
			resultado = new BaseDatos(context).consultar("select u.usuario, u.nombre, decode(u.contrasena, 'hex') as contrasena, " +
					"u.id_perfil, p.nombre as perfil, u.id_estado, e.nombre as estado, u.fecha_ultima_contrasena " +
					"from usuario u inner join perfil p on u.id_perfil = p.id " +
					"inner join estado e on e.id = u.id_estado " +
					"where u.usuario = '" + BaseDatos.limpiarParametro(usuario) + "'");
			
			if(resultado.next())
			{
				objUsuario = new Usuario(null);
				objUsuario.usuario = resultado.getString ("usuario");
				objUsuario.nombre = resultado.getString("nombre");
				objUsuario.contrasena = resultado.getString("contrasena");
				objUsuario.id_perfil = resultado.getInt("id_perfil");
				objUsuario.perfil = resultado.getString("perfil");
				objUsuario.id_estado = resultado.getInt("id_estado");
				objUsuario.estado = resultado.getString("estado");
				objUsuario.fecha_ultima_contrasena = resultado.getDate("fecha_ultima_contrasena");		
			}
			
			resultado.close();
		}
		catch(SQLException sqlEx)
		{
			resultado.close();
			
			throw new Exception("Error SQL: " +  sqlEx.getMessage());
		}
		
		return objUsuario;
	}
	
	public boolean validarUsuario(String usuario, String contrasena) throws Exception
	{
		Usuario objUsuario = consultarUsuario(usuario);
		
		if(objUsuario != null)
			if(objUsuario.contrasena.equals(contrasena))
				return true;
		
		return false;
	}
}