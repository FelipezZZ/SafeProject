package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import dao.PessoaDao;
import vo.Admin;
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
		
			//Gerenciamento de pessoas
		
		
		//CADASTRAR PESSOA
		if (acao.equals("cadastrarPessoa")) {
			
			
			Pessoa p = new Pessoa();
		

			String nome = request.getParameter("nome");
			
			String nickname = request.getParameter("nickname");
			String senha = request.getParameter("senha");
			int tipoPerf = Integer.valueOf(request.getParameter("tipoPerf"));
			boolean verificado = (Boolean.parseBoolean(request.getParameter("verificado")));
			String identidade = request.getParameter("identidade"); 
			boolean anonimo = (Boolean.parseBoolean(request.getParameter("anonimo")));
			String login = request.getParameter("login");
		
			p.setIdentidade(identidade);
			p.setNickname(nickname);
			p.setSenha(senha);
			p.setTipoPerf(tipoPerf);
			p.setVerificado(verificado);
			p.setAnonimo(anonimo);
			p.setLogin(login);
			
				try {
					
						System.out.println("tentou cadastrar");
					//obj.put("status", pDao.cadastraPessoa(p));
					System.out.println(pDao.cadastraPessoa(p));
			
				}catch(SQLIntegrityConstraintViolationException e) {
						e.printStackTrace();
						obj.put("status", "login existente");
					
				} catch (InvalidKeyException e) {
				
					e.printStackTrace();
				}catch (IllegalBlockSizeException e) {
					
					e.printStackTrace();
				} catch (BadPaddingException e) {
				
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
				
					e.printStackTrace();
				
			} catch (SQLException e) {
				obj.put("status",erroSQL);
				e.printStackTrace();
				
			} catch (NoSuchAlgorithmException e) {
		
				e.printStackTrace();
			} catch (JSONException e) {
			
				e.printStackTrace();	
		
		}
		}
		
				//LOGAR PESSOA
		
		else if(acao.equals("logarPessoa")) {
			System.out.println("conectou no logar");
			v=1;
			Pessoa p = new Pessoa();
			p.setLogin(request.getParameter("login"));
			p.setSenha(request.getParameter("senha"));
			p.setAnonimo(Boolean.parseBoolean(request.getParameter("anonimo")));
			
			
			try {
				try {
					
					if (pDao.tentaLogin(p)) {
				
						if(p.isAnonimo()) {
							p.setNickname("Anônimo");
							obj.put("nickname", p.getNickname());
							
						}else {
						
						obj.put("nickname",pDao.pegaNickname(p.getLogin(), p.getSenha()));
						}
							obj.put("status", sucesso);
							obj.put("chave", pDao.pegaChave(p.getLogin(), p.getSenha()));
							
							System.out.println("foi ");
												
					}else {
						obj.put("status",falha);
						System.out.println("meh");
					}
				} catch (InvalidKeyException e) {
					
					e.printStackTrace();
				} catch (NoSuchPaddingException e) {
					
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
				
					e.printStackTrace();
				} catch (BadPaddingException e) {
				
					e.printStackTrace();
				} catch (JSONException e) {
		
					e.printStackTrace();
				}
			} catch (NoSuchAlgorithmException e) {
			
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		else if(acao.equals("editarPerfil")) {
			try {
				
				if(pDao.editaPerfil(request.getParameter("edit"),Integer.parseInt(request.getParameter("cod_pessoa")),request.getParameter("dado"))) {
				System.out.println("editou com sucesso");
				}else {
					System.out.println("falha na edição");
				}
			} catch (NumberFormatException e) {
			
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
			
				e.printStackTrace();
			} catch (SQLException e) {
	
				e.printStackTrace();
			}
		}
	
		
		
		//LISTAR PESSOA//
		
		
		else if(acao.equals("listarPessoa")) {
			try {
							
				List<Pessoa> listaPessoas = pDao.listarTodosUsuarios();
				for (Pessoa p : listaPessoas) {
					obj = new JSONObject();
					obj.put("cod_pessoa", p.getCod_pessoa());
					obj.put("login",p.getLogin());
					obj.put("nickname", p.getNickname());
					obj.put("identidade", p.getIdentidade());
					obj.put("tipoPerf", p.getTipoPerf());
					out.print(obj.toString()+"\n");
				}
			} catch (SQLException e) {
			
				e.printStackTrace();
			}
		}else if(acao.equals("deslogar")) {
			System.out.println("tentou deslogar");
		}		
		
			
		
		
		//GERENCIAMENTO DE ADMINS
		
		
		//CADASTRO DE ADMINS E AÇÕES DE ADMINS
		else if(acao.equals("cadastraAdmin")) {
			Admin a = new Admin();
			a.setLogin(request.getParameter("login"));
			a.setSenha(request.getParameter("senha"));
			
		try {
			if(pDao.cadastraAdmin(a)) {
				obj.put("status", sucesso);
			}else {
				obj.put("status",falha);
			}
		} catch (NoSuchAlgorithmException e) {
			obj.put("status","erro de algoritimo");
			e.printStackTrace();
		} catch (SQLException e) {
			obj.put("status", erroSQL);
		
			e.printStackTrace();
		}	
		
		}
		
		//LOGIN DE ADMIN
		else if(acao.equals("logarAdmin")) {
			Admin a = new Admin();
			
			try {
				if(pDao.tentaLoginAdmin(a)) {
					obj.put("status",sucesso);
				}else {
					obj.put("status", falha);
				}
			} catch (NoSuchAlgorithmException e) {
					obj.put("status", "algoritimo invalido");
				e.printStackTrace();
			} catch (SQLException e) {
				obj.put("status", erroSQL);
			
				e.printStackTrace();
			}
				
		
		}else if(acao.equals("listarNaoVerificados")) {
				v = 1;
				System.out.println("tentou listar");
				try {
					List<Pessoa> listaPessoas = pDao.listarNaoVerificados();
			
					for (Pessoa p : listaPessoas) {
						obj = new JSONObject();
						obj.put("cod_pessoa", p.getCod_pessoa());
						obj.put("login",p.getLogin());
						obj.put("nickname", p.getNickname());
						obj.put("identidade", p.getIdentidade());
						obj.put("registro", p.getRegistro());
					
						out.print(obj.toString()+"\n");
	
						
					}
				} catch (JSONException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			
		}else if(acao.equals("verificaPessoa")) {
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
			
		if(acao.equals("logarPessoa")) {
		out.print(obj.toString());
		}else {
			
		}
			}

		
}

