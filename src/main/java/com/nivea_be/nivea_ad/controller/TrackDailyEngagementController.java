package com.nivea_be.nivea_ad.controller;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import com.nivea_be.nivea_ad.service.TrackDailyEngagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.nivea_be.nivea_ad.constants.TrackConstants.*;

/**
 * REST Controller for tracking daily engagements (yes/no).
 */
@RestController
@RequestMapping("/api/engagement")
@RequiredArgsConstructor
public class TrackDailyEngagementController {

    private final TrackDailyEngagementService trackDailyEngagementService;

    /**
     * Single endpoint to track engagement based on user response.
     * Valid responses: "yes" or "no".
     *
     * @param response - "yes" or "no"
     * @return a success or error message.
     */
    @PostMapping
    public ResponseEntity<String> trackEngagement(@RequestParam("response") String response) {
        if (TrackConstants.RESPONSE_YES.equalsIgnoreCase(response)) {
            trackDailyEngagementService.incrementYesToday();
            return ResponseEntity.ok(YES_INCREMENT_SUCCESS);
        } else if (TrackConstants.RESPONSE_NO.equalsIgnoreCase(response)) {
            trackDailyEngagementService.incrementNoToday();
            return ResponseEntity.ok(NO_INCREMENT_SUCCESS);
        } else {
            return ResponseEntity.badRequest()
                    .body(INVALID_RESPONSE_ERROR);
        }
    }

    /**
     * Retrieves today's engagement counts.
     *
     * @return the counts (yes/no) for the current day.
     */
    @GetMapping
    public ResponseEntity<TrackDailyEngagement> getTodayEngagementCounts() {
        TrackDailyEngagement counts = trackDailyEngagementService.getTodayEngagement();
        if (counts == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(counts);
    }
}
