package project.interpreter.IncorrectnessTests;

import org.junit.jupiter.api.Test;
import project.exceptions.InterpreterError;
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

public class PrintsAndReadsErrors {

    private String path = "fileSources/errors/printsAndReadsErrors/";

    @Test
    void shouldInterpretReadNotIntError() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretReadNotIntError";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        Scanner reader = new Scanner("a");

        Interpreter interpreter = new Interpreter(program, writer, reader,
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        try {
            interpreter.execute();
            interpreter.start();
        } catch (InterpreterError interpreterError) {
            assertEquals("You wrote something which isn't int!", interpreterError.getDesc());
        }
    }

    @Test
    void shouldInterpretReadNotDoubleError() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretReadNotDoubleError";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        Scanner reader = new Scanner("a");

        Interpreter interpreter = new Interpreter(program, writer, reader,
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        try {
            interpreter.execute();
            interpreter.start();
        } catch (InterpreterError interpreterError) {
            assertEquals("You wrote something which isn't double!", interpreterError.getDesc());
        }
    }

    @Test
    void shouldInterpretPrintNotIntError() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintNotIntError";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        Scanner reader = new Scanner("a");

        Interpreter interpreter = new Interpreter(program, writer, reader,
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        try {
            interpreter.execute();
            interpreter.start();
        } catch (SemanticError semanticError) {
            assertEquals("Incorrect type of param in printInt() call - int is required", semanticError.getDesc());
            assertEquals(4, semanticError.getLineNr());
            assertEquals(4, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretPrintNotDoubleError() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintNotDoubleError";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        Scanner reader = new Scanner("a");

        Interpreter interpreter = new Interpreter(program, writer, reader,
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        try {
            interpreter.execute();
            interpreter.start();
        } catch (SemanticError semanticError) {
            assertEquals("Incorrect type of param in printDouble() call - double is required", semanticError.getDesc());
            assertEquals(4, semanticError.getLineNr());
            assertEquals(4, semanticError.getPositionAtLine());
        }
    }
}
