package project.interpreter.IncorrectnessTests;

import org.junit.jupiter.api.Test;
import project.exceptions.SemanticError;
import project.interpreter.Interpreter;
import project.lexer.Lexer;
import project.parser.Parser;
import project.program.Program;
import project.source.FileSource;
import project.source.Source;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Expressions {

    private String path = "fileSources/expressions/";

    @Test
    void shouldInterpretExpressions() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretExpressions";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();

        Interpreter interpreter = new Interpreter(program, writer,
                new Scanner(new InputStreamReader(InputStream.nullInputStream())),
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        interpreter.execute();
        interpreter.start();

        Scanner checker = new Scanner(writer.toString());
        assertEquals(14, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));

        //------------------------------------------------------------------

        assertEquals(12, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));

        //------------------------------------------------------------------

        assertEquals(3, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));

        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretExpressions2() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretExpressions2";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();

        Interpreter interpreter = new Interpreter(program, writer,
                new Scanner(new InputStreamReader(InputStream.nullInputStream())),
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        interpreter.execute();
        interpreter.start();

        Scanner checker = new Scanner(writer.toString());
        assertEquals(-6, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));

        assertEquals(-4, Integer.parseInt(checker.nextLine()));

        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }
}
