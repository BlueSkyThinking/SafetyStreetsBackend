package com.code4piter.blueskythinking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CameraDetailInfo {

    private String name;
    private String address;
    private String videoAndroid;
    private Double dangerLevel;
    private Base64 image;
}
