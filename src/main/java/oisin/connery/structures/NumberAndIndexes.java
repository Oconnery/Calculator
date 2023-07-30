package oisin.connery.structures;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NumberAndIndexes { // make valueAndIndexes and make value generic?
    private int number;
    private Integer startingIndex;
    private Integer endingIndex;
}
