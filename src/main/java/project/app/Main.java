package project.app;

import project.lexer.Lexer;
import project.source.Source;
import project.source.StringSource;
import project.token.Token;

public class Main {

	public static void main(String[] args) {

		Source source = new StringSource("int a = 1; if (a < 5) { return 0;}");
		Lexer lexer = new Lexer(source);

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ID) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ASSIGN) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.SEMICOLON) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.IF) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.L_PARENTH) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.ID) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.LT) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.R_PARENTH) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.L_BRACE) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.RETURN) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.INT_NUMBER) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.SEMICOLON) {
			System.out.println("error");
			return;
		}

		lexer.nextToken();
		if (lexer.getToken().getType() != Token.TokenType.R_BRACE) {
			System.out.println("error");
			return;
		}

		System.out.println("Lexer is OK");
	}
}
