package project.interpreter;

import org.junit.jupiter.api.Test;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.FileSource;
import project.source.Source;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class RecursionTests {

    @Test
    void shouldInterpretSimpleFunc() {
        String fileName = "shouldInterpretSimpleFunc";
        Source source;
        try {
            source = new FileSource("./src/test/java/project/interpreter/fileSources/" + fileName);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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

    @Test
    void shouldInterpretSimpleFunc2() {
        String fileName = "shouldInterpretSimpleFunc2";
        Source source = null;
        try {
            source = new FileSource("./src/test/java/project/interpreter/fileSources/" + fileName);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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

    @Test
    void shouldInterpretNestedStruct() {
        String fileName = "shouldInterpretNestedStruct";
        Source source = null;
        try {
            source = new FileSource("./src/test/java/project/interpreter/fileSources/" + fileName);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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

    @Test
    void shouldInterpretFibonacci() {
        String fileName = "shouldInterpretFibonacci";
        Source source = null;
        try {
            source = new FileSource("./src/test/java/project/interpreter/fileSources/" + fileName);
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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
}
