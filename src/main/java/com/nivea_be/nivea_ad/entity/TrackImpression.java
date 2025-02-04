package com.nivea_be.nivea_ad.entity;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = TrackConstants.TRACK_IMPRESSION_COLLECTION)
public class TrackImpression {

    @Id
    private String id;

    private long impressionCount;
}

