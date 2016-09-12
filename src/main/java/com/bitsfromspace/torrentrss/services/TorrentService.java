package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.Torrent;

import java.util.List;

/**
 * @author chris
 * @since 01-09-16.
 */
public interface TorrentService {
    List<Torrent> searchLatestTorrents(String search) throws Exception;
}
