package com.nivea_be.nivea_ad.repository;


import com.nivea_be.nivea_ad.entity.TrackEngagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackEngagementRepository extends MongoRepository<TrackEngagement, String> {

}

