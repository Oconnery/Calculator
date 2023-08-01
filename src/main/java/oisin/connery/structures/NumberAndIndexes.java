package oisin.connery.structures;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class NumberAndIndexes { // make valueAndIndexes and make value generic?
    private BigDecimal number; // todo: make generic number?
    private Integer startingIndex; // Index of what?
    private Integer endingIndex; // these are the starting and ending indexes of the number, so should be ok. //todo: ending Index is actually ending index + 1. This isn't clear
}
