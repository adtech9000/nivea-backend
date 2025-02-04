package com.nivea_be.nivea_ad.repository;


import com.nivea_be.nivea_ad.entity.TrackImpression;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackImpressionRepository extends MongoRepository<TrackImpression, String> {

}
