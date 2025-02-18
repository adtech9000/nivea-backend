package com.nivea_be.nivea_ad.utils;

import com.nivea_be.nivea_ad.enums.DimensionType;
import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import com.nivea_be.nivea_ad.entity.TrackDailyImpression;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class DataMigrationService {

    private final MongoTemplate mongoTemplate;
    private static final Logger LOGGER = Logger.getLogger(DataMigrationService.class.getName());

    /**
     * ✅ Runs once when the application starts to remove `DIMENSION_320_500` from existing records.
     */
    @PostConstruct
    public void removeInvalidDimension() {
        LOGGER.info("🔄 Removing invalid dimension: DIMENSION_320_500 from existing records...");

        // Remove from Engagement Collection
        Query engagementQuery = new Query(Criteria.where("dimension").is(DimensionType.DIMENSION_320_500));
        mongoTemplate.remove(engagementQuery, TrackDailyEngagement.class);
        LOGGER.info("✅ Removed `DIMENSION_320_500` from engagement collection.");

        // Remove from Impression Collection
        Query impressionQuery = new Query(Criteria.where("dimension").is(DimensionType.DIMENSION_320_500));
        mongoTemplate.remove(impressionQuery, TrackDailyImpression.class);
        LOGGER.info("✅ Removed `DIMENSION_320_500` from impression collection.");

        LOGGER.info("🎯 Dimension cleanup complete!");
    }
}
