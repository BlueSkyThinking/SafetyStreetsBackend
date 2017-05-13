package com.code4piter.blueskythinking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filter {

    private String search;
    private Double distance;
    private Double dangerLevel;
    private String sortBy;
}
