package com.nivea_be.nivea_ad.entity;

import com.nivea_be.nivea_ad.enums.DimensionType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import static com.nivea_be.nivea_ad.constants.TrackConstants.TRACK_DAILY_ENGAGEMENT_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = TRACK_DAILY_ENGAGEMENT_COLLECTION)
public class TrackDailyEngagement {

    @Id
    private String id;
    private LocalDate date;
    private long yesCount;
    private long noCount;
    private DimensionType dimension;
}

