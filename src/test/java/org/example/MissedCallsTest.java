package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MissedCallsTest {
    PhoneBook phoneBook;
    Contact contact;
    MissedCalls missedCalls;

    @BeforeEach
    void newMissedCalls() {
        // arrange
        phoneBook = new PhoneBook();
        contact = new Contact("89267095033", "Maksim");
        missedCalls = new MissedCalls(phoneBook);
    }

    @Test
    void addMissedCallsTest() {
        try {
            // act
            Method method = MissedCalls.class.getDeclaredMethod("addMissedCalls", String.class);
            method.setAccessible(true);
            method.invoke(missedCalls, "89267095033");
            // assert
            assertTrue(missedCalls.missedCalls.containsValue("89267095033"));
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}