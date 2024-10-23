package ru.naumen.collection.task2;

import java.util.Arrays;

/**
 * Пользователь
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class User {
    private String username;
    private String email;
    private byte[] passwordHash;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;
        return username.equals(user.username) &&
                email.equals(user.email) &&
                Arrays.equals(passwordHash, ((User) o).passwordHash);
    }

    @Override
    public int hashCode() {
        return 31 * username.hashCode() + email.hashCode() + Arrays.hashCode(passwordHash);
    }
}
