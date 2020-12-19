package project.app;

import project.exceptions.FileSourceReadException;
import project.lexer.Lexer;
import project.source.FileSource;
import project.source.Source;
import project.source.StringSource;
import project.token.Token;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		try {
			fileSourceTest();
		} catch (FileSourceReadException ex) {
			System.out.println(ex.getMessage());
		}

		stringSourceTest();
	}

	private static void fileSourceTest() {
		Source source = null;
		try {
			source = new FileSource("mainTest");
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
		Lexer lexer = new Lexer(source);

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT) {
			System.out.println("error 1");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ID) {
			System.out.println("error 2");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ASSIGN) {
			System.out.println("error 3");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error 4");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.SEMICOLON) {
			System.out.println("error 5");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.IF) {
			System.out.println("error 6");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.L_PARENTH) {
			System.out.println("error 7");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ID) {
			System.out.println("error 8");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.LT) {
			System.out.println("error 9");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error 10");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.R_PARENTH) {
			System.out.println("error 11");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.L_BRACE) {
			System.out.println("error 12");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.RETURN) {
			System.out.println("error 13");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error 14");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.SEMICOLON) {
			System.out.println("error 15");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.R_BRACE) {
			System.out.println("error 16");
			return;
		}

		System.out.println("Lexer is OK");
	}

	private static void stringSourceTest() {
		Source source = new StringSource("int a = 1; if (a < 5) { return 0;}");
		Lexer lexer = new Lexer(source);

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT) {
			System.out.println("error 1");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ID) {
			System.out.println("error 2");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ASSIGN) {
			System.out.println("error 3");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error 4");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.SEMICOLON) {
			System.out.println("error 5");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.IF) {
			System.out.println("error 6");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.L_PARENTH) {
			System.out.println("error 7");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ID) {
			System.out.println("error 8");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.LT) {
			System.out.println("error 9");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error 10");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.R_PARENTH) {
			System.out.println("error 11");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.L_BRACE) {
			System.out.println("error 12");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.RETURN) {
			System.out.println("error 13");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error 14");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.SEMICOLON) {
			System.out.println("error 15");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.R_BRACE) {
			System.out.println("error 16");
			return;
		}

		System.out.println("Lexer is OK");
	}
}
