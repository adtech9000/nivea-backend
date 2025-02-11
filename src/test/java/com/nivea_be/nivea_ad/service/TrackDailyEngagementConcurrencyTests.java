package com.nivea_be.nivea_ad.service;

import com.nivea_be.nivea_ad.entity.TrackDailyEngagement;
import com.nivea_be.nivea_ad.entity.TrackDailyImpression;
import com.nivea_be.nivea_ad.exception.EngagementDocumentNotFoundException;
import com.nivea_be.nivea_ad.exception.ImpressionDocumentNotFoundException;
import com.nivea_be.nivea_ad.repository.TrackDailyEngagementRepository;
import com.nivea_be.nivea_ad.repository.TrackDailyImpressionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrackDailyEngagementConcurrencyTests {

    @Autowired
    private TrackDailyEngagementService trackDailyEngagementService;

    @Autowired
    private TrackDailyImpressionService trackDailyImpressionService;

    @Autowired
    private TrackDailyEngagementRepository trackDailyEngagementRepository;

    @Autowired
    private TrackDailyImpressionRepository trackDailyImpressionRepository;

    private static final ZoneId TIMEZONE = ZoneId.of("UTC");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String engagementDocId;
    private String impressionDocId;

    @BeforeEach
    void setup() {
        // Clear daily collections
        trackDailyEngagementRepository.deleteAll();
        trackDailyImpressionRepository.deleteAll();

        // Generate today's IDs using UTC
        String today = ZonedDateTime.now(TIMEZONE).format(DATE_FORMATTER);
        engagementDocId = "engagement_" + today;
        impressionDocId = "impression_" + today;

        // Create today's daily engagement doc
        TrackDailyEngagement dailyEngagement = new TrackDailyEngagement();
        dailyEngagement.setId(engagementDocId);
        dailyEngagement.setDate(ZonedDateTime.now(TIMEZONE).toLocalDate());
        dailyEngagement.setYesCount(0);
        dailyEngagement.setNoCount(0);
        trackDailyEngagementRepository.save(dailyEngagement);

        // Create today's daily impression doc
        TrackDailyImpression dailyImpression = new TrackDailyImpression();
        dailyImpression.setId(impressionDocId);
        dailyImpression.setDate(ZonedDateTime.now(TIMEZONE).toLocalDate());
        dailyImpression.setImpressionCount(0);
        trackDailyImpressionRepository.save(dailyImpression);
    }

    @Test
    void testConcurrentYesIncrements() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackDailyEngagementService.incrementYesToday());
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        TrackDailyEngagement engagement = trackDailyEngagementService.getTodayEngagement();
        assertNotNull(engagement);
        assertEquals(numberOfThreads, engagement.getYesCount());
        assertEquals(0, engagement.getNoCount());
    }

    @Test
    void testConcurrentNoIncrements() throws InterruptedException {
        int numberOfThreads = 200;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackDailyEngagementService.incrementNoToday());
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        TrackDailyEngagement engagement = trackDailyEngagementService.getTodayEngagement();
        assertNotNull(engagement);
        assertEquals(0, engagement.getYesCount());
        assertEquals(numberOfThreads, engagement.getNoCount());
    }

    @Test
    void testConcurrentImpressionIncrements() throws InterruptedException {
        int numberOfThreads = 200;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackDailyImpressionService.incrementImpressionToday());
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        TrackDailyImpression impression = trackDailyImpressionService.getTodayImpression();
        assertNotNull(impression);
        assertEquals(numberOfThreads, impression.getImpressionCount());
    }

    @Test
    void testConcurrentYesNoImpressionIncrements() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackDailyEngagementService.incrementYesToday());
            executorService.submit(() -> trackDailyEngagementService.incrementNoToday());
            executorService.submit(() -> trackDailyImpressionService.incrementImpressionToday());
        }

        executorService.shutdown();
        assertTrue(executorService.awaitTermination(1, TimeUnit.MINUTES));

        TrackDailyEngagement engagement = trackDailyEngagementService.getTodayEngagement();
        TrackDailyImpression impression = trackDailyImpressionService.getTodayImpression();

        assertNotNull(engagement);
        assertEquals(numberOfThreads, engagement.getYesCount());
        assertEquals(numberOfThreads, engagement.getNoCount());

        assertNotNull(impression);
        assertEquals(numberOfThreads, impression.getImpressionCount());
    }

    @Test
    void testIncrementYesWithMissingEngagementDocument() {
        trackDailyEngagementRepository.deleteById(engagementDocId);

        EngagementDocumentNotFoundException ex = assertThrows(
                EngagementDocumentNotFoundException.class,
                () -> trackDailyEngagementService.incrementYesToday()
        );

        assertEquals("Daily engagement document not found for today: " + engagementDocId, ex.getMessage());
    }

    @Test
    void testIncrementImpressionWithMissingImpressionDocument() {
        trackDailyImpressionRepository.deleteById(impressionDocId);

        ImpressionDocumentNotFoundException ex = assertThrows(
                ImpressionDocumentNotFoundException.class,
                () -> trackDailyImpressionService.incrementImpressionToday()
        );

        assertEquals("Daily impression document not found for today: " + impressionDocId, ex.getMessage());
    }
}
