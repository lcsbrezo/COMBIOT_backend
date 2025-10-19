package com.iot.combiot.combiot.device.repository;

import com.iot.combiot.combiot.device.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
