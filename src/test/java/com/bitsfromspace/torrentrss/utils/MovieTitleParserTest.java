package com.bitsfromspace.torrentrss.utils;

import org.junit.Test;

import static org.junit.Assert.*;
import static com.bitsfromspace.torrentrss.utils.MovieTitleParser.*;

/**
 * @author chris
 * @since 01-09-16.
 */
public class MovieTitleParserTest {

    @Test
    public void testIt() throws Exception {

        assertEquals("triple 9", parseMovieTitle("Triple 9 retail nl dvdrip [eagle]"));
        assertEquals("manhattan nocturne", parseMovieTitle("Manhattan Nocturne retail nl [eagle]"));
        assertEquals("manhattan nocturne", parseMovieTitle("Manhattan Nocturne retail nl eagle.[PRiME]"));
        assertEquals("kollektivet", parseMovieTitle("KOLLEKTIVET_NL-RENTAL_DVD5"));
        assertEquals("de grote zwaen", parseMovieTitle("De Grote Zwaen retail nl [eagle]"));
        assertEquals("meneer pluizenbol", parseMovieTitle("Meneer.Pluizenbol.2016.Dutch.NL.HD-TS.x264.AC3-MMF.mkv"));
        assertEquals("inside men", parseMovieTitle("Inside Men retail nl[eagle]"));
        assertEquals("10 cloverfield land", parseMovieTitle("10 Cloverfield Land, 2016. 1080p. NL subs"));
        assertEquals("the hunger games mockingjay part 2", parseMovieTitle("The.Hunger.Games.Mockingjay.Part.2.2015.720p.BRRip.NL subs-EVO"));
        assertEquals("robinson crusoe", parseMovieTitle("ROBINSON_CRUSOE_NL-RENTAL_DVD5"));
        assertEquals("meneer pluizenbol", parseMovieTitle("Meneer.Pluizenbol.2016.FLEMiSH.NL.TS.XViD.AC3-EXViD.avi"));
        assertEquals("the shamers daughter", parseMovieTitle("THE_SHAMERS_DAUGHTER_NL-RENTAL_DVD5"));
        assertEquals("woezel en pip op zoek sloddervos", parseMovieTitle("WOEZEL_EN_PIP_OP_ZOEK_SLODDERVOS_NL-RENTAL_AVI"));
        assertEquals("boulevard", parseMovieTitle("Boulevard (2014) BRRip (xvid) NL Subs. DMT"));
        assertEquals("shaun het schaap de film", parseMovieTitle("Shaun Het Schaap De Film 2015 NL DUTCH nederlands gesproken.mp4"));
        assertEquals("universal soldier day of reckoning", parseMovieTitle("Universal.Soldier;Day.of.Reckoning.2012.NL-subs.bdr.xvid"));
        assertEquals("isaksen slangeb reren 13e sterrenbeeld", parseMovieTitle("Isaksen Slangebæreren - 13e Sterrenbeeld DvdRip avi nl"));
        assertEquals("hobbit battle of the five armies", parseMovieTitle("Hobbit - battle of the five armies 1080p NL subs"));
        assertEquals("the curse of sleeping beauty", parseMovieTitle("[ www.torrenting.com ] - THE_CURSE_OF_SLEEPING_BEAUTY_NL-RENTAL_AVI"));

    }
}