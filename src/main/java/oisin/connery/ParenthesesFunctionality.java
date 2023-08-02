package oisin.connery;

import lombok.Getter;
import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.structures.ExpressionAndIndex;

@Getter
public class ParenthesesFunctionality {
    public static final char OPENING_SYMBOL;
    public static final char CLOSING_SYMBOL;

    static{
        OPENING_SYMBOL = '(';
        CLOSING_SYMBOL =')';
    }

    public static ExpressionAndIndex extractSubExpression(String expression, int closingSymbolIndex){
        int openingSymbolIndex = findOpeningSymbolIndex(expression, closingSymbolIndex);
        String parenthesesExpression = expression.substring(openingSymbolIndex+1, closingSymbolIndex);
        return new ExpressionAndIndex(parenthesesExpression, openingSymbolIndex);
    }

    private static int findOpeningSymbolIndex(String expression, int closingSymbolIndex){
        for (int i=closingSymbolIndex; i>=0; i--){
            if (expression.charAt(i) == OPENING_SYMBOL)
                return i;
        }
        throw new ExpressionFormatException(ExceptionMessages.closingBracketHasNoOpeningBracket(closingSymbolIndex));
    }
}
