package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HorarioDao {

	private Connection con;
	private PreparedStatement ps;

	public List<Integer> pesquisaHorario(String dia, String horario) throws SQLException {

		List<Integer> codEstagiarios = new ArrayList<Integer>();
		con = ConnectionDB.getConnection();
		String sql = "";
		switch (dia) {

		case "dom":
			sql = "select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true and h.dom = ?";	
		//	select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true	
			break;
		case "seg":
			sql = "select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true and h.seg = ?";	
			break;
		case "ter":
			sql = "select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true and h.ter = ?";	
			break;
		case "qua":
			sql = "select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true and h.qua = ?";	
			break;
		case "qui":
			sql = "select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true and h.quin = ?";	
			break;
		case "sex":
			sql = "select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true and h.sex = ?";	
			break;
		case "sab":
			sql = "select h.cod_estagiario, h.dom from pessoa p inner join horarios h on p.cod_pessoa = h.cod_estagiario where verificado = true and h.sab = ?";	
			break;

		}

		ps = con.prepareStatement(sql);
		ps.setString(1, horario);

		System.out.println(ps);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			codEstagiarios.add(rs.getInt(1));
		}
		
		System.out.println("lista" + codEstagiarios);
		return codEstagiarios;
		
	}
}
