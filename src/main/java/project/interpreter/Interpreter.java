package project.interpreter;

import project.program.Program;

public class Interpreter {

    private Program program;
    private Environment env;

    public Interpreter(Program program, Environment env) {
        this.program = program;
        this.env = env;
    }

    public Environment getEnv() {
        return env;
    }
}
