package oisin.connery.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExpressionAndIndex {
    private String expression; //todo: should this just be generic and moved into other class?
    private int leftSymbolIndex; // improve names?
}
