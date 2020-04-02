package com.appsuresolutions.utils;

import com.appsuresolutions.utils.entrypoint.LibraryFacade;

import org.junit.Test;
//import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class AesTest {
    @Test
    public void AesEncryptionTest() throws Throwable {
        final String input = "plain";
        final char[] pin = new char[]{'p','i','n'};
        final String output = LibraryFacade.EncryptAes(input,pin);
        assertEquals("f418e5567b7f8cddcccc909e5f010de6",output);
    }

    @Test
    public void AesDecryptionTest() throws Throwable {
        final String input = "f418e5567b7f8cddcccc909e5f010de6";
        final char[] pin = new char[]{'p','i','n'};
        final String output = LibraryFacade.DecryptAes(input,pin);
        assertEquals("plain",output);
    }
}