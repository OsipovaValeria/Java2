package com.company;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

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
            System.out.print(exception);
        }
    }
}