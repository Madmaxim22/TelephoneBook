package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class PhoneBookTest {

    Contact contact;
    Contact contact1;
    Contact contact2;
    PhoneBook phoneBook;

    @BeforeEach
    public void newPhoneBook() {
        // arrange
        contact = new Contact("89267095033", "Maksim");
        contact1 = new Contact("89264215237", "Kseny");
        contact2 = new Contact("89054326759", "Vladimir");
        phoneBook = new PhoneBook();
        // act
        phoneBook.addContacts(contact);
        phoneBook.addContacts(contact1);
        phoneBook.addContacts(contact2);
        phoneBook.addGroup("Work");
        phoneBook.addContactInGroup("Work", "89267095033");
    }

    @AfterEach
    public void nullPhoneBook() {
        contact = null;
        contact1 = null;
        contact2 = null;
        phoneBook = null;
    }

    @Test
    void addContactsTest() {
        // assert
        assertThat(phoneBook.getContactsList(), is(not(empty())));
        assertThat(phoneBook.getContactsList(), contains(contact, contact1, contact2));
    }

    @Test
    void removeContactsIsContactListTest() {
        // act
        phoneBook.removeContacts("89267095033");
        phoneBook.removeContacts("89264215237");
        phoneBook.removeContacts("89054326759");
        // assert
        assertThat(phoneBook.getContactsList(), not(hasItems(contact, contact1, contact2)));
    }

    @Test
    void removeContactIsGroupListTest() {
        // arrange
        phoneBook.addContactInGroup("Work", "89267095033");
        // act
        phoneBook.removeContactInGroup("89267095033");
        // assert
        assertThat(phoneBook.getGroupsList(), not(hasValue(contact)));
    }

    @Test
    void searchContactTest() {
        // assert
        assertThat(phoneBook.searchContact("89267095033"), equalTo(contact));
    }

    @Test
    void SearchForNonExistentContactTest() {
        // assert
        assertThat(phoneBook.searchContact("89267095066"), nullValue());
    }

    @Test
    void addGroupTest() {
        // assert
        assertThat(phoneBook.getGroupsList(), hasKey("Work"));
    }

    @Test
    void removeGroupTest() {
        // act
        phoneBook.removeGroup("Work");
        // assert
        assertThat(phoneBook.getGroupsList(), not(hasKey("Work")));
    }

    @ParameterizedTest
    @CsvSource({"'Friend', '89267095033'", "'Work', '89264215237'"})
    void addContactInGroupTest(String nameGroup, String number) {
        // act
        phoneBook.addContactInGroup(nameGroup, number);
        // assert
        assertTrue(phoneBook.getGroupsList().get(nameGroup).contains(contact));
    }

    @Test
    void removeContactInGroupTest() {
        // act
        phoneBook.removeContactInGroup("Work", "89267095033");
        // assert
        assertFalse(phoneBook.getGroupsList().get("Work").contains(contact));
    }

    @Test
    void showRemoveContactInGroupTest() {
        String consoleOutput = null;
        PrintStream originalOut = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(100);
            PrintStream capture = new PrintStream(outputStream);
            System.setOut(capture);
            phoneBook.removeContactInGroup("Work", "89267095033");
            capture.flush();
            consoleOutput = outputStream.toString();
            System.setOut(originalOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals("Контакт \n" +
                " Maksim   (89267095033) удален из группы Work \n", consoleOutput);
    }

    @Test
    void checkingContactInGroupTest() {
        // assert
        assertTrue(phoneBook.checkingContactInGroup("Work", contact));
    }

    @Test
    void getGroupsListTest() {
        // assert
        assertThat(phoneBook.getGroupsList(), hasKey("Work"));
    }

    @Test
    void getContactsListTest() {
        // assert
        assertThat(phoneBook.getContactsList(), is(not(empty())));
        assertThat(phoneBook.getContactsList(), contains(contact, contact1, contact2));
    }

    @Test
    void copyContactsListTest() {
        try {
            Field field = PhoneBook.class.getDeclaredField("contactsList");
            field.setAccessible(true);
            List<Contact> contacts = (List<Contact>) field.get(phoneBook);
            assertNotSame(phoneBook.getContactsList(), contacts);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}