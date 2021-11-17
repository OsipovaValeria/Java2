package com.company;
import java.util.Scanner;
/**
 * Класс Main, показывающий работу класса Calculator
 */
public class Main {
    /**
     * Демонстрация методов класса Calculator
     * @param args - аргументы
     */
    public static void main(String[] args) {
        String exit;
        do {
            System.out.print("Введите математическое выражение: ");
            /*
             * Создание переменной-строки, содержащей математическое выражение
             */
            Scanner console = new Scanner(System.in);
            String Expression = console.nextLine();
            Calculator MyExpression = new Calculator(Expression);
            /*
             * Вывод результата
             */
            try {
                System.out.println(MyExpression.Solution());
            } catch(Exception exception){
                System.out.print(exception.getMessage());
            }
            System.out.println("\nЧтобы продолжить, нажмите любую клавишу. \nЕсли хотите выйти - a.");
            Scanner console2 = new Scanner(System.in);
            exit = console2.nextLine();
        } while(!exit.equals("a"));
    }
}