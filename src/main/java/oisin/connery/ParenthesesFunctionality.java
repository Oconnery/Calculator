package oisin.connery;

import lombok.Getter;
import oisin.connery.exceptions.ExceptionMessages;
import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.structures.ExpressionAndIndex;
import oisin.connery.symbols.DualSymbols;
import oisin.connery.symbols.SymbolType;

@Getter
public class ParenthesesFunctionality extends DualSymbols {

    public ParenthesesFunctionality() {
        super(SymbolType.LEFT_PARENTHESES.symbol, SymbolType.LEFT_PARENTHESES, SymbolType.RIGHT_PARENTHESES.symbol, SymbolType.RIGHT_PARENTHESES);
    }

    public ExpressionAndIndex extractSubExpression(String expression, int closingSymbolIndex){
        int openingSymbolIndex = findOpeningSymbolIndex(expression, closingSymbolIndex);
        String parenthesesExpression = expression.substring(openingSymbolIndex+1, closingSymbolIndex);
        return new ExpressionAndIndex(parenthesesExpression, openingSymbolIndex);
    }

    private int findOpeningSymbolIndex(String expression, int closingSymbolIndex){
        for (int i=closingSymbolIndex; i>=0; i--){
            if (expression.charAt(i) == firstSymbol)
                return i;
        }
        throw new ExpressionFormatException(ExceptionMessages.closingBracketHasNoOpeningBracket(closingSymbolIndex));
    }
}
