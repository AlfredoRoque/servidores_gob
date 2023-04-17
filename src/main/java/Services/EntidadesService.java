package Services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import Scraping.ScrapingSerch;
import beans.Dependencia;
import beans.DirectorioEntidad;
import beans.Entidades;
import beans.EntidadesEnum;
import beans.Secretarias;
import beans.Servidores;

public class EntidadesService {
	
	private ScrapingSerch scrap = new ScrapingSerch();
	private final String noAsignado = "N/A";
	private final String urlGob = "https://www.gob.mx/";
	private final String idDependencias = "#dependencias";
	private final String listaDependencias = "li.clearfix";
	private final String cidigoEspacio = "&nbsp";
	private final String Stringul = "ul";
	private final String codigoSplit = ",,";
	private final String stringApi = "/api/v1/levels?";
	private final String PENDIENTE = "PENDIENTE";
	private final String urlAPIAltamira = "https://www.puertoaltamira.com.mx/espi/0002011/ubicacion";
	private final String stringUbicacion = "ubicacion";
	private final String urlAPIEnsenada = "https://www.puertoensenada.com.mx/espi/0000140/ubicacion";
	private final String urlAPIManzanillo = "http://www.puertomanzanillo.com.mx/esps/0020202/ubicacion-y-zona-de-influencia.html";
	private final String urlAPIMazatlan = "https://www.puertomazatlan.com.mx/acerca-del-puerto/localizacion/";
	private final String urlAPIProgreso = "https://www.puertosyucatan.com/ap/ubicacion.htm";
	private final String urlAPISalinaCruz = "https://www.puertosalinacruz.com.mx/esps/0020201/ubicacion";
	private final String urlCSAEGRO = "https://csaegro.agricultura.gob.mx/";
	private final String urlEDUCAL = "https://www.educal.com.mx/librerias";
	private final String urlFonaturMantenimientoTurístico = "https://www.gob.mx/fmt";
	private final String urlINER = "https://www.gob.mx/salud%7Ciner";
	private final String urlCanal22 = "http://corporativo.canal22.org.mx/contact/";
	private final String urlTribunalesAgrarios = "https://www.tribunalesagrarios.gob.mx/ta/";
	private final String urlAPBP = "https://www.gob.mx/salud%7Capbp";
	private final String urlAFSEDF = "https://www.aefcm.gob.mx/gbmx/index.html";
	private final String urlBANSEFI = "https://www.gob.mx/bancodelbienestar";
	private final String urlCNEGSR = "https://www.gob.mx/salud%7Ccnegsr";
	private final String urlCENETEC = "https://www.gob.mx/salud%7Ccenetec";
	private final String urlCENSIA = "https://www.gob.mx/salud%7Ccensia";
	private final String urlCRAE = "https://www.gob.mx/salud%7Ccrae";
	private final String urlCIJ = "https://www.gob.mx/salud%7Ccij";
	private final String urlCNPSS = "https://www.gob.mx/salud%7Cseguropopular";
	private final String urlFEESA = "https://www.gob.mx/agricultura";
	private final String urlHospitalGeneralDrManuelGeaGonzález = "https://www.gob.mx/salud%7Chospitalgea";
	private final String urlInstitutodeFormaciónMinisterialPolicialyPericial = "https://www.gob.mx/fgr";
	private final String urlINPER = "https://www.gob.mx/salud%7Cinper";
	
	public List<DirectorioEntidad> listaEnt= new ArrayList<DirectorioEntidad>();
	

	public List<String[]> getEntidades() {
		List<String[]> lista = new ArrayList<String[]>();
		Elements secretariasList = new Elements();
		String url = urlGob+"gobierno";
		Elements secretarias = scrap.obtenHTML(url+idDependencias).select(listaDependencias);
		for(Element sec : secretarias) {
			if(secretarias.indexOf(sec)>=29&&secretarias.indexOf(sec)<=343) {
					secretariasList.add(sec);
			}
		}
		String allDependencias = "";
		String allCodigos = "";
		String codigo = "";
		for(Element sec : secretariasList) {
			if(sec.select(Stringul).outerHtml()=="") {
				String codigo2 = "";
				String link = sec.select("a").attr("href");
				String scrtaria = sec.getElementsByClass("abbr").text();
				if(scrtaria!="") {
					allDependencias =allDependencias+ scrtaria+codigoSplit;
					codigo2 = scrtaria;
					codigo2.replace(")", "");
					codigo2 = codigo2.replace("(", "--");
					String[] array = codigo2.split("--");
					if(array.length<2) {
						codigo = codigo+array[0].replace(" ", "")+codigoSplit;
					}else {
						codigo = codigo+array[1].replace(" ", "")+codigoSplit;
					}
				}else {
					String scrtariab = sec.getElementsByTag("a").text();
					allDependencias =allDependencias+ scrtariab+codigoSplit;
					codigo2 = scrtariab;
					codigo2 = codigo2.replace(")", "");
					codigo2 = codigo2.replace("(", "--");
					String[] array = codigo2.split("--");
					if(array.length<2) {
						codigo = codigo+array[0].replace(" ", "")+codigoSplit;
					}else {
						codigo = codigo+array[1].replace(" ", "")+codigoSplit;
					}
				}
				if(link!="") {
					allCodigos =allCodigos+ link+codigoSplit;
				}else {
					String codigob = "";
					allCodigos =allCodigos+ codigob+codigoSplit;
				}
			}else {
				sec.getElementsByTag(Stringul).remove();
				sec.getElementsByClass("sectorizadas").remove();
				String scrtaria = sec.getElementsByTag("a").text();
				codigo = sec.select("a").attr("href");
				codigo = codigo.replace("/", "");
				codigo = codigo.replace("http:www", "");
				codigo = codigo.replace("gob", "");
				codigo = codigo.replace(".", "");
				codigo = codigo.replace("mx", "");
				allDependencias = allDependencias+scrtaria+codigoSplit;
				allCodigos = allCodigos+codigo+codigoSplit;
			}
		}
		if(allDependencias!=null) {
			String[] dependencias = allDependencias.split(codigoSplit);
			lista.add(dependencias);
		}
		if(allCodigos!=null) {
			String[] codigos = allCodigos.split(codigoSplit);
			lista.add(codigos);
		}
		if(codigo!=null) {
			String[] codigos = codigo.split(codigoSplit);
			lista.add(codigos);
		}
		return lista;
	}
	
