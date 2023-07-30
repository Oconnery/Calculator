package oisin.connery.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpressionAndIndex {
    private String expression;
    private int leftSymbolIndex; // improve names?
}
