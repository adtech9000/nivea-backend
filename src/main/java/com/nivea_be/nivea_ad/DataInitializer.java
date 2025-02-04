package com.nivea_be.nivea_ad;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import com.nivea_be.nivea_ad.entity.TrackEngagement;
import com.nivea_be.nivea_ad.entity.TrackImpression;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final MongoTemplate mongoTemplate;

    @Override
    public void run(String... args) {
        TrackEngagement existingEngagement = mongoTemplate.findById(TrackConstants.ENGAGEMENT_DOCUMENT_ID, TrackEngagement.class);
        if (existingEngagement == null) {
            TrackEngagement engagement = new TrackEngagement();
            engagement.setId(TrackConstants.ENGAGEMENT_DOCUMENT_ID);
            engagement.setYesCount(0);
            engagement.setNoCount(0);
            mongoTemplate.insert(engagement);
            System.out.println("Initialized engagement document with ID: " + TrackConstants.ENGAGEMENT_DOCUMENT_ID);
        } else {
            System.out.println("Engagement document already exists with ID: " + TrackConstants.ENGAGEMENT_DOCUMENT_ID);
        }


        TrackImpression existingImpression = mongoTemplate.findById(TrackConstants.IMPRESSION_DOCUMENT_ID, TrackImpression.class);
        if (existingImpression == null) {
            TrackImpression impression = new TrackImpression();
            impression.setId(TrackConstants.IMPRESSION_DOCUMENT_ID);
            impression.setImpressionCount(0);
            mongoTemplate.insert(impression);
            System.out.println("Initialized impression document with ID: " + TrackConstants.IMPRESSION_DOCUMENT_ID);
        } else {
            System.out.println("Impression document already exists with ID: " + TrackConstants.IMPRESSION_DOCUMENT_ID);
        }
    }
}
