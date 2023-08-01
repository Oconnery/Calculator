package oisin.connery;

import lombok.Getter;
import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.structures.ExpressionAndIndex;

@Getter
public class Parentheses {
    private final char openingSymbol;
    private final char closingSymbol;

    public Parentheses(){
        openingSymbol = '(';
        closingSymbol =')';
    }

    public ExpressionAndIndex extractSubExpressionFromExpression(String expression, int closingSymbolIndex){ // bad name?
        int openingSymbolIndex = findOpeningSymbolIndex(expression, closingSymbolIndex);
        String parenthesesExpression = expression.substring(openingSymbolIndex+1, closingSymbolIndex);
        return new ExpressionAndIndex(parenthesesExpression, openingSymbolIndex);
    }

    private int findOpeningSymbolIndex(String expression, int closingSymbolIndex){
        for (int i=closingSymbolIndex; i>=0; i--){
            if (expression.charAt(i) == openingSymbol)
                return i;
        }
        throw new ExpressionFormatException(ExceptionMessages.closingBracketHasNoOpeningBracket(closingSymbolIndex));
    }
}
