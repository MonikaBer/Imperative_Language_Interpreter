package project.interpreter.IncorrectnessTests;

import org.junit.jupiter.api.Test;
import project.exceptions.InterpreterError;
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

public class EmptyProgram {

    private String path = "fileSources/errors/emptyProgram/";

    @Test
    void shouldInterpretEmptyProgram() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretEmptyProgram";
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
        } catch (InterpreterError interpreterError) {
            assertEquals("Program without main!", interpreterError.getDesc());
        }
    }

    @Test
    void shouldInterpretProgramWithoutMain() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretProgramWithoutMain";
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
        } catch (InterpreterError interpreterError) {
            assertEquals("Program without main!", interpreterError.getDesc());
        }
    }

    @Test
    void shouldInterpretProgramWithWrongMain() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretProgramWithWrongMain";
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
        } catch (InterpreterError interpreterError) {
            assertEquals("Main function shouldn't have any args!", interpreterError.getDesc());
        }
    }

    @Test
    void shouldInterpretProgramWithWrongMain2() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretProgramWithWrongMain2";
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
        } catch (InterpreterError interpreterError) {
            assertEquals("Main function hasn't int as returned type", interpreterError.getDesc());
        }
    }
}
