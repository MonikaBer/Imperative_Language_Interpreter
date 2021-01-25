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

public class SimpleFuncTests {

    @Test
    void shouldInterpretSimpleFunc() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretSimpleFunc";
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

//        Scanner checker = new Scanner(writer.toString());
//        assertEquals(34, Integer.parseInt(checker.nextLine()));
//        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

    @Test
    void shouldInterpretSimpleFunc2() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretSimpleFunc2";
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

//        Scanner checker = new Scanner(writer.toString());
//        assertEquals(34, Integer.parseInt(checker.nextLine()));
//        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }
}
