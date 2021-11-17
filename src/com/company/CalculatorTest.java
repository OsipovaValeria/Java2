package com.company;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;

public class CalculatorTest {

    @Test
    public void solution() {
        String Expression1 = "3*(2+2)-1/2+0.5";
        Calculator MyExpression1 = new Calculator(Expression1);
        try {
            double expected1 = MyExpression1.Solution();
            double actual1 = 12;
            Assert.assertEquals(expected1, actual1, 0.00001);
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
        }
    }

    @Test
    public void TestForInvalidCharacters() {
        String Expression1 = "3$(2+2)";
        Calculator MyExpression1 = new Calculator(Expression1);
        try {
            MyExpression1.Solution();
            fail();
        } catch (Exception exception) {
            assertThat(exception.getMessage(), is("Некорректное выражение - используются недопустимые символы! Позиция ошибки: 1"));
        }
    }

    @Test
    public void TestForMissingSings() {
        String Expression1 = "3*(2+2)1";
        Calculator MyExpression1 = new Calculator(Expression1);
        try {
            MyExpression1.Solution();
            fail();
        } catch (Exception exception) {
            assertThat(exception.getMessage(), is("Некорректное выражение - пропущен знак операции! Позиция ошибки: 6"));
        }
    }

    @Test
    public void TestForMissingParenthesis() {
        String Expression1 = "3-1)(1+2";
        Calculator MyExpression1 = new Calculator(Expression1);
        try {
            MyExpression1.Solution();
            fail();
        } catch (Exception exception) {
            assertThat(exception.getMessage(), is("Некорректное выражение - отсутсвует открывающая скобка! Позиция ошибки: 3"));
        }
    }

    @Test
    public void TestForMissingNumbers() {
        String Expression1 = "3*(2+2)*";
        Calculator MyExpression1 = new Calculator(Expression1);
        try {
            MyExpression1.Solution();
            fail();
        } catch (Exception exception) {
            assertThat(exception.getMessage(), is("Некорректное выражение - в выражении недостаточно чисел!"));
        }
    }

    @Test
    public void TestForDivisionByZero() {
        String Expression1 = "3/(2-2)";
        Calculator MyExpression1 = new Calculator(Expression1);
        try {
            MyExpression1.Solution();
            fail();
        } catch (Exception exception) {
            assertThat(exception.getMessage(), is("Деление на 0!"));
        }
    }

    @Test
    public void TestForAnEmptyString() {
        String Expression1 = "";
        Calculator MyExpression1 = new Calculator(Expression1);
        try {
            MyExpression1.Solution();
            fail();
        } catch (Exception exception) {
            assertThat(exception.getMessage(), is("Некорректное выражение - введена пустая строка или пустые скобки!"));
        }
    }

}