package com.iot.combiot.combiot.device.model;

import com.iot.combiot.combiot.auth.model.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String macAddress;
    private String deviceName;
    private String deviceType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
