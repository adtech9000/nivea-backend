package com.nivea_be.nivea_ad;

import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import com.nivea_be.nivea_ad.entity.TrackDailyImpression;
import com.nivea_be.nivea_ad.enums.DimensionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class DailyDataInitializer {

    private final MongoTemplate mongoTemplate;
    private static final Logger LOGGER = Logger.getLogger(DailyDataInitializer.class.getName());
    private static final ZoneId TIMEZONE = ZoneId.of("UTC");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 🕒 Runs every day at midnight to ensure daily engagement and impression documents exist for each dimension.
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void createDailyDocs() {
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        LOGGER.info("🔄 Scheduled job running at: " + ZonedDateTime.now(TIMEZONE));

        for (DimensionType dimension : DimensionType.values()) {
            createDailyEngagementDocIfNotExists(today, dimension);
            createDailyImpressionDocIfNotExists(today, dimension);
        }

        LOGGER.info("✅ Scheduled job completed at: " + ZonedDateTime.now(TIMEZONE));
    }

    private void createDailyEngagementDocIfNotExists(String date, DimensionType dimension) {
        String docId = "engagement_" + date + "_" + dimension.getValue();
        TrackDailyEngagement existing = mongoTemplate.findById(docId, TrackDailyEngagement.class);

        if (existing == null) {
            TrackDailyEngagement newDoc = new TrackDailyEngagement(docId, ZonedDateTime.now(TIMEZONE).toLocalDate(), 0L, 0L, dimension);
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
            TrackDailyImpression newDoc = new TrackDailyImpression(docId, ZonedDateTime.now(TIMEZONE).toLocalDate(), 0L, dimension);
            mongoTemplate.insert(newDoc);
            LOGGER.info("📌 Created daily impression doc for " + date + " with dimension " + dimension.getValue());
        } else {
            LOGGER.info("⚠️ Impression document already exists for " + date + " with dimension " + dimension.getValue());
        }
    }
}
