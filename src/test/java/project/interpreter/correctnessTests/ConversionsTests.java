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

public class ConversionsTests {

    private String path = "fileSources/conversions/";

    @Test
    void shouldInterpretConversions() throws IOException, URISyntaxException {
        String srcFile = "shouldInterpretConversions";
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
        assertEquals(0.0, Double.parseDouble(checker.nextLine()));
        assertEquals(0, Integer.parseInt(checker.nextLine()));

        assertEquals("1", checker.nextLine());
        assertEquals(1, Integer.parseInt(checker.nextLine()));

        assertEquals("1", checker.nextLine());

        assertEquals(1.1, Double.parseDouble(checker.nextLine()));
        assertEquals("1.1", checker.nextLine());
        assertEquals(1.1, Double.parseDouble(checker.nextLine()));

        assertEquals(0, Integer.parseInt(checker.nextLine()));
    }

}
