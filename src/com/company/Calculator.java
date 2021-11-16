package com.company;
import java.util.Stack;
/**
 * Выражение может содержать числа, знаки операций, скобки.
 * В случае, если выражение записано корректно, вычислить значение,
 * в противном случае — вывести сообщение об ошибке.
 *  @author Осипова Валерия
 *  @version 1
 **/
public class Calculator {
    /** Поле математического выражения */
    private String Expression;

    /**
     * Конструктор
     * @param Exp - математическое выражение
     */
    public Calculator(String Exp){
        Expression = Exp;
    }
    /**
     * Функция получения решения выражения
     * @return возвращает решение математического выражение
     */
    public double Solution() throws Exception{
        Errors();
        String PreparingExpression = PreparingExpression();
        String RPN = ExpressionToRPN(PreparingExpression);
        return RPNtoAnswer(RPN);
    }
    /**
     * Процедура проверки на ошибки введенного выражения
     */
    private void Errors() throws Exception{
        int open_parentheses = 0, closing_parentheses = 0, digit_counter = 0;
        if (Expression.length() == 0)
            throw new Exception("Некорректное выражение - вы ввели пустую строку!");
        if (Expression.charAt(0) == '*' || Expression.charAt(0) == '/')
            throw new Exception("Некорректное выражение - выражение не может начинаться с этого символа!");
        if (Expression.charAt(Expression.length() - 1) == '*' || Expression.charAt(Expression.length() - 1) == '/' || Expression.charAt(Expression.length() - 1) == '+' || Expression.charAt(Expression.length() - 1) == '-')
            throw new Exception("Некорректное выражение - выражение не может заканчиваться на этот символ!");
        for (int i = 0; i < Expression.length(); i++){
            char symbol = Expression.charAt(i);
            if (Character.isDigit(symbol)) digit_counter++;
            if (symbol == '(') open_parentheses++;
            if (symbol == ')') closing_parentheses++;
            if (!Character.isDigit(symbol) && symbol != '+' && symbol != '-' && symbol != '/' && symbol != '*' && symbol != '(' && symbol != ')' && symbol != '.')
                throw new Exception("Некорректное выражение - используются недопустимые символы!");
            if (i < Expression.length() - 1) {
                char next_symbol = Expression.charAt(i + 1);
                if ((symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/') && (!Character.isDigit(next_symbol) && next_symbol != '('))
                    throw new Exception("Некорректное выражение - возможно пропущенно число!");
                if (symbol == '(' && next_symbol == ')')
                    throw new Exception("Некорректное выражение - отсутствует выражение в скобках!");
            }
        }
        if (digit_counter == Expression.length()) throw new Exception("Выражение состоит из одного числа!");
        if (open_parentheses != closing_parentheses) throw new Exception("Некорректное выражение - количество '(' не равно ')'!");
    }
    /**
     * Функция подготовки выражения для вычисления
     * @return возвращает исправленное выражение
     */
    private String PreparingExpression(){
        String PreparingExpression = "";
        for (int i = 0; i < Expression.length(); i++){
            char symbol = Expression.charAt(i);
            if(symbol == '-' || symbol == '+') {
                if (i == 0) PreparingExpression += 0;
                else if(Expression.charAt(i - 1) == '(') PreparingExpression += 0;
            }
            if (i > 0)
                if(symbol == '(' && Character.isDigit(Expression.charAt(i - 1))) PreparingExpression += '*';
            PreparingExpression += symbol;
        }
        return PreparingExpression;
    }
    /**
     * Функция преобразования выражения с помощью обратной пользовательской нотации
     * @param PreparingExpression -  подготовленное математическое выражение
     * @return возвращает преобразованное выражение
     */
    private String ExpressionToRPN(String PreparingExpression){
        String current = ""; //текущая строка
        Stack<Character> Stack = new Stack<>(); //стэк

        int priority;
        for(int i = 0; i < PreparingExpression.length(); i++){
            priority = GetPriority(PreparingExpression.charAt(i));
            if (priority == 0)  current += PreparingExpression.charAt(i);
            if (priority == 1) Stack.push(PreparingExpression.charAt(i));
            if (priority == 2 || priority == 3){
                current += ' ';
                while(!Stack.empty()){
                    if (GetPriority(Stack.peek()) >= priority)
                        current += Stack.pop();
                    else break;
                }
                Stack.push(PreparingExpression.charAt(i));
            }
            if (priority == -1){
                current += ' ';
                while (GetPriority(Stack.peek()) != 1)
                    current += Stack.pop();
                Stack.pop();
            }
        }
        while (!Stack.empty()) current += Stack.pop();
        return current;
    }
    /**
     * Функция вычисления значения выражения
     * @param RPN - преобразованное с помощью обратной пользовательской нотации математическое выражение
     * @return возвращает вычисленное значение
     */
    private double RPNtoAnswer(String RPN) throws Exception{
        String operand = "";
        Stack<Double> Stack = new Stack<>();

        for(int i = 0; i < RPN.length(); i++){
            if (RPN.charAt(i) == ' ') continue;
            if (GetPriority(RPN.charAt(i)) == 0){
                while(RPN.charAt(i) != ' ' && GetPriority(RPN.charAt(i)) == 0){
                    operand += RPN.charAt(i++);
                    if (i == RPN.length()) break;
                }
                Stack.push(Double.parseDouble(operand));
                operand = "";
            }
            if (GetPriority(RPN.charAt(i)) == 2 || GetPriority(RPN.charAt(i)) == 3){
                double a = Stack.pop(), b = Stack.pop();
                if (RPN.charAt(i) == '+') Stack.push(b + a);
                if (RPN.charAt(i) == '-') Stack.push(b - a);
                if (RPN.charAt(i) == '*') Stack.push(b * a);
                if (RPN.charAt(i) == '/') {
                    if (a == 0) throw new Exception("Деление на 0!");
                    Stack.push(b / a);
                }
            }
        }
        return Stack.pop();
    }
    /**
     * Функция получения приоритета операции
     * @param i - знак операции
     * @return возвращает приоритет
     */
    private int GetPriority(char i){
        if (i == '*' || i == '/') return 3;
        else if (i == '+' || i == '-') return 2;
        else if (i == '(') return 1;
        else if (i == ')') return -1;
        else return 0;
    }
}
