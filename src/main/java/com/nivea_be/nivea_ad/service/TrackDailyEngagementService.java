package com.nivea_be.nivea_ad.service;

import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import com.nivea_be.nivea_ad.exception.EngagementDocumentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static com.nivea_be.nivea_ad.constants.TrackConstants.*;

@Service
@RequiredArgsConstructor
public class TrackDailyEngagementService {

    private final MongoTemplate mongoTemplate;
    private static final ZoneId TIMEZONE = ZoneId.of("UTC");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void incrementYesToday() {
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        String docId = ENGAGEMENT_DOC_PREFIX + today;

        Query query = new Query(Criteria.where("_id").is(docId));
        Update update = new Update().inc(YES_COUNT_FIELD, 1);

        TrackDailyEngagement updated = mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(false),
                TrackDailyEngagement.class
        );

        if (updated == null) {
            throw new EngagementDocumentNotFoundException(ENGAGEMENT_DOC_NOT_FOUND_ERROR + docId);
        }
    }

    public void incrementNoToday() {
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        String docId = ENGAGEMENT_DOC_PREFIX + today;

        Query query = new Query(Criteria.where("_id").is(docId));
        Update update = new Update().inc(NO_COUNT_FIELD, 1);

        TrackDailyEngagement updated = mongoTemplate.findAndModify(
                query,
                update,
                FindAndModifyOptions.options().returnNew(true).upsert(false),
                TrackDailyEngagement.class
        );

        if (updated == null) {
            throw new EngagementDocumentNotFoundException(ENGAGEMENT_DOC_NOT_FOUND_ERROR + docId);
        }
    }

    public TrackDailyEngagement getTodayEngagement() {
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        String docId = ENGAGEMENT_DOC_PREFIX + today;

        return mongoTemplate.findById(docId, TrackDailyEngagement.class);
    }
}
