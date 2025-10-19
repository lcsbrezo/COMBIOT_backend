package com.iot.combiot.combiot.device.service;

import com.iot.combiot.combiot.auth.model.User;
import com.iot.combiot.combiot.auth.repository.UserRepository;
import com.iot.combiot.combiot.device.dto.DeviceRegistrationRequest;
import com.iot.combiot.combiot.device.model.Device;
import com.iot.combiot.combiot.device.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    // --- INICIO DE LA MODIFICACIÓN ---
    @Autowired
    private ResourceLoader resourceLoader;
    // --- FIN DE LA MODIFICACIÓN ---


    public String registerDevice(DeviceRegistrationRequest request) {
        Device device = new Device();
        device.setMacAddress(request.getMacAddress());
        device.setDeviceName(request.getDeviceName());
        device.setDeviceType(request.getDeviceType());

        User user = userRepository.findById((long) Integer.parseInt(request.getUserId()))
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        device.setUser(user);

        deviceRepository.save(device);

        // Llamamos al nuevo método mejorado
        return getFirmwareUrlForDeviceType(request.getDeviceType());
    }

    // --- INICIO DE LA MODIFICACIÓN ---
    private String getFirmwareUrlForDeviceType(String deviceType) {
        // Asegúrate de que la URL base sea la correcta para tu entorno.
        String baseUrl = "http://192.168.1.100:8080";

        try {
            // 1. Sanitizar la entrada para evitar Path Traversal.
            // Esto asegura que 'deviceType' no contenga caracteres como '../'
            String sanitizedDeviceType = Paths.get(deviceType).getFileName().toString();

            // 2. Construir el nombre del fichero y la ruta del recurso.
            String firmwareFilename = sanitizedDeviceType.toLowerCase() + ".bin";
            String resourcePath = "classpath:static/firmware/" + firmwareFilename;

            // 3. Comprobar si el recurso existe y es accesible.
            Resource firmwareResource = resourceLoader.getResource(resourcePath);
            if (firmwareResource.exists() && firmwareResource.isReadable()) {
                // 4. Si existe, devolver la URL completa.
                return baseUrl + "/firmware/" + firmwareFilename;
            } else {
                // Opcional: Log de que se solicitó un firmware no existente.
                System.out.println("Se solicitó un firmware no encontrado para el tipo: " + deviceType);
                return ""; // Devolver URL vacía o a un firmware por defecto.
            }
        } catch (Exception e) {
            // Capturar cualquier error potencial con las rutas de ficheros.
            System.err.println("Error al procesar el tipo de dispositivo: " + deviceType + " - " + e.getMessage());
            return "";
        }
    }
    // --- FIN DE LA MODIFICACIÓN ---
}
