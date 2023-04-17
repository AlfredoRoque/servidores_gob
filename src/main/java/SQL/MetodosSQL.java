package SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Services.SecretariasService;
import beans.Dependencia;
import beans.Directorio;
import beans.DirectorioEntidad;
import beans.Entidades;
import beans.Redes;
import beans.Servidores;

public class MetodosSQL {

	private PreparedStatement query;
	private ResultSet resultado;
	private static Connection conexion;
	
	public void conexion() throws SQLException{
		conexion = null;
		conexion = ConexionDB.conectar();
		conexion.setAutoCommit(false);
	}
	
	//Inserta Dependencias
	public boolean registrar(List<Dependencia> dependencias) throws SQLException{
		boolean registro = false;
		conexion();
		try {
			if(conexion != null) {
				for(Dependencia dep:dependencias) {
						String check = "SELECT * FROM dependencias WHERE codigo = '"+dep.getCodigo()+"'";
						Statement query2 = conexion.prepareStatement(check);
						resultado = query2.executeQuery(check);
						boolean cantidad = resultado.next();
						if(!cantidad) {
							String consulta = "INSERT INTO dependencias (dependencia,codigo,direccion,telefono_1,contacto_sec,mision) VALUES ('"+dep.getDependencia()+"','"+dep.getCodigo()+"','"+dep.getDireccion()+"','"+dep.getTelefono()+"','"+dep.getContactoSecundario()+"','"+dep.getMision()+"')";
							query = conexion.prepareStatement(consulta);
							int i = query.executeUpdate();
							if(i>0) {
								conexion.commit();
								registro = true;
							}else {
								conexion.rollback();
								registro = false;
								System.out.println("Mal");
							}
						}else{
							String consulta = "UPDATE dependencias SET dependencia = '"+dep.getDependencia()+"', direccion = '"+dep.getDireccion()+"', telefono_1 = '"+dep.getTelefono()+"', contacto_sec = '"+dep.getContactoSecundario()+"', mision = '"+dep.getMision()+"' WHERE codigo = '"+dep.getCodigo()+"'";
							query = conexion.prepareStatement(consulta);
							int i = query.executeUpdate();
							if(i>0) {
								conexion.commit();
								registro = true;
							}else {
								conexion.rollback();
								registro = false;
								System.out.println("Mal");
							}
						}
				}
				conexion.close();
			}
		} catch (Exception e) {
			System.out.println("Error: "+e);
		}	finally {
			try {
				if(conexion != null) {
					conexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return registro;
	}
	
	//Inserta directorio que representa a cada dependencia
	public boolean registrarDirectorio(List<Servidores> servidores) throws SQLException{
		boolean registro = false;
		List<Object> existe;
		conexion();
		try {
			if(conexion != null) {
				String consulta2 = "delete from directorio_dependencias";
				query = conexion.prepareStatement(consulta2);
				int iii = query.executeUpdate();
				for(Servidores dep1:servidores) {
						for(Servidores dep:dep1.getListaServidores()) {
							existe = new ArrayList<Object>();
							existe = buscarSecretarias(dep);
							if((boolean) existe.get(1)) {
								Servidores dep3 = (Servidores) existe.get(0);
								String check = "SELECT * FROM directorio_dependencias WHERE id_dependencia = "+dep3.getSecretaria().getId();
								Statement query2 = conexion.prepareStatement(check);
								ResultSet resultado = null;
								resultado = query2.executeQuery(check);
								List<Integer> listaIds = new ArrayList<Integer>();
								while(resultado.next()) {
									listaIds.add(resultado.getInt("id_servidor"));
								}
								if(listaIds.size()<=0) {
										String consulta = "INSERT INTO directorio_dependencias (id_dependencia,nombre,puesto,mision) VALUES ("+dep3.getIdDependencia()+",'"+dep3.getNombre()+"','"+dep3.getPuesto()+"','"+dep3.getMision()+"')";
										query = conexion.prepareStatement(consulta);
										int i = query.executeUpdate();
										if(i>0) {
											registro = true;
										}else {
											registro = false;
											System.out.println("Mal");
										}
								}else {
									if(iii>0) {
										registro = true;
										if(registro) {
											String consulta3 = "INSERT INTO directorio_dependencias (id_dependencia,nombre,puesto,mision) VALUES ("+dep3.getIdDependencia()+",'"+dep3.getNombre()+"','"+dep3.getPuesto()+"','"+dep3.getMision()+"')";
											query = conexion.prepareStatement(consulta3);
											int in = query.executeUpdate();
											if(in>0) {
												registro = true;
											}else {
												registro = false;
												System.out.println("Mal");
											}
										}
									}else {
										registro = false;
										System.out.println("Mal");
									}
								}
							}else {
								System.out.println("No existe: "+dep.getSecretaria().getCodigo());
							}
						}
				}
				if(registro) {
					conexion.commit();
					conexion.close();
				}else {
					conexion.rollback();
					conexion.close();
				}
			}
		} catch (Exception e) {
			System.out.println("Error: "+e);
		}	finally {
			try {
				if(conexion != null) {
					conexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return registro;
	}
	
	//busca las dependencias para saber si existen
	public List<Object> buscarSecretarias(Servidores servidores) throws SQLException {
		boolean registro = false;
		List<Object> lista = new ArrayList<Object>();
		try {
			if (conexion != null) {
					ResultSet resultado;
					String check = "SELECT id FROM dependencias WHERE codigo = '" + servidores.getSecretaria().getCodigo() + "'";
					PreparedStatement query2 = conexion.prepareStatement(check);
					resultado = query2.executeQuery(check);
						if(resultado.next()) {
							registro = true;
						}
					lista.add(servidores);
					lista.add(registro);
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		} finally {
		}
		return lista;
	}
	
	//busca las dependencias para obtener su ID
	public List<Dependencia> buscarSecretariasLista(List<Dependencia> dependencia) throws SQLException {
		conexion();
		try {
			if (conexion != null) {
					for(Dependencia dep:dependencia) {
						ResultSet resultado;
						String check = "SELECT id FROM dependencias WHERE codigo = '" + dep.getCodigo() + "'";
						PreparedStatement query2 = conexion.prepareStatement(check);
						resultado = query2.executeQuery(check);
						if(resultado.next()) {
							Integer id= resultado.getInt("id");
							dep.setId(id);
						}else {
							dep.setId(0);
						}
					}
				conexion.close();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		} finally {
			conexion.close();
		}
		return dependencia;
	}
	
	public List<Object> getServidor(Servidores servidor) throws SQLException {
		boolean check = false;
		List<Object> lista = new ArrayList<Object>();
		List<Integer> nums = new ArrayList<Integer>();
		conexion();
		try {
			if (conexion != null) {
						ResultSet resultado;
						String query = "SELECT * FROM directorio_dependencias WHERE id_dependencia = '" + servidor.getIdDependencia() + "'";
						PreparedStatement query2 = conexion.prepareStatement(query);
						resultado = query2.executeQuery(query);
						while(resultado.next()) {
							nums.add(resultado.getInt("id_servidor"));
							check =true;
						}
						lista.add(nums);
						lista.add(check);
				conexion.close();
			}
		} catch (Exception e) {
			System.out.println("Error: " + e);
		} finally {
			conexion.close();
		}
		
		return lista;
	}
	
	//Inserta las redes sociales de cada dependencia
	public boolean registrarRedesDependencia(List<Redes> redes) throws SQLException{
		boolean registro = false;
		List<Object> existe;
		conexion();
		try {
			if(conexion != null) {
				for(Redes dep:redes) {
					Servidores servidores = new Servidores();
						servidores.setSecretaria(dep.getDependencia());
						existe = buscarSecretarias(servidores);
						if((boolean) existe.get(1)) {
							dep.setDependencia(((Servidores) existe.get(0)).getSecretaria());
							String check = "SELECT * FROM redes_dependencia WHERE id_dependencia = "+dep.getDependencia().getId();
							Statement query2 = conexion.prepareStatement(check);
							ResultSet resultado = null;
							resultado = query2.executeQuery(check);
							boolean cantidad = resultado.next();
							if(!cantidad) {
								String consulta = "INSERT INTO redes_dependencia (id_dependencia,twiter,facebook,youtube,instagram) VALUES ("+dep.getDependencia().getId()+",'"+dep.getTwiter()+"','"+dep.getFacebook()+"','"+dep.getYoutube()+"','"+dep.getInstagram()+"')";
								query = conexion.prepareStatement(consulta);
								int i = query.executeUpdate();
								if(i>0) {
									registro = true;
								}else {
									registro = false;
									System.out.println("Mal");
								}
							}else{
								String consulta = "UPDATE redes_dependencia SET twiter = '"+dep.getTwiter()+"',facebook = '"+dep.getFacebook()+"',youtube ='"+dep.getYoutube()+"',instagram ='"+dep.getInstagram()+"' WHERE id_dependencia ='"+dep.getDependencia().getId()+"'";
								query = conexion.prepareStatement(consulta);
								int i = query.executeUpdate();
								if(i>0) {
									registro = true;
								}else {
									registro = false;
									System.out.println("Mal");
								}
							}
						}else {
							System.out.println("No existe: "+dep.getDependencia().getCodigo());
						}
				}
				if(registro) {
					conexion.commit();
					conexion.close();
				}else {
					conexion.rollback();
					conexion.close();
				}
			}
		} catch (Exception e) {
			System.out.println("Error: "+e);
		}	finally {
			try {
				if(conexion != null) {
					conexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return registro;
	}

	
	public boolean registrarEntidades(List<Entidades> entidades) throws SQLException{
		boolean registro = false;
		boolean check22 = false;
		conexion();
		try {
			if(conexion != null) {
				for(Entidades ent:entidades) {
						String check = "SELECT * FROM entidades WHERE codigo = '"+ent.getCodigo()+"'";
						Statement query2 = conexion.prepareStatement(check);
						resultado = query2.executeQuery(check);
						boolean cantidad = resultado.next();
						if(!cantidad) {
							String consulta = "INSERT INTO entidades (entidad,codigo,direccion,url,telefono,contacto) VALUES ('"+ent.getEntidad()+"','"+ent.getCodigo()+"','"+ent.getDireccion()+"','"+ent.getUrl()+"','"+ent.getTelefono()+"','"+ent.getContacto()+"')";
							query = conexion.prepareStatement(consulta);
							int i = query.executeUpdate();
							if(i>0) {
								registro = true;
							}else {
								registro = false;
								System.out.println("Mal");
							}
							if(registro) {
								if(ent.getDirectorioList()!=null) {
									if(ent.getDirectorioList().size()>0) {
										try {
											check22 = registrarDirectorioEntidades(ent.getDirectorioList(),ent);
										} catch (SQLException e) {
											System.out.println("ERROR1: "+e);
										}
									}
								}
							}
						}else{
							String consulta = "UPDATE entidades SET entidad = '"+ent.getEntidad()+"', url = '"+ent.getUrl()+"', direccion = '"+ent.getDireccion()+"', telefono = '"+ent.getTelefono()+"', contacto = '"+ent.getContacto()+"' WHERE codigo = '"+ent.getCodigo()+"'";
							query = conexion.prepareStatement(consulta);
							int i = query.executeUpdate();
							if(i>0) {
								registro = true;
							}else {
								registro = false;
								System.out.println("Mal");
							}
						}
				}
				if(registro) {
					conexion.commit();
				}else {
					conexion.rollback();
				}
				conexion.close();
			}
		} catch (Exception e) {
			System.out.println("Error: "+e);
		}	finally {
			try {
				if(conexion != null) {
					conexion.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return registro;
	}
	
	public boolean registrarDirectorioEntidades(List<DirectorioEntidad> entidades,Entidades entidad) throws SQLException{
		boolean registro = false;
		List<Object> existe;
		//conexion();
		try {
			if(conexion != null) {
				//String consulta2 = "delete from directorio_dependencias";
				//query = conexion.prepareStatement(consulta2);
				//int iii = query.executeUpdate();
				for(DirectorioEntidad dep1:entidades) {
							//existe = new ArrayList<Object>();
							//existe = buscarSecretarias(dep);
							if(dep1!=null) {
								String check3 = "SELECT * FROM entidades WHERE codigo = '"+entidad.getCodigo()+"'";
								Statement query3 = conexion.prepareStatement(check3);
								ResultSet resultado3 = null;
								int idEntidad = 0;
								resultado3 = query3.executeQuery(check3);
								while(resultado3.next()) {
									idEntidad = resultado3.getInt("id");
								}
								
								//Servidores dep3 = (Servidores) existe.get(0);
								String check = "SELECT * FROM directorio_entidades WHERE id_entidad = "+idEntidad;
								Statement query2 = conexion.prepareStatement(check);
								ResultSet resultado = null;
								resultado = query2.executeQuery(check);
								List<Integer> listaIds = new ArrayList<Integer>();
								while(resultado.next()) {
									listaIds.add(resultado.getInt("id_entidad"));
								}
								if(listaIds.size()<=0) {
										String consulta = "INSERT INTO directorio_entidades (id_entidad,nombre,puesto,mision) VALUES ("+idEntidad+",'"+dep1.getNombre()+"','"+dep1.getPuesto()+"','"+dep1.getMision()+"')";
										query = conexion.prepareStatement(consulta);
										int i = query.executeUpdate();
										if(i>0) {
											registro = true;
										}else {
											registro = false;
											System.out.println("Mal");
										}
								}else {
									/*if(iii>0) {
										registro = true;
										if(registro) {
											String consulta3 = "INSERT INTO directorio_dependencias (id_dependencia,nombre,puesto,mision) VALUES ("+dep3.getIdDependencia()+",'"+dep3.getNombre()+"','"+dep3.getPuesto()+"','"+dep3.getMision()+"')";
											query = conexion.prepareStatement(consulta3);
											int in = query.executeUpdate();
											if(in>0) {
												registro = true;
											}else {
												registro = false;
												System.out.println("Mal");
											}
										}
									}else {
										registro = false;
										System.out.println("Mal");
									}
									*/
								}
							}else {
								System.out.println("No existe: ");
							}
				}
				if(registro) {
					conexion.commit();
				}
				//else {
					//conexion.rollback();
				//}
			}
		} catch (Exception e) {
			System.out.println("Error: "+e);
		}
		return registro;
	}
}
