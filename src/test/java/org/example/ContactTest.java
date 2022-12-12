package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

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
        assertThat(contact.getNumber(), equalTo("89267095033"));
    }

    @Test
    void setNumberTest() {
        // act
        contact.setNumber("89003007007");
        // assert
        assertThat(contact.getNumber(), equalTo("89267095033"));
    }

    @Test
    void setNumberIfEmptyTest() {
        // arrange
        contact = new Contact("", "Maksim");
        // act
        contact.setNumber("89003007007");
        // assert
        assertThat(contact.getNumber(), equalTo("89003007007"));
    }

    @Test
    void getNameTest() {
        // assert
        assertThat(contact.getName(), equalTo("Maksim"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Jon", "Sony", "Petr"})
    void setNameMultipleValuesTest(String name) {
        contact.setName(name);
        assertThat(contact.getName(), equalTo(name));
    }
}