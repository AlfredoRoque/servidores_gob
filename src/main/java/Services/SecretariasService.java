package Services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Scraping.ScrapingSerch;
import beans.Dependencia;
import beans.Secretarias;
import beans.Servidores;

public class SecretariasService {
	
	public ScrapingSerch scrap = new ScrapingSerch();
	public String noAsignado = "N/A";
	public String urlGob = "https://www.gob.mx/";
	public String idSecretarias = "#secretarias";
	public String listaSecretarias = "li.clearfix";
	public String cidigoEspacio = "&nbsp";
	public String codigoSplit = ",,";
	private final String stringApi = "/api/v1/levels?";
	public List<Servidores> listaServ = new ArrayList<Servidores>();
	
	//obtiene el todas las secretarias desde el sitio con url = "https://www.gob.mx/gobierno"
	public List<String[]> getSecretarias() {
		List<String[]> lista = new ArrayList<String[]>();
		Elements secretariasList = new Elements();
		String url = urlGob+"gobierno";
		Elements secretarias = scrap.obtenHTML(url+idSecretarias).select(listaSecretarias);
		for(Element sec : secretarias) {
			
			if(secretarias.indexOf(sec)<=28	) {
				secretariasList.add(sec);
			}
		}
		String allDependencias = "";
		String allCodigos = "";
		for(Element sec : secretariasList) {
			if(sec.select("ul").outerHtml()=="") {
				String codigo = sec.select("a").attr("href");
				codigo = codigo.replace("/", "");
				codigo = codigo.replace("http:www", "");
				codigo = codigo.replace("gob", "");
				codigo = codigo.replace(".", "");
				codigo = codigo.replace("mx", "");
				String scrtaria = sec.getElementsByClass("abbr").text();
				if(scrtaria!="") {
					allDependencias =allDependencias+ scrtaria+codigoSplit;
				}else {
					String scrtariab = sec.getElementsByTag("a").text();
					allDependencias =allDependencias+ scrtariab+codigoSplit;
				}
				if(codigo!="") {
					allCodigos =allCodigos+ codigo+codigoSplit;
				}else {
					String codigob = "";
					allCodigos =allCodigos+ codigob+codigoSplit;
				}
			}else {
				sec.getElementsByTag("ul").remove();
				sec.getElementsByClass("sectorizadas").remove();
				String scrtaria = sec.getElementsByTag("a").text();
				String codigo = sec.select("a").attr("href");
				codigo = codigo.replace("/", "");
				codigo = codigo.replace("http:www", "");
				if(!codigo.equals("segob")) {
					codigo = codigo.replace("gob", "");
				}
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
		return lista;
	}
	
	//obtener la direccion por secretaria
	public String getDireccion(String codigo,Document direccion) {
		String direccionString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-6.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("address");
					Element ele = divs.get(1);
					direccionString = ele.text().replace(cidigoEspacio, " ");
					direccionString = direccionString.substring(0,61);
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}
		else if(codigo.equals(Secretarias.PLICIAFEDERAL.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.INM.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDENA.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-12.bottom-buffer");
				if(divs.outerHtml()!="") {
						Elements ele = divs.select("address");
						direccionString = ele.textNodes().get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+ele.textNodes().get(1).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SHCP.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					direccionString = noAsignado;
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEP.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADE.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SFP.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.STPS.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
						Elements ele = divs.select("address");
						direccionString = ele.textNodes().get(0).text().replace(cidigoEspacio, " ");
						direccionString = direccionString+" "+ele.textNodes().get(1).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}
		return direccionString;
	}
	
	//obtener la telefono por secretaria
	public String getTelefono(String codigo,Document telefono) {
		String telefonoString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						if(ele.text().contains("Telefono: ")) {
							telefonoString = ele.text().replace("Telefono: ", "");
						}else if(ele.text().contains("Telefono:")) {
							telefonoString = ele.text().replace("Telefono:", "-");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			telefonoString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						telefonoString = telefonoString.substring(0,24);
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-6.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
						Element ele = divs.get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						telefonoString = telefonoString.substring(61,83);
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Teléfono:")) {
							telefonoString = telefonoString.replace("Teléfono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Teléfono: ")) {
							telefonoString = telefonoString.replace("Teléfono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.PLICIAFEDERAL.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.INM.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDENA.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-12.bottom-buffer");
				if(divs.outerHtml()!="") {
						Elements ele = divs.select("address");
						telefonoString = ele.textNodes().get(3).text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SHCP.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					telefonoString = noAsignado;
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEP.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					Elements ele = divs.select("address");
					telefonoString = ele.textNodes().get(2).text().replace(cidigoEspacio, " ");
					if(telefonoString.contains("Atención Ciudadana: ")) {
						telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
						telefonoString = telefonoString.replace("-", "");
					}else if(telefonoString.contains("Atención Ciudadana:")) {
						telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
						telefonoString = telefonoString.replace("-", "");
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADE.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SFP.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.STPS.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				Elements ele = divs.select("address");
				if(ele.outerHtml()!="") {
						telefonoString = ele.textNodes().get(2).text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(telefono!=null) {
				Elements divs = telefono.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Telefono: ")) {
							telefonoString = telefonoString.replace("Telefono: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Telefono:")) {
							telefonoString = telefonoString.replace("Telefono:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}
		return telefonoString;
	}
	
	//obtener contacto opcional por secretaria
	public String getTelefono2(String codigo,Document direccion) {
		String telefonoString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = null;
						if(el.children().size()>2) {
							ele = el.children().get(2);
						}
						if(ele!=null) {
							if(ele.text().contains("Atención Ciudadana: ")) {
								telefonoString = ele.text().replace("Atención Ciudadana: ", "");
							}else if(ele.text().contains("Atención Ciudadana:")) {
								telefonoString = ele.text().replace("Atención Ciudadana:", "-");
							}else {
								telefonoString = ele.text();
							}
						}else {
							telefonoString = noAsignado;
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			telefonoString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						telefonoString = telefonoString.substring(26,telefonoString.length());
						if(telefonoString.contains("Correo de Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Correo de Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Correo de Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Correo de Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(3);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
						if(telefonoString.equals("")) {
							telefonoString = noAsignado;
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
						if(telefonoString.contains("y")) {
							telefonoString = telefonoString.replace(" ", "");
							telefonoString = telefonoString.replace("y", ",");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-6.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
						Element ele = divs.get(1);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						telefonoString = telefonoString.substring(133,154);
						if(telefonoString.contains("Atención a la ciudadanía: ")) {
							telefonoString = telefonoString.replace("Atención a la ciudadanía: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención a la ciudadanía:")) {
							telefonoString = telefonoString.replace("Atención a la ciudadanía:", "-");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención a la ciudadanía:")) {
							telefonoString = telefonoString.replace("Atención a la ciudadanía:", "-");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención a la ciudadanía: ")) {
							telefonoString = telefonoString.replace("Atención a la ciudadanía: ", "");
							telefonoString = telefonoString.replace("-", "");
						}
						if(telefonoString.equals(" ")) {
							telefonoString = noAsignado;
						}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(3);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.PLICIAFEDERAL.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.INM.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDENA.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-12.bottom-buffer");
				if(divs.outerHtml()!="") {
						Elements ele = divs.select("address");
						telefonoString = ele.textNodes().get(4).text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SHCP.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(3);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					telefonoString = noAsignado;
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEP.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
						Elements ele = divs.select("address");
						telefonoString = ele.textNodes().get(3).text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADE.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(2);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SFP.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(3);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.STPS.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					telefonoString = noAsignado;
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(3);
						telefonoString = ele.text().replace(cidigoEspacio, " ");
						if(telefonoString.contains("Atención Ciudadana: ")) {
							telefonoString = telefonoString.replace("Atención Ciudadana: ", "");
							telefonoString = telefonoString.replace("-", "");
						}else if(telefonoString.contains("Atención Ciudadana:")) {
							telefonoString = telefonoString.replace("Atención Ciudadana:", "-");
							telefonoString = telefonoString.replace("-", "");
						}
					}
				}else {
					telefonoString = noAsignado;
				}
			}else {
				telefonoString = noAsignado;
			}
		}
		return telefonoString;
	}
	
	//obtener la mision por secretaria
	public String getMision(String codigo,Document mision) {
		String misionString = "";
		Element parrafos = null;
		if(codigo.equals(Secretarias.SRE.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								parrafos = mis.select("p").get(2);
								misionString = parrafos.text();
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.AGRICULTURA.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								parrafos = mis.select("p").get(1);
								misionString = parrafos.text();
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.BIENESTAR.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								parrafos = mis.select("p").get(1);
								misionString = parrafos.text();
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CJEF.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								parrafos = mis.select("p").get(1);
								misionString = parrafos.text()+" "+mis.select("p").get(2).text();
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONAGUA.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							parrafos = mis.select("p").get(1);
							misionString = parrafos.text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONAFOR.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							parrafos = mis.select("blockquote").get(0);
							misionString = parrafos.text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())) {
			if(mision!=null) {
				misionString = noAsignado;
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							parrafos = mis.select("p").get(0);
							misionString = parrafos.text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SALUD.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							parrafos = mis.select("p").get(1);
							misionString = parrafos.text()+" "+mis.select("p").get(3).text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SCT.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							Elements d = mis.select("div.col-md-7");
							Elements d2 = d.get(0).children();
							Elements d3 = d2.select("div");
							parrafos = d3.get(0);
							misionString = parrafos.text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(2);
								misionString = parrafos.text();
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDATU.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(2);
								misionString = parrafos.text();
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							Elements d = mis.select("div.col-md-7");
							Elements d2 = d.get(0).children();
							Elements d3 = d2.select("p");
							parrafos = d3.get(1);
							misionString = parrafos.text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							Elements d = mis.select("div.col-md-7");
							Elements d2 = d.get(0).children();
							Elements d3 = d2.select("p");
							parrafos = d3.get(1);
							misionString = parrafos.text();
							misionString = misionString+" "+d3.get(2).text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CORREOS.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							Elements d = mis.select("div.col-md-7");
							Elements d2 = d.get(0).children();
							Elements d3 = d2.select("p");
							parrafos = d3.get(1);
							misionString = parrafos.text();
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("div");
								parrafos = d3.get(1);
								misionString = parrafos.text();
								if(misionString.equals("")) {
									misionString = noAsignado;
								}
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}
		else if(codigo.equals(Secretarias.PLICIAFEDERAL.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(0);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.INM.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDENA.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(0);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(2);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SHCP.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					misionString = noAsignado;
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEP.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}
		else if(codigo.equals(Secretarias.CONADE.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SFP.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.STPS.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(1);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(mision!=null) {
				Elements divs = mision.select("section.section.quehacemos");
				if(divs.outerHtml()!="") {
					Elements divs2 = divs.get(0).select("div.row.quehacemos");
					Elements a = divs2.get(0).select("a").not("a.sendEst");
					for(Element el:a ) {
						if(el.text().equals("Leer más de este tema")) {
							Document mis = scrap.obtenHTML(urlGob+el.attr("href"));
							if(mis!=null) {
								Elements d = mis.select("div.col-md-7");
								Elements d2 = d.get(0).children();
								Elements d3 = d2.select("p");
								parrafos = d3.get(2);
								misionString = parrafos.text();
							}else {
								misionString = noAsignado;
							}
						}
					}
				}else {
					misionString = noAsignado;
				}
			}else {
				misionString = noAsignado;
			}
		}
		return misionString;
	}
	
	//obtener la secretaria completa
	public String secretariasCompletas(String dependencia,String codigo) {
		String nombre = "";
		if(codigo.equals(Secretarias.CJEF.getLabel())) {
			nombre="Consejería Jurídica del Ejecutivo Federal";
		}else if(codigo.equals(Secretarias.SCT.getLabel())) {
			nombre="Secretaría de Comunicaciones y Transportes";
		}else if(codigo.equals(Secretarias.SE.getLabel())) {
			nombre="Secretaría de Economía";
		}else if(codigo.equals(Secretarias.SEDATU.getLabel())) {
			nombre="Secretaría de Desarrollo Agrario, Territorial y Urbano";
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())) {
			nombre="Secretaría de Medio Ambiente y Recursos Naturales";
		}else if(codigo.equals(Secretarias.SENER.getLabel())) {
			nombre="Secretaría de Energía";
		}else if(codigo.equals(Secretarias.SRE.getLabel())) {
			nombre="Secretaría de Relaciones Exteriores";
		}else if(codigo.equals(Secretarias.SSPC.getLabel())) {
			nombre="Secretaría de Seguridad y Protección Ciudadana";
		}else if(codigo.equals(Secretarias.AGRICULTURA.getLabel())) {
			nombre="Secretaría de Agricultura y Desarrollo Rural";
		}else if(codigo.equals(Secretarias.BIENESTAR.getLabel())) {
			nombre="Secretaría de Bienestar";
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())) {
			nombre="Secretaría de Cultura";
		}else if(codigo.equals(Secretarias.SALUD.getLabel())) {
			nombre="Secretaría de Salud";
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())) {
			nombre="Secretaría de Gobernación";
		}else if(codigo.equals(Secretarias.SEDENA.getLabel())) {
			nombre="Secretaría de la Defensa Nacional";
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())) {
			nombre="Secretaría de la Marina Nacional";
		}else if(codigo.equals(Secretarias.SHCP.getLabel())) {
			nombre="Secretaría de Hacienda y Credito Público";
		}else if(codigo.equals(Secretarias.SEP.getLabel())) {
			nombre="Secretaría de Educación Pública";
		}else if(codigo.equals(Secretarias.CONADE.getLabel())) {
			nombre="Comisión Nacional de Cultura Física y Deporte";
		}else if(codigo.equals(Secretarias.SFP.getLabel())) {
			nombre="Secretaría de la Función Pública";
		}else if(codigo.equals(Secretarias.STPS.getLabel())) {
			nombre="Secretaría del Trabajo y Previsión Social ";
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())) {
			nombre="Secretaría de Turismo";
		}else {
			nombre = dependencia;
		}
		return nombre;
	}

	//obtener nobre del servidor
	public void getNombre(String codigo,Document direccion) {
		String direccionString = noAsignado;
		listaServ = new ArrayList<Servidores>();
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
						Elements ele = divs.select("h5");
						for(Element e : ele) {
							Servidores serv = new Servidores();
							serv.setSecretaria(new Dependencia());
							serv.getSecretaria().setCodigo(codigo);
							serv.setNombre(e.text().replace(cidigoEspacio, " "));
							serv.setPuesto(getPuesto(codigo, direccion,ele.indexOf(e)));
							listaServ.add(serv);
						}
						//direccionString = ele.get(0).text().replace(cidigoEspacio, " ");						
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			Servidores serv = new Servidores();
			serv.setSecretaria(new Dependencia());
			serv.getSecretaria().setCodigo(codigo);
			serv.setNombre("N/A");
			serv.setPuesto("N/A");
			listaServ.add(serv);
			//direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
					Elements ele = divs.select("h5");
					for(Element e : ele) {
						Servidores serv = new Servidores();
						serv.setSecretaria(new Dependencia());
						serv.getSecretaria().setCodigo(codigo);
						serv.setNombre(e.text().replace(cidigoEspacio, " "));
						serv.setPuesto(getPuesto(codigo, direccion,ele.indexOf(e)));
						listaServ.add(serv);
					}
					//direccionString = ele.get(0).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
					Elements ele = divs.select("h5");
					for(Element e : ele) {
						Servidores serv = new Servidores();
						serv.setSecretaria(new Dependencia());
						serv.getSecretaria().setCodigo(codigo);
						serv.setNombre(e.text().replace(cidigoEspacio, " "));
						serv.setPuesto(getPuesto(codigo, direccion,ele.indexOf(e)));
						listaServ.add(serv);
					}
					//direccionString = ele.get(0).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
					Elements ele = divs.select("h5");
					for(Element e : ele) {
						Servidores serv = new Servidores();
						serv.setSecretaria(new Dependencia());
						serv.getSecretaria().setCodigo(codigo);
						serv.setNombre(e.text().replace(cidigoEspacio, " "));
						serv.setPuesto(getPuesto(codigo, direccion,ele.indexOf(e)));
						listaServ.add(serv);
					}
					//direccionString = ele.get(0).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.list-unstyled.slides");
				if(divs.outerHtml()!="") {
					Element ele = divs.get(0);
					Elements elem = ele.select("h3");
					for(Element e : elem) {
						Servidores serv = new Servidores();
						serv.setSecretaria(new Dependencia());
						serv.getSecretaria().setCodigo(codigo);
						direccionString = e.text().replace(cidigoEspacio, " ");
						String[] s = direccionString.split(",");
						direccionString = s[0];
						serv.setNombre(direccionString);
						serv.setPuesto(getPuesto(codigo, direccion,elem.indexOf(e)));
						listaServ.add(serv);
					}
					//direccionString = elem.get(0).text().replace(cidigoEspacio, " ");
					//String[] s = direccionString.split(",");
					//direccionString = s[0];
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			//if(direccion!=null) {
			//	Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
			//	if(divs.outerHtml()!="") {
			//		for(Element el:divs ) {
			//			Element ele = el.children().get(0);
			//			direccionString = ele.text().replace(cidigoEspacio, " ");
			//		}
			//	}else {
			//		direccionString = noAsignado;
			//	}
			//}else {
			//	direccionString = noAsignado;
			//}
			Servidores serv = new Servidores();
			serv.setSecretaria(new Dependencia());
			serv.getSecretaria().setCodigo(codigo);
			serv.setNombre("N/A");
			serv.setPuesto("N/A");
			listaServ.add(serv);
			//direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			//if(direccion!=null) {
			//	Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
			//	if(divs.outerHtml()!="") {
			//		for(Element el:divs ) {
			//			Element ele = el.children().get(0);
			//			direccionString = ele.text().replace(cidigoEspacio, " ");
			//		}
			//	}else {
			//		direccionString = noAsignado;
			//	}
			//}else {
				//direccionString = noAsignado;
			//}
			Servidores serv = new Servidores();
			serv.setSecretaria(new Dependencia());
			serv.getSecretaria().setCodigo(codigo);
			serv.setNombre("N/A");
			serv.setPuesto("N/A");
			listaServ.add(serv);
			//direccionString = noAsignado;	
		}
		//return direccionString;
	}
	
	//obtener puesto del servidor
	public String getPuesto(String codigo,Document direccion,Integer posicion) {
		String direccionString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())) {
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
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("span");
					direccionString = ele.get(posicion).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("span");
					direccionString = ele.get(posicion).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.office-sm-structure.pull-left");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("span");
					direccionString = ele.get(posicion).text().replace(cidigoEspacio, " ");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.list-unstyled.slides");
				if(divs.outerHtml()!="") {
					Element ele = divs.get(0);
					Elements elem = ele.select("h3");
					direccionString = elem.get(posicion).text().replace(cidigoEspacio, " ");
					String[] s = direccionString.split(",");
					direccionString = s[1];
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			//if(direccion!=null) {
			//	Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
			//	if(divs.outerHtml()!="") {
			//		for(Element el:divs ) {
			//			Element ele = el.children().get(0);
			//			direccionString = ele.text().replace(cidigoEspacio, " ");
			//		}
			//	}else {
			//		direccionString = noAsignado;
			//	}
			//}else {
			//	direccionString = noAsignado;
			//}
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			//if(direccion!=null) {
			//	Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
			//	if(divs.outerHtml()!="") {
			//		for(Element el:divs ) {
			//			Element ele = el.children().get(0);
			//			direccionString = ele.text().replace(cidigoEspacio, " ");
			//		}
			//	}else {
			//		direccionString = noAsignado;
			//	}
			//}else {
			//	direccionString = noAsignado;
			//}
			direccionString = noAsignado;
		}
		return direccionString;
	}
	
	//obtener msion del servidor
	public String getMisionServidor(String codigo) {
		String direccionString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())) {
			Document direccion = scrap.obtenHTML(urlGob+codigo+"/api/v1/levels?offset=3&size=3&up_to=2343");
			if(direccion!=null) {
				Elements divs = direccion.select("div.col-md-4.col-sm-8.col-xs-12.bottom-buffer");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					for(Element el:divs ) {
						Element ele = el.children().get(0);
						direccionString = ele.text().replace(cidigoEspacio, " ");
					}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}
		return direccionString;
	}
	
	//obtener twiter de la dependencia
	public String getTwiter(String codigo,Document direccion) {
		String direccionString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())&&!codigo.equals(Secretarias.BIENESTAR.getLabel())
				&&!codigo.equals(Secretarias.CONAGUA.getLabel())&&!codigo.equals(Secretarias.SALUD.getLabel())
				&&!codigo.equals(Secretarias.SEDATU.getLabel())&&!codigo.equals(Secretarias.CJEF.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
						Elements ele = divs.select("a");
						direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.BIENESTAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONAGUA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SALUD.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDATU.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CJEF.getLabel())){
				direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("table");
				if(divs.outerHtml()!="") {
					Elements elemt = divs.select("a");
					direccionString = elemt.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.INM.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SHCP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					direccionString = noAsignado;
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CONADE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SFP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.STPS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}
		return direccionString;
	}
	
	//obtener facebook de la dependencia
	public String getFacebook(String codigo,Document direccion) {
		String direccionString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())&&!codigo.equals(Secretarias.BIENESTAR.getLabel())
				&&!codigo.equals(Secretarias.CONAGUA.getLabel())&&!codigo.equals(Secretarias.SALUD.getLabel())
				&&!codigo.equals(Secretarias.SEDATU.getLabel())&&!codigo.equals(Secretarias.CJEF.getLabel())
				&&!codigo.equals(Secretarias.CORREOS.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
						Elements ele = divs.select("a");
						direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.CORREOS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.BIENESTAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONAGUA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SALUD.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDATU.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CJEF.getLabel())){
				direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("table");
				if(divs.outerHtml()!="") {
					Elements elemt = divs.select("a");
					direccionString = elemt.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.INM.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SHCP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					direccionString = noAsignado;
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CONADE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SFP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.STPS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}
		return direccionString;
	}
	
	//obtener youtube de la dependencia
	public String getYoutube(String codigo,Document direccion) {
		String direccionString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())&&!codigo.equals(Secretarias.BIENESTAR.getLabel())
				&&!codigo.equals(Secretarias.CONAGUA.getLabel())&&!codigo.equals(Secretarias.SALUD.getLabel())
				&&!codigo.equals(Secretarias.SEDATU.getLabel())&&!codigo.equals(Secretarias.CJEF.getLabel())
				&&!codigo.equals(Secretarias.CORREOS.getLabel())&&!codigo.equals(Secretarias.CONAFOR.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
						Elements ele = divs.select("a");
						if(ele.size()>3) {
							direccionString = ele.get(3).attr("href");
						}else {
							direccionString = noAsignado;
						}
						
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.CONAFOR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
						Elements ele = divs.select("a");
						direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CORREOS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.BIENESTAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONAGUA.getLabel())){
			//if(direccion!=null) {
			//	Elements divs = direccion.select("ul.fa-ul.social-follow");
			//	if(divs.outerHtml()!="") {
			//		divs.get(0).select("div");
			//		Elements ele = divs.select("a");
			//		direccionString = ele.get(0).attr("href");
			//	}else {
			//		direccionString = noAsignado;
			//	}
			//}else {
			//	direccionString = noAsignado;
			//}
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SALUD.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDATU.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CJEF.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("table");
				if(divs.outerHtml()!="") {
					Elements elemt = divs.select("a");
					direccionString = elemt.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.INM.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SHCP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					direccionString = noAsignado;
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CONADE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(4).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SFP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.STPS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(1).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}
		return direccionString;
	}
	
	//obtener instagram de la dependencia
	public String getInstagram(String codigo,Document direccion) {
		String direccionString = noAsignado;
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())&&!codigo.equals(Secretarias.BIENESTAR.getLabel())
				&&!codigo.equals(Secretarias.CONAGUA.getLabel())&&!codigo.equals(Secretarias.SALUD.getLabel())
				&&!codigo.equals(Secretarias.SEDATU.getLabel())&&!codigo.equals(Secretarias.CJEF.getLabel())
				&&!codigo.equals(Secretarias.CORREOS.getLabel())&&!codigo.equals(Secretarias.CONAFOR.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
						if(!codigo.equals("sedena")) {
							divs.get(0).select("div");
							Elements ele = divs.select("a");
							direccionString = ele.get(2).attr("href");
						}else {
							direccionString = noAsignado;
						}
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.CONAFOR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
						Elements ele = divs.select("a");
						direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CORREOS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.BIENESTAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CONAGUA.getLabel())){
			//if(direccion!=null) {
			//	Elements divs = direccion.select("ul.fa-ul.social-follow");
			//	if(divs.outerHtml()!="") {
			//		divs.get(0).select("div");
			//		Elements ele = divs.select("a");
			//		direccionString = ele.get(0).attr("href");
			//	}else {
			//		direccionString = noAsignado;
			//	}
			//}else {
			//	direccionString = noAsignado;
			//}
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SALUD.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEDATU.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CJEF.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("table");
				if(divs.outerHtml()!="") {
					Elements elemt = divs.select("a");
					direccionString = elemt.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			direccionString = noAsignado;
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(4).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.INM.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					direccionString = noAsignado;
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					direccionString = noAsignado;
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SHCP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					direccionString = noAsignado;
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SEP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.CONADE.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(3).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SFP.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.STPS.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(0).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}else if(codigo.equals(Secretarias.SECTUR.getLabel())){
			if(direccion!=null) {
				Elements divs = direccion.select("ul.fa-ul.social-follow");
				if(divs.outerHtml()!="") {
					divs.get(0).select("div");
					Elements ele = divs.select("a");
					direccionString = ele.get(2).attr("href");
				}else {
					direccionString = noAsignado;
				}
			}else {
				direccionString = noAsignado;
			}	
		}
		return direccionString;
	}
	
	//obtener pagina de las redes sociales de la dependencia
	public Document getRedesSociales(String codigo) {
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())&&!codigo.equals(Secretarias.BIENESTAR.getLabel())
				&&!codigo.equals(Secretarias.CONAGUA.getLabel())&&!codigo.equals(Secretarias.SALUD.getLabel())
				&&!codigo.equals(Secretarias.SEDATU.getLabel())&&!codigo.equals(Secretarias.CJEF.getLabel())
				&&!codigo.equals(Secretarias.CORREOS.getLabel())&&!codigo.equals(Secretarias.CONAFOR.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"offset=3&size=3&up_to=2337");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			return null;
		}else if(codigo.equals(Secretarias.CONAFOR.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"offset=3&size=3&up_to=2337");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CORREOS.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"offset=3&size=3&up_to=2337");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.BIENESTAR.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=311");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CONAGUA.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=954");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SALUD.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=390");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SEDATU.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=6910");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CJEF.getLabel())){
			return null;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=2350");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=595");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=569");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=5945");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=364");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=10068");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())||codigo.equals(Secretarias.INM.getLabel())
				||codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())||codigo.equals(Secretarias.SECTUR.getLabel())
				||codigo.equals(Secretarias.SHCP.getLabel())
				||codigo.equals(Secretarias.SEP.getLabel())||codigo.equals(Secretarias.CONADE.getLabel())
				||codigo.equals(Secretarias.SFP.getLabel())||codigo.equals(Secretarias.STPS.getLabel())) {
			
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
			
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())){
			return null;
		}
		return null;
	}
	
	//obtener pagina de los datos de contacto de la dependencia
	public Document getDatosContacto(String codigo) {
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			return null;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())||codigo.equals(Secretarias.INM.getLabel())
				||codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())||codigo.equals(Secretarias.SECTUR.getLabel())
				||codigo.equals(Secretarias.SHCP.getLabel())
				||codigo.equals(Secretarias.SEP.getLabel())||codigo.equals(Secretarias.CONADE.getLabel())
				||codigo.equals(Secretarias.SFP.getLabel())||codigo.equals(Secretarias.STPS.getLabel())) {
			
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
			
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())){
			return null;
		}
		return null;
	}
	
	//obtener pagina de los datos servidores de cada dependencia
	public Document getDatosDirectorio(String codigo) {
		if(!codigo.equals(Secretarias.CONADIS.getLabel())&&!codigo.equals(Secretarias.SCT.getLabel())
				&&!codigo.equals(Secretarias.CULTURA.getLabel())&&!codigo.equals(Secretarias.SE.getLabel())
				&&!codigo.equals(Secretarias.SENER.getLabel())&&!codigo.equals(Secretarias.SEMARNAT.getLabel())
				&&!codigo.equals(Secretarias.SSPC.getLabel())
				&&!codigo.equals(Secretarias.SEGOB.getLabel())&&!codigo.equals(Secretarias.INM.getLabel())
				&&!codigo.equals(Secretarias.SEMAR.getLabel())&&!codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())
				&&!codigo.equals(Secretarias.SHCP.getLabel())&&!codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())
				&&!codigo.equals(Secretarias.SEP.getLabel())&&!codigo.equals(Secretarias.CONADE.getLabel())
				&&!codigo.equals(Secretarias.SFP.getLabel())&&!codigo.equals(Secretarias.STPS.getLabel())
				&&!codigo.equals(Secretarias.SECTUR.getLabel())) {
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"offset=3&size=3&up_to=2343");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CONADIS.getLabel())){
			return null;
		}else if(codigo.equals(Secretarias.SCT.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=7718");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CULTURA.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=596");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SE.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=570");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SENER.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=383");
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.SEMARNAT.getLabel())){
			//Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=370");
			//if(direccion!=null) {
			//	return direccion;
			//}else {
			//	direccion = null;
			//	return direccion;
			//}
			return null;
		}else if(codigo.equals(Secretarias.SSPC.getLabel())){
			//Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi+"up_to=10070");
			//if(direccion!=null) {
			//	return direccion;
			//}else {
			//	direccion = null;
			//	return direccion;
			//}
			return null;
		}else if(codigo.equals(Secretarias.SEGOB.getLabel())||codigo.equals(Secretarias.INM.getLabel())
				||codigo.equals(Secretarias.UNIVERSIDADNAVAL.getLabel())||codigo.equals(Secretarias.SECTUR.getLabel())
				||codigo.equals(Secretarias.SHCP.getLabel())
				||codigo.equals(Secretarias.SEP.getLabel())||codigo.equals(Secretarias.CONADE.getLabel())
				||codigo.equals(Secretarias.SFP.getLabel())||codigo.equals(Secretarias.STPS.getLabel())) {
			
			Document direccion = scrap.obtenHTML(urlGob+codigo+stringApi);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
			
		}else if(codigo.equals(Secretarias.SEMAR.getLabel())){
			Document direccion = scrap.obtenHTML(urlGob+codigo);
			if(direccion!=null) {
				return direccion;
			}else {
				direccion = null;
				return direccion;
			}
		}else if(codigo.equals(Secretarias.CREZCAMOSJUNTOS.getLabel())){
			return null;
		}
		return null;
	}
	
	public List<Servidores> getDirectorio(String codigo,Document direccion) {
		getNombre(codigo, direccion);
		return listaServ;
	}
}
