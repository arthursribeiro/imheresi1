package com.google.code.imheresi1.gui;

import java.util.Scanner;

public class CommandLineInterface {
	
	

	private static Scanner input;
	private static final String SEPARATOR = System.getProperty("line.separator");


	private static final String PROMPT_1 = "Bem vindo ao I'm her!" + SEPARATOR +  SEPARATOR + 
	"Escolha a opcao desejada:" + SEPARATOR + "1. Login" 	+ SEPARATOR + "2. hIHI" + SEPARATOR + "3. Exit";
	private static final String PROMPT_2 = "Opcao: ";


	private static final int LOGIN = 1;
	private static final int CREATE_USER = 2;
	private static final int SAIR = 3;

	private static int getOption(String option){
		int chosenNumber;

		try{
			chosenNumber = Integer.parseInt(option);
		} catch (NumberFormatException e) {
			chosenNumber = -1;
		}

		return chosenNumber;
	}

	private static void createUser() {
		

	}

	public static void main(String[] args) {
		input = new Scanner(System.in);


		System.out.println(PROMPT_1);
		int option = getOption(input.nextLine());

		while (option != SAIR) {
			switch (option) {
			case LOGIN:
				System.out.println("oi");
				break;
			case CREATE_USER:
				createUser();
				break;
			default:
				System.out.println(SEPARATOR + "<<< Opcao invalida! >>>");
			break;
			}

			System.out.println(SEPARATOR + PROMPT_1);
			option = getOption(input.nextLine());

		}

	}
}