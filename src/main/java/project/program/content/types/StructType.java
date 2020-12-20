package project.program.content.types;

public class StructType extends NonVoidType {

    private final String name;

    public StructType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
