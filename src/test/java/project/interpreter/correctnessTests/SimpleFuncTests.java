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

public class SimpleFuncTests {

    @Test
    void shouldInterpretPrintIntFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintIntFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintDoubleFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintDoubleFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals(1.0, Double.parseDouble(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintTrueFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintTrueFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintFalseFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintFalseFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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

        System.out.println(writer.toString());

        Scanner checker = new Scanner(writer.toString());
        assertEquals(-1, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintStrFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintStrFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals("Ala ma kota", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintStructFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintStructFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals("Andrzej", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintIntResultFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintIntResultFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintDoubleResultFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintDoubleResultFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals(1.0, Double.parseDouble(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintTrueResultFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintTrueResultFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintFalseResultFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintFalseResultFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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

        System.out.println(writer.toString());

        Scanner checker = new Scanner(writer.toString());
        assertEquals(-1, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintStrResultFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintStrResultFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals("Ala ma kota", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretPrintStructResultFromFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintStructResultFromFunc";
        URL res = getClass().getClassLoader().getResource("fileSources/" + srcFile);
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
        assertEquals("Andrzej", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }
}
