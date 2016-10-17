package ownradio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ownradio.domain.Device;
import ownradio.repository.DeviceRepository;
import ownradio.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService {

	private final DeviceRepository deviceRepository;

	@Autowired
	public DeviceServiceImpl(DeviceRepository deviceRepository) {
		this.deviceRepository = deviceRepository;
	}


	@Override
	public void save(Device device) {
		deviceRepository.saveAndFlush(device);
	}
}
