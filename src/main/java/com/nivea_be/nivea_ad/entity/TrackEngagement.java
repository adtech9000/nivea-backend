package com.nivea_be.nivea_ad.entity;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = TrackConstants.TRACK_ENGAGEMENT_COLLECTION)
public class TrackEngagement {

    @Id
    private String id;

    private long yesCount;
    private long noCount;
}

