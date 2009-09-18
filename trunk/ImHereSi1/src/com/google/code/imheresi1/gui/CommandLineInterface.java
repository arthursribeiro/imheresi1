package com.google.code.imheresi1.gui;

import java.util.Scanner;

import com.googlecode.imheresi1.project.MainSystem;

public class CommandLineInterface {
	
	

	private static Scanner input;
	private static MainSystem mySystem = new MainSystem();
	private static final String SEPARATOR = System.getProperty("line.separator");


	private static final String PROMPT_1 = "<<< Bem vindo ao I'm here! >>>" + SEPARATOR +  SEPARATOR + 
	"Escolha a opcao desejada:" + SEPARATOR + "1. Login" 	+ SEPARATOR + "2. hIHI" + SEPARATOR + "3. Exit";
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
				"Preencha o formulario abaixo:" + SEPARATOR);
		
		//Form
		System.out.println(SEPARATOR + "Nome: ");
		name = entrada.nextLine();
		System.out.println(SEPARATOR + "Email: ");
		email = entrada.nextLine();
		System.out.println(SEPARATOR  );
		
		mySystem.createUser(userName, password, email, name, phone);

	}

	public static void main(String[] args) {
		input = new Scanner(System.in);

		System.out.println(PROMPT_1 + SEPARATOR + PROMPT_2);
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

			System.out.println(SEPARATOR + PROMPT_1 + SEPARATOR + PROMPT_2);
			option = getOption(input.nextLine());
		}
	}
	
	
}