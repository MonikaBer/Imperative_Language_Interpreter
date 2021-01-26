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

public class IncorrectnessTypesTests {

    private String path = "fileSources/errors/incorrectnessOfTypes/";

    @Test
    void shouldInterpretTrialOfStringsSubtraction() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretTrialOfStringsSubtraction";
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
            assertEquals("Left operand of subtract expression is string - it is prohibited", semanticError.getDesc());
            assertEquals(6, semanticError.getLineNr());
            assertEquals(18, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretBoolExpressions() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretBoolExpressions";
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
            assertEquals("Left operand of alternative isn't bool", semanticError.getDesc());
            assertEquals(4, semanticError.getLineNr());
            assertEquals(15, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretBoolExpressions2() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretBoolExpressions2";
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
            assertEquals("Right operand of greater expression is bool - it is prohibited", semanticError.getDesc());
            assertEquals(6, semanticError.getLineNr());
            assertEquals(22, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretAssignmentsErr() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretAssignmentsErr";
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
            assertEquals("Type required by initialisation of var is int, but value isn't int", semanticError.getDesc());
            assertEquals(4, semanticError.getLineNr());
            assertEquals(12, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretReturnFromVoidFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretReturnFromVoidFunc";
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
            assertEquals("Void function 'func' returned value", semanticError.getDesc());
            assertEquals(8, semanticError.getLineNr());
            assertEquals(4, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretVoidReturnFromNonVoidFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretVoidReturnFromNonVoidFunc";
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
            assertEquals("Non void function 'func' returned nothing", semanticError.getDesc());
            assertEquals(8, semanticError.getLineNr());
            assertEquals(12, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretWrongReturnFromNonVoidFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretWrongReturnFromNonVoidFunc";
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
            assertEquals("Type required by returned value from func call is int, but value isn't int", semanticError.getDesc());
            assertEquals(8, semanticError.getLineNr());
            assertEquals(15, semanticError.getPositionAtLine());
        }
    }
}
