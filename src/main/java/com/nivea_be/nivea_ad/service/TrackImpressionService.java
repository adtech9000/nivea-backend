package com.nivea_be.nivea_ad.service;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import com.nivea_be.nivea_ad.entity.TrackImpression;
import com.nivea_be.nivea_ad.exception.ImpressionDocumentNotFoundException;
import com.nivea_be.nivea_ad.repository.TrackImpressionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
@RequiredArgsConstructor
public class TrackImpressionService {

    private final MongoTemplate mongoTemplate;
    private final TrackImpressionRepository trackImpressionRepository;

    /**
     * Atomically increments the impression count.
     */
    public void incrementImpression() {
        Query query = new Query(Criteria.where("_id").is(TrackConstants.IMPRESSION_DOCUMENT_ID));
        Update update = new Update().inc(TrackConstants.IMPRESSION_COUNT_FIELD, 1);
        TrackImpression updated = mongoTemplate.findAndModify(
                query,
                update,
                options().returnNew(true).upsert(false),
                TrackImpression.class
        );
        if (updated == null) {
            throw new ImpressionDocumentNotFoundException("Impression document not found.");
        }
    }

    /**
     * Retrieves the current impression count.
     *
     * @return TrackImpression object containing the count.
     */
    public TrackImpression getImpressionCount() {
        return trackImpressionRepository.findById(TrackConstants.IMPRESSION_DOCUMENT_ID)
                .orElse(new TrackImpression());
    }
}
