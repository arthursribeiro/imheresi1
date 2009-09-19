package com.googlecode.imheresi1.localization;

import java.io.IOException;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

/**
 * 
 * Class that handles the position type
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class Position {

	private double latitude;
	private double longitude;
	private final String DIR = System.getProperty("user.dir");
	private final String SEPARATOR = System.getProperty("file.separator");
	private final String DATABASE = DIR + SEPARATOR + "GeoLiteCity.dat";

	/**
	 * Constructor receiving an ip (GeoIP)
	 * 
	 * @param ip
	 * @throws PositionException
	 */
	public Position(String ip) throws PositionException {
		try {
			LookupService lookUp = new LookupService(DATABASE,
					LookupService.GEOIP_MEMORY_CACHE);
			Location localizacao = lookUp.getLocation(ip);
			if (localizacao == null) {
				throw new PositionException(
						"Nao foi possivel obter a localizacao.");
			}
			this.latitude = localizacao.latitude;
			this.longitude = localizacao.longitude;
			lookUp.close();
		} catch (IOException e) {
			throw new PositionException("Banco de dados nao encontrado");
		}
	}

	/**
	 * Constructor receiving coordinates (Manual)
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws PositionException
	 */
	public Position(double latitude, double longitude) throws PositionException {
		setPosition(latitude, longitude);
	}

	/**
	 * Sets position
	 * 
	 * @param lat
	 * @param lon
	 * @throws PositionException
	 */
	public void setPosition(double lat, double lon) throws PositionException {
		if (!isLocation(lat, lon))
			throw new PositionException("Latitude/Longitude invalidos.");
		this.latitude = lat;
		this.longitude = lon;
	}

	/**
	 * @param lat
	 *            - latitude
	 * @param lon
	 *            - longitude
	 * @return boolean
	 */
	private boolean isLocation(double lat, double lon) {
		return ((lon >= -180 && lon <= 180) && (lat >= -90 && lat <= 90));
	}

	/**
	 * @return latitude
	 */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * @return longitude
	 */
	public double getLongitude() {
		return this.longitude;
	}

	/**
	 * @return position to string
	 */
	public String toString() {
		return "Lat: " + this.latitude + ", Long: " + this.longitude;
	}

}
