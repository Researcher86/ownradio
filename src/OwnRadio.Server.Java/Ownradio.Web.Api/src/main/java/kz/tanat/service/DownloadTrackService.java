package kz.tanat.service;

import kz.tanat.domain.DownloadTrack;
import kz.tanat.repository.DownloadTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownloadTrackService {

    private final DownloadTrackRepository downloadTrackRepository;

    @Autowired
    public DownloadTrackService(DownloadTrackRepository downloadTrackRepository) {
        this.downloadTrackRepository = downloadTrackRepository;
    }


    public void save(DownloadTrack downloadTrack) {
        downloadTrackRepository.saveAndFlush(downloadTrack);
    }
}
