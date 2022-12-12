package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContactTest {

    Contact contact;

    @BeforeEach
    public void newCotact() {
        // arrange
        contact = new Contact("89267095033", "Maksim");
    }

    @AfterEach
    public void nullContact() {
        contact = null;
    }

    @Test
    void getNumberTest() {
        // assert
        assertEquals("89267095033", contact.getNumber());
    }

    @Test
    void setNumberTest() {
        // act
        contact.setNumber("89003007007");
        // assert
        assertEquals("89267095033", contact.getNumber());
    }

    @Test
    void setNumberIfEmptyTest() {
        // arrange
        contact = new Contact("", "Maksim");
        // act
        contact.setNumber("89003007007");
        // assert
        assertEquals("89003007007", contact.getNumber());
    }

    @Test
    void getNameTest() {
        // assert
        assertEquals("Maksim", contact.getName());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jon", "Sony", "Petr"})
    void setNameMultipleValuesTest(String name) {
        contact.setName(name);
        assertEquals(name, contact.getName());
    }
}