package com.bitsfromspace.torrentrss.services;

import com.bitsfromspace.torrentrss.Torrent;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.Collection;

/**
 * @author chris
 * @since 01-09-16.
 */
@Component
public class TorrentRssFeedServiceImpl implements TorrentRssFeedService {

    @Override
    public String generateFeed(Collection<Torrent> torrents) throws Exception {
        //http://www.bittorrent.org/beps/bep_0036.html
        final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final Element channelElement = buildChannelElement(document);

        torrents.forEach(t -> addItem(t, document, channelElement));

        return toString(document);
    }

    private String toString(Document document) throws Exception {
        final Transformer transformer = TransformerFactory.newInstance().newTransformer();
        final DOMSource source = new DOMSource(document);
        try (final StringWriter stringWriter = new StringWriter()) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, new StreamResult(stringWriter));
            return stringWriter.getBuffer().toString();
        }
    }

    private void addItem(Torrent torrent, Document document, Element channelElement) {
        final Element title = document.createElement("title");
        title.setTextContent(torrent.getName());

        final Element enclosure = document.createElement("enclosure");
        enclosure.setAttribute("type", "application/x-bittorrent");
        enclosure.setAttribute("url", torrent.getMagnetLink());

        final Element item = document.createElement("item");
        item.appendChild(title);
        item.appendChild(enclosure);
        channelElement.appendChild(item);
    }

    private Element buildChannelElement(Document document) {
        final Element rssElement = document.createElement("rss");
        rssElement.setAttribute("version", "2.0");
        document.appendChild(rssElement);

        final Element channelElement = document.createElement("channel");
        rssElement.appendChild(channelElement);

        final Element channelTitleElement = document.createElement("title");
        channelTitleElement.setTextContent("Torrent RSS Service");
        channelElement.appendChild(channelTitleElement);

        final Element ttlElement = document.createElement("ttl");
        ttlElement.setTextContent("60");
        channelElement.appendChild(ttlElement);
        return channelElement;
    }

}
