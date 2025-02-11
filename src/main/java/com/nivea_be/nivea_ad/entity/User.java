package com.nivea_be.nivea_ad.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.nivea_be.nivea_ad.constants.TrackConstants.USERS_COLLECTION;

@Data
@Document(collection = USERS_COLLECTION)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    private String username;
    private String password;
}

