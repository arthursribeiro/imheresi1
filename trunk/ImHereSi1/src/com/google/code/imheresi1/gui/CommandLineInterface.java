package com.google.code.imheresi1.gui;

import java.util.Scanner;

public class CommandLineInterface {

	static Scanner input;
	static final String SEPARATOR = System.getProperty("line.separator");
	static final String OPT = "Choose your option: " + SEPARATOR + "1. Login"
			+ SEPARATOR + "2. hIHI" + SEPARATOR + "3. Exit";
	static final int LOG = 1;
	static final int SEILAH = 2;
	static final int SAIR = 3;

	public static void main(String[] args) {
		input = new Scanner(System.in);
		initialMenu();
	}

	static void initialMenu() {
		System.out.println(OPT);
		int opt = input.nextInt();
		while (opt != SAIR) {
			switch (opt) {
			case LOG:
				System.out.println("oi");
				break;
			case SEILAH:
				System.out.println("danou-se");
				break;
			default:
				System.out.println("oxe");
				break;
			}
			System.out.println(OPT);
			opt = input.nextInt();
		}
	}

	static void laerteHelpMe() {

	}
}