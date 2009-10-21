package com.googlecode.imheresi1.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class that implements a method to save the message, given a Message object
 * 
 * @author Arthur de Souza Ribeiro
 * @author Jose Laerte
 * @author Raquel Rolim
 * @author Raissa Sarmento
 * 
 */
public class MessageController {

	/**
	 * @param msg - Message object that contains the data to be saved
	 * @throws MessageControllerException
	 */
	public static void sendMessage(Message msg) throws MessageControllerException {
		try {
			FileInputStream file = new FileInputStream(msg.getPath());
			FileWriter bOut = new FileWriter(new File(msg.getPath()), true);
			bOut.write(msg.build());
			bOut.close();
			file.close();
		} catch (FileNotFoundException e) {
			try {
				FileOutputStream fs = new FileOutputStream(msg.getPath());
				fs.write(msg.build().getBytes());
				fs.close();
			} catch (IOException io) {
				throw new MessageControllerException(
						"Nao foi possivel enviar o email.");
			}
		} catch (IOException io) {
			throw new MessageControllerException(
					"Nao foi possivel enviar o email.");
		}
	}

}
