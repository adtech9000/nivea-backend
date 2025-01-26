package com.nivea_be.nivea_ad.controller;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import com.nivea_be.nivea_ad.entity.TrackEngagement;
import com.nivea_be.nivea_ad.service.TrackEngagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for tracking engagements (yes/no).
 */
@RestController
@RequestMapping("/api/engagement")
@RequiredArgsConstructor
public class TrackEngagementController {

    private final TrackEngagementService trackEngagementService;

    /**
     * Endpoint to track engagement based on user response.
     *
     * @param response "yes" or "no"
     * @return Success or error message.
     */
    @PostMapping
    public ResponseEntity<String> trackEngagement(@RequestParam("response") String response) {
        if (TrackConstants.RESPONSE_YES.equalsIgnoreCase(response)) {
            trackEngagementService.incrementYes();
            return ResponseEntity.ok(TrackConstants.YES_INCREMENT_SUCCESS);
        } else if (TrackConstants.RESPONSE_NO.equalsIgnoreCase(response)) {
            trackEngagementService.incrementNo();
            return ResponseEntity.ok(TrackConstants.NO_INCREMENT_SUCCESS);
        } else {
            return ResponseEntity.badRequest().body(TrackConstants.INVALID_RESPONSE_ERROR);
        }
    }

    /**
     * Optional: Endpoint to retrieve current engagement counts.
     *
     * @return TrackEngagement object containing counts.
     */
    @GetMapping
    public ResponseEntity<TrackEngagement> getEngagementCounts() {
        TrackEngagement counts = trackEngagementService.getEngagementCounts();
        return ResponseEntity.ok(counts);
    }
}

