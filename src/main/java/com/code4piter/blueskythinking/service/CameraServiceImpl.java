package com.code4piter.blueskythinking.service;

import com.code4piter.blueskythinking.entity.*;
import com.code4piter.blueskythinking.repository.CameraRepository;
import com.code4piter.blueskythinking.utils.GeoTools;
import com.code4piter.blueskythinking.utils.GoogleVision;
import com.code4piter.blueskythinking.utils.VideoStremer;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CameraServiceImpl implements CameraService{

    @Autowired
    private CameraRepository repository;

    @Override
    public CameraDetailInfo getCameraDetailInfo(long id) {
        Camera camera = repository.findOne(id);
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] imageBytes = getImage(camera.getVideoFlv());
        String imageString = encoder.encode(imageBytes);
        CameraDetailInfo detailInfo = new CameraDetailInfo(camera.getName(),
                camera.getAddress(),
                camera.getVideoAndroid(),
                80.0,
                imageString);
        return detailInfo;
    }

    @Override
    public List<CameraForMap> getAll() {
        List<Camera> cameras = repository.findAll();
        List<CameraForMap> cameraForMap = new ArrayList<>();
        for (Camera camera : cameras) {
            CameraForMap e = new CameraForMap(camera.getId(), camera.getLatitude(), camera.getLongitude());
            cameraForMap.add(e);
        }
        return cameraForMap;
    }

    @Override
    public List<Camera> search(Filter filter) {
        if (filter.getSearch() != null)
            return repository.findAll()
                    .stream()
                    .filter(e -> (e.getName()).equals(filter.getSearch()))
                    .collect(Collectors.toList());
        return null;
    }

    @Override
    public List<NearCamera> getNearCameras(double latitude, double longitude) {
        List<Camera> cameras = repository.findAll();
        List<NearCamera> nearCamera = new ArrayList<>();
        for (Camera camera : cameras) {
            if (camera.getLatitude() != latitude && camera.getLongitude() != longitude)
                if (GeoTools.distination(latitude, longitude, camera.getLatitude(), camera.getLongitude()) <= 20000) {
                    NearCamera e = new NearCamera(camera.getId(), camera.getName());
                    nearCamera.add(e);
                }
        }
        return nearCamera;
    }

    public byte[] getImage(String url) {
        return VideoStremer.saveImage(url);
    }

    public void getStats() {
        String cam5 = "http://46.243.177.176:2222/XUN75U4HQKN6ZVXN74VKW5LMF2FOOKPP7KJC2U6VF5AGRFRRTFSASLDHHJRLUDPD2XD44UQR3FTMJG7Q2ICTW5ICRKBFXNDQSEOSJCZIYXGLS5ZR6EHMTS4NU2JZZEMOV6ZHKEBTN26BFE4PJXZN375T3LJX3TEZCWS3YKTVKPIYB62NHAURXLWA736QBFRLHIMWEDOYMJIBG6QQQUBQDVOTDWLA7FIGOAUC2YEYGLMXQWZ3XS4XLMBM4NP22S62YVOSHKV2Q52YIIO5P46DLGAH2372T4TPNO5X43BNOWOJ7GSC7W2MU4TZSDNUKK4RKX5WYI5LQ2FRJ552NIRT272IZGPYHDWBNNOEUJQ/4cb262910fd4c6cd51f64ea7163194cc-public";

        String fileName = "D:/images/1.jpg";
        List<AnnotateImageResponse> response = GoogleVision.getStats(fileName);

        for (AnnotateImageResponse res : response) {
            if (res.hasError()) {
                System.out.printf("Error: %s\n", res.getError().getMessage());
                return;
            }

            for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                annotation.getAllFields().forEach((k, v)->System.out.printf("%s : %s\n", k, v.toString()));
            }
        }
    }
}
