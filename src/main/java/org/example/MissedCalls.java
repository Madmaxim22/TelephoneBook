package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class MissedCalls {

    private final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private final PhoneBook phoneBook;
    NavigableMap<LocalDateTime, String> missedCalls = new TreeMap<LocalDateTime, String>().descendingMap();

    public MissedCalls(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
    }

    private void addMissedCalls(String number) {
        missedCalls.put(LocalDateTime.now(), number);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<LocalDateTime, String> entry : missedCalls.entrySet()) {
            Contact contact = phoneBook.searchContact(entry.getValue());
            builder
                    .append(DATE_TIME_FORMATTER.format(entry.getKey()))
                    .append(" -> ")
                    .append(contact != null ? contact.getName() : entry.getValue())
                    .append("\n");
        }
        return builder.toString();
    }
}
