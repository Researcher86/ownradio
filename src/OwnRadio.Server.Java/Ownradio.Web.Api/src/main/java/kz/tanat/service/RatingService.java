package kz.tanat.service;

import kz.tanat.domain.Rating;
import kz.tanat.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    public void save(Rating rating) {
        ratingRepository.saveAndFlush(rating);
    }
}
