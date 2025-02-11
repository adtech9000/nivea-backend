package com.nivea_be.nivea_ad.service;

import com.nivea_be.nivea_ad.entity.TrackDailyImpression;
import com.nivea_be.nivea_ad.exception.ImpressionDocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.nivea_be.nivea_ad.constants.TrackConstants.*;

@Service
@RequiredArgsConstructor
public class TrackDailyImpressionService {

    private final MongoTemplate mongoTemplate;
    private static final ZoneId TIMEZONE = ZoneId.of("UTC");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void incrementImpressionToday() {
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        String docId = IMPRESSION_DOC_PREFIX + today;

        Query query = new Query(Criteria.where("_id").is(docId));
        Update update = new Update().inc(IMPRESSION_COUNT_FIELD, 1);

        TrackDailyImpression updated = mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(false),
                TrackDailyImpression.class
        );

        if (updated == null) {
            throw new ImpressionDocumentNotFoundException(IMPRESSION_DOC_NOT_FOUND_ERROR + docId);
        }
    }

    public TrackDailyImpression getTodayImpression() {
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        String docId = IMPRESSION_DOC_PREFIX + today;

        return mongoTemplate.findById(docId, TrackDailyImpression.class);
    }
}
