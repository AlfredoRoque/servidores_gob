package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jsoup.nodes.Document;

import SQL.MetodosSQL;
import Services.EntidadesService;
import beans.Dependencia;
import beans.DirectorioEntidad;
import beans.Entidades;

/**
 * Servlet implementation class ServletEntidades
 */
public class ServletEntidades extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private List<String[]> entidades = new ArrayList<String[]>();
	private EntidadesService serviceE = new EntidadesService();
	private MetodosSQL metodos = new MetodosSQL();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEntidades() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String caca = "";
		boolean check = false;
		boolean check2 = false;
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession sesion = request.getSession();
		entidades = serviceE.getEntidades();
		List<Entidades> listDep = new ArrayList<Entidades>();
		/*for(int i =0; i<=entidades.get(0).length-1;i++) {
			System.out.println(entidades.get(0)[i]);
			System.out.println(entidades.get(1)[i]);
			System.out.println(entidades.get(2)[i]);
		}*/
		for(int i =0; i<=entidades.get(0).length-1;i++) {
			caca = caca+entidades.get(0)[i]+"<br>";
			Entidades entidad = new Entidades();
			entidad.setEntidad(entidades.get(0)[i]);
			entidad.setUrl(entidades.get(1)[i]);
			String cod = entidades.get(2)[i].replace(",", "");
			cod = cod.replace(".", "");
			cod = cod.replace("/", "");
			entidad.setCodigo(cod);
			Document html = serviceE.getDatosContacto(entidad);
			List<Object> lista = serviceE.getDireccionTelCont(entidad,html);
			entidad.setDireccion((String)lista.get(0));
			entidad.setTelefono((String)lista.get(1));
			entidad.setContacto((String)lista.get(2));
			if(((List<DirectorioEntidad>)lista.get(3)).size() > 0) {
				entidad.setDirectorioList((List<DirectorioEntidad>)lista.get(3));
			}
			//dependencia.setMision(serviceS.getMision(dependencia.getCodigo(),html));
			listDep.add(entidad);
		}
		if(listDep.size()>0) {
			try {
				check = metodos.registrarEntidades(listDep);
			} catch (SQLException e) {
				System.out.println("ERROR1: "+e);
			}
		}
		
		if(check) {
			sesion.setAttribute("caca", caca);
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<body>");
			out.println("<script type=\"text/javascript\">");
			out.println("location='index.jsp'");
			out.println("</script>");
			out.println("</body>");
			out.println("</html>");	
		}else {
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<body>");
			out.println("<h1>");
			out.println("FALLO!!");
			out.println("</h1>");
			out.println("<script type=\"text/javascript\">");
			//out.println("location='index.jsp'");
			out.println("</script>");
			out.println("</body>");
			out.println("</html>");
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
