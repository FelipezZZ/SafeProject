package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.AnamneseDao;


@WebServlet("/ProcessaAnamnese")
public class ProcessaAnamnese extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ProcessaAnamnese() {
        super();
  
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	String acao = request.getParameter("acao");
	AnamneseDao aDao = new AnamneseDao();
	JSONObject obj = new JSONObject();
	PrintWriter out = response.getWriter();
	String falha = "falha na anamnese";
	String sucesso = "sucesso na anamnese";
	
	
		if(acao.equals("anamnese")) {
		int cod_pessoa = Integer.valueOf(request.getParameter("cod_pessoa"));
		double ansiedade = Double.valueOf(request.getParameter("ansiedade"));
		double depressao = Double.valueOf(request.getParameter("depressao"));
		double estresse = 	Double.valueOf(request.getParameter("estresse"));
		String comentario = request.getParameter("comentario");
			try {
				if(aDao.fazAnamnese(cod_pessoa, ansiedade, depressao, estresse, comentario)) {
					System.out.println("sucesso na anamnese");
					obj.put("status", sucesso);
				}else {
					System.out.println("falha na anamnese");
					obj.put("status", falha);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		
		out.print(obj);
		
	}

}
