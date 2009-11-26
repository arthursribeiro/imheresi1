package interfaceGrafica;

import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Images {

	public static final ImageIcon TELA_INICIAL = criaImagem("inicial.jpg");
	public static final ImageIcon BACKGROUND = criaImagem("background.jpg");

	private static ImageIcon criaImagem(String imagem) {
		try {
			return new ImageIcon(ImageIO.read(new File(imagem)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
