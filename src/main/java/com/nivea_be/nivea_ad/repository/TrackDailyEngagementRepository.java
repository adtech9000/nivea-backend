package com.nivea_be.nivea_ad.repository;


import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackDailyEngagementRepository extends MongoRepository<TrackDailyEngagement, String> {

}

