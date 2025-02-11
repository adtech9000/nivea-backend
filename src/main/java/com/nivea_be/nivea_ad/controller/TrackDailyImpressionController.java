package com.nivea_be.nivea_ad.controller;

import com.nivea_be.nivea_ad.entity.TrackDailyImpression;
import com.nivea_be.nivea_ad.service.TrackDailyImpressionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nivea_be.nivea_ad.constants.TrackConstants.IMPRESSION_INCREMENT_SUCCESS;

/**
 * REST Controller for tracking impressions.
 */
@RestController
@RequestMapping("/api/impression")
@RequiredArgsConstructor
public class TrackDailyImpressionController {


    private final TrackDailyImpressionService trackDailyImpressionService;

    @PostMapping
    public ResponseEntity<String> trackTodayImpression() {
        trackDailyImpressionService.incrementImpressionToday();
        return ResponseEntity.ok(IMPRESSION_INCREMENT_SUCCESS);
    }

    @GetMapping
    public ResponseEntity<TrackDailyImpression> getTodayImpressionCount() {
        TrackDailyImpression count = trackDailyImpressionService.getTodayImpression();
        if (count == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(count);
    }
}

