package com.bitsfromspace.torrentrss.utils;

import java.util.Arrays;

/**
 * @author chris
 * @since 01-09-16.
 */
public class MovieTitleParser {

    private static final String[] TITLE_TERMINATORS = {
        "nl", "dvdrip", "rental", "dvd5", "avi", "retail", "mp4", "dutch", "1080p", "1080u", "720p", "720i", "brrip"

    };

    public static String parseMovieTitle(final String torrentName){
        String movieName = torrentName.toLowerCase();
        movieName = movieName.replaceAll("\\[.*\\]", " ");
        movieName = movieName.replaceAll("-", " ");
        movieName = movieName.replaceAll("[^a-zA-Z0-9 -]", " ");
        movieName = movieName.replaceAll("(19|20)[0-9][0-9].*", "");
        movieName = movieName.replace("  ", "");
        final int cutOff = Arrays.stream(TITLE_TERMINATORS).mapToInt(movieName::indexOf).filter(i->i>3).min().orElse(-1);
        if (cutOff != -1){
            movieName = movieName.substring(0, cutOff);
        }


        return movieName.trim();

    }
}
