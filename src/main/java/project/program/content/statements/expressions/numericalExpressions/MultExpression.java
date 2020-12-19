package project.program.content.statements.expressions.numericalExpressions;


public class MultExpression extends NumericalExpression {

    private final NumericalExpression leftOperand;
    private final NumericalExpression rightOperand;

    public MultExpression(NumericalExpression leftOperand, NumericalExpression rightOperand) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    public NumericalExpression getLeftOperand() {
        return leftOperand;
    }

    public NumericalExpression getRightOperand() {
        return rightOperand;
    }
}
