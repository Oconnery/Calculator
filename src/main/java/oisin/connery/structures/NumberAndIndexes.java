package oisin.connery.structures;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class NumberAndIndexes { // make valueAndIndexes and make value generic?
    private BigDecimal number; // todo: make generic number?
    private Integer startingIndex;
    private Integer endingIndex;
}
