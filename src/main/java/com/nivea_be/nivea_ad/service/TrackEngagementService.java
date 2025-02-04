package com.nivea_be.nivea_ad.service;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import com.nivea_be.nivea_ad.entity.TrackEngagement;
import com.nivea_be.nivea_ad.exception.EngagementDocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.nivea_be.nivea_ad.repository.TrackEngagementRepository;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
@RequiredArgsConstructor
public class TrackEngagementService {

    private final MongoTemplate mongoTemplate;
    private final TrackEngagementRepository trackEngagementRepository;

    /**
     * Atomically increments the 'yes' count.
     */
    public void incrementYes() {
        Query query = new Query(Criteria.where("_id").is(TrackConstants.ENGAGEMENT_DOCUMENT_ID));
        Update update = new Update().inc(TrackConstants.YES_COUNT_FIELD, 1);
        TrackEngagement updated = mongoTemplate.findAndModify(
                query,
                update,
                options().returnNew(true).upsert(false),
                TrackEngagement.class
        );

        if (updated == null) {
            throw new EngagementDocumentNotFoundException("Engagement document not found.");
        }
    }

    /**
     * Atomically increments the 'no' count.
     */
    public void incrementNo() {
        Query query = new Query(Criteria.where("_id").is(TrackConstants.ENGAGEMENT_DOCUMENT_ID));
        Update update = new Update().inc(TrackConstants.NO_COUNT_FIELD, 1);
        TrackEngagement updated = mongoTemplate.findAndModify(
                query,
                update,
                options().returnNew(true).upsert(false),
                TrackEngagement.class
        );

        if (updated == null) {
            throw new EngagementDocumentNotFoundException("Engagement document not found.");
        }
    }

    /**
     * Retrieves the current engagement counts.
     *
     * @return TrackEngagement object containing counts.
     */
    public TrackEngagement getEngagementCounts() {
        return trackEngagementRepository.findById(TrackConstants.ENGAGEMENT_DOCUMENT_ID)
                .orElse(new TrackEngagement());
    }
}
