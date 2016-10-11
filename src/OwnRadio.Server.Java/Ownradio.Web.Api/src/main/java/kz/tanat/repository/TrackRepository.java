package kz.tanat.repository;

import kz.tanat.domain.Track;
import kz.tanat.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, String> {
	@Query("select t from Track t where t.uploadUser = :user order by random()")
	List<Track> getRandomTrackByUserId(@Param("user") User user, Pageable pageable);
}
