package edu.itstep;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
    private final static String URL = "jdbc:postgresql://localhost/millionaire";
    private final static String USER_DB = "postgres";
    private final static String PASSWORD = "7777";

    public static void createMillionaireTable() {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USER_DB, PASSWORD);
            statement = connection.createStatement();
            statement.execute("CREATE TABLE if not exists questions(id BIGSERIAL PRIMARY KEY NOT NULL, question VARCHAR NOT NULL, correctans VARCHAR NOT NULL, ans1 VARCHAR NOT NULL, ans2 VARCHAR NOT NULL, ans3 VARCHAR NOT NULL)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void insertQuestion(Question question) {
        try (Connection connection = DriverManager.getConnection(URL, USER_DB, PASSWORD);
             Statement statement = connection.createStatement()) {
            //statement.execute("INSERT INTO ");
            String sql = String.format("INSERT INTO questions(question, correctans, ans1, ans2, ans3) VALUES('%s','%s','%s','%s','%s')",
                    question.getQuestion(),
                    question.getRightAnswer(),
                    question.getAnswer1(),
                    question.getAnswer2(),
                    question.getAnswer3()
            );
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Question> getQuestions() {
        String separator = ";";
        ArrayList<Question> questions = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER_DB, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM questions")) {
            while (resultSet.next()) {
                //long id = resultSet.getLong(1);
                String question = resultSet.getString("question");
                String correctAns = resultSet.getString("correctans");
                String ans1 = resultSet.getString("ans1");
                String ans2 = resultSet.getString("ans2");
                String ans3 = resultSet.getString("ans3");
                questions.add(new Question(question + separator, correctAns + separator, ans1 + separator, ans2 + separator, ans3 + separator));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static void deleteQuestionById(long id) {
        try (Connection connection = DriverManager.getConnection(URL, USER_DB, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE from questions WHERE id = " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
