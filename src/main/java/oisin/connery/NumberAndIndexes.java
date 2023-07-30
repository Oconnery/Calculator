package oisin.connery;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NumberAndIndexes {
    private int number;
    private Integer startingIndex;
    private Integer endingIndex;
}
