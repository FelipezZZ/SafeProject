package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import dao.HorarioDao;
import dao.PessoaDao;
import vo.Admin;
import vo.Anamnese;
import vo.Pessoa;

@WebServlet("/ProcessaPessoa")
public class ProcessaPessoa extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProcessaPessoa() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("conectou");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			//FUNCIONARIO COD = 1
			//PACIENTE COD = 2 
			//PELO AMOR DE JEOVÁ,ZEUS E VISHNU NÃO ESQUECER DISSO
		int v = 0;
		System.out.println("conectou no processa");
		
		PrintWriter out = response.getWriter();
		JSONObject obj = new JSONObject();
		PessoaDao pDao = new PessoaDao();
			
		//strings da preguiça	
		String sucesso = "sucesso";
		String falha = "falha";
		String erroSQL = "erro no sql";
		
		
		String acao = request.getParameter("acao");
		
		//WEB
		//CADASTRAR PESSOA
		if (acao.equals("cadastrarPessoaWeb")) {
			System.out.println("ovo cadastrar web");
			
			Pessoa p = new Pessoa();
			
			String universidade = request.getParameter("universidade");
			String RA = request.getParameter("RA");
			
			
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			int sexo = Integer.valueOf(request.getParameter("sexo"));
			int tipoPerf = Integer.valueOf(request.getParameter("tipoPerf"));
			String ScadastroFB = request.getParameter("cadastroFB");
			
			if(tipoPerf == 1) {
				p.setUniversidade(universidade);
				p.setRA(RA);
			}
			
			p.setNome(nome);
			p.setEmail(email);
			p.setSenha(senha);
			p.setSexo(sexo);
			p.setTipoPerf(tipoPerf);
			p.setCadastroFb(Boolean.parseBoolean(ScadastroFB));
			
			try {
				int cod_pessoa = pDao.cadastraPessoa(p);
				obj.put("cod_pessoa", cod_pessoa);
				obj.put("email", email);
				obj.put("tipoPerf", tipoPerf);
				out.print(obj);
			} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException
					| NoSuchPaddingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(acao.equals("cadastrarADM")) {
			
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			
			try {
				pDao.cadastraAdm(login, senha);
			} catch (NoSuchAlgorithmException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(acao.equals("loginADM")) {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			
			try {
				if(pDao.loginAdm(login, senha)) {
					obj.put("msg", "logou");
					out.print(obj);
				}else {
					obj.put("msg", "num logo");
					out.print(obj);
				}
			} catch (NoSuchAlgorithmException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//WEB
		//LOGIN WEB
		if(acao.equals("loginWeb")) {
			System.out.println("ovo logar");
			
			Pessoa p = new Pessoa();
			
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			
			System.out.println(email);
			System.out.println(senha);
			
			p.setEmail(email);
			p.setSenha(senha);
			
			try {
				int cod_pessoa = pDao.login(p);
				int tipoPerf = pDao.pegarTipoPerf(email);
				obj.put("cod_pessoa", cod_pessoa);
				obj.put("email", email);
				obj.put("tipoPerf", tipoPerf);
				out.print(obj);
			} catch (NoSuchAlgorithmException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(acao.equals("verificaCadastroSQL")) {
			
			System.out.println("to no verificaCadastroSQL");
			
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			
			System.out.println(email + " " + senha);
			
			try {
				int cod_pessoa = pDao.verificaCadastroSQL(email, senha);
				String Scod_pessoa = String.valueOf(cod_pessoa);
				String nome = pDao.pegarNome(Scod_pessoa);
				if(cod_pessoa != 0) {
					pDao.mudarCadastroFB(Scod_pessoa);
				}
				obj.put("cod_pessoa", cod_pessoa);
				obj.put("nome", nome);
				out.print(obj);
			} catch (NoSuchAlgorithmException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//WEB
		//EDITARNOMEEMAIL WEB
		if (acao.equals("editarNomeEmailWeb")) {
			System.out.println("ovo editar nome e email");
			
			Pessoa p = new Pessoa();
			
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			Integer cod_pessoa = Integer.valueOf(request.getParameter("cod_pessoa"));
			
			p.setCod_pessoa(cod_pessoa);
			p.setNome(nome);
			p.setEmail(email);
			
			try {
				if(pDao.editarNomeEmailWeb(p)) {
					System.out.println("editou");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (acao.equals("editarSenhaWeb")) {
			System.out.println("ovo editar senha");
			
			Pessoa p = new Pessoa();
			
			String senha = request.getParameter("senha");
			Integer cod_pessoa = Integer.valueOf(request.getParameter("cod_pessoa"));
			
			p.setCod_pessoa(cod_pessoa);
			p.setSenha(senha);
			
			try {
				if(pDao.editarSenhaWeb(p)) {
					System.out.println("editou senha");
				}
			} catch (NoSuchAlgorithmException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
		
		if (acao.equals("editarUniversidadeWeb")) {
			System.out.println("ovo editar universidade");
			
			Pessoa p = new Pessoa();
			
			String universidade = request.getParameter("universidade");
			Integer cod_pessoa = Integer.valueOf(request.getParameter("cod_pessoa"));
			
			p.setCod_pessoa(cod_pessoa);
			p.setUniversidade(universidade);
			

				try {
					if(pDao.editarUniversidadeWeb(p)) {
						System.out.println("editou universidade");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		if (acao.equals("editarRAWeb")) {
			System.out.println("ovo editar RA");
			
			Pessoa p = new Pessoa();
			
			String RA = request.getParameter("RA");
			Integer cod_pessoa = Integer.valueOf(request.getParameter("cod_pessoa"));
			
			p.setCod_pessoa(cod_pessoa);
			p.setRA(RA);
			

				try {
					if(pDao.editarRAWeb(p)) {
						System.out.println("editou universidade");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		if (acao.equals("editarDescricaoWeb")) {
			System.out.println("ovo editar descricao");
			
			Pessoa p = new Pessoa();
			
			String descricao = request.getParameter("descricao");
			Integer cod_pessoa = Integer.valueOf(request.getParameter("cod_pessoa"));
			
			p.setCod_pessoa(cod_pessoa);
			p.setDescricao(descricao);;
			

				try {
					if(pDao.editarDescricaoWeb(p)) {
						System.out.println("editou universidade");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		//CADASTRAR PESSOA
		if (acao.equals("cadastrarPessoa")) {
			System.out.println("To no cadastra");
			
			Pessoa p = new Pessoa();
			
			String universidade = request.getParameter("universidade");
			String RA = request.getParameter("RA");
			
			
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String senha = request.getParameter("senha");
			//int sexo = Integer.valueOf(request.getParameter("sexo"));
			int tipoPerf = Integer.valueOf(request.getParameter("tipoPerf"));
			String ScadastroFB = request.getParameter("cadastroFB");
		
			
			System.out.println(universidade);
			System.out.println(RA);
			
			System.out.println(nome);
			System.out.println(email);
			System.out.println(senha);
//			System.out.println("sexo " + sexo);
			System.out.println(tipoPerf);
			System.out.println("cadastroFB " + ScadastroFB);
			
			if(tipoPerf == 1) {
				p.setUniversidade(universidade);
				p.setRA(RA);
			}
			
			p.setNome(nome);
			p.setEmail(email);
			p.setSenha(senha);
//			p.setSexo(sexo);
			p.setTipoPerf(tipoPerf);
			p.setCadastroFb(Boolean.parseBoolean(ScadastroFB));
			
			try {
				int cod_pessoa = pDao.cadastraPessoa(p);
				out.print(cod_pessoa);
			} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException
					| NoSuchPaddingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(acao.equals("verificarTipoUsuario")) {
			System.out.println("to no verificarTipoUsuario");
			
			String cod_pessoa = request.getParameter("codPessoa");
			
			try {
				int tipousuario = pDao.verificarTipoUsuario(cod_pessoa);
				out.print(tipousuario);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(acao.equals("verificarPrimeiroAcesso")) {
			System.out.println("to no verificarPrimeiroAcesso");
			
			String cod_pessoa = request.getParameter("codPessoa");
			
			try {
				if(pDao.verificarPrimeiroAcesso(cod_pessoa)) {
					out.print(true);
				}else {
					out.print(false);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		if(acao.equals("salvarAnamnese")) {
			
			System.out.println("to no salvarAnamnese");
			
			String cod_pessoa = request.getParameter("codPessoa");
			String a = request.getParameter("a");
			String d = request.getParameter("d");
			String s = request.getParameter("s");
			
			System.out.println(cod_pessoa);
			
			try {
				pDao.salvarAnamnese(cod_pessoa, a, d, s);
				out.print("success");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(acao.equals("mudarAcesso")) {
			
			System.out.println("to no mudarAcesso");
			
			String cod_pessoa = request.getParameter("codPessoa");
			
			try {
				pDao.mudarAcesso(cod_pessoa);
				out.print("success");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(acao.equals("pegarNome")) {
			
			System.out.println("to no pegarNome");
			
			String cod_pessoa = request.getParameter("codPessoa");
			
			try {
				String nome = pDao.pegarNome(cod_pessoa);
				pDao.mudarCadastroFB(cod_pessoa);
				out.print(nome);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(acao.equals("listarNaoVerificados")) {
			v = 1;
			System.out.println("tentou listar");
			try {
				List<Pessoa> listaPessoas = pDao.listarNaoVerificados();
		
				for (Pessoa p : listaPessoas) {
					obj = new JSONObject();
					obj.put("cod_pessoa", p.getCod_pessoa());
					obj.put("universidade",p.getUniversidade());
					obj.put("RA", p.getRA());
					obj.put("nome", p.getNome());
				
					out.print(obj.toString()+"\n");
				}
			} catch (JSONException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if(acao.equals("pegarPessoa")) {
			
			String cod_pessoa = request.getParameter("cod_pessoa");
			System.out.println("servlet cod pessoa: " + cod_pessoa);
			
			try {
				List<Pessoa> pessoa = pDao.pegarPessoa(cod_pessoa);
				
				for(Pessoa p : pessoa){
					obj = new JSONObject();
					obj.put("email", p.getEmail());
					obj.put("universidade",p.getUniversidade());
					obj.put("RA", p.getRA());
					obj.put("nome", p.getNome());
					obj.put("descricao", p.getDescricao());
				
					System.out.println("obj " + obj);
					
					out.print(obj.toString());
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(acao.equals("listarAnamneses")) {
			System.out.println("to no listar anamnese");
			
			String cod_pessoa = request.getParameter("cod_pessoa");
			
			try {
				List<Anamnese> anameneses = pDao.listarAnamneses(cod_pessoa);
				
				for (Anamnese a : anameneses) {
					obj = new JSONObject();
					obj.put("cod_pessoa", a.getCod_pessoa());
					obj.put("a", a.getAnsiedade());
					obj.put("d", a.getDepressao());
					obj.put("s", a.getEstresse());
					obj.put("dataAnamnese", a.getDataAnamneses());
				
					out.print(obj.toString()+"\n");
					System.out.println(obj.toString());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if(acao.equals("verificaPessoa")) {
			System.out.println("tentou verificar");
			int i = Integer.parseInt(request.getParameter("cod_pessoaV"));
			
			try {
				if(pDao.verificaPessoa(i)) {
					obj.put("status", sucesso);
				}else {
					obj.put("status", falha);
				}
			} catch (SQLException e) {
				obj.put("status", erroSQL);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		if(acao.equals("pesquisaEstagiario")) {
			HorarioDao hDao = new HorarioDao();
			
			System.out.println("tou no pesquisa estagiario");
			String dia = request.getParameter("dia");
			String horario = request.getParameter("horario");
			try {
			
			int f = 1;
			
				List<Integer>listaCodigos = hDao.pesquisaHorario(dia, horario);
				
				for(Integer i : listaCodigos) {
					obj = new JSONObject();
					obj.put("cod_pessoa", i);
					out.print(obj.toString()+"\n");
					System.out.println("conseguiu enviar os códigos");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("não enviou códigos");
				e.printStackTrace();
			}
		}
		
		if(acao.equals("codFBinSql")) {
			System.out.println("kkkk codFBinSql");
			
			String cod_pessoa = request.getParameter("cod_pessoa");
			String cod_firebase = request.getParameter("cod_firebase");
			
			try {
				if(pDao.codFBinSql(cod_pessoa, cod_firebase)) {
					out.print("ok");
				}else {
					out.print("erro");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		
	}	
}

