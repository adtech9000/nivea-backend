package com.nivea_be.nivea_ad.constants;

public final class TrackConstants {

    private TrackConstants() {}

    // Collection Names
    public static final String TRACK_ENGAGEMENT_COLLECTION = "track_engagement";
    public static final String TRACK_IMPRESSION_COLLECTION = "track_impression";

    // Field Names for TrackEngagement
    public static final String YES_COUNT_FIELD = "yesCount";
    public static final String NO_COUNT_FIELD = "noCount";

    // Field Names for TrackImpression
    public static final String IMPRESSION_COUNT_FIELD = "impressionCount";
    public static final String ENGAGEMENT_DOCUMENT_ID = "engagement_counter";
    public static final String IMPRESSION_DOCUMENT_ID = "impression_counter";

    // Response Values
    public static final String RESPONSE_YES = "yes";
    public static final String RESPONSE_NO = "no";

    // Success Messages
    public static final String YES_INCREMENT_SUCCESS = "Yes count incremented.";
    public static final String NO_INCREMENT_SUCCESS = "No count incremented.";
    public static final String IMPRESSION_INCREMENT_SUCCESS = "Impression count incremented.";

    // Error Messages
    public static final String INVALID_RESPONSE_ERROR = "Invalid response. Use 'yes' or 'no'.";
}
