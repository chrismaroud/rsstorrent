package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.utils.TimeProviderImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author chris
 * @since 01-09-16.
 */
public class OmdbMovieRatingServiceTest {

    @Test
    public void test() throws Exception {
        OmdbMovieRatingService omdbMovieRatingService = new OmdbMovieRatingService(new TimeProviderImpl(), 1);
        assertEquals(7.3, omdbMovieRatingService.getRating("frozen"), 0.9);
        assertNull(omdbMovieRatingService.getRating("chris123"));

    }
}