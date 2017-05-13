package com.code4piter.blueskythinking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CameraListElement {

    private Long id;
    private String name;
    private String address;
    private Double latitude;
    private Double longitude;
}
