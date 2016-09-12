package com.bitsfromspace.torrentrss.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author chris
 * @since 05-09-16.
 */
@SuppressWarnings("ALL")
public class CacheTest {

    private TimeProvider timeProviderMock;

    @Before
    public void setUp() throws Exception {
        timeProviderMock = mock(TimeProvider.class);
    }


    @Test

    public void testRemoveOnDelay() throws Exception {
        final Cache<String, String> cache = new Cache<>(timeProviderMock, 3, 1, TimeUnit.SECONDS);
        final Function<String,String> supplierMock = mock(Function.class);
        when(supplierMock.apply(anyString()))
                .thenReturn("string1")
                .thenReturn("string2")
                .thenReturn("string3")
                .thenReturn("string1updated")
                .thenReturn("string2updated")
                .thenReturn("string3updated");

        when(timeProviderMock.getTime()).thenReturn(0L);
        assertEquals("string1", cache.computeIfAbsent("key1", supplierMock));
        when(timeProviderMock.getTime()).thenReturn(100L);
        assertEquals("string2", cache.computeIfAbsent("key2", supplierMock));
        when(timeProviderMock.getTime()).thenReturn(200L);
        assertEquals("string3", cache.computeIfAbsent("key3", supplierMock));

        when(timeProviderMock.getTime()).thenReturn(999L);
        assertEquals("string1", cache.computeIfAbsent("key1", supplierMock));
        assertEquals("string2", cache.computeIfAbsent("key2", supplierMock));
        assertEquals("string3", cache.computeIfAbsent("key3", supplierMock));
        when(timeProviderMock.getTime()).thenReturn(1001L);
        assertEquals("string1updated", cache.computeIfAbsent("key1", supplierMock));
        assertEquals("string2", cache.computeIfAbsent("key2", supplierMock));
        assertEquals("string3", cache.computeIfAbsent("key3", supplierMock));

        when(timeProviderMock.getTime()).thenReturn(1099L);
        assertEquals("string1updated", cache.computeIfAbsent("key1", supplierMock));
        assertEquals("string2", cache.computeIfAbsent("key2", supplierMock));
        assertEquals("string3", cache.computeIfAbsent("key3", supplierMock));
        when(timeProviderMock.getTime()).thenReturn(1101L);
        assertEquals("string1updated", cache.computeIfAbsent("key1", supplierMock));
        assertEquals("string2updated", cache.computeIfAbsent("key2", supplierMock));
        assertEquals("string3", cache.computeIfAbsent("key3", supplierMock));

        when(timeProviderMock.getTime()).thenReturn(1198L);
        assertEquals("string1updated", cache.computeIfAbsent("key1", supplierMock));
        assertEquals("string2updated", cache.computeIfAbsent("key2", supplierMock));
        assertEquals("string3", cache.computeIfAbsent("key3", supplierMock));
        when(timeProviderMock.getTime()).thenReturn(1201L);
        assertEquals("string1updated", cache.computeIfAbsent("key1", supplierMock));
        assertEquals("string2updated", cache.computeIfAbsent("key2", supplierMock));
        assertEquals("string3updated", cache.computeIfAbsent("key3", supplierMock));

        verify(supplierMock, times(6)).apply(anyString());
        verifyNoMoreInteractions(supplierMock);


    }

}