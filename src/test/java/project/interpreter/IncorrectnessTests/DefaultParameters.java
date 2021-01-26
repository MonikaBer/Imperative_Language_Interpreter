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

public class DefaultParameters {

    private String path = "fileSources/errors/defaultParameters/";

    @Test
    void shouldInterpretTooLittleParameters() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretTooLittleParameters";
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

        try {
            interpreter.execute();
            interpreter.start();
        } catch (SemanticError semanticError) {
            assertEquals("Incorrect order, number or type of params in func call", semanticError.getDesc());
            assertEquals(8, semanticError.getLineNr());
            assertEquals(4, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretWrongTypesOfParameters() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretWrongTypesOfParameters";
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

        try {
            interpreter.execute();
            interpreter.start();
        } catch (SemanticError semanticError) {
            assertEquals("Type required by param of func call is double, but value isn't double", semanticError.getDesc());
            assertEquals(8, semanticError.getLineNr());
            assertEquals(11, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretTooMuchParameters() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretTooMuchParameters";
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

        try {
            interpreter.execute();
            interpreter.start();
        } catch (SemanticError semanticError) {
            assertEquals("Too much params in func call - 2 params are permitted", semanticError.getDesc());
            assertEquals(8, semanticError.getLineNr());
            assertEquals(13, semanticError.getPositionAtLine());
        }
    }
}
