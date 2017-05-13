package com.code4piter.blueskythinking.service;

import com.code4piter.blueskythinking.entity.*;

import java.util.List;

public interface CameraService {

    CameraDetailInfo getCameraDetailInfo(long id);
    List<CameraForMap> getAll();
    List<CameraListElement> search(Filter filter);
    List<NearCamera> getNearCameras(double latitude, double longitude);
}
