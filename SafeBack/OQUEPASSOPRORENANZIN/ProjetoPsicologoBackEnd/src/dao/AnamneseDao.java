package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AnamneseDao {
	
	private Connection con;
	private PreparedStatement ps;

	public boolean fazAnamnese(int cod_pessoa,double ansiedade,double depressao,double estresse,String comentario) throws SQLException {
		
	String sql = "INSERT INTO ANAMNESE(cod_pessoa,ansiedade,depressao,estresse,comentario) values(?,?,?,?,?)";	
	
	con = ConnectionDB.getConnection();
	
		ps = con.prepareStatement(sql);
		
	ps.setInt(1, cod_pessoa);
	ps.setDouble(2, ansiedade);
	ps.setDouble(3, depressao);
	ps.setDouble(4, estresse);
	ps.setString(5, comentario);
	
	
	return ps.executeUpdate() > 0;
		
	
	
	
	}
	
	

}
