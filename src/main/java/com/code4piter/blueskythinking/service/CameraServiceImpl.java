package com.code4piter.blueskythinking.service;

import com.code4piter.blueskythinking.entity.*;
import com.code4piter.blueskythinking.repository.CameraRepository;
import com.code4piter.blueskythinking.repository.WordRepository;
import com.code4piter.blueskythinking.utils.GeoTools;
import com.code4piter.blueskythinking.utils.GoogleVision;
import com.code4piter.blueskythinking.utils.VideoStremer;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CameraServiceImpl implements CameraService {

    @Autowired
    private CameraRepository repository;

    @Autowired
    private WordRepository wordRepository;

    @Override
    public CameraDetailInfo getCameraDetailInfo(long id) {
        Camera camera = repository.findOne(id);
        BASE64Encoder encoder = new BASE64Encoder();
        byte[] imageBytes = getImage(camera.getVideoFlv());

        String imageString = encoder.encode(imageBytes);
        CameraDetailInfo detailInfo = new CameraDetailInfo(camera.getName(),
                camera.getAddress(),
                camera.getVideoAndroid(),
                getStats(imageBytes),
                imageString);
        return detailInfo;
    }

    @Override
    public List<CameraForMap> getAll() {
        List<Camera> cameras = repository.findAll();
        List<CameraForMap> cameraForMap = new ArrayList<>();
        for (Camera camera : cameras) {
            CameraForMap e =
                    new CameraForMap(camera.getId(), camera.getLatitude(), camera.getLongitude());
            cameraForMap.add(e);
        }
        return cameraForMap;
    }

    @Override
    public List<CameraListElement> search(Filter filter) {
        List<Camera> list = repository.findAll();
        List<CameraListElement> cameras = new ArrayList<>();

        for (Camera camera : list) {
            CameraListElement element = new CameraListElement(camera.getId(), camera.getName(),
                    camera.getAddress(), camera.getLatitude(), camera.getLongitude());
            cameras.add(element);
        }

        if (filter.getSearch() != null)
            cameras = cameras
                    .parallelStream()
                    .filter(e -> (e.getName().toLowerCase()).contains(filter.getSearch().toLowerCase()))
                    .collect(Collectors.toList());
        if (filter.getDistance() != null)
            cameras = cameras
                    .parallelStream()
                    .filter(e -> ((GeoTools.distination(
                            e.getLatitude(),
                            e.getLongitude(),
                            filter.getLatitude(),
                            filter.getLongitude()
                            )) <= filter.getDistance()))
                    .collect(Collectors.toList());
        if (filter.getSortDirection() != null)
            if (!filter.getSortDirection())
                Collections.reverse(cameras);
        return cameras;
    }

    @Override
    public List<NearCamera> getNearCameras(double latitude, double longitude) {
        List<Camera> cameras = repository.findAll();
        List<NearCamera> nearCamera = new ArrayList<>();
        for (Camera camera : cameras) {
            if (camera.getLatitude() != latitude && camera.getLongitude() != longitude)
                if (GeoTools.distination(
                        latitude, longitude, camera.getLatitude(), camera.getLongitude()) <= 20000) {
                    NearCamera e = new NearCamera(camera.getId(), camera.getName());
                    nearCamera.add(e);
                }
        }
        return nearCamera;
    }

    public byte[] getImage(String url) {
        return VideoStremer.saveImage(url);
    }

    public Double getStats(byte[] data) {
        String filename = "D:/images/" + System.currentTimeMillis() + ".png";
        try {
            InputStream input = new ByteArrayInputStream(data);
            BufferedImage bufferedImage = ImageIO.read(input);

            BufferedImage scaled = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = scaled.createGraphics();
            g.drawImage(bufferedImage, 0, 0, 1024, 768, null);
            g.dispose();
            ImageIO.write(scaled, "png", new File(filename));

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<AnnotateImageResponse> response = GoogleVision.getStats(filename);

        double result = 0;
        for (AnnotateImageResponse res : response) {
            if (res.hasError()) {
                System.out.printf("Error: %s\n", res.getError().getMessage());
                return null;
            }

            List<String> words = new ArrayList<>();
            for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                annotation.getAllFields().forEach((k, v) -> {
                    if (k.getFullName().contains("description"))
                        words.add(v.toString());
                });
            }

            double count = 0;
            double value = 0;
            List<Word> dictionary = wordRepository.findAll();
            for (int i = 0; i < dictionary.size(); i++) {
                for (String word : words) {
                    if (dictionary.get(i).getWord().toLowerCase().contains(word.toLowerCase())) {
                        System.out.println(word);
                        value += dictionary.get(i).getValue();
                        count++;
                    }
                }
            }

            System.out.println("VALUE: " + value);
            System.out.println("COUNT: " + count);
            if (count != 0)
                result = value / count;
            else
                result = 0;
        }

        return result;
    }
}
