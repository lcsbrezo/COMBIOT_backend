package com.iot.combiot.combiot.device.controller;

import com.iot.combiot.combiot.device.dto.DeviceRegistrationRequest;
import com.iot.combiot.combiot.device.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/register")
    public ResponseEntity<String> registerDevice(@RequestBody DeviceRegistrationRequest request) {
        String firmwareUrl = deviceService.registerDevice(request);
        return ResponseEntity.ok(firmwareUrl);
    }
}
