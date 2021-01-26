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

public class RepeatedDeclarations {

    private String path = "fileSources/errors/repeatedDeclarations/";

    @Test
    void shouldInterpretRepeatedDeclaration() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretRepeatedDeclaration";
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
            assertEquals("Var 'a' already exists in locals of block context", semanticError.getDesc());
            assertEquals(5, semanticError.getLineNr());
            assertEquals(8, semanticError.getPositionAtLine());
        }
    }

    @Test
    void shouldInterpretRepeatedDeclarationOfStructVar() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretRepeatedDeclarationOfStructVar";
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
            assertEquals("Var 'student' already exists in locals of block context", semanticError.getDesc());
            assertEquals(11, semanticError.getLineNr());
            assertEquals(12, semanticError.getPositionAtLine());
        }
    }
}
