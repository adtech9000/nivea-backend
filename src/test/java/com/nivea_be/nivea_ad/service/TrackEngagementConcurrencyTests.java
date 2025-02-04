package com.nivea_be.nivea_ad.service;

import com.nivea_be.nivea_ad.constants.TrackConstants;
import com.nivea_be.nivea_ad.entity.TrackEngagement;
import com.nivea_be.nivea_ad.entity.TrackImpression;
import com.nivea_be.nivea_ad.exception.EngagementDocumentNotFoundException;
import com.nivea_be.nivea_ad.exception.ImpressionDocumentNotFoundException;
import com.nivea_be.nivea_ad.repository.TrackEngagementRepository;
import com.nivea_be.nivea_ad.repository.TrackImpressionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Import JUnit Assertions
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Tests to verify concurrency handling in TrackEngagementService and TrackImpressionService.
 */
@SpringBootTest
public class TrackEngagementConcurrencyTests {

    @Autowired
    private TrackEngagementService trackEngagementService;

    @Autowired
    private TrackImpressionService trackImpressionService;

    @Autowired
    private TrackEngagementRepository trackEngagementRepository;

    @Autowired
    private TrackImpressionRepository trackImpressionRepository;

    /**
     * Resets the counts before each test to ensure test isolation.
     */
    @BeforeEach
    void setup() {
        // Delete all documents in TrackEngagement and TrackImpression collections
        trackEngagementRepository.deleteAll();
        trackImpressionRepository.deleteAll();

        // Initialize counts to zero by creating new documents with specific IDs
        TrackEngagement engagement = new TrackEngagement();
        engagement.setId(TrackConstants.ENGAGEMENT_DOCUMENT_ID);
        engagement.setYesCount(0);
        engagement.setNoCount(0);
        trackEngagementRepository.save(engagement);

        TrackImpression impression = new TrackImpression();
        impression.setId(TrackConstants.IMPRESSION_DOCUMENT_ID);
        impression.setImpressionCount(0);
        trackImpressionRepository.save(impression);
    }

    /**
     * Tests concurrent increments of 'yes' count.
     *
     * @throws InterruptedException if thread interruption occurs.
     */
    @Test
    void testConcurrentYesIncrements() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Pool size can be adjusted

        // Initialize tasks to increment 'yes'
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackEngagementService.incrementYes());
        }

        // Shutdown executor and await termination
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished, "ExecutorService did not terminate in the specified time.");

        // Retrieve and assert the final 'yes' count
        TrackEngagement engagement = trackEngagementService.getEngagementCounts();
        assertEquals(numberOfThreads, engagement.getYesCount(), "Yes count should be " + numberOfThreads);
        assertEquals(0, engagement.getNoCount(), "No count should be 0");
    }

    /**
     * Tests concurrent increments of 'no' count.
     *
     * @throws InterruptedException if thread interruption occurs.
     */
    @Test
    void testConcurrentNoIncrements() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Pool size can be adjusted

        // Initialize tasks to increment 'no'
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackEngagementService.incrementNo());
        }

        // Shutdown executor and await termination
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished, "ExecutorService did not terminate in the specified time.");

        // Retrieve and assert the final 'no' count
        TrackEngagement engagement = trackEngagementService.getEngagementCounts();
        assertEquals(numberOfThreads, engagement.getNoCount(), "No count should be " + numberOfThreads);
        assertEquals(0, engagement.getYesCount(), "Yes count should be 0");
    }

    /**
     * Tests concurrent increments of impression count.
     *
     * @throws InterruptedException if thread interruption occurs.
     */
    @Test
    void testConcurrentImpressionIncrements() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(10); // Pool size can be adjusted

        // Initialize tasks to increment impressions
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackImpressionService.incrementImpression());
        }

        // Shutdown executor and await termination
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished, "ExecutorService did not terminate in the specified time.");

        // Retrieve and assert the final impression count
        TrackImpression impression = trackImpressionService.getImpressionCount();
        assertEquals(numberOfThreads, impression.getImpressionCount(), "Impression count should be " + numberOfThreads);
    }

    /**
     * Tests concurrent increments of 'yes', 'no', and impressions simultaneously.
     *
     * @throws InterruptedException if thread interruption occurs.
     */
    @Test
    void testConcurrentYesNoImpressionIncrements() throws InterruptedException {
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(30); // Pool size can be adjusted

        // Initialize tasks to increment 'yes'
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackEngagementService.incrementYes());
        }

        // Initialize tasks to increment 'no'
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackEngagementService.incrementNo());
        }

        // Initialize tasks to increment impressions
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> trackImpressionService.incrementImpression());
        }

        // Shutdown executor and await termination
        executorService.shutdown();
        boolean finished = executorService.awaitTermination(1, TimeUnit.MINUTES);
        assertTrue(finished, "ExecutorService did not terminate in the specified time.");

        // Retrieve and assert the final counts
        TrackEngagement engagement = trackEngagementService.getEngagementCounts();
        TrackImpression impression = trackImpressionService.getImpressionCount();

        assertEquals(numberOfThreads, engagement.getYesCount(), "Yes count should be " + numberOfThreads);
        assertEquals(numberOfThreads, engagement.getNoCount(), "No count should be " + numberOfThreads);
        assertEquals(numberOfThreads, impression.getImpressionCount(), "Impression count should be " + numberOfThreads);
    }

    /**
     * Tests behavior when engagement document is missing.
     */
    @Test
    void testIncrementYesWithMissingEngagementDocument() {
        // Delete the engagement document
        trackEngagementRepository.deleteById(TrackConstants.ENGAGEMENT_DOCUMENT_ID);

        // Attempt to increment 'yes' and expect an exception
        EngagementDocumentNotFoundException exception = assertThrows(
                EngagementDocumentNotFoundException.class,
                () -> trackEngagementService.incrementYes(),
                "Expected incrementYes() to throw, but it didn't"
        );

        assertEquals("Engagement document not found.", exception.getMessage());
    }

    /**
     * Tests behavior when impression document is missing.
     */
    @Test
    void testIncrementImpressionWithMissingImpressionDocument() {
        // Delete the impression document
        trackImpressionRepository.deleteById(TrackConstants.IMPRESSION_DOCUMENT_ID);

        // Attempt to increment impressions and expect an exception
        ImpressionDocumentNotFoundException exception = assertThrows(
                ImpressionDocumentNotFoundException.class,
                () -> trackImpressionService.incrementImpression(),
                "Expected incrementImpression() to throw, but it didn't"
        );

        assertEquals("Impression document not found.", exception.getMessage());
    }
}
