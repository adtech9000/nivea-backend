package com.nivea_be.nivea_ad.entity;


import com.nivea_be.nivea_ad.enums.DimensionType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

import static com.nivea_be.nivea_ad.constants.TrackConstants.TRACK_DAILY_IMPRESSION_COLLECTION;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = TRACK_DAILY_IMPRESSION_COLLECTION)
public class TrackDailyImpression {

    @Id
    private String id;
    private LocalDate date;
    private long impressionCount;
    private DimensionType dimension;
}

