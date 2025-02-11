package com.nivea_be.nivea_ad.constants;

public final class TrackConstants {

    private TrackConstants() {}

    // Collection Names
    public static final String TRACK_DAILY_ENGAGEMENT_COLLECTION = "track_daily_engagement";
    public static final String TRACK_DAILY_IMPRESSION_COLLECTION = "track_daily_impression";
    public static final String USERS_COLLECTION = "users";

    // Field Names for TrackDailyEngagement
    public static final String YES_COUNT_FIELD = "yesCount";
    public static final String NO_COUNT_FIELD = "noCount";

    // Field Names for TrackDailyImpression
    public static final String IMPRESSION_COUNT_FIELD = "impressionCount";

    // Document ID Prefixes
    public static final String ENGAGEMENT_DOC_PREFIX = "engagement_";
    public static final String IMPRESSION_DOC_PREFIX = "impression_";

    // Response Values
    public static final String RESPONSE_YES = "yes";
    public static final String RESPONSE_NO = "no";

    // Success Messages
    public static final String YES_INCREMENT_SUCCESS = "Yes count incremented for today.";
    public static final String NO_INCREMENT_SUCCESS = "No count incremented for today.";
    public static final String IMPRESSION_INCREMENT_SUCCESS = "Impression count incremented for today.";

    // Error Messages
    public static final String INVALID_RESPONSE_ERROR = "Invalid response. Use 'yes' or 'no'.";
    public static final String ENGAGEMENT_DOC_NOT_FOUND_ERROR = "Daily engagement document not found for today: ";
    public static final String IMPRESSION_DOC_NOT_FOUND_ERROR = "Daily impression document not found for today: ";
    public static final String INVALID_CREDENTIALS_ERROR = "Invalid username or password.";

    // User-related Messages
    public static final String LOGIN_SUCCESS = "Login successful!";
    public static final String FETCH_USERS_SUCCESS = "Fetched all users successfully.";
    public static final String USER_SAVE_SUCCESS = "User saved successfully.";
}
