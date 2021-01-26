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

public class UnknownIdentifiers {

    private String path = "fileSources/errors/unknownIdentifiers/";

    @Test
    void shouldInterpretUnknownFuncName() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretUnknownFuncName";
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
            assertEquals("Unknown name of function", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(4, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretUnknownStructName() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretUnknownStructName";
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
            assertEquals("Unknown type of struct in declaration", semanticError.getDesc());
            assertEquals(4, semanticError.getLineNr());
            assertEquals(4, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretUnknownStructNameInsideDefinitionOfAnotherStruct() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretUnknownStructNameInsideDefinitionOfAnotherStruct";
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
            assertEquals("Unknown type of field in struct definition", semanticError.getDesc());
            assertEquals(2, semanticError.getLineNr());
            assertEquals(4, semanticError.getPositionAtLine());
        }
    }
}
