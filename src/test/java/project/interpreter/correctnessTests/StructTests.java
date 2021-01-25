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

public class StructTests {

    private String path = "fileSources/struct/";

    @Test
    void shouldInterpretNestedStruct() throws URISyntaxException {
        String srcFile = "shouldInterpretNestedStruct";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        try {
            assert res != null;
            source = new FileSource(Paths.get(res.toURI()).toString());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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
        assertEquals("\"Student:", checker.nextLine());
        assertEquals("Jan Kowalski,", checker.nextLine());
        assertEquals("Wiek: 22", checker.nextLine());
        assertEquals("'Adres: Spokojna 1, Lublin", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretNestedStructDefaultValues() throws URISyntaxException {
        String srcFile = "shouldInterpretNestedStructDefaultValues";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        try {
            assert res != null;
            source = new FileSource(Paths.get(res.toURI()).toString());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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
        assertEquals("\"Student:", checker.nextLine());
        assertEquals("Jan Kowalski,", checker.nextLine());
        assertEquals("Wiek: 22", checker.nextLine());
        assertEquals("'Adres: Cicha 40, Warszawa", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretNestedStructsAssignment() throws URISyntaxException {
        String srcFile = "shouldInterpretNestedStructsAssignment";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        try {
            assert res != null;
            source = new FileSource(Paths.get(res.toURI()).toString());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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
        assertEquals("\"Student:", checker.nextLine());
        assertEquals("Jan Kowalski,", checker.nextLine());
        assertEquals("Wiek: 19", checker.nextLine());
        assertEquals("'Adres: Topolowa 40, Warszawa", checker.nextLine());

        assertEquals("\"Student:", checker.nextLine());
        assertEquals("Jan Kowalski,", checker.nextLine());
        assertEquals("Wiek: 22", checker.nextLine());
        assertEquals("'Adres: Cicha 40, Warszawa", checker.nextLine());

        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretNestedStructsAssignment2() throws URISyntaxException {
        String srcFile = "shouldInterpretNestedStructsAssignment2";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        try {
            assert res != null;
            source = new FileSource(Paths.get(res.toURI()).toString());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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
        assertEquals("\"Student:", checker.nextLine());
        assertEquals("Jan Kowalski,", checker.nextLine());
        assertEquals("Wiek: 22", checker.nextLine());
        assertEquals("'Adres: Topolowa 40, Warszawa", checker.nextLine());
        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretNestedStructsAssignment3() throws URISyntaxException {
        String srcFile = "shouldInterpretNestedStructsAssignment3";
        URL res = getClass().getClassLoader().getResource(path + srcFile);
        Source source;

        try {
            assert res != null;
            source = new FileSource(Paths.get(res.toURI()).toString());
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            return;
        }

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
        assertEquals("\"Student:", checker.nextLine());
        assertEquals("Jan Kowalski,", checker.nextLine());
        assertEquals("Wiek: 19", checker.nextLine());
        assertEquals("'Adres: Topolowa 40, Warszawa", checker.nextLine());

        assertEquals("\"Student:", checker.nextLine());
        assertEquals("Jan Kowalski,", checker.nextLine());
        assertEquals("Wiek: 22", checker.nextLine());
        assertEquals("'Adres: Topolowa 40, Warszawa", checker.nextLine());

        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }
}
