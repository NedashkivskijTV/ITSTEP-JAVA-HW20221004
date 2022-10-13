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
        //List<Question> objectList = read_from_file(); // первинний код - читання з файлу
        List<Question> objectList = read_from_DB(); // отримання списку питань з БД

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
        if (isGame == true) {
            System.out.println("Congratulations, " + user.getName() + "!" + "You win this game!");
        }
    }

    // отримання списку питань з БД
    public List<Question> read_from_DB() {
        Db.createMillionaireTable();
        //questionsLoudToDb(); // завантаження списку 15 питань до БД

        List<Question> objectList = Db.getQuestions();
        Collections.shuffle(objectList);

        return objectList;
    }

    // видалення списку питань за діапазоном
    public static void dellQuestions(int start, int end) {
        for (int i = start; i <= end; i++) {
            Db.deleteQuestionById(i);
        }
    }

    // первинний код - отримання списку питань з файлу
    public List<Question> read_from_file() {
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
        //objectList.forEach(System.out::println);
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

    // завантаження блоку з 15 питань до БД
//    public static void questionsLoudToDb(){
//        ArrayList<Question> questions = new ArrayList<>();
//        questions.add(new Question("Скільки років нараховують з дня затвердження Незалежності України", "31", "18", "27", "30"));
//        questions.add(new Question("Яка кількість обєктів Світової спадщини ЮНЕСКО знаходиться в нашій країні","5","4","3","6"));
//        questions.add(new Question("Скільки памятників Шевченку можна нарахувати по всьому світу", "Більше 1300", "Більше 300","Більше 2000","Більше 500"));
//        questions.add(new Question("Ширина рекордно великої площі Свободи в Харкові 125 метрів, а яка її довжина","750 метрів","600 метрів","400 метрів","500 метрів"));
//        questions.add(new Question("Із якою масою вантажу літак Ан-225 «Мрія» могла впоратись","Понад 400 тонн","Понад 200 тонн","Понад 100 тонн","Понад 600 тонн"));
//        questions.add(new Question("Яка точна глибина станції метро «Арсенальна» у Києві","105,5 метрів","95 метрів","90 метрів","80,5 метрів"));
//        questions.add(new Question("Найстарішим дубом України, як відомо, є дуб Максима Залізняка — одне з природних чудес України. Скільки йому років","Понад 1100 років","Понад 1000 років","Понад 900 років","Понад 800 років"));
//        questions.add(new Question("Скільки кілометрів у довжину сягає найдовша печера «Оптимістична» у Тернопільській області","216 км","516 км","116 км","106 км"));
//        questions.add(new Question("Створений українцями рекордно великий келих шампанського важить 23 кг, а скільки ж він вміщує літрів","Близько 57 литрів","Близько 77 литрів","Близько 67 литрів","Близько 47 литрів"));
//        questions.add(new Question("Який з князів був вбитий древлянами через надто велику данину","Ігор","Олег","Святослав","Аскольд"));
//        questions.add(new Question("Яке місто першим отримало Магдебурзьке право – правову систему міського самоврядування","Львів","Галич","Київ","Луцьк"));
//        questions.add(new Question("Юрій Хмельницький, Павло Тетеря, Григорій Сагайдачний, Данило Апостол – хто з них не був гетьманом","Григорій Сагайдачний","Юрій Хмельницький","Павло Тетеря","Данило Апостол"));
//        questions.add(new Question("Назви скількох українських областей не збігаються з назвами їхніх обласних центрів?","чотирьох","двох","трьох","однієї"));
//        questions.add(new Question("Скільки разів Україна вигравала на Євробаченні","3","2","1","0"));
//        questions.add(new Question("Пісня-символ Помаранчевого майдану","Ґринджоли – \"Разом нас багато\"","Скрябін \"Руїна\"","Океан Ельзи – \"Відпусти\"","Kalush Orchestra - \"Стефанія\""));
//
//        for (Question question : questions) {
//            Db.insertQuestion(question);
//        }
//    }

}
