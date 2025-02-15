package com.nivea_be.nivea_ad.utils;

import com.nivea_be.nivea_ad.enums.DimensionType;
import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import com.nivea_be.nivea_ad.entity.TrackDailyImpression;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DataMigrationService {

    private final MongoTemplate mongoTemplate;
    private static final Logger LOGGER = Logger.getLogger(DataMigrationService.class.getName());
    private static final ZoneId TIMEZONE = ZoneId.of("UTC");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * ✅ Runs once when the application starts to create today's documents for all dimensions.
     */
    @PostConstruct
    public void initializeDailyDocs() {
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        LOGGER.info("🔄 Initializing daily documents for: " + today);

        for (DimensionType dimension : DimensionType.values()) {
            createDailyEngagementDocIfNotExists(today, dimension);
            createDailyImpressionDocIfNotExists(today, dimension);
        }

        LOGGER.info("✅ Initialization completed for: " + today);
    }

    private void createDailyEngagementDocIfNotExists(String date, DimensionType dimension) {
        String docId = "engagement_" + date + "_" + dimension.getValue();
        TrackDailyEngagement existing = mongoTemplate.findById(docId, TrackDailyEngagement.class);

        if (existing == null) {
            TrackDailyEngagement newDoc = new TrackDailyEngagement(
                    docId,
                    ZonedDateTime.now(TIMEZONE).toLocalDate(),
                    0L,
                    0L,
                    dimension
            );
            mongoTemplate.insert(newDoc);
            LOGGER.info("📌 Created daily engagement doc for " + date + " with dimension " + dimension.getValue());
        } else {
            LOGGER.info("⚠️ Engagement document already exists for " + date + " with dimension " + dimension.getValue());
        }
    }

    private void createDailyImpressionDocIfNotExists(String date, DimensionType dimension) {
        String docId = "impression_" + date + "_" + dimension.getValue();
        TrackDailyImpression existing = mongoTemplate.findById(docId, TrackDailyImpression.class);

        if (existing == null) {
            TrackDailyImpression newDoc = new TrackDailyImpression(
                    docId,
                    ZonedDateTime.now(TIMEZONE).toLocalDate(),
                    0L,
                    dimension
            );
            mongoTemplate.insert(newDoc);
            LOGGER.info("📌 Created daily impression doc for " + date + " with dimension " + dimension.getValue());
        } else {
            LOGGER.info("⚠️ Impression document already exists for " + date + " with dimension " + dimension.getValue());
        }
    }
}

