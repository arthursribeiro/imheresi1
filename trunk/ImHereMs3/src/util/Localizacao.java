package util;

import java.io.IOException;


import com.maxmind.geoip.*;


/**
 * Classe Localizacao.
 * @author Delano Helio, Izabela Vanessa, Joao Paulo e Savyo Igor
 * @version 1.2 13 de setembro de 2009
 */

public class Localizacao{

	/**private float longitude;
	private float latitude;
	private boolean localizou;
	
	public Localizacao(){
		longitude = 0;
		latitude = 0;
		localizou = false;
	}*/
	
	/**
	 * Localiza um ip.
	 * @param ip
	 * @return objeto location
	 */
	public Location localize(String ip){
		 try {
			 
			 String sep = System.getProperty("file.separator");
			 String dir = System.getProperty("user.dir"); 
			 String dbfile = dir + sep + "GeoLiteCity.dat";
			 LookupService cl = new LookupService(dbfile,LookupService.GEOIP_STANDARD);
			 com.maxmind.geoip.Location l1 = cl.getLocation(ip);
			 /**if(l1 == null){
				localizou = false;
			 }else{
				localizou = true;
				this.latitude = l1.latitude;
				this.longitude = l1.longitude;	
			 }*/
			 cl.close();
			 
			 if(l1 == null && ip.equalsIgnoreCase("127.0.0.1")){
				 throw new EntradaNoSistemaException("Nao foi possivel obter a localizacao.");
			 }
			 return l1;
		 } catch (IOException e) {
			//localizou = false;
			 System.out.println("IOException - O arquivo GeoIP.dat nao foi encontrado pela classe Localizacao.");
		}
		return null;
	}
	
	/**public float getLatitude(){
		return latitude;
	}
	
	public float getLongitude(){
		return longitude;
	}
	
	public boolean getLocalizou(){
		return localizou;
	}*/
	
	public static void main(String[] args) {
		Localizacao l = new Localizacao();
		Location ll = l.localize("189.71.21.90");
		System.out.println(ll.latitude);
		System.out.println(ll.longitude);
		System.out.println(ll.countryName);
		System.out.println(ll.city);
		//System.out.println(l.localizou);
		//System.out.println(l.latitude);
		//System.out.println(l.longitude);
		ll = l.localize("127.0.0.1");
		System.out.println(ll);
		//System.out.println(l.localizou);
	}
}
