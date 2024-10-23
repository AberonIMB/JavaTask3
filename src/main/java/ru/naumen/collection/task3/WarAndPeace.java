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
        Map<String, Integer> wordsFrequency = new LinkedHashMap<>();
        //HashMap используется для ассоциации слова с частотой его использования.
        // Выбрал LinkedHashMap, потому что она использует связный список для хранения порядка элементов,
        // что делает итерацию быстрее по сравнению с HashMap, которая итерируется по бакетам

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

        List<Map.Entry<String, Integer>> top10Words = new LinkedList<>();
        //Использую LinkedList, что бы удобно выводить топ 10 слов.
        // Вставка элемента в начало связного списка работает за O(1);
        while (!mostUsedWords.isEmpty()) {
            Map.Entry<String, Integer> entry = mostUsedWords.poll(); //O(log(n)), где n = 10
            top10Words.addFirst(entry); //O(1)
        }

        for (Map.Entry<String, Integer> entry: top10Words) { //O(10)
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        while (!mostUnusedWords.isEmpty()) {
            Map.Entry<String, Integer> entry = mostUnusedWords.poll(); //O(log(n)), где n = 10
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void createTop10(Queue<Map.Entry<String, Integer>> queue, Map.Entry<String, Integer> entry) {
        queue.add(entry); //O(log n), так как PriorityQueue реализована на основе кучи

        if (queue.size() > 10) {
            queue.remove();
        } //(делал поздно вечером, надеюсь ничего не забыл)
    }
    // Общая сложность 7n
}