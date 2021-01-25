package project.interpreter.correctnessTests;

import org.junit.jupiter.api.Test;
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

public class VisibilityTests {

    private String path = "fileSources/visibility/";

    @Test
    void shouldInterpretVisibilityBetweenBlocks1() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVisibilityBetweenBlocks1";
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
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretVisibilityBetweenBlocks2() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVisibilityBetweenBlocks2";
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
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretVisibilityBetweenBlocks3() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVisibilityBetweenBlocks3";
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
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretVisibilityBetweenFunctionCalls() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVisibilityBetweenFunctionCalls";
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
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretVisibilityGlobals() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVisibilityGlobals";
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
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(1, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretVisibilityInWhile() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVisibilityInWhile";
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
        assertEquals(0, Integer.parseInt(checker.nextLine()));
        assertEquals(5.5, Double.parseDouble(checker.nextLine()));
        assertEquals(5.5, Double.parseDouble(checker.nextLine()));
        assertEquals(2, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretVisibilityInWhile2() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVisibilityInWhile2";
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
        assertEquals(0, Integer.parseInt(checker.nextLine()));
        assertEquals(5.5, Double.parseDouble(checker.nextLine()));
        assertEquals(5.5, Double.parseDouble(checker.nextLine()));
        assertEquals(7, Integer.parseInt(checker.nextLine()));
        assertEquals(2, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }
}
