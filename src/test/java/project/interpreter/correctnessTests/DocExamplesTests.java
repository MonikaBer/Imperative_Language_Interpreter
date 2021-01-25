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
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DocExamplesTests {

    private String path = "fileSources/docExamples/";

    @Test
    void shouldInterpretPrintHelloWorld() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretPrintHelloWorld";
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
        assertEquals("Hello world!", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretStringsConcatenation() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretStringsConcatenation";
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
        assertEquals("Ala ma kota", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretDefaultValuesCheck() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretDefaultValuesCheck";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        StringWriter errWriter = new StringWriter();

        Interpreter interpreter = new Interpreter(program, writer,
                new Scanner(new InputStreamReader(InputStream.nullInputStream())), errWriter);

        interpreter.execute();
        interpreter.start();

        Scanner checker1 = new Scanner(writer.toString());
        assertEquals(0, Integer.parseInt(checker1.nextLine()));

        Scanner checker2 = new Scanner(errWriter.toString());
        assertFalse(checker2.hasNextLine());
    }

    @Test
    void shouldInterpretStructs() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretStructs";
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
        assertEquals("100200 Jan Kowalski Spokojna 25 Lublin", checker.nextLine());

        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretVarsVisibility() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVarsVisibility";
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
        assertEquals(20, Integer.parseInt(checker.nextLine()));
        assertEquals(100, Integer.parseInt(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretBigTest1() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretBigTest";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        String reader = "0\n0";

        Interpreter interpreter = new Interpreter(program, writer,
                new Scanner(reader),
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        interpreter.execute();
        interpreter.start();

        Scanner checker = new Scanner(writer.toString());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
        assertEquals("Podaj liczbe od 1 do 3", checker.nextLine());
        assertEquals("Podales liczbe spoza zakresu 1-3", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretBigTest2() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretBigTest";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        String reader = "3\n1";

        Interpreter interpreter = new Interpreter(program, writer,
                new Scanner(reader),
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        interpreter.execute();
        interpreter.start();

        Scanner checker = new Scanner(writer.toString());
        assertEquals(4, Integer.parseInt(checker.nextLine()));
        assertEquals("Podaj liczbe od 1 do 3", checker.nextLine());
        assertEquals("Twoja liczba to 1", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretBigTest3() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretBigTest";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        String reader = "3\n2";

        Interpreter interpreter = new Interpreter(program, writer,
                new Scanner(reader),
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        interpreter.execute();
        interpreter.start();

        Scanner checker = new Scanner(writer.toString());
        assertEquals(4, Integer.parseInt(checker.nextLine()));
        assertEquals("Podaj liczbe od 1 do 3", checker.nextLine());
        assertEquals("Twoja liczba to 2", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretBigTest4() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretBigTest";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        assert res != null;
        source = new FileSource(Paths.get(res.toURI()).toString());

        Lexer lexer = new Lexer(source);
        Parser parser = new Parser(lexer);
        Program program = parser.parseProgram();

        StringWriter writer = new StringWriter();
        String reader = "3\n3";

        Interpreter interpreter = new Interpreter(program, writer,
                new Scanner(reader),
                new OutputStreamWriter(OutputStream.nullOutputStream()));

        interpreter.execute();
        interpreter.start();

        Scanner checker = new Scanner(writer.toString());
        assertEquals(4, Integer.parseInt(checker.nextLine()));
        assertEquals("Podaj liczbe od 1 do 3", checker.nextLine());
        assertEquals("Twoja liczba to 3", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }
}
