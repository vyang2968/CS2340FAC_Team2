package com.example.sprintproject;

import static org.junit.Assert.assertSame;

import com.example.sprintproject.model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class UserUnitTests {
    private User user;
    @Before
    public void init() {
        user = new User();
    }

    @Test
    public void testSetEmail() {
        String testEmail = "john123@example.com";

        user.setEmail(testEmail);
        String returnedEmail = user.getEmail();

        assertSame(returnedEmail, testEmail);

    }

    @Test
    public void testUsername() {
        String testEmail = "john123@example.com";
        String expectedUsername = "john123";

        user.setEmail(testEmail);
        String actualUsername = user.getUsername();

        assertSame(expectedUsername, actualUsername);

    }

    @Test
    public void testSetStartDate() {
        Date startDate = new Date(2000, 02, 11);

        user.setStartDate(startDate);
        Date returnedDate = user.getStartDate();

        assertSame(startDate, returnedDate);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void testEmailWithNoAtSymbol() {
        user.setEmail("invalidEmail");
    }

}
