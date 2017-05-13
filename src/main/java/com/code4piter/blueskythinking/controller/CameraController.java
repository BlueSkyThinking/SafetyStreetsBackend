package com.code4piter.blueskythinking.controller;

import com.code4piter.blueskythinking.entity.*;
import com.code4piter.blueskythinking.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/camera")
public class CameraController {

    @Autowired
    private CameraService service;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<CameraForMap> getAllCameras() {
        return service.getAll();
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public CameraDetailInfo getCameraDetail(@PathVariable("id") long id) {
        return service.getCameraDetailInfo(id);
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<CameraListElement> getCameraFilter(@RequestBody Filter filter){
        return service.search(filter);
    }

    @RequestMapping(value = "/getNearCameras", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public List<NearCamera> getNearCamera(@RequestBody Location location) {
        return service.getNearCameras(location.getLatitude(), location.getLongitude());
    }
}
