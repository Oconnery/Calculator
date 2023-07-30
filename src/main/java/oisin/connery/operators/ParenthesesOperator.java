package oisin.connery.operators;

import lombok.Getter;
import oisin.connery.structures.ExpressionAndIndex;

@Getter
public class ParenthesesOperator {
    private final char openingSymbol;
    private final char closingSymbol;

    public ParenthesesOperator(){
        openingSymbol = '(';
        closingSymbol =')';
    }

    public ExpressionAndIndex evaluateSingleParentheses(String expression, int closingSymbolIndex){
        int openingSymbolIndex = findOpeningSymbolIndex(expression, closingSymbolIndex);
        String parenthesesExpression = expression.substring(openingSymbolIndex+1, closingSymbolIndex);
        return new ExpressionAndIndex(parenthesesExpression, openingSymbolIndex);
    }

    // separate into method with getLeftNumber and getRightNumber? Will save performance and mem and ++ abstraction.
    private int findOpeningSymbolIndex(String expression, int closingSymbolIndex){
        for (int i=closingSymbolIndex; i>=0; i--){
            if (expression.charAt(i) == openingSymbol)
                return i;
        }
        // todo: throw error because there is no '(' for this ')'
        return -1;
    }
}
