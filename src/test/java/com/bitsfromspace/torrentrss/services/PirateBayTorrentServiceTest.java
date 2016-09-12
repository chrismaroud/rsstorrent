package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.utils.TimeProviderImpl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author chris
 * @since 01-09-16.
 */
public class PirateBayTorrentServiceTest {

    @Test
    public void testSmth() throws Exception {
        PirateBayTorrentService torrentService = new PirateBayTorrentService(new TimeProviderImpl(), 1

        );
        torrentService.searchLatestTorrents("nl");

    }
}