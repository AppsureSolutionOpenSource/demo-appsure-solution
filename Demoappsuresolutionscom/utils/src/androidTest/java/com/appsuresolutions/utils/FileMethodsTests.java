package com.appsuresolutions.utils;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.appsuresolutions.utils.entrypoint.LibraryFacade;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class FileMethodsTests {
    @Test
    public void ListFilesSuccess() throws Throwable {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        final List<File> list = LibraryFacade.ListFiles(appContext);
        assertNotNull("No files in list.",list);
    }

    @Test
    public void SaveStringToFileSuccess() throws Throwable {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        final String filePath = LibraryFacade.SaveStringToFileAsUtf8(appContext,"test");
        assertNotNull("File was not saved.",filePath);
    }

    @Test
    public void SaveZeroLengthStringToFile() throws Throwable {
        Throwable t = null;
        try {
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            final String filePath = LibraryFacade.SaveStringToFileAsUtf8(appContext, null);
        }catch (Throwable err){
            t = err;
        }
        assertNotNull("Expecting a null pointer exception.",t);
        assertTrue(t instanceof NullPointerException);
    }

    @Test
    public void SaveEmptyStringToFileSuccess() throws Throwable {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        final String filePath = LibraryFacade.SaveStringToFileAsUtf8(appContext,"");
        assertNotNull("File was not saved.",filePath);
    }
}
