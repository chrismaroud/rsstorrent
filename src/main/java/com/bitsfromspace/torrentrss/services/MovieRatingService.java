package com.bitsfromspace.torrentrss.services;

import javax.validation.constraints.Null;

/**
 * @author chris
 * @since 01-09-16.
 */
public interface MovieRatingService {

    @Null Double getRating(String movieTitle) throws Exception;
}
