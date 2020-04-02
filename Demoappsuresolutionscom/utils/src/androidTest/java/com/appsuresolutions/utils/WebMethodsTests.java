package com.appsuresolutions.utils;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.appsuresolutions.utils.entrypoint.LibraryFacade;
import com.appsuresolutions.utils.entrypoint.Employee;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class WebMethodsTests {


    @Test
    public void getEmployeesSuccess() throws Throwable {
        final List<Employee> list = LibraryFacade.GetEmployees();
        assertNotNull("No employee data",list);
    }

    @Test
    public void updateEmployeeSalary() throws Throwable {
        final List<Employee> employeeList = LibraryFacade.GetEmployees();
        assertNotNull("No employee data",employeeList);
        final Employee employee = employeeList.get(0);
        LibraryFacade.UpdateSalary(employee,9999);
    }
}
