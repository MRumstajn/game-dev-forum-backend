package com.mrumstajn.gamedevforum.workoffer.repository;

import com.mrumstajn.gamedevforum.workoffer.entity.WorkOfferRating;
import net.croz.nrich.search.api.repository.SearchExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkOfferRatingRepository extends JpaRepository<WorkOfferRating, Long>, SearchExecutor<WorkOfferRating> {

    @Query("""
            SELECT AVG(r.rating) FROM WorkOfferRating r WHERE r.workOfferId = :workOfferId
            """)
    Double findAverageRatingByWorkOfferId(@Param("workOfferId") Long workOfferId);

    Long countAllByWorkOfferId(Long workOfferId);

    Optional<WorkOfferRating> findByUserIdAndWorkOfferId(Long userId, Long workOfferId);

    void deleteAllByWorkOfferId(Long workOfferId);
}
