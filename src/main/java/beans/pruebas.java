package beans;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import SQL.MetodosSQL;
import Scraping.ScrapingSerch;
import Services.EntidadesService;
import Services.SecretariasService;
import Servlets.ServletSearchHTML;



import javax.net.ssl.*;
import javax.swing.JOptionPane;

import java.io.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import org.json.JSONArray;
import org.json.JSONObject;
 

public class pruebas {
	
	//private static ScrapingSerch scrap = new ScrapingSerch();
	//private static MetodosSQL metodos = new MetodosSQL();
	//private static SecretariasService srv = new SecretariasService();
	//private static EntidadesService entd = new EntidadesService();

	private static HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	
	public static void main(String[] args) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException, KeyManagementException {
		/*Entidades entidad = new Entidades();
		entidad.setEntidad("TribunalesAgrarios");
		entidad.setUrl("https://www.tribunalesagrarios.gob.mx/ta/");
		entidad.setCodigo("TribunalesAgrarios");
		Document html = entd.getDatosContacto(entidad);
		List<Object> lista = entd.getDireccionTelCont(entidad,html);
		
		System.out.println((String)lista.get(0));
		System.out.println((String)lista.get(1));
		System.out.println((String)lista.get(2));*/
		
		//https://pokeapi.co/api/v2/pokemon?limit=100&offset=200
		
		HttpRequest request =  HttpRequest.newBuilder().GET().uri(URI.create("https://pokeapi.co/api/v2/pokemon?limit=20")).build();
		
		try {
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			JSONObject jsonPokemons;
			JSONObject jsonPokemon;
			JSONArray jsonarray = new JSONArray("["+response.body()+"]");
			System.out.println(jsonarray);
			jsonPokemons = jsonarray.getJSONObject(0);
			JSONArray pokemons = jsonPokemons.getJSONArray("results");
			System.out.println(pokemons);
			String res = "";
			for (int i = 0; i < pokemons.length(); i++) {
				jsonPokemon = pokemons.getJSONObject(i);
	            System.out.println(jsonPokemon.getString("name"));
	            res = res+jsonPokemon.getString("name")+"\n";
	            //listUser.add(user);
	        }
			JOptionPane.showMessageDialog(null, res);
		} catch (IOException|InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
