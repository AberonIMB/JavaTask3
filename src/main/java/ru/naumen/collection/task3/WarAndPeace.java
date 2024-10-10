package ru.naumen.collection.task3;


import java.nio.file.Path;
import java.util.*;

/**
 * <p>Написать консольное приложение, которое принимает на вход произвольный текстовый файл в формате txt.
 * Нужно собрать все встречающийся слова и посчитать для каждого из них количество раз, сколько слово встретилось.
 * Морфологию не учитываем.</p>
 * <p>Вывести на экран наиболее используемые (TOP) 10 слов и наименее используемые (LAST) 10 слов</p>
 * <p>Проверить работу на романе Льва Толстого “Война и мир”</p>
 *
 * @author vpyzhyanov
 * @since 19.10.2023
 */
public class WarAndPeace
{
    private static final Path WAR_AND_PEACE_FILE_PATH = Path.of("src/main/resources",
            "Лев_Толстой_Война_и_мир_Том_1,_2,_3,_4_(UTF-8).txt");

    public static void main(String[] args) {
        Map<String, Integer> wordsFrequency = new HashMap<>(); //стандартный map для подсчета частоты слов в тексте

        Queue<Map.Entry<String, Integer>> mostUsedWords = new PriorityQueue<>(
                (e1, e2) -> e1.getValue().compareTo(e2.getValue())
        );
        Queue<Map.Entry<String, Integer>> mostUnusedWords = new PriorityQueue<>(
                (e1, e2) -> e2.getValue().compareTo(e1.getValue())
        );

        /**
         * Очередь с приоритетом удобна, потому что вставка и удаление происходят с логарифмической сложностью,
         * а доступ к наименьшему/наибольшему элементу — за константное время
         * (очень надеюсь,что я правильно понял тему про сложности)
         */
        new WordParser(WAR_AND_PEACE_FILE_PATH)
                .forEachWord(word -> {
                    wordsFrequency.put(word, wordsFrequency.getOrDefault(word, 0) + 1);
                }); // проходим по каждому слову, сложность O(n)

        for (Map.Entry<String, Integer> entry: wordsFrequency.entrySet()) {
            //проходим по всем элементам wordsFrequency,в худшем случае сложность O(n),если все слова уникальные
            createTop10(mostUsedWords, entry);
            createTop10(mostUnusedWords, entry);
            /**
             * сложность удаления и вставки log(n),в нашем случае log(10) - примерно 3,
             * то есть сложность createTop10 = 3 * n(количество итераций),а так как очереди две,
             * сложность всего этого алгоритма 6n
             */
        }

        for (Map.Entry<String, Integer> entry: mostUsedWords.stream().sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())).toList()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        for (Map.Entry<String, Integer> entry: mostUnusedWords) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void createTop10(Queue<Map.Entry<String, Integer>> queue, Map.Entry<String, Integer> entry) {
        queue.add(entry);

        if (queue.size() > 10) {
            queue.remove();
        }
    }

    // Общая сложность 7n
}