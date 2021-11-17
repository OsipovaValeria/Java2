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
    final private String Expression;

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
     * @throws Exception если выражение введено некорректно
     */
    public double Solution() throws Exception{
        String PreparingExpression = PreparingExpression();
        String RPN = ExpressionToRPN(PreparingExpression);
        return RPNtoAnswer(RPN);
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
     * @throws Exception если используются недопустимые символы
     * @throws Exception если пропущен знак операции
     * @throws Exception если отсутсвуют необходимые скобки
     */
    private String ExpressionToRPN(String PreparingExpression) throws Exception{
        String current = "";
        Stack<Character> Stack = new Stack<>();
        int open_parentheses = 0, closing_parentheses = 0;
        for(int i = 0; i < PreparingExpression.length(); i++){
            char symbol = PreparingExpression.charAt(i);
            if (!Character.isDigit(symbol) && symbol != '+' && symbol != '-' && symbol != '/' && symbol != '*' && symbol != '(' && symbol != ')' && symbol != '.')
                throw new Exception("Некорректное выражение - используются недопустимые символы! Позиция ошибки: " + i);
            int priority = GetPriority(symbol);
            if (priority == 0)  current += symbol;
            if (priority == 1) {
                open_parentheses++;
                Stack.push(symbol);
            }
            if (priority == 2 || priority == 3){
                current += ' ';
                while(!Stack.empty()){
                    if (GetPriority(Stack.peek()) >= priority)
                        current += Stack.pop();
                    else break;
                }
                Stack.push(symbol);
            }
            if (priority == -1) {
                closing_parentheses++;
                if (i < Expression.length() - 1 && Character.isDigit(Expression.charAt(i + 1)))
                    throw new Exception("Некорректное выражение - пропущен знак операции! Позиция ошибки: " + i);
                current += ' ';
                while (!Stack.empty() && GetPriority(Stack.peek()) != 1)
                    current += Stack.pop();
                if (Stack.empty())
                    throw new Exception("Некорректное выражение - отсутсвует открывающая скобка! Позиция ошибки: " + i);
                Stack.pop();
            }
        }
        if (open_parentheses != closing_parentheses)
            throw new Exception("Некорректное выражение - количество '(' не равно ')'!");
        while (!Stack.empty()) current += Stack.pop();
        return current;
    }
    /**
     * Функция вычисления значения выражения
     * @param RPN - преобразованное с помощью обратной пользовательской нотации математическое выражение
     * @return возвращает вычисленное значение
     * @throws Exception если в выражении недостаточно чисел
     * @throws Exception если происходит деление на ноль
     * @throws Exception если введена пустая строка или пустые скобки
     */
    private double RPNtoAnswer(String RPN) throws Exception{
        String operand = "";
        Stack<Double> Stack = new Stack<>();

        for(int i = 0; i < RPN.length(); i++){
            if (RPN.charAt(i) == ' ') continue;
            if (GetPriority(RPN.charAt(i)) == 0){
                while(i < RPN.length() && RPN.charAt(i) != ' ' && GetPriority(RPN.charAt(i)) == 0)
                    operand += RPN.charAt(i++);
                Stack.push(Double.parseDouble(operand));
                operand = "";
            }
            if (i < RPN.length() && (GetPriority(RPN.charAt(i)) == 2 || GetPriority(RPN.charAt(i)) == 3)){
                if (Stack.empty()) throw new Exception("Некорректное выражение - в выражении недостаточно чисел!");
                double a = Stack.pop();
                if (Stack.empty()) throw new Exception("Некорректное выражение - в выражении недостаточно чисел!");
                double b = Stack.pop();
                if (RPN.charAt(i) == '+') Stack.push(b + a);
                if (RPN.charAt(i) == '-') Stack.push(b - a);
                if (RPN.charAt(i) == '*') Stack.push(b * a);
                if (RPN.charAt(i) == '/') {
                    if (a == 0) throw new Exception("Деление на 0!");
                    Stack.push(b / a);
                }
            }
        }
        if (Stack.empty()) throw new Exception("Некорректное выражение - введена пустая строка или пустые скобки!");
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
