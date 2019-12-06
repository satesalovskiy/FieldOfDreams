package com.tsa;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Character.toUpperCase;
import static java.lang.System.exit;

public class Game {

    private int numberOfPlayers;
    private String task;
    private String answer;
    private char[] progress;
    private Player[] players;
    private boolean difficult;
    private boolean endGame = false;
    private Drum drum;
    private Scanner scanner;
    private int winner = 0;

    public void start() {
        Random random = new Random();
        Data data;

        //Барабан
        drum = new Drum();

        //Сканер для получения введенных данных с консоли
        scanner = new Scanner(System.in);

        System.out.println("Welcome bodies! \nHow much players want to play?");

        //Получение количества игроков
        boolean correct = false;
        while (correct == false) {
            try {
                numberOfPlayers = scanner.nextInt();
                correct = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect input. Try again");
                scanner.next();
                continue;
            }
        }

        //Инициализация массива игроков
        initializationOfPlayers(numberOfPlayers);

        //Определение сложности
        System.out.println("Want a difficult game? true/false");
        correct = false;
        while (correct == false) {
            try {
                difficult = scanner.nextBoolean();
                correct = true;
            } catch (InputMismatchException e) {
                System.out.println("Incorrect complexity input. Try again");
                scanner.next();
                continue;
            }
        }

        //Создание объекта, содержащего задания для игры с нужным нам уровнем сложности
        data = new Data(difficult);

        int randomTaskNumber = random.nextInt(3);

        //Получение задания
        task = data.getTask(randomTaskNumber);
        answer = data.getAnswer(randomTaskNumber);

        //Создание строки, в которой будет отображаться прогресс отгадывания слова
        initializationOfProgress(answer.length());

        //Игроки играют пока не настанет конец игры
        while (endGame == false) {

            for (int i = 0; i < players.length; i++) {
                System.out.println("Your turn player " + players[i].getName());
                System.out.println("Rolling drum..");

                //Определение выпавшего числа на барабане
                readDrum(drum.roll(), i);
                System.out.println("Now u have " + players[i].getScore() + " points");

                //Напоминание задания и текущего погресса в слове
                printTaskAndProgress();

                //Ход игрока
                playerStep(i);
            }
        }

        //Конец игры
        System.out.println("Congratulations " + players[winner].getName() + " you won! Total Points: " + players[winner].getScore());
        exit(1);
    }

    private void initializationOfProgress(int size) {

        //Изначально прогресс отгадывания слова 0 и отображаются только *
        progress = new char[size];
        for (int i = 0; i < progress.length; i++) {
            progress[i] = '*';
        }
    }

    private void initializationOfPlayers(int number) {
        players = new Player[number];
        Scanner scanner = new Scanner(System.in);

        //Кладем пустые объекты игроков в массив
        for (int i = 0; i < number; i++) {
            players[i] = new Player();
        }

        //Заполняем имена и ставим начальный счет
        for (int i = 0; i < players.length; i++) {
            System.out.println("Enter name of player " + (i+1));
            String name = scanner.next();
            players[i].setName(name);
            players[i].setScore(0);

        }
    }

    private void readDrum(int numberOnDrum, int player) {
        switch (numberOnDrum) {
            case 0:
                System.out.println("You got 10 points!");
                players[player].increaseScore(10);
                break;
            case 1:
                System.out.println("You got 30 points!");
                players[player].increaseScore(30);
                break;
            case 2:
                System.out.println("X2!!!");
                int newScore = players[player].getScore();
                newScore = newScore * 2;
                players[player].setScore(newScore);
                break;
            case 3:
                System.out.println("You got nothing unlucky :(");
                break;
        }
    }

    private void printTaskAndProgress() {
        System.out.println("Task: " + task);
        System.out.print("Progress: ");
        for (int i = 0; i < progress.length; i++) {
            System.out.print(progress[i]);
        }
        System.out.println();
    }

    private void playerStep(int player) {

        System.out.println("The whole word or only one letter? Type 1/2");
        int answer = 0;

        //Считываем вариант отгадывания слова
        boolean correct = false;
        while (correct == false) {
            try {
                answer = scanner.nextInt();

                //Если ответ вне диапазона доступных
                if (answer != 1 && answer != 2) {
                    System.out.println("There is no such option. Try again");
                    continue;
                }
                correct = true;
            } catch (InputMismatchException e) {
                System.out.println("Wrong parameter. Try again");
                scanner.next();
                continue;
            }
        }

        if (answer == 1) {
            enterWord(player);
        } else if (answer == 2) {
            enterLetter(player);
        }
    }

    private void enterWord(int player) {
        System.out.println("Enter word: ");
        String word = scanner.next();
        if (word.equalsIgnoreCase(answer)) {
            winner = player;
            endGame = true;
        } else {
            System.out.println("Wrong word!");
            System.out.println();
        }
    }

    private void enterLetter(int player) {
        System.out.println("Enter letter: ");
        Character letter = scanner.next().charAt(0);
        int totalEntries = 0;

        //Проверяем наличие введенной буква в нашем слове
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == letter || answer.charAt(i) == toUpperCase(letter)) {
                totalEntries++;
                progress[i] = letter;
            }
        }

        //Если ни одного совпадения - передаем ход следующему игроку
        //Иначе смотрим сколько закрытых букв осталось
        if (totalEntries == 0) {
            System.out.println("Wrong letter");
            System.out.println();
            return;
        } else {
            System.out.println("You right!");
            printTaskAndProgress();
            int totalStars = 0;

            //Проверяем сколько букв осталось закрыто
            for (int i = 0; i < progress.length; i++) {
                if (progress[i] == '*') {
                    totalStars++;
                }
            }

            //Если все буквы открыты то победа
            if (totalStars == 0) {
                winner = player;
                endGame = true;
            } else {
                playerStep(player);
            }
        }
    }
}
