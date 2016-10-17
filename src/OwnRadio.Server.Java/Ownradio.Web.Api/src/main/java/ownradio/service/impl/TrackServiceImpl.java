package ownradio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ownradio.domain.Track;
import ownradio.domain.User;
import ownradio.repository.TrackRepository;
import ownradio.service.TrackService;
import ownradio.util.ResourceUtil;

import java.util.UUID;

@Service
public class TrackServiceImpl implements TrackService {

	private final TrackRepository trackRepository;

	@Autowired
	public TrackServiceImpl(TrackRepository trackRepository) {
		this.trackRepository = trackRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public Track getById(UUID id) {
		return trackRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly = true)
	public UUID getNextTrackId(UUID deviceId) {
		return trackRepository.getNextTrackId(deviceId).getId();
	}

	@Override
	@Transactional
	public void save(Track track, MultipartFile file) {
		trackRepository.save(track);

		String dirName = track.getUploadUser().getId().toString();
		String fileName = track.getId() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
		String filePath = ResourceUtil.save(dirName, fileName, file);

		track.setPath(filePath);
		trackRepository.flush();
	}
}
