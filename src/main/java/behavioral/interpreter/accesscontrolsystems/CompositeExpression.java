package behavioral.interpreter.accesscontrolsystems;

import java.util.List;

/**
 * Abstract base class for composite expressions that combine multiple access expressions.
 */
public abstract class CompositeExpression implements AccessExpression {
    protected final List<AccessExpression> expressions;
    protected final String operatorName;
    
    protected CompositeExpression(List<AccessExpression> expressions, String operatorName) {
        this.expressions = List.copyOf(expressions);
        this.operatorName = operatorName;
    }
    
    @Override
    public String getExpressionDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < expressions.size(); i++) {
            if (i > 0) {
                sb.append(" ").append(operatorName).append(" ");
            }
            sb.append(expressions.get(i).getExpressionDescription());
        }
        sb.append(")");
        return sb.toString();
    }
    
    protected String formatReason(String operation, List<AccessResult> results) {
        StringBuilder sb = new StringBuilder();
        sb.append(operation).append(" evaluation: ");
        for (int i = 0; i < results.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(results.get(i).decision());
        }
        return sb.toString();
    }
}