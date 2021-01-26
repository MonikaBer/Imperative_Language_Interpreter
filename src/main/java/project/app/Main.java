package project.app;

import project.exceptions.*;
import project.interpreter.Interpreter;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.FileSource;
import project.source.Source;

import java.io.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Usage: java -jar target/project-1.0.0.jar <path_to_file>");
			return;
		}

		try {
			Source source = new FileSource(args[0]);
			Lexer lexer = new Lexer(source);
			Parser parser = new Parser(lexer);
			Program program = parser.parseProgram();

			Writer writer = new OutputStreamWriter(System.out);
			Scanner reader = new Scanner(System.in);
			Writer errWriter = new OutputStreamWriter(System.err);

			Interpreter interpreter = new Interpreter(program, writer, reader, errWriter);
			interpreter.execute();
			interpreter.start();
		}
		catch (IOException ex) {
			System.err.println(ex.getMessage());
			return;
		}
		catch (FileSourceReadException err) {
			System.err.println(err.getMessage());
			System.err.println("Interpreter finished with exit code: " + err.getRetCode());
			return;
		}
		catch (SyntaxError err) {
			System.err.println(err.getMessage());
			System.err.println("Interpreter finished with exit code: " + err.getRetCode());
			return;
		}
		catch (SemanticError err) {
			System.err.println(err.getMessage());
			System.err.println("Interpreter finished with exit code: " + err.getRetCode());
			return;
		}
		catch (InterpreterError err) {
			System.err.println(err.getMessage());
			System.err.println("Interpreter finished with exit code: " + err.getRetCode());
			return;
		}
		catch (PanicError err) {
			System.err.println(err.getMessage());
			System.err.println("Interpreter finished with exit code: " + err.getRetCode());
			return;
		}

		System.out.println("Interpreter finished with exit code: 0");
	}
}
