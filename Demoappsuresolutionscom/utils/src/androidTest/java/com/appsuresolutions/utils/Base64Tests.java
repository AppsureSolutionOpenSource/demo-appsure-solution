package com.appsuresolutions.utils;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.appsuresolutions.utils.entrypoint.Employee;
import com.appsuresolutions.utils.entrypoint.LibraryFacade;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Base64Tests {
    @Test
    public void testBase64Encode() throws Throwable {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String result = LibraryFacade.EncodeToBase64("test");
        assertEquals("dGVzdA==", result);
    }

    @Test
    public void testBase64Decode() throws Throwable {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String result = LibraryFacade.DecodeFromBase64("dGVzdA==");
        assertEquals("test", result);
    }


}
