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
import Services.SecretariasService;
import beans.Dependencia;
import beans.Redes;
import beans.Servidores;

/**
 * Servlet implementation class ServletSearchHTML
 */
public class ServletSearchHTML extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String[]> secretarias = new ArrayList<String[]>();
	private SecretariasService serviceS = new SecretariasService();
	private MetodosSQL metodos = new MetodosSQL();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletSearchHTML() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean check = false;
		boolean check2 = false;
		boolean check3 = false;
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession sesion = request.getSession();
		secretarias = serviceS.getSecretarias();
		List<Dependencia> listDep = new ArrayList<Dependencia>();
		for(int i =0; i<=secretarias.get(0).length-1;i++) {
			Dependencia dependencia = new Dependencia();
			dependencia.setDependencia(secretarias.get(0)[i]);
			dependencia.setCodigo(secretarias.get(1)[i]);
			dependencia.setDependencia(serviceS.secretariasCompletas(dependencia.getDependencia(),dependencia.getCodigo()));
			Document html = serviceS.getDatosContacto(dependencia.getCodigo());
			dependencia.setDireccion(serviceS.getDireccion(dependencia.getCodigo(),html));
			dependencia.setTelefono(serviceS.getTelefono(dependencia.getCodigo(),html));
			dependencia.setContactoSecundario(serviceS.getTelefono2(dependencia.getCodigo(),html));
			dependencia.setMision(serviceS.getMision(dependencia.getCodigo(),html));
			listDep.add(dependencia);
		}
		if(listDep.size()>0) {
			try {
				check = metodos.registrar(listDep);
			} catch (SQLException e) {
				System.out.println("ERROR1: "+e);
			}
			if(check) {
				try {
					List<Dependencia> listDepen = new ArrayList<Dependencia>();
					List<Servidores> listServ = new ArrayList<Servidores>();
					listDepen = metodos.buscarSecretariasLista(listDep);
					if(listDepen.size()>0) {
						for(Dependencia dep : listDepen) {
							Servidores servidores = new Servidores();
							servidores.setSecretaria(dep);
							servidores.setIdDependencia(dep.getId());
							Document html = serviceS.getDatosDirectorio(dep.getCodigo());
							servidores.setListaServidores(serviceS.getDirectorio(dep.getCodigo(),html));
							for(Servidores ser:servidores.getListaServidores()) {
								ser.setIdDependencia(dep.getId());
								List<Object> listaS = metodos.getServidor(ser);
								if((boolean) listaS.get(1)) {
									ser.getSecretaria().setId(dep.getId());
									ser.setNumeros((List<Integer>) listaS.get(0));
								}
							}
							listServ.add(servidores);
						}
						if(listServ.size()>0) {
							check2 = metodos.registrarDirectorio(listServ);
						}
						if(check2) {
							try {
								List<Dependencia> lista = new ArrayList<Dependencia>();
								List<Redes> listaRedes = new ArrayList<Redes>();
								lista = metodos.buscarSecretariasLista(listDep);
								if(lista.size()>0) {
									for(Dependencia dep : lista) {
										Redes redes = new Redes();
										redes.setDependencia(dep);
										Document html = serviceS.getRedesSociales(dep.getCodigo());
										redes.setTwiter(serviceS.getTwiter(dep.getCodigo(),html));
										redes.setFacebook(serviceS.getFacebook(dep.getCodigo(),html));
										redes.setYoutube(serviceS.getYoutube(dep.getCodigo(),html));
										redes.setInstagram(serviceS.getInstagram(dep.getCodigo(),html));
										listaRedes.add(redes);
									}
									if(listaRedes.size()>0) {
										check3 = metodos.registrarRedesDependencia(listaRedes);
									}
								}
							} catch (SQLException e) {
								System.out.println("ERROR2: "+e);
							}
						}
					}
				} catch (SQLException e) {
					System.out.println("ERROR2: "+e);
				}
			}
		}
		if(check3) {
			sesion.setAttribute("cuerpo", listDep.get(listDep.size()-1).getDependencia());
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
