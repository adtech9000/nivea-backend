package com.nivea_be.nivea_ad.controller;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import com.nivea_be.nivea_ad.entity.TrackImpression;
import com.nivea_be.nivea_ad.service.TrackImpressionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for tracking impressions.
 */
@RestController
@RequestMapping("/api/impression")
@RequiredArgsConstructor
public class TrackImpressionController {

    private final TrackImpressionService trackImpressionService;

    /**
     * Endpoint to track an impression.
     *
     * @return Success message.
     */
    @PostMapping
    public ResponseEntity<String> trackImpression() {
        trackImpressionService.incrementImpression();
        return ResponseEntity.ok(TrackConstants.IMPRESSION_INCREMENT_SUCCESS);
    }

    /**
     * Optional: Endpoint to retrieve current impression count.
     *
     * @return TrackImpression object containing the count.
     */
    @GetMapping
    public ResponseEntity<TrackImpression> getImpressionCount() {
        TrackImpression count = trackImpressionService.getImpressionCount();
        return ResponseEntity.ok(count);
    }
}

