package com.tsa;

import java.util.ArrayList;

public class Data {
    private boolean difficult = false;

    //Коллекция с простыми заданиями и еще одна с ответами к ними
    private ArrayList<String> simpleTasks;
    private ArrayList<String> simpleTasksAnswers;

    //Так же для сложных заданий
    private ArrayList<String> complicatedTasks;
    private ArrayList<String> complicatedTasksAnswers;

    //Конструктор по-умолчанию private, для того, чтобы избежать создания данных для игры без выбора сложности
    private Data() {
    }

    public Data(boolean difficult) {

        this.difficult = difficult;

        if (difficult == false) {
            //Инициализируем простой массив
            simpleTasks = new ArrayList<>();
            simpleTasksAnswers = new ArrayList<>();

            simpleTasks.add("У этих животных, считающихся в некоторых странах деликатесом, зубы расположены на языке");
            simpleTasksAnswers.add("Улитка");

            simpleTasks.add("В Чили находится самое крупное сооружение подобного типа. Его длина – 1 километр.");
            simpleTasksAnswers.add("Бассейн");

            simpleTasks.add("Это вкусное изделие появилось в средневековой Германии, а приобрело современный вид в начале 19 века");
            simpleTasksAnswers.add("Сосиска");
        } else {
            //Инициализиция сложного
            complicatedTasks = new ArrayList<>();
            complicatedTasksAnswers = new ArrayList<>();

            complicatedTasks.add("Это оружие применялось для охоты на волков и лис. При таком способе охоты необходимо было нанести удар по носу зверя");
            complicatedTasksAnswers.add("Нагайка");

            complicatedTasks.add("Что считается самой распространенной в мире незаразной болезнью");
            complicatedTasksAnswers.add("Кариес");

            complicatedTasks.add("С японского это слово переводится как «Божественный ветер»");
            complicatedTasksAnswers.add("Камикадзе");
        }
    }

    public String getTask(int i) {
        if (difficult == false)
            return simpleTasks.get(i);
        else
            return complicatedTasks.get(i);
    }

    public String getAnswer(int i) {
        if (difficult == false)
            return simpleTasksAnswers.get(i);
        else
            return complicatedTasksAnswers.get(i);
    }
}
