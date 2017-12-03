package com.bigdata.bookenizer.utils.kafka;


import java.util.ArrayList;
import java.util.HashMap;

// Класс-парсер для преобразования сообщений из кафки в нормальный вид
public class ParserMessage {

    public static HashMap<Integer, ArrayList<Integer>> parse(String message){

        HashMap<Integer, ArrayList<Integer>> resalt = new HashMap<>();

        String[] usersAndBooks = message.split("~"); // Получаем набор UserId;bookId;bookId;...
        for(String oneRecord : usersAndBooks){

            oneRecord = oneRecord.trim();

            if (oneRecord.length() > 2){ // Например, 1;2 - меньше уже фигня
                String[] ids = oneRecord.split(";");
                if (ids.length > 2){
                    // то начинаем обрабатывать
                    Integer userId = Integer.parseInt(ids[0].trim());
                    ArrayList<Integer> bookIds = new ArrayList<>();
                    for(int i = 1; i < ids.length; i++){
                        bookIds.add(Integer.parseInt(ids[i].trim()));
                    }
                    /*
                    // Проверяем, есть ли такой элемент?
                    if(resalt.containsKey(userId)){
                        // то нужно переписать новыми записями, потому что старые устарели
                        resalt.remove(userId);
                    }
                    */
                    // и осталось перезаписать старые данные новыми новые данные
                    resalt.replace(userId, bookIds);
                }
            }
        }

        return resalt;

    }


}
