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
	SEPARATOR + "6. Enviar mensagem" + SEPARATOR + "7. Aceitar/Recusar Convites" + SEPARATOR + "8. Logout";

	private static final int ATUALIZAR = 1;
	private static final int DELETAR = 2;
	private static final int EDITAR_COMPARTILHAMENTO = 3;
	private static final int ADICIONAR_AMIGOS = 4;
	private static final int RECUPERAR_LOCALIZACAO = 5;
	private static final int ENVIAR = 6;
	private static final int ACC_REC_COMPARTILHAMENTO = 7;
	private static final int LOGOUT = 8;
	private static final String PROMPT_2 = "Opcao: ";

	public SystemSecondMenu(MainSystem mySystem,Scanner input, String userName){
		system = mySystem;
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
				recuperarLocalizacao();
				break;
			case ENVIAR:
				enviar();
				break;
			case ACC_REC_COMPARTILHAMENTO:
				accRecCompartilhamento();
				break;
			default:
				System.out.println(SEPARATOR + "Opcao invalida!");
				break;
			}

			System.out.print(SEPARATOR + PROMPT_1 + SEPARATOR + PROMPT_2);
			option = getOption(input.nextLine());
		}
	}

	private void accRecCompartilhamento() {
		String prompt = system.toStringMyInvitations(this.userName);
		if(prompt.equals("")) {
			System.out.println(SEPARATOR + "Nenhum convite!" + SEPARATOR);
			return;
		}
		System.out.println(prompt);
		String uName = "";
		while(true){
			System.out.print("Digite o username: ");
			uName = input.nextLine().trim();
			if(!uName.equals("")) break;
		}
		System.out.println("Deseja:" + SEPARATOR + "1. Recusar" + SEPARATOR + "2. Aceitar");
		while(true){
			System.out.print("Opcao: ");
			String opt = input.nextLine();
			int option = getOption(opt);
			if(option == 1){
				try {
					system.refuseSharing(userName, uName);
				} catch (MainSystemException e) {
					System.out.println(e.getMessage());
				}
				return;
			} else if(option == 2){
				while(true){
					System.out.println("Modo de Compartilhamento:");
					System.out.println("1. Oculto");
					System.out.println("2. Visivel");
					System.out.print("Opcao: ");
					String opt2 = input.nextLine();
					int option2 = getOption(opt2);
					if(option2 == 2 || option2 == 1){
						try {
							system.confirmSharing(userName, uName,option2);
						} catch (MainSystemException e) {
							System.out.println(e.getMessage());
						} catch (UserException e) {
							System.out.println(e.getMessage());
						}
						return;
					} else {
						System.out.println("Opcao Invalida!");
					}
				}
			} else {
				System.out.println("Opcao Invalida!");
			}

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
		System.out.print(SEPARATOR + "<<< Adicionar um amigo! >>>" + SEPARATOR
				+ "Digite o email: ");
		String email = this.input.nextLine().trim();
		
		while((email.equals("")) || (email == null)){
			System.out.print("Email invalido: ");
			email = this.input.nextLine().trim();
		}
		
		try {
			this.system.sendInvitation(this.userName, email);
		} catch (MainSystemException e) {
			// Do nothing
		} catch (MessageControllerException e) {
			System.out.println(e.getMessage());
			adicionarAmigos();
		}

	}

	private void recuperarLocalizacao() {
		String userNameParaLocalizar;
		String menu = "";
		try {
			menu = this.system.getFriendsList(userName);
            if(menu.equals("")) {
                System.out.println(SEPARATOR + "Nenhum Amigo" + SEPARATOR);
                return;
            }
		} catch (MainSystemException e) {
			//Do Nothing
		}
		menu += "Selecione um amigo: ";
		System.out.print(menu);
		userNameParaLocalizar = this.input.nextLine().trim();
		
		try {
			String localizacao = this.system.getAFriendPosition(this.userName,
					userNameParaLocalizar);
			System.out.println("Posicao de " + userNameParaLocalizar + " >>> "
					+ localizacao);
		} catch (MainSystemException e) {
			System.out.println(e.getMessage());
			recuperarLocalizacao();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void editarCompartilhamento() {
        String userNameParaEscolher;
        String menu = "";
        try{
            menu = this.system.getFriendsList(userName);
            if(menu.equals("")) {
                System.out.println(SEPARATOR + "Nenhum Amigo" + SEPARATOR);
                return;
            }
        } catch (MainSystemException e) {
            //            System.out.println("entrei");
        }
        menu += "Selecione um amigo: ";
        System.out.print(menu);
        userNameParaEscolher = this.input.nextLine().trim();
        
        System.out.println("Opcao de compartilhamento: " + SEPARATOR + "1. Ocultar" + SEPARATOR + "2. Exibir");
        System.out.print(SEPARATOR + "Opcao: ");
        int opcao = this.getOption(this.input.nextLine().trim());

        while(opcao == -1){
            System.out.println(SEPARATOR + "Opcao: ");
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
					try {
						system.removeUser(this.userName);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
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

}
