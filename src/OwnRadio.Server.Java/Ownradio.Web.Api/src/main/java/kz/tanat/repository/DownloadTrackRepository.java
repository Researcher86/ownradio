package kz.tanat.repository;

import kz.tanat.domain.DownloadTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadTrackRepository extends JpaRepository<DownloadTrack, String> {
}
