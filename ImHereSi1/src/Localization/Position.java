package Localization;
import java.io.IOException;
import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;

/**
 * Classe que lida com o posicionamento seja a partir de um ip ou de coordenadas
 * ja dadas
 * 
 * @author Grupo E
 * 
 */
public class Position {

	private double latitude;
	private double longitude;
	// Isso (abaixo) da conflito com a classe System da gente!
	// Por isso criei um package separado... :P
	private final String DIR = System.getProperty("user.dir");
	private final String SEPARATOR = System.getProperty("file.separator");
	private final String DATABASE = DIR + SEPARATOR + "GeoLiteCity.dat";

	/**
	 * Construtor a partir de um ip (GeoIP)
	 * 
	 * @param ip
	 * @throws PositionException
	 */
	public Position(String ip) throws PositionException {
		try {
			LookupService lookUp = new LookupService(DATABASE,
					LookupService.GEOIP_STANDARD);
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
	 * Construtor a partir de coordenadas (Manual)
	 * 
	 * @param latitude
	 * @param longitude
	 * @throws PositionException
	 */
	public Position(double latitude, double longitude) throws PositionException {
		setPosition(latitude, longitude);
	}

	/**
	 * Seta a posicao a partir das coordenadas passadas
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
	 * So pra ver se a latitude ta entre -90 e 90 e se a longitude ta entre -180
	 * e 180 (pq coordenadas sempre sao desse jeito)
	 * 
	 * @param lat
	 *            - latitude
	 * @param lon
	 *            - longitude
	 * @return se a latitude e longitude sao validas
	 */
	private boolean isLocation(double lat, double lon) {
		if ((lon >= -180 && lon <= 180) && (lat >= -90 && lat <= 90))
			return true;
		return false;
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

	public String toString() {
		return "Lat: " + this.latitude + ", Long: " + this.longitude;
	}

	/**
	public static void main(String[] args) {
		try {
			Position pos = new Position("127.0.0.1");
			System.out.println(pos.getLatitude());
			System.out.println(pos.getLongitude());
		} catch (PositionException e) {
			e.printStackTrace();
		}

	}*/

}
