package ru.naumen.collection.task2;


import java.util.*;

/**
 * Дано:
 * <pre>
 * public class User {
 *     private String username;
 *     private String email;
 *     private byte[] passwordHash;
 *     …
 * }
 * </pre>
 * Нужно реализовать метод
 * <pre>
 * public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB);
 * </pre>
 * <p>который возвращает дубликаты пользователей, которые есть в обеих коллекциях.</p>
 * <p>Одинаковыми считаем пользователей, у которых совпадают все 3 поля: username,
 * email, passwordHash. Дубликаты внутри коллекций collA, collB можно не учитывать.</p>
 * <p>Метод должен быть оптимален по производительности.</p>
 * <p>Пользоваться можно только стандартными классами Java SE.
 * Коллекции collA, collB изменять запрещено.</p>
 *
 * См. {@link User}
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class Task2
{

    /**
     * Возвращает дубликаты пользователей, которые есть в обеих коллекциях
     */
    public static List<User> findDuplicates(Collection<User> collA, Collection<User> collB) {
        List<User> duplicateUsers = new ArrayList<>(Math.min(collA.size(), collB.size()));
        //Использую ArrayList, потому что удобно хранить элементы
        // + добавление элементов в данном случае работает за O(1)
        Set<User> uniqueUsersCollA = new HashSet<>(collA);
        //при создании hashset происходит итерация по всем элементам collA, сложность O(n)

        //Используется HashSet, потому что хранит только уникальные элементы.
        //HashSet использует хэш-таблицу для хранения элементов, из-за чего contains выполняется за O(1)
        //сложность O(n) в худшем случае достигается при наличии коллизий
        for (User user: collB) { //Проходим по всей коллекции O(n)
            if (uniqueUsersCollA.contains(user)) { //O(1)
                duplicateUsers.add(user);
            }
        }

        return duplicateUsers;
    }
}
//Общая сложность O(2n)