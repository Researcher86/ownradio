package kz.tanat.service;

import kz.tanat.domain.Device;
import kz.tanat.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }


    public void save(Device device) {
        deviceRepository.saveAndFlush(device);
    }
}
