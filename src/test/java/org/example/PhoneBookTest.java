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
        assertTrue(phoneBook.getContactsList().contains(contact));
    }

    @Test
    void removeContactsIsContactListTest() {
        // act
        phoneBook.removeContacts("89267095033");
        // assert
        assertFalse(phoneBook.getContactsList().contains(contact));
    }

    @Test
    void removeContactIsGroupListTest() {
        // arrange
        phoneBook.addContactInGroup("Work", "89267095033");
        // act
        phoneBook.removeContacts("89267095033");
        // assert
        assertFalse(phoneBook.checkingContactInGroup("Work", contact));
    }

    @Test
    void searchContactTest() {
        // assert
        assertEquals(contact, phoneBook.searchContact("89267095033"));
    }

    @Test
    void SearchForNonExistentContactTest() {
        // assert
        assertNull(phoneBook.searchContact("89267095066"));
    }

    @Test
    void addGroupTest() {
        // assert
        assertTrue(phoneBook.getGroupsList().containsKey("Work"));
    }

    @Test
    void removeGroupTest() {
        // act
        phoneBook.removeGroup("Work");
        // assert
        assertFalse(phoneBook.getGroupsList().containsKey("Work"));
    }

    @ParameterizedTest
    @CsvSource({"'Friend', '89267095033'", "'Work', '89264215237'"})
    void addContactInGroup(String nameGroup, String number) {
        // act
        phoneBook.addContactInGroup(nameGroup, number);
        // assert
        assertTrue(phoneBook.getGroupsList().get(nameGroup).contains(contact));
    }


    @Test
    void addContactInNonExistingGroupTest() {
        // assert
        assertTrue(phoneBook.getGroupsList().get("Work").contains(contact));
    }

    @Test
    void addExistingInGroupContactTest() {
        // act
        phoneBook.addContactInGroup("Work", "89267095033");
        // assert
        assertTrue(phoneBook.getGroupsList().get("Work").contains(contact));
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
        assertTrue(phoneBook.getGroupsList().containsKey("Work"));
        assertTrue(phoneBook.getGroupsList().containsValue(List.of(contact)));
    }

    @Test
    void getContactsListTest() {
        // assert
        assertTrue(phoneBook.getContactsList().contains(contact));
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