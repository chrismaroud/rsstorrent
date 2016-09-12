package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.Torrent;

import java.util.Collection;

/**
 * @author chris
 * @since 01-09-16.
 */
public interface TorrentRssFeedService {

    String generateFeed(Collection<Torrent> torrents) throws Exception;
}
