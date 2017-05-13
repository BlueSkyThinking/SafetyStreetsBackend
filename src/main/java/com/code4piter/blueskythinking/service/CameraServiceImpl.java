package com.code4piter.blueskythinking.service;

import com.code4piter.blueskythinking.entity.*;
import com.code4piter.blueskythinking.repository.CameraRepository;
import com.code4piter.blueskythinking.utils.GeoTools;
import com.code4piter.blueskythinking.utils.VideoStremer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        CameraDetailInfo detailInfo = new CameraDetailInfo(camera.getName(), camera.getAddress(), camera.getVideoAndroid(), 80.0, null);
        return detailInfo;
    }

    @Override
    public List<CameraForMap> getAll() {
        getImage();
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

    public void getImage() {
        String cam7 = "http://46.243.177.178:2222/BXKFE3Z6RJJTT4PAYI3XC5TBQLCKJNILO57VVEVVCSNXM6EQ5ZS3673MYGHOGV6OO5EWYFMR7B4BRNLPRJ4NSHPZ77X4MRGRX5GINGZIYXGLS5ZR6EHMTS4NU2JZZEMOV6ZHKEBTN26BFE4PJXZN375T3IXFYYYTWASAXBAZQ35EPIH3AJFBXLWA736QBFRLHIMWEDOYMJIBG6QQQUBQDVOTDWLA7FIGOAUC2YEYGLMXQWZ3XS4XLMBM4NP22S62YVOSHKV2Q52YIIO5P46DLGAH2372T4TPNO5X43BNOWOJ7GSC7W2MU4TZSDNUKK4RKX5WYI5LQ2FRJ552NIRT272IZGPYHDWBNNOEUJQ/bb1f1e1d3328dfd25003e1c0fceb0eda-public";

        String fileName = "1.png";
        if (!VideoStremer.saveImage(cam7, fileName))
            System.out.println("Erorr! File don't save");
        System.out.println("File " + fileName + " created");
    }
}
