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

public class WrongConversions {

    private String path = "fileSources/errors/wrongConversions/";

    @Test
    void shouldInterpretWrongIntToDoubleConversion() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretNotIntToDoubleConversion";
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
            assertEquals("Incorrect type of param in convertIntToDouble() call - int is required", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(15, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretWrongDoubleToIntConversion() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretNotDoubleToIntConversion";
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
            assertEquals("Incorrect type of param in convertDoubleToInt() call - double is required", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(12, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretWrongIntToStrConversion() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretNotIntToStrConversion";
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
            assertEquals("Incorrect type of param in convertIntToStr() call - int is required", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(15, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretWrongDoubleToStrConversion() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretNotDoubleToStrConversion";
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
            assertEquals("Incorrect type of param in convertDoubleToStr() call - double is required", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(15, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretWrongStrToIntConversion() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretNotStrToIntConversion";
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
            assertEquals("Incorrect type of param in convertStrToInt() call - string is required", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(12, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretWrongStrToDoubleConversion() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretNotStrToDoubleConversion";
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
            assertEquals("Incorrect type of param in convertStrToDouble() call - string is required", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(15, semanticError.getPositionAtLine());
        }
    }
}