	public Document getDatosContacto(Entidades entidad) {
		Document direccion = null;
		if(entidad!=null) {
			if(!entidad.getCodigo().equals(EntidadesEnum.AGROASEMEXSA.getLabel())) {
				if(entidad.getCodigo().equals(EntidadesEnum.CCINSHAE.getLabel())) {
					String codigo = "insalud";
					direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.AMEXCID.getLabel())) {
					direccion = scrap.obtenHTML(urlGob+entidad.getCodigo().toLowerCase());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONVIM.getLabel())) {
					String codigo = "conavim";
					direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.COMAR.getLabel())) {
					direccion = scrap.obtenHTML(urlGob+entidad.getCodigo().toLowerCase());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FOCIR.getLabel())) {
					direccion = scrap.obtenHTML(urlGob+entidad.getCodigo().toLowerCase());
				}else if(entidad.getCodigo().equals(EntidadesEnum.IPAB.getLabel())) {
					direccion = scrap.obtenHTML(urlGob+entidad.getCodigo().toLowerCase());
				}else if(entidad.getCodigo().equals(EntidadesEnum.PF.getLabel())) {
					String codigo = "policiafederal";
					direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.PGR.getLabel())) {
					String codigo = "fgr";
					direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.Pronósticos.getLabel())) {
					String codigo = "pronosticos";
					direccion = scrap.obtenHTML(urlGob+codigo);
				}else if(entidad.getCodigo().equals(EntidadesEnum.PROSPERA.getLabel())) {
					String codigo = "becasbenitojuarez";
					direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.SEMAR.getLabel())) {
					direccion = scrap.obtenHTML(urlGob+entidad.getCodigo().toLowerCase());
				}else if(entidad.getCodigo().equals(EntidadesEnum.SAE.getLabel())) {
					String codigo = "indep";
					direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.SEPOMEX.getLabel())) {
					String codigo = "correosdemexico";
					direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIAltamira.getLabel())) {
					direccion = scrap.obtenHTML(urlAPIAltamira);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APICoatzacoalcos.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringUbicacion);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIDosBocas.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringUbicacion);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIEnsenada.getLabel())) {
					direccion = scrap.obtenHTML(urlAPIEnsenada);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIGuaymas.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringUbicacion);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APILázaroCárdenas.getLabel())) {
					String urL = entidad.getUrl().replace("http", "https");
					direccion = scrap.obtenHTML(urL);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIManzanillo.getLabel())) {
					direccion = scrap.obtenHTML(urlAPIManzanillo);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIMazatlán.getLabel())) {
					direccion = scrap.obtenHTML(urlAPIMazatlan);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIProgreso.getLabel())) {
					direccion = scrap.obtenHTML(urlAPIProgreso);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIPuertoMadero.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringUbicacion);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APISalinaCruz.getLabel())) {
					direccion = scrap.obtenHTML(urlAPISalinaCruz);
				}else if(entidad.getCodigo().equals(EntidadesEnum.APITampico.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.APITuxpan.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.APITopolobampo.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+"acerca-del-puerto/ubicacion/");
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIVeracruz.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+"negocios-en-el-puerto/zona-de-actividades-logisticas/");
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIPuertoVallarta.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringUbicacion);
				}else if(entidad.getCodigo().equals(EntidadesEnum.AICM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.AIC.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CCC.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CETI.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl().replace("index.php", ""));
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIDESI.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CECY.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CICESE.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIAD.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CentroGeo.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIMAT.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIMAV.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIQA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIATEJ.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CINVESTAV.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIDETEQ.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIDE.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CNI.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.Cibnor.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIO.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIESAS.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CECC.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CentroFederaldeProtecciónaPersonas.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIATECAC.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIATEQAC.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+"index.php/contacto/dirección.html");
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLPOS.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CSAEGRO.getLabel())) {
					direccion = scrap.obtenHTML(urlCSAEGRO);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CDPIM.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.COFAAIPN.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CFE.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.ComisiónparalaSeguridadyelDesarrolloIntegralenelEstadodeMichoacán.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.COMESA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CECUT.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.ConsejodeMenores.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CPTM.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONACYT.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONEVAL.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONOCER.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONAPRED.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONADIS.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONASE.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.CAIMFS.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CNSPD.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.COMIMSA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CrezcamosJuntos.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.EDUCAL.getLabel())) {
					direccion = scrap.obtenHTML(urlEDUCAL);
				}else if(entidad.getCodigo().equals(EntidadesEnum.Colef.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.ECOSUR.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+"contactanos/");
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLMEX.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLMICH.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLSAN.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.EstudiosChurubusco.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FERROISTMO.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FIDENA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FideicomisoparalaCinetecaNacional.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FonaturConstructora.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FonaturMantenimientoTurístico.getLabel())) {
					direccion = scrap.obtenHTML(urlFonaturMantenimientoTurístico+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.FCE.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+"LibreriasEstado/9");
				}else if(entidad.getCodigo().equals(EntidadesEnum.FOPESCA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FONDO.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FEGA.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.FOVI.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.FIDERH.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.GACM.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.HGM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.HRAEI.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.HRAEOAXACA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.HRAEB.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.HRAEV.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.IIIServiciosSAdeCV.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.IEPSA.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.INFOTEC.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.IIISA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl().replace("organismos/Paginas/instalaciones_inmobiliarias.aspx", ""));
				}else if(entidad.getCodigo().equals(EntidadesEnum.INECOL.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.InstitutoMora.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INFONAVIT.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAI.getLabel())) {
					direccion = scrap.obtenHTML("https://home.inai.org.mx/");
				}else if(entidad.getCodigo().equals(EntidadesEnum.IMCINE.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.IMER.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAP.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAH.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAOEP.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INBA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INCAN.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INCICH.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INNSZ.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INACIPE.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.INER.getLabel())) {
					direccion = scrap.obtenHTML(urlINER+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.INEHRM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INGER.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INALI.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INMEGEN.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INP.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INPRF.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INR.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INSP.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INDAUTOR.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+"ubicacion.php");
				}else if(entidad.getCodigo().equals(EntidadesEnum.INADEM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+"contacto/");
				}else if(entidad.getCodigo().equals(EntidadesEnum.INDETEC.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.IPN.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.IPICYT.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.BIRMEX.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.MexicoDigital.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.NOTIMEX.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.PMI.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl().replace("Inicio.aspx", "Directorio-Comercial.aspx"));
				}else if(entidad.getCodigo().equals(EntidadesEnum.POIIPN.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXExploraciónyProducción.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXGasyPetroquímicaBásica.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXPetroquímica.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXRefinación.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEX.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.PRESIDENCIA.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.PRODECON.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.ProgramaparaDemocratizarlaProductivdad.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.ERadio.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.SecciónMexicanadelaComisiónInternacionaldeLímitesyAguasentreMéxicoyEstadosUnidos.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.SeccionesMexicanasdelasComisionesInternacionalesdeLímitesyAguasentreMéxicoyGuatemalayentreMéxicoyBelice.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.STCCPRI.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.SETEC.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.SACM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.SPR.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.SuperISSSTE.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.TecNM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.Telecomm.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.Canal22.getLabel())) {
					direccion = scrap.obtenHTML(urlCanal22);
				}else if(entidad.getCodigo().equals(EntidadesEnum.TFCA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.TribunalesAgrarios.getLabel())) {
					direccion = scrap.obtenHTML(urlTribunalesAgrarios);
				}else if(entidad.getCodigo().equals(EntidadesEnum.TURISSSTE.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.UnADM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.APBP.getLabel())) {
					direccion = scrap.obtenHTML(urlAPBP+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.AFSEDF.getLabel())) {
					direccion = scrap.obtenHTML(urlAFSEDF);
				}else if(entidad.getCodigo().equals(EntidadesEnum.BANSEFI.getLabel())) {
					direccion = scrap.obtenHTML(urlBANSEFI+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.BANJERCITO.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CNEGSR.getLabel())) {
					direccion = scrap.obtenHTML(urlCNEGSR+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CENETEC.getLabel())) {
					direccion = scrap.obtenHTML(urlCENETEC+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CENAPRECE.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CENADIC.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CENSIA.getLabel())) {
					direccion = scrap.obtenHTML(urlCENSIA+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CRAE.getLabel())) {
					direccion = scrap.obtenHTML(urlCRAE+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIJ.getLabel())) {
					direccion = scrap.obtenHTML(urlCIJ+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.ColegiodeBachilleres.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CNPSS.getLabel())) {
					direccion = scrap.obtenHTML(urlCNPSS+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CNSNS.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.CORETT.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONAMPROS.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.AV.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.ESSA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FIFOMI.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FIRA.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.FonaturOperadoraPortuaria.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.FEESA.getLabel())) {
					direccion = scrap.obtenHTML(urlFEESA+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.HospitalGeneralDrManuelGeaGonzález.getLabel())) {
					direccion = scrap.obtenHTML(urlHospitalGeneralDrManuelGeaGonzález+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.HIMFG.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.HJM.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.HRAEPY.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.InstitutodeFormaciónMinisterialPolicialyPericial.getLabel())) {
					direccion = scrap.obtenHTML(urlInstitutodeFormaciónMinisterialPolicialyPericial+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.INNN.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.INPER.getLabel())) {
					direccion = scrap.obtenHTML(urlINPER+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.OADPRS.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.ProMéxico.getLabel())) {
					//direccion = scrap.obtenHTML(entidad.getUrl());
					direccion = new Document(PENDIENTE);
				}else if(entidad.getCodigo().equals(EntidadesEnum.SecretariadoEjecutivo.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.SCVSHF.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else if(entidad.getCodigo().equals(EntidadesEnum.SAP.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.SPF.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl());
				}else if(entidad.getCodigo().equals(EntidadesEnum.DIF.getLabel())) {
					direccion = scrap.obtenHTML(entidad.getUrl()+stringApi);
				}else {
					direccion = scrap.obtenHTML(urlGob+entidad.getCodigo().toLowerCase()+stringApi);
				}
				if(direccion!=null) {
					return direccion;
				}
			}else if(entidad.getCodigo().equals(EntidadesEnum.AGROASEMEXSA.getLabel())) {
				direccion = scrap.obtenHTML(urlGob+entidad.getCodigo().replace("SA", "").toLowerCase()+"#contacto");
				if(direccion!=null) {
					return direccion;
				}
			}
		}
		return null;
	}
	
	
	public List<Object> getDireccionTelCont(Entidades entidad,Document direccion) {
		String direccionString = "";
		String telefonoString = "";
		String contactoString = "";
		List<Object> direccionList = new ArrayList<Object>();
		if(true) {
			if(direccion!=null) {
				if(!entidad.getCodigo().equals(EntidadesEnum.AGROASEMEXSA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.AMEXCID.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COMAR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FOCIR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IPAB.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PGR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.Pronósticos.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SENER.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SEMAR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIAltamira.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APICoatzacoalcos.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIDosBocas.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIEnsenada.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIGuaymas.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APILázaroCárdenas.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIManzanillo.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIMazatlán.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIProgreso.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIPuertoMadero.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APISalinaCruz.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APITampico.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APITopolobampo.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APITuxpan.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIVeracruz.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.APIPuertoVallarta.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.AICM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.AIC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CCC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CETI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIDESI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CECY.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CICESE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIAD.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CentroGeo.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIMAT.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIMAV.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIQA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIATEJ.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CINVESTAV.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIDETEQ.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIDE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CNI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.Cibnor.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIO.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIESAS.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CECC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CentroFederaldeProtecciónaPersonas.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIATECAC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CIATEQAC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COLPOS.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CSAEGRO.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CDPIM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COFAAIPN.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CFE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.ComisiónparalaSeguridadyelDesarrolloIntegralenelEstadodeMichoacán.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COMESA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CECUT.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.ConsejodeMenores.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CPTM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CONACYT.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CONEVAL.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CONOCER.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CONAPRED.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CONADIS.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CONASE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CNSPD.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CAIMFS.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COMIMSA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CrezcamosJuntos.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.EDUCAL.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.Colef.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.ECOSUR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COLMEX.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COLMICH.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.COLSAN.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.EstudiosChurubusco.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FERROISTMO.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FIDENA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FideicomisoparalaCinetecaNacional.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FonaturConstructora.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FonaturMantenimientoTurístico.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FCE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FOPESCA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FONDO.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FOVI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FEGA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FIDERH.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.GACM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HGM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HRAEI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HRAEOAXACA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HRAEB.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HRAEV.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IIIServiciosSAdeCV.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IEPSA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INFOTEC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IIISA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INECOL.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.InstitutoMora.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INFONAVIT.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INAI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IMCINE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IMER.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INAP.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INAH.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INAOEP.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INBA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INCAN.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INCICH.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INNSZ.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INACIPE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INER.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INEHRM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INGER.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INALI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INMEGEN.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INP.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INPRF.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INSP.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INDAUTOR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INADEM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INDETEC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IPN.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.IPICYT.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.BIRMEX.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.MexicoDigital.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.NOTIMEX.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PMI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.POIIPN.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PEMEXExploraciónyProducción.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PEMEXGasyPetroquímicaBásica.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PEMEXPetroquímica.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PEMEXRefinación.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PEMEX.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PRESIDENCIA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.PRODECON.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.ProgramaparaDemocratizarlaProductivdad.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.ERadio.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SecciónMexicanadelaComisiónInternacionaldeLímitesyAguasentreMéxicoyEstadosUnidos.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SeccionesMexicanasdelasComisionesInternacionalesdeLímitesyAguasentreMéxicoyGuatemalayentreMéxicoyBelice.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.STCCPRI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SETEC.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SACM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SPR.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SuperISSSTE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.TecNM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.Telecomm.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.Canal22.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.TFCA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.TribunalesAgrarios.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.TURISSSTE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.UnADM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.AFSEDF.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.BANJERCITO.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CENAPRECE.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CNSNS.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.CONAMPROS.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.ESSA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FIFOMI.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FIRA.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.FonaturOperadoraPortuaria.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HIMFG.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HJM.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.HRAEPY.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.INNN.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.ProMéxico.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SAP.getLabel())
					&&!entidad.getCodigo().equals(EntidadesEnum.SPF.getLabel())) {
					Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
					if(divs.outerHtml()!="") {
						divs.get(0).select("div");
						if(!entidad.getCodigo().equals(EntidadesEnum.InstitutodeFormaciónMinisterialPolicialyPericial.getLabel())) {
							for(Element el:divs ) {
								Element ele = el.children().get(0);
								direccionString = ele.text().replace(cidigoEspacio, " ");
								
								Element ele2 = el.children().get(1);
								telefonoString = ele2.text().replace(cidigoEspacio, " ");
								
								if(el.children().size()>3) {
									Element ele3 = el.children().get(2);
									contactoString = ele3.text().replace(cidigoEspacio, " ");
								}
								
								getDirectorio(entidad.getCodigo(),direccion);
								
							}
						}else {
							for(Element el:divs ) {
								Element ele = el.children().get(0);
								direccionString = ele.textNodes().get(0).text().replace(cidigoEspacio, " ");
								direccionString = direccionString+ele.textNodes().get(1).text().replace(cidigoEspacio, " ");
								
								Element ele2 = el.children().get(0);
								telefonoString = ele2.textNodes().get(2).text().replace(cidigoEspacio, " ");
								
								Element ele3 = el.children().get(0);
								contactoString = ele3.textNodes().get(3).text().replace(cidigoEspacio, " ");
							}
						}
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.AGROASEMEXSA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.AMEXCID.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.COMAR.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FOCIR.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.IPAB.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.Pronósticos.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.SEMAR.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.CIQA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FIDENA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.HGM.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.HRAEI.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.HRAEOAXACA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.HRAEV.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INCAN.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INNSZ.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INGER.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INMEGEN.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INP.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INPRF.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INR.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.Telecomm.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.BANJERCITO.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.CENAPRECE.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.CNSNS.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.CONAMPROS.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.ESSA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FIFOMI.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FIRA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.HIMFG.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.HJM.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.HRAEPY.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INNN.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.SAP.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.SPF.getLabel())
						&&!entidad.getCodigo().equals(EntidadesEnum.SENER.getLabel())
						&&!entidad.getCodigo().equals(EntidadesEnum.PGR.getLabel())
						&&!entidad.getCodigo().equals(EntidadesEnum.FONDO.getLabel())) {
					Elements divs = direccion.select("div.col-md-4.col-sm-12.bottom-buffer");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("address");
						for(Element el:divs2) {
							if(!entidad.getCodigo().equals(EntidadesEnum.INP.getLabel())) {
								direccionString = direccionString+el.textNodes().get(0);
								direccionString = direccionString+el.textNodes().get(1);
								
								telefonoString = telefonoString+el.textNodes().get(2);
								if(el.textNodes().size()>4) {
									contactoString = contactoString+el.textNodes().get(3);
								}else {
									contactoString = noAsignado;
								}
								if(entidad.getCodigo().equals(EntidadesEnum.INCAN.getLabel())
									||entidad.getCodigo().equals(EntidadesEnum.INNSZ.getLabel())) {
									direccionString = direccionString+el.textNodes().get(2);
									telefonoString = "";
									contactoString = "";
									telefonoString = telefonoString+el.textNodes().get(3);
									
									contactoString = contactoString+el.textNodes().get(4);
								}
								if(entidad.getCodigo().equals(EntidadesEnum.INNSZ.getLabel())) {
									direccionString = direccionString+el.textNodes().get(3);
									telefonoString = "";
									contactoString = "";
									telefonoString = telefonoString+el.textNodes().get(5);
									
									contactoString = contactoString+el.textNodes().get(6);
								}
							}else if(entidad.getCodigo().equals(EntidadesEnum.INP.getLabel())){
								direccionString = direccionString+el.textNodes().get(0);
								telefonoString = telefonoString+el.textNodes().get(2);
								
								contactoString = contactoString+el.textNodes().get(3);
							}
							direccionString = direccionString.replace(cidigoEspacio, " ");
							telefonoString = telefonoString.replace(cidigoEspacio, " ");
							
							contactoString = contactoString.replace(cidigoEspacio, " ");
						}
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PGR.getLabel())) {
					Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("address");
						for(Element el:divs2) {
							direccionString = direccionString+el.textNodes().get(0);
							direccionString = direccionString+el.textNodes().get(1);
							direccionString = direccionString.replace(cidigoEspacio, " ");
							telefonoString = telefonoString+el.textNodes().get(2);
							contactoString = contactoString+el.textNodes().get(3);
						}
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.SENER.getLabel())) {
					Elements divs = direccion.select("div.col-md-6.col-sm-8.col-xs-12.bottom-buffer");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(1).select("address");
						for(Element el:divs2) {
							direccionString = direccionString+el.textNodes().get(0);
							direccionString = direccionString+el.textNodes().get(1);
							direccionString = direccionString.replace(cidigoEspacio, " ");
							telefonoString = telefonoString+el.textNodes().get(2);
							contactoString = contactoString+el.textNodes().get(3);
						}
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIAltamira.getLabel())) {
					Elements divs = direccion.select("div.col-xs-12.col-sm-12.col-md-12.col-lg-8");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("div");
							direccionString = divs2.get(1).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						telefonoString = noAsignado;
						contactoString = noAsignado;
						direccionString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APICoatzacoalcos.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(21).select("div");
							direccionString = divs2.get(0).getElementsByTag("p").textNodes().get(0).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIDosBocas.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.APIEnsenada.getLabel())) {
					Elements divs = direccion.select("p");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).getElementsByTag("p").textNodes().get(0).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIGuaymas.getLabel())) {
					Elements divs = direccion.select("p");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APILázaroCárdenas.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FonaturConstructora.getLabel())) {
					Elements divs = direccion.select("div.infoDir");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+divs2.get(1).text().replace(cidigoEspacio, " ");
							String[] s = null;
							if(entidad.getCodigo().equals(EntidadesEnum.APILázaroCárdenas.getLabel())) {
								s = direccionString.split(" Tel: ");
							}else if(entidad.getCodigo().equals(EntidadesEnum.FonaturConstructora.getLabel())) {
								s = direccionString.split(", T. ");
							}
							direccionString = s[0];
							telefonoString = s[1];
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIManzanillo.getLabel())) {
					Elements divs = direccion.select("div.col-xs-12.col-sm-12.col-md-12.col-lg-8");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIMazatlán.getLabel())) {
					Elements divs = direccion.select("div.col-md-12");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("li");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+divs2.get(1).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIProgreso.getLabel())) {
					Elements divs = direccion.select("dd");
					if(divs.outerHtml()!="") {
							direccionString = divs.textNodes().get(0).text().replace(cidigoEspacio, " ");
							
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIPuertoMadero.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(35).select("div");
							direccionString = divs2.get(0).getElementsByTag("p").textNodes().get(0).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APISalinaCruz.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(9).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APITampico.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.APITuxpan.getLabel())) {
					Elements divs = direccion.select("h8");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+", "+divs.get(2).text().replace(cidigoEspacio, " ");
							telefonoString = divs.get(1).select("a").text();
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APITopolobampo.getLabel())) {
					Elements divs = direccion.select("div.col-md-12");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.select("p");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIVeracruz.getLabel())) {
					Elements divs = direccion.select("h6");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+", "+divs.get(1).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.APIPuertoVallarta.getLabel())) {
					Elements divs = direccion.select("p");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(2).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.AICM.getLabel())) {
					Elements divs = direccion.select("div.footer-note.span12.aligncenter");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).text().replace(cidigoEspacio, " ");
							String[] s = direccionString.split(", Teléfono: ");
							direccionString = s[0].replace(cidigoEspacio, " ");
							telefonoString = s[1].replace(cidigoEspacio, " ");
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.AIC.getLabel())) {
					Elements divs = direccion.select("div.footer-note.span12.aligncenter");
					if(divs.outerHtml()!="") {
							
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CCC.getLabel())) {
					Elements divs = direccion.select("div.custom");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.select("p");
							direccionString = divs2.get(5).text().replace(cidigoEspacio, " ");
							String[] s = direccionString.split(" » ");
							direccionString = s[1];
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CETI.getLabel())) {
					Elements divs = direccion.select("div.col-md-4");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.select("address");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							String[] s = direccionString.split(" Teléfono:");
							direccionString = s[0];
							telefonoString = s[1];
							contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIDESI.getLabel())) {
					Elements divs = direccion.select("span.copyright");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
							telefonoString = divs.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
							contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CECY.getLabel())) {
					Elements divs = direccion.select("div.footer_dv");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+divs.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+divs.get(0).textNodes().get(4).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+divs.get(0).textNodes().get(5).text().replace(cidigoEspacio, " ");
							telefonoString = divs.get(0).textNodes().get(6).text().replace(cidigoEspacio, " ");
							contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CICESE.getLabel())) {
					Elements divs = direccion.select("div.footer-col");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.select("p");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(0).textNodes().get(4).text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIAD.getLabel())) {
					Elements divs = direccion.select("div.sppb-addon-content");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(11).textNodes().get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs.get(11).textNodes().get(1).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CentroGeo.getLabel())) {
					Elements divs = direccion.select("dl.info-list");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.select("div");
						direccionString = divs2.get(1).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(2).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(10).select("a").text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIMAT.getLabel())) {
					Elements divs = direccion.select("div#notice");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						String[] s = telefonoString.split(" / Responsable de la información ");
						telefonoString = s[0];
						contactoString = s[1];
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIMAV.getLabel())) {
					Elements divs = direccion.select("td.unidad");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
						telefonoString = divs.get(0).textNodes().get(4).text().replace(cidigoEspacio, " ");
						contactoString = divs.get(0).textNodes().get(5).text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIATEJ.getLabel())) {
					Elements divs = direccion.select("div.col-lg-2");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(2).select("p");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(0).textNodes().get(5).text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CINVESTAV.getLabel())) {
					Elements divs = direccion.select("div#dnn_ctr1212_HtmlModule_lblContent");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("span");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" Tel: ");
						direccionString = s[0].replace(cidigoEspacio, " ");
						telefonoString = s[1].replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIDETEQ.getLabel())) {
					Elements divs = direccion.select("div#text-8");
					Elements div2	 = direccion.select("div#text-9");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						Elements div = div2.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = div.get(0).text();
						String[] s = telefonoString.split(" / ");
						telefonoString = s[0];
						contactoString = s[1];
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIDE.getLabel())) {
					Elements divs = direccion.select("div.grey-text.text-lighten-3.address");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(0).textNodes().get(4).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(0).textNodes().get(5).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(0).textNodes().get(6).text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CNI.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = PENDIENTE;
						contactoString = PENDIENTE;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.Cibnor.getLabel())) {
					Elements divs = direccion.select("div#custom-6482-particle");
					Elements div = direccion.select("div#contact-6204-particle");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p").not("p.hidden-tablet");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = div.select("div.g-contact-text").get(1).text();
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIO.getLabel())) {
					Elements divs = direccion.select("div.datos_centrados");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).text().replace(cidigoEspacio, " ");
						String[] s = telefonoString.split("-");
						telefonoString = s[0];
						contactoString = s[1];
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIESAS.getLabel())) {
					Elements divs = direccion.select("ul.datos-contacto");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("li");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CECC.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CentroFederaldeProtecciónaPersonas.getLabel())) {
					Elements divs = direccion.select("div.url");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("table");
						direccionString = divs2.get(1).select("td").textNodes().get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).select("td").textNodes().get(1).text().replace(cidigoEspacio, " ");;
						contactoString = divs2.get(1).select("td").select("a").text().replace(cidigoEspacio, " ");;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIATECAC.getLabel())) {
					Elements divs = direccion.select("div.texto");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(6).select("p");
						direccionString = divs2.get(2).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(3).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(4).text().replace(cidigoEspacio, " ");;
						contactoString = divs2.get(5).text().replace(cidigoEspacio, " ");;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CIATEQAC.getLabel())) {
					Elements divs = direccion.select("div.content.clearfix");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(15).textNodes().get(6).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(15).textNodes().get(7).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(15).textNodes().get(8).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(15).select("span").get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(15).textNodes().get(2).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(15).textNodes().get(5).text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLPOS.getLabel())) {
					Elements divs = direccion.select("div#pie_1a");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CSAEGRO.getLabel())) {
					Elements divs = direccion.select("section#block-block-22");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(2).select("a").text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CDPIM.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.COFAAIPN.getLabel())) {
					Elements divs = direccion.select("div.direccion");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("strong");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						String[] s = telefonoString.split(" Ext. ");
						telefonoString = s[0];
						contactoString = "Ext. "+s[1];
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CFE.getLabel())) {
					Elements divs = direccion.select("div.widget.clearfix");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(5).select("address");
						direccionString = divs2.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				} else if(entidad.getCodigo().equals(EntidadesEnum.ComisiónparalaSeguridadyelDesarrolloIntegralenelEstadodeMichoacán.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.CONASE.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.CAIMFS.getLabel())) {
					Elements divs = direccion.select("div.infoblock");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(1).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(", Tel. ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = divs2.get(2).text().replace(cidigoEspacio, " ");
						String[] s2 = contactoString.split(":");
						contactoString = s2[1];
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.COMESA.getLabel())) {
					Elements divs = direccion.select("div.bottom-footer.widget.widget_ultimatumwysiwyg.inner-container");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(1).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(2).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CECUT.getLabel())) {
					Elements divs = direccion.select("div.p5.c");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" TELS. ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = divs.get(0).select("a").text();
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.ConsejodeMenores.getLabel())) {
					Elements divs = direccion.select("table#pie");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("tr");
						direccionString = divs2.get(5).select("p").textNodes().get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(5).select("p").textNodes().get(1).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" Tel. ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CPTM.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONACYT.getLabel())) {
					Elements divs = direccion.select("div.contenedor.texto-foot");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(0).textNodes().get(4).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(0).textNodes().get(5).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONEVAL.getLabel())) {
					Elements divs = direccion.select("div.col-md-7.dir");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" Conmutador: ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = divs.get(0).select("a").text();
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONOCER.getLabel())) {
					Elements divs = direccion.select("div.contact-info__caption");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("address");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(3).text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONAPRED.getLabel())) {
					Elements divs = direccion.select("div#footer");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(0).select("span").get(1).text();
						contactoString = divs2.get(0).select("span").get(2).text();
						String[] s = contactoString.split(" con");
						contactoString = s[0];
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CONADIS.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CNSPD.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.COMIMSA.getLabel())) {
					Elements divs = direccion.select("ul.list-large.list-icons");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("li");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.CrezcamosJuntos.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.EDUCAL.getLabel())) {
					Elements divs = direccion.select("div.whole-line.selectable-location");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						telefonoString = divs.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						contactoString = divs.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.Colef.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.ECOSUR.getLabel())) {
					Elements divs = direccion.select("div.col-xs-12.col-sm-3.col-lg-3");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split("Tel. ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLMEX.getLabel())) {
					Elements divs = direccion.select("p.uk-text-center");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(1).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" Tel.: ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLMICH.getLabel())) {
					Elements divs = direccion.select("div#pie");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("address");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" / Tel ");
						direccionString = s[0];
						telefonoString = s[1].replace("Fax", "Tel.");
						telefonoString = telefonoString.replace("y ", "");
						contactoString = s[1];
						contactoString = contactoString.replace("y ", "");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.COLSAN.getLabel())) {
					Elements divs = direccion.select("ul.footer-ul");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("li");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs2.get(1).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(2).text().replace(cidigoEspacio, " ");
						contactoString = divs2.get(3).select("a").attr("href").replace(cidigoEspacio, " ");
						String[] s = contactoString.split(":");
						contactoString = s[1];
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.EstudiosChurubusco.getLabel())) {
					Elements divs = direccion.select("font.address");
					Elements div = direccion.select("a.tel");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("a");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = div.get(0).text();
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FERROISTMO.getLabel())) {
					Elements divs = direccion.select("div#pie");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(2).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+divs.get(0).textNodes().get(3).text().replace(cidigoEspacio, " ");
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FideicomisoparalaCinetecaNacional.getLabel())) {
					Elements divs = direccion.select("div#pie");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" - Tel ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = noAsignado;
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FonaturMantenimientoTurístico.getLabel())) {
					Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("div");
							direccionString = divs2.get(1).text().replace(cidigoEspacio, " ");
							telefonoString = divs2.get(2).text().replace(cidigoEspacio, " ");
							contactoString = divs2.get(3).text().replace(cidigoEspacio, " ");
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FCE.getLabel())) {
					Elements divs = direccion.select("span.thumb-info-caption-text");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(1).textNodes().get(1).text().replace(cidigoEspacio, " ");
							telefonoString = divs.get(1).textNodes().get(9).text().replace(cidigoEspacio, " ");
							contactoString = divs.get(1).textNodes().get(5).text().replace(cidigoEspacio, " ");
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FOPESCA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FONDO.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.FEGA.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FOVI.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FIDERH.getLabel())) {
					Elements divs = direccion.select("div.footer-text");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.get(1).select("div");
							direccionString = divs2.get(1).textNodes().get(0).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+divs2.get(1).textNodes().get(1).text().replace(cidigoEspacio, " ");
							telefonoString = divs2.get(1).textNodes().get(2).text().replace(cidigoEspacio, " ");
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.GACM.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.HRAEB.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.INCICH.getLabel())) {
					Elements divs = direccion.select("div.col-md-4.col-sm-12.bottom-buffer");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("address");
						direccionString = direccionString+divs2.get(1).textNodes().get(0);
						direccionString = direccionString+divs2.get(1).textNodes().get(1);
						direccionString = direccionString+divs2.get(1).textNodes().get(2);
						direccionString = direccionString.replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).textNodes().get(3).text();
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.IIIServiciosSAdeCV.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.IEPSA.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INFOTEC.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.IIISA.getLabel())
						||entidad.getCodigo().equals(EntidadesEnum.PEMEX.getLabel())) {
					Elements divs = direccion.select("div.wrapper960.span12");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(8).textNodes().get(3).text();
						direccionString = direccionString+divs.get(8).textNodes().get(4).text();
						String[] s = direccionString.split(" CDMX ");
						direccionString = s[0].replace(cidigoEspacio, " ");
						telefonoString = s[1].replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}
				else if(entidad.getCodigo().equals(EntidadesEnum.INECOL.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.InstitutoMora.getLabel())) {
					Elements divs = direccion.select("section#enlaces-gobierno");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(10).text();
						String[] s = direccionString.split(" - Tel. ");
						direccionString = s[0].replace(cidigoEspacio, " ");
						telefonoString = s[1].split("Google")[0].replace(cidigoEspacio, " ");
						contactoString = divs2.get(10).select("a").get(1).attr("href");
						String[] s2 = contactoString.split(":");
						contactoString = s2[1];
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INFONAVIT.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAI.getLabel())) {
					Elements divs = direccion.select("div.elementor-text-editor.elementor-clearfix");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(19).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.IMCINE.getLabel())) {
					Elements divs = direccion.select("div.wpb_wrapper");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(43).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						direccionString = divs2.get(0).text().replace("© 2010-2019. Instituto Mexicano de Cinematografía – ", "");
						String[] s = direccionString.split(" Teléfono ");
						direccionString = s[0].replace(cidigoEspacio, " ");
						telefonoString = s[1].replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.IMER.getLabel())) {
					Elements divs = direccion.select("ul.contact-info");
					Elements div = direccion.select("div.widget-info");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("li");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = div.get(0).textNodes().get(1).text();
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAP.getLabel())) {
					Elements divs = direccion.select("p.elementor-icon-box-description");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs.get(2).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAH.getLabel())) {
					Elements divs = direccion.select("div.direccion");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(" Teléfonos ");
						direccionString = s[0].replace(cidigoEspacio, " ");
						telefonoString = s[1].replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INAOEP.getLabel())) {
					Elements divs = direccion.select("div#pie");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split("Teléfono:");
						direccionString = s[0].replace("|", "");
						telefonoString = s[1].split("Contacto:")[0].replace("|", "");
						contactoString = s[1].split("Contacto:")[1].replace("|", "");
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INBA.getLabel())) {
					Elements divs = direccion.select("div.col-sm-4");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(14).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(1).textNodes().get(0).text();
						telefonoString = telefonoString+" "+divs2.get(1).textNodes().get(1).text();
						telefonoString = telefonoString+divs2.get(1).textNodes().get(2).text();
						contactoString = divs2.get(1).textNodes().get(3).text();
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INACIPE.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INER.getLabel())) {
					Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
					if(divs.outerHtml()!="") {
						divs.get(0).select("div");
						for(Element el:divs ) {
							Element ele = el.children().get(0);
							direccionString = ele.text().replace(cidigoEspacio, " ");
							telefonoString = el.children().get(2).text().split(":")[1];
							contactoString = noAsignado;
						}
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INEHRM.getLabel())) {
					Elements divs = direccion.select("div.textothumb2");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(3).textNodes().get(0).text().replace(cidigoEspacio, " ");
							direccionString = direccionString+divs.get(3).textNodes().get(1).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INALI.getLabel())) {
					Elements divs = direccion.select("div#pie_1a");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
							String[] s = direccionString.split(" - Tel. ");
							direccionString = s[0];
							telefonoString = s[1];
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INSP.getLabel())) {
					Elements divs = direccion.select("div.container.mx-auto.flex.items-center.mb-4");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							telefonoString = direccion.select("div.w-full.flex.items-center.mb-2").get(1).text();
							contactoString = direccion.select("div.w-full.flex.items-center.mb-2").get(0).text();
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INDAUTOR.getLabel())) {
					Elements divs = direccion.select("div.col-md-8");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INADEM.getLabel())) {
					Elements divs = direccion.select("div#contact-info");
					if(divs.outerHtml()!="") {
							direccionString = divs.get(0).select("div").get(4).select("p").text().split(":")[1].replace(cidigoEspacio, " ");
							telefonoString = divs.get(0).select("div").get(1).select("p").text().split(":")[1].replace(cidigoEspacio, " ");
							contactoString = divs.get(0).select("div").get(2).select("p").text().split(":")[1].replace(cidigoEspacio, " ");
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.INDETEC.getLabel())) {
					Elements divs = direccion.select("address.no-mb");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							telefonoString = divs2.get(1).text().replace(cidigoEspacio, " ");
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.IPN.getLabel())) {
					Elements divs = direccion.select("div.col-lg-12.pl-7.piePagina-texto");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							String[] s = direccionString.split(", 2019. ");
							direccionString = s[0];
							telefonoString = s[1];
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.IPICYT.getLabel())) {
					Elements divs = direccion.select("div.cajas_de_iconos");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.get(3).select("p");
							direccionString = divs2.get(1).text().replace(cidigoEspacio, " ");
							String[] s = direccionString.split("Tel:");
							direccionString = s[0];
							telefonoString = s[1];
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.BIRMEX.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.MexicoDigital.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.NOTIMEX.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PMI.getLabel())) {
					Elements divs = direccion.select("div.direction");
					if(divs.outerHtml()!="") {
							Elements divs2 = divs.get(0).select("p");
							direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
							telefonoString = noAsignado;
							contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.POIIPN.getLabel())) {
					Elements divs = direccion.select("div.col-lg-10.pl-5.piePagina-texto");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split("2019. ");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXExploraciónyProducción.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXGasyPetroquímicaBásica.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXPetroquímica.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PEMEXRefinación.getLabel())) {
					Elements divs = direccion.select("div.footerLeft.left");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(0).text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split("Conmutador");
						direccionString = s[0];
						telefonoString = s[1];
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PRESIDENCIA.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.PRODECON.getLabel())) {
					Elements divs = direccion.select("div#TextoCentral");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(1).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(2).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(3).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(4).text().split("L")[0].replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.ProgramaparaDemocratizarlaProductivdad.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.ERadio.getLabel())) {
					Elements divs = direccion.select("div#footer-wrapper");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("div");
						direccionString = divs2.get(7).select("p").textNodes().get(1).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(7).select("p").textNodes().get(2).text().replace(cidigoEspacio, " ");
						telefonoString = divs2.get(7).select("p").textNodes().get(3).text().replace(cidigoEspacio, " ");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.SecciónMexicanadelaComisiónInternacionaldeLímitesyAguasentreMéxicoyEstadosUnidos.getLabel())) {
					Elements divs = direccion.select("p.direccion");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(2).text().replace(cidigoEspacio, " ");
						telefonoString = divs.get(3).text().replace(cidigoEspacio, " ");
						contactoString = direccion.select("td.rojo").select("strong").text().replace(cidigoEspacio, " ");
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.SeccionesMexicanasdelasComisionesInternacionalesdeLímitesyAguasentreMéxicoyGuatemalayentreMéxicoyBelice.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.STCCPRI.getLabel())) {
					Elements divs = direccion.select("div.dirFooter");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+divs2.get(1).text().split(" - ")[0];
						telefonoString = divs2.get(1).text().split(" - ")[1];
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.SETEC.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.SACM.getLabel())) {
					Elements divs = direccion.select("div.inner");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("div");
						direccionString = divs2.get(2).text().split(", Teléfono: ")[0];
						telefonoString = divs2.get(2).text().split(", Teléfono: ")[1];
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.SPR.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.SuperISSSTE.getLabel())) {
					Elements divs = direccion.select("div.widget-content");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = divs2.get(0).text().replace("", "");
						String[] s = direccionString.split(" - Tel. ");
						direccionString = s[0];
						telefonoString = s[1].split("-")[0];
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.TecNM.getLabel())) {
					Elements divs = direccion.select("p.mbr-text");
					if(divs.outerHtml()!="") {
						direccionString = divs.get(0).textNodes().get(3).text().replace("", "");
						telefonoString = divs.get(0).textNodes().get(10).text().replace("", "");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.Canal22.getLabel())) {
					Elements divs = direccion.select("div.entry-content");
					if(divs.outerHtml()!="") {
						direccionString = divs.select("a").get(0).text().replace("", "");
						telefonoString = divs.select("p").get(2).text().replace("", "");
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.TFCA.getLabel())) {
					Elements divs = direccion.select("div.col-md-6");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("address");
						direccionString = direccionString+divs2.textNodes().get(0);
						direccionString = direccionString+divs2.textNodes().get(1);
						telefonoString = divs2.textNodes().get(3).text();
						contactoString = noAsignado;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.TribunalesAgrarios.getLabel())) {
					Elements divs = direccion.select("div.textwidget.custom-html-widget");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(11).select("p");
						direccionString = direccionString+divs2.get(4).text();
						direccionString = direccionString+divs2.get(5).text();
						telefonoString = divs2.get(2).text();
						contactoString = divs2.get(2).text();
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.TURISSSTE.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.UnADM.getLabel())) {
					Elements divs = direccion.select("div.custom.ubicacion");
					if(divs.outerHtml()!="") {
						Elements divs2 = divs.get(0).select("p");
						direccionString = direccionString+divs2.get(0).textNodes().get(0).text();
						direccionString = direccionString+divs2.get(0).textNodes().get(1).text();
						telefonoString = divs2.get(0).textNodes().get(4).text();
						contactoString = divs2.get(0).textNodes().get(6).text();;
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.AFSEDF.getLabel())) {
					Elements divs = direccion.select("p.t-justified.t-base-color");
					if(divs.outerHtml()!="") {
						direccionString = direccionString+divs.get(0).textNodes().get(0).text();
						direccionString = direccionString+divs.get(0).textNodes().get(1).text();
						telefonoString = divs.get(0).textNodes().get(2).text();
						contactoString = divs.get(0).textNodes().get(3).text();
					}else {
						direccionString = noAsignado;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.FonaturOperadoraPortuaria.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}else if(entidad.getCodigo().equals(EntidadesEnum.ProMéxico.getLabel())) {
					Elements divs = direccion.select("div");
					if(divs.outerHtml()!="") {
						
					}else {
						//direccionString = noAsignado;
						direccionString = PENDIENTE;
						telefonoString = noAsignado;
						contactoString = noAsignado;
					}
				}
				
			}else {
				direccionString = noAsignado;
				telefonoString = noAsignado;
				contactoString = noAsignado;
			}
		}
		direccionString = direccionString.replace("'", "");
		direccionString = direccionString.replace("\"", "");
		telefonoString = telefonoString.replace("'", "");
		telefonoString = telefonoString.replace("\"", "");
		contactoString = contactoString.replace("'", "");
		contactoString = contactoString.replace("\"", "");
		if(direccionString.equals("")||direccionString.equals(" ")) {
			direccionString = noAsignado;
		}if(telefonoString.equals("")||telefonoString.equals(" ")) {
			telefonoString = noAsignado;
		}if(contactoString.equals("")||contactoString.equals(" ")) {
			contactoString = noAsignado;
		}
		direccionList.add(direccionString);
		direccionList.add(telefonoString);
		direccionList.add(contactoString);
		direccionList.add(listaEnt);
		return direccionList;
	}
	
	public List<DirectorioEntidad> getDirectorio(String codigo,Document direccion) {
		getNombre(codigo, direccion);
		return listaEnt;
	}
	
	public void getNombre(String codigo,Document direccion) {
		String direccionString = noAsignado;
		listaEnt = new ArrayList<DirectorioEntidad>();
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
						Elements ele = divs.select("h5");
						for(Element e : ele) {
							DirectorioEntidad ent = new DirectorioEntidad();
							ent.setEntidad(new Entidades());
							ent.getEntidad().setCodigo(codigo);
							ent.setNombre(e.text().replace(cidigoEspacio, " "));
							ent.setPuesto(getPuesto(codigo, direccion,ele.indexOf(e)));
							listaEnt.add(ent);
						}
						//direccionString = ele.get(0).text().replace(cidigoEspacio, " ");						
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		
	}
	
	//obtener puesto del servidor
		public String getPuesto(String codigo,Document direccion,Integer posicion) {
			String direccionString = noAsignado;
			
				if(direccion!=null) {
					Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
					if(divs.outerHtml()!="") {
						//for(Element el:divs ) {
						Elements ele = divs.select("span");
						direccionString = ele.get(posicion).text().replace(cidigoEspacio, " ");
						//}
					}else {
						direccionString = noAsignado;
					}
				}else {
					direccionString = noAsignado;
				}
			
			return direccionString;
		}
	
}
