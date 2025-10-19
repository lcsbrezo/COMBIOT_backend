package com.iot.combiot.combiot.device.dto;

import lombok.Data;

@Data
public class DeviceRegistrationRequest {
    private String macAddress;
    private String userId;
    private String deviceName;
    private String deviceType;
}
