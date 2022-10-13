package edu.itstep;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    private Question questions = new Question();
    private User user = new User();
    private static final String FILE_NAME = "./Questions.txt";
    private static final int PRIZE = 100;
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        user.setName(name);
        //створення списку з обєктами, які зчитані з файлу
        List<Question> objectList = read_from_file();
        // створення хеш-таблиці з питаннями
        Map<String, List<String>> fList = new HashMap<String, List<String>>();
        for (Question q : objectList) {
            List<String> temp = new ArrayList<>();
            temp.add(q.getRightAnswer());
            temp.add(q.getAnswer1());
            temp.add(q.getAnswer2());
            temp.add(q.getAnswer3());
            fList.put(q.getQuestion(), temp);
        }
        int countQuestions = 0;
        boolean isGame = true;
        Iterator<Map.Entry<String, List<String>>> entries = fList.entrySet().iterator();
        while (entries.hasNext() && isGame) {
            Map.Entry<String, List<String>> entry = entries.next();
            //виведення одного питання для користувача
            countQuestions++;
            System.out.println("Питання " + countQuestions + " - " + entry.getKey());
            List<String> temp = entry.getValue();
            String[] answers_temp = temp.toString().split(";");
            String rigth_answer = answers_temp[0];
            rigth_answer = rigth_answer.substring(1);
            //рандомізація правильний відповідей для користувача
            Collections.shuffle(temp);
            answers_temp = temp.toString().split(";");
            //виведення варіантів відповідей
            int count = 1;
            for (String t : temp) {
                System.out.println(count + " - " + t);
                count++;
            }
            //введення корисувачем відповіді
            System.out.println("Enter your answer: ");
            int k = scanner.nextInt();
            //перевірка відповіді, якщо правильна + 100 грн, ні - програш
            isGame = check_answers(answers_temp, rigth_answer, k);
            if (isGame == false) {
                break;
            }
        }
        if(isGame == true){
            System.out.println("Congratulations, " + user.getName() +"!"+ "You win this game!");
        }
    }

    public List<Question> read_from_file(){
        ArrayList<String> arrayList = null;
        try (Stream<String> stream = Files.lines(Paths.get(FILE_NAME))) {
            arrayList = stream
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            // Handle a potential exception
        }
        Collections.shuffle(arrayList);
        List<Question> objectList = new ArrayList<Question>();
        for (String t : arrayList) {
            objectList.add(new Question(t));
        }
        return objectList;
    }

    public boolean check_answers(String[] answers_temp, String rigth_answer, int k) {
        //перевірка відповіді, якщо правильна + 100 грн, ні - програш
        //виведення балів і виграшу користувача
        boolean isGame = true;
        if (answers_temp[k - 1].contains(rigth_answer)) {
            System.out.println("It`s right answer");
            user.setPrizeMoney(user.getPrizeMoney() + PRIZE);
            System.out.println("Your prize is - " + user.getPrizeMoney());
        } else {
            isGame = false;
            System.out.println("It`s not a right answer! You lose this game.");
        }
        return isGame;
    }
}
