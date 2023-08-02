package oisin.connery.structures;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class NumberAndIndexes {
    private BigDecimal number;
    private Integer startingIndex;
    private Integer endingIndex;
}
