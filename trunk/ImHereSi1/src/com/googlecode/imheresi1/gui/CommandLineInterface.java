package com.googlecode.imheresi1.gui;

import java.io.IOException;
import java.util.Scanner;

import com.googlecode.imheresi1.localization.PositionException;
import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.MainSystemException;
import com.googlecode.imheresi1.project.UserException;

public class CommandLineInterface {
	
	

	private static Scanner input;
	private static MainSystem mySystem = new MainSystem();
	private static final String SEPARATOR = System.getProperty("line.separator");


	private static final String PROMPT_1 = "<<< Bem vindo ao I'm here! >>>" + SEPARATOR + 
	SEPARATOR +	"Escolha a opcao desejada:" + SEPARATOR + "1. Login" 	+ SEPARATOR + 
	"2. Criar Usuario" 	+ SEPARATOR + "3. Exit";
	private static final String PROMPT_2 = "Opcao: ";


	private static final int LOGIN = 1;
	private static final int CREATE_USER = 2;
	private static final int EXIT = 3;

	private static int getOption(String option){
		int chosenNumber;

		try{
			chosenNumber = Integer.parseInt(option);
		} catch (NumberFormatException e) {
			chosenNumber = -1;
		}

		return chosenNumber;
	}

	private static void createUser(Scanner entrada) {
		String userName, password, email, name, phone;
	
		System.out.println(SEPARATOR + "<<< Cadastro de usuario >>>" + SEPARATOR + 
				"Preencha o formulario abaixo:");
		
		//Form
		System.out.print(SEPARATOR + "Nome: ");
		name = entrada.nextLine().trim();
		System.out.print(SEPARATOR + "Email: ");
		email = entrada.nextLine().trim();
		System.out.print(SEPARATOR  + "Telefone: ");
		phone = entrada.nextLine().trim();
		System.out.print(SEPARATOR + "Username: ");
		userName = entrada.nextLine().trim();
		System.out.print(SEPARATOR + "Senha: ");
		password = entrada.nextLine().trim();
		
		
		try{
			mySystem.createUser(userName, password, email, name, phone);
		}catch (Exception e) {
			System.out.println(e.getMessage());
			createUser(entrada);
		}	
		
		System.out.println(SEPARATOR + "Usuario criado com sucesso!");
		getLocationData(userName, entrada, password);
	}

	private static void getLocationData(String userName, Scanner entrada, String password){
		System.out.print(SEPARATOR + "Ip: ");
		String ip = entrada.nextLine().trim();
		try {
			mySystem.logIn(userName, password, ip);
			mySystem.setLocal(userName, ip);
		} catch (UserException e) {
			System.out.println(SEPARATOR + e.getMessage()); //"Login/senha invalidos." ou "IP invalido."
			getLocationData(userName, entrada, password);
		} catch (PositionException e) {
			double latitude, longitude;
			System.out.println(e.getMessage()); //"Nao foi possivel obter a localizacao."
			System.out.println(SEPARATOR + "Digite sua localizacao manualmente");
			System.out.print(SEPARATOR + "Latitude: ");
			latitude = Double.parseDouble(entrada.nextLine().trim());
			System.out.print(SEPARATOR + "Longitude: ");
			longitude = Double.parseDouble(entrada.nextLine().trim());
			try {
				mySystem.setLocal(userName, latitude, longitude);
				System.out.println("<<< Login efetuado com sucesso >>>");
			} catch (PositionException e1) {
				//e1.printStackTrace();
			} catch (MainSystemException e1) {
				//e1.printStackTrace();
			}
		} catch (MainSystemException e) {
			//e.printStackTrace();
		}
	}
	
	private void getDoubleValue(String prompt, Scanner entrada, String value){
		System.out.print(SEPARATOR + prompt);
	}
	
	public static void main(String[] args) {
		input = new Scanner(System.in);

		System.out.print(PROMPT_1 + SEPARATOR + PROMPT_2);
		int option = getOption(input.nextLine());

		while (option != EXIT) {
			switch (option) {
			case LOGIN:
				System.out.println("oi");
				break;
			case CREATE_USER:
				createUser(input);
				break;
			case EXIT:
				System.out.println(SEPARATOR + "Obrigado, volte sempre!");
				System.exit(0);
				break;
			default:
				System.out.println(SEPARATOR + "Opcao invalida!");
				break;
			}

			System.out.print(SEPARATOR + PROMPT_1 + SEPARATOR + PROMPT_2);
			option = getOption(input.nextLine());
		}
	}
	
	
}