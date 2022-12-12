package org.example;

import java.util.*;

public class PhoneBook {

    private final Map<String, List<Contact>> groupsList = new HashMap<>();

    private final List<Contact> contactsList = new ArrayList<>();

    // добавление нового контакта
    public void addContacts(Contact... contacts) {
        contactsList.addAll(Arrays.asList(contacts));
    }

    // удаление контакта из списка контактов и групп
    public void removeContacts(String number) {
        if (contactsList.isEmpty()) return;
        for (Contact contact : contactsList) {
            if (contact.getNumber().equals(number)) {
                contactsList.remove(contact);
                System.out.println("Контакт " + contact + " удален из списка контактов!");
                for (String group : groupsList.keySet()) {
                    if (checkingContactInGroup(group, contact)) {
                        groupsList.get(group).remove(contact);
                        System.out.printf("Контакт %s удален из группы %s %n", contact, group);
                    }
                }
            }
            return;
        }
    }

    // поиск по номеру
    public Contact searchContact(String number) {
        for (Contact contact : contactsList) {
            if (contact.getNumber().equals(number)) {
                return contact;
            }
        }
        return null;
    }

    // добавдение группы
    public void addGroup(String nameGroup) {
        if (!groupsList.containsKey(nameGroup)) {
            groupsList.put(nameGroup, new ArrayList<>());
        }
    }

    // удаление группы
    public void removeGroup(String nameGroup) {
        groupsList.remove(nameGroup);
    }

    // добавление контакта(ов) в группу
    public void addContactInGroup(String nameGroup, String... numbers) {
        if (!groupsList.containsKey(nameGroup)) {
            addGroup(nameGroup);
        }
        for (String number : numbers) {
            for (Contact contact : getContactsList()) {
                if (contact.getNumber().equals(number)) {
                    if (checkingContactInGroup(nameGroup, contact)) {
                        System.out.println("Контак уже добавлен в группу " + nameGroup);
                        break;
                    }
                    groupsList.get(nameGroup).add(contact);
                    System.out.printf("%s добавлен в группу %s \n", contact, nameGroup);
                }
            }
        }
    }

    // удаление контакта(ов) в группе
    public void removeContactInGroup(String nameGroup, String... numbers) {
        for (String number : numbers) {
            for (Contact contact : getContactsList()) {
                if (contact.getNumber().equals(number)) {
                    if (checkingContactInGroup(nameGroup, contact)) {
                        groupsList.get(nameGroup).remove(contact);
                        System.out.printf("Контакт %s удален из группы %s %n", contact, nameGroup);
                        return;
                    } else {
                        System.out.println("Такого контакта нет в группе " + nameGroup);
                    }
                }
            }
        }
    }

    // проверка наличия контакта в группе
    public boolean checkingContactInGroup(String nameGroup, Contact contact) {
        for (int i = 0; i < groupsList.size(); i++) {
            List<Contact> list = groupsList.get(nameGroup);
            if (groupsList.get(nameGroup).isEmpty()) {
                return false;
            }
            if (list.contains(contact)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, List<Contact>> getGroupsList() {
        return groupsList;
    }

    public List<Contact> getContactsList() {
        return List.copyOf(contactsList);
    }

    @Override
    public String toString() {
        return "PhoneBook{" +
                "groupsList=" + groupsList +
                ", contactsList=" + contactsList +
                '}';
    }
}



