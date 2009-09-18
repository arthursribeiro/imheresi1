package com.googlecode.imheresi1.gui;

import java.io.IOException;
import java.util.Scanner;

import com.googlecode.imheresi1.message.MessageControllerException;
import com.googlecode.imheresi1.project.MainSystem;
import com.googlecode.imheresi1.project.MainSystemException;
import com.googlecode.imheresi1.project.UserException;

public class SystemSecondMenu {

	private Scanner input;
	private MainSystem system;
	private static String userName;
	private boolean sair;

	private final static String SEPARATOR = System.getProperty("line.separator");

	private static final String PROMPT_1 = "<< Bem Vindo " + userName + " >>" + SEPARATOR + 
	"1. Atualizar informacoes" + SEPARATOR + "2. Deletar Conta" + SEPARATOR + "3. Editar compartilhamento" + 
	SEPARATOR + "4. Adicionar amigos" + SEPARATOR + "5. Recuperar localizacao dos amigos" + 
	SEPARATOR + "6. Enviar mensagem" + SEPARATOR + "7. Logout";

	private static final int ATUALIZAR = 1;
	private static final int DELETAR = 2;
	private static final int EDITAR_COMPARTILHAMENTO = 3;
	private static final int ADICIONAR_AMIGOS = 4;
	private static final int RECUPERAR_LOCALIZACAO = 5;
	private static final int ENVIAR = 6;
	private static final int LOGOUT = 7;
	private static final String PROMPT_2 = "Opcao: ";

	public SystemSecondMenu(Scanner input, String userName){
		system = new MainSystem();
		this.userName = userName;
		this.input = input;
		sair = false;
	}

	private static int getOption(String option){
		int chosenNumber;

		try{
			chosenNumber = Integer.parseInt(option);
		} catch (NumberFormatException e) {
			chosenNumber = -1;
		}

		return chosenNumber;
	}

	private void atualizar() {
		while(true) {
			System.out.println();
			System.out.println("<< Atualizar Dados >>");
			System.out.println("1. Atualizar Senha");
			System.out.println("2. Atualizar e-mail");
			System.out.println("3. Atualizar telefone");
			System.out.println("4. Atualizar nome");
			System.out.println("5. Voltar ao menu anterior");
			System.out.print(PROMPT_2);
			int option = getOption(input.nextLine());
			switch(option) {
			case(1):
				System.out.print("Nova senha: ");
			String valor = input.nextLine().trim();
			try {
				system.updatePass(userName, valor);
			} catch (MainSystemException e) {
				System.out.println(e.getMessage());
			} catch (UserException e) {
				System.out.println(e.getMessage());
			}
			break;
			case(2):
				System.out.print("Novo e-mail: ");
			String mail = input.nextLine().trim();
			try {
				system.updateMail(userName, mail);
			} catch (MainSystemException e) {
				System.out.println(e.getMessage());
			} catch (UserException e) {
				System.out.println(e.getMessage());
			}
			break;
			case(3):
				System.out.print("Novo telefone: ");
			String tel = input.nextLine().trim();
			try {
				system.updatePhone(userName, tel);
			} catch (MainSystemException e) {
				System.out.println(e.getMessage());
			} 
			break;
			case(4):
				System.out.print("Novo nome: ");
			String nom = input.nextLine().trim();
			try {
				system.updateName(userName, nom);
			} catch (MainSystemException e) {
				System.out.println(e.getMessage());
			} catch (UserException e) {
				System.out.println(e.getMessage());
			} 
			break;
			case(5):
				return;
			default:
				System.out.println("Opcao invalida");
			}
		}
	}

	public void mainLoop(){
		System.out.print(PROMPT_1 + SEPARATOR + PROMPT_2);

		String opt = input.nextLine();
		int option = getOption(opt);
		while (option != LOGOUT) {
			switch (option) {
			case ATUALIZAR:
				atualizar();
				break;
			case DELETAR:
				deletar();
				if(sair)
					return;
				break;
			case EDITAR_COMPARTILHAMENTO:
				editarCompartilhamento();
				break;
			case ADICIONAR_AMIGOS:
				adicionarAmigos();
				break;
			case RECUPERAR_LOCALIZACAO:
				break;
			case ENVIAR:
				enviar();
				break;
			default:
				System.out.println(SEPARATOR + "Opcao invalida!");
				break;
			}

			System.out.print(SEPARATOR + PROMPT_1 + SEPARATOR + PROMPT_2);
			option = getOption(input.nextLine());
		}
	}

	private void enviar() {
		while(true){
			System.out.println("1. Email");
			System.out.println("2. SMS");
			System.out.println("3. Voltar");
			System.out.print("Opcao: ");
			int option = 0;
			switch(option){
			case(1):
				String to, subject, msg;
			System.out.print("Enviar email para (nome do usuario): ");
			while(true){
				to = input.nextLine().trim();
				if(!to.equals("")) break;
			}
			System.out.print("Assunto: ");
			subject = input.nextLine().trim();
			System.out.print("Mensagem: ");
			msg = input.nextLine().trim();
			try {
				system.sendMail(userName, to, subject, msg);
			} catch (MainSystemException e) {
				System.out.println(e.getMessage());
			} catch (MessageControllerException e) {
				System.out.println(e.getMessage());
			}
			break;
			case(2):
				System.out.print("Enviar SMS para (nome do usuario): ");
			while(true){
				to = input.nextLine().trim();
				if(!to.equals("")) break;
			}
			System.out.print("Mensagem: ");
			msg = input.nextLine().trim();
			try {
				system.sendSMS(userName, to, msg);
			} catch (MainSystemException e) {
				System.out.println(e.getMessage());
			} catch (MessageControllerException e) {
				System.out.println(e.getMessage());
			}
			break;
			case(3):
				return;
			default:
				System.out.println("Opcao Invalida!");
			}
		}

	}

	private void adicionarAmigos() {
		System.out.print(SEPARATOR + "");
	}

	private void editarCompartilhamento() {
		String userNameParaEscolher;
		String menu = "";
		try{
			menu += SEPARATOR + this.system.getFriendsList(userName);
		} catch (MainSystemException e) {
			//			System.out.println("entrei");
		}
		menu += "Selecione um amigo: ";
		System.out.print(menu);
		userNameParaEscolher = this.input.nextLine().trim();
		System.out.println("Opcao de compartilhamento: " + SEPARATOR + "1. Ocultar" + SEPARATOR + "2. Exibir" + "Opcao: ");
		int opcao = this.getOption(this.input.nextLine().trim());

		while(opcao != -1){
			opcao = this.getOption(this.input.nextLine().trim());
		}

		try{
			this.system.setSharing(this.userName, userNameParaEscolher, opcao);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			editarCompartilhamento();
		}
	}

	private void deletar() {
		while(true){
			System.out.print("Deseja realmente deletar a conta?(S/N) ");
			String escolha = input.nextLine().trim();
			if(escolha.equalsIgnoreCase("s")){
				try {
					system.removeUser(userName);
					sair = true;
					return;
				} catch (MainSystemException e) {
					System.out.println(e.getMessage());
				} catch (UserException e) {
					System.out.println(e.getMessage());
				} 
			} else if(escolha.equalsIgnoreCase("n")){
				return;
			} else {
				System.out.println("Opcao invalida. Digite `S` ou `N`");
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SystemSecondMenu s = new SystemSecondMenu(sc, "dasdasd");
		s.mainLoop();
	}
}
