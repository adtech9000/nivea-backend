package com.nivea_be.nivea_ad.utils;

import com.nivea_be.nivea_ad.enums.DimensionType;
import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import com.nivea_be.nivea_ad.entity.TrackDailyImpression;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataMigrationService {

    private final MongoTemplate mongoTemplate;

    @PostConstruct
    public void fixOldRecords() {
        fixOldEngagementRecords();
        fixOldImpressionRecords();
    }

    private void fixOldEngagementRecords() {
        Query query = new Query(Criteria.where("dimension").exists(false));
        Update update = new Update().set("dimension", DimensionType.DIMENSION_320_480.getValue());

        mongoTemplate.updateMulti(query, update, TrackDailyEngagement.class);
        System.out.println("✅ Fixed old engagement records, set dimension=dimension_320_480");
    }

    private void fixOldImpressionRecords() {
        Query query = new Query(Criteria.where("dimension").exists(false));
        Update update = new Update().set("dimension", DimensionType.DIMENSION_320_480.getValue());

        mongoTemplate.updateMulti(query, update, TrackDailyImpression.class);
        System.out.println("✅ Fixed old impression records, set dimension=dimension_320_480");
    }
}

