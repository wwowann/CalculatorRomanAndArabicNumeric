package org.example;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Input a mathematical expression: ");
        try {
            System.out.println("ответ: " + calc(in.nextLine()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String calc(String input) throws Exception {
        boolean roman = false;
        String[] result = input.split(" ");
        String[] operands = {"+", "-", "/", "*"};
        String operand = result[1];
        int value1, value2;
        if (!(result.length == 3))
            throw new Exception("формат математической операции не удовлетворяет заданию");//проверка на количество знаков в строке
        if (Arrays.stream(operands).noneMatch(operand::equals)) {//проверка является ли символ операндом калькулятора
            throw new Exception("Строка не является математической операцией");
        }
        if (isNumber(result[0]) && isNumber(result[2])) {//являются ли цифры арабские
            value1 = Integer.parseInt(result[0]);
            value2 = Integer.parseInt(result[2]);
            if (value1 > 10 || value1 < 1 && value2 > 10 || value2 < 1)
                throw new Exception("используются одновременно разные системы счисления или значения лежат вне диапазона значений, предусмотренного заданием");
        } else if (Arrays.stream(RomanNumeral.values()).anyMatch(element -> element.value == getValueEnum(result[0])) &&
                Arrays.stream(RomanNumeral.values()).anyMatch(element -> element.value == getValueEnum(result[2]))) {
            value1 = getValueEnum(result[0]);
            value2 = getValueEnum(result[2]);
            roman = true;
            if (value2 > value1 && operand.equals("-"))
                throw new Exception("в римской системе нет отрицательных чисел");
        } else
            throw new Exception("используются одновременно разные системы счисления или значения лежат вне диапазона значений, предусмотренного заданием");
        if (value2 == 0 && operand.equals("/")) throw new Exception("делить на ноль нельзя");
        if (roman) return convertIntegerToRoman(calculated(value1, value2, operand));
        return String.valueOf(calculated(value1, value2, operand));


    }


    public static int getValueEnum(String value) {
        int returnValue = 0;
        for (RomanNumeral val : RomanNumeral.values()) {
            RomanNumeral num = RomanNumeral.valueOf(val.name());
            if (value.equals(val.name())) {
                returnValue = num.value;
            }
        }
        return returnValue;
    }

    public static boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static int calculated(int num1, int num2, String operand) {
        int result = 0;
        switch (operand) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> {
                try {
                    result = num1 / num2;
                } catch (ArithmeticException | InputMismatchException e) {
                    System.out.println("делить на ноль нельзя");

                }
            }
        }
        return result;
    }

    static String convertIntegerToRoman(int number) {
        int[] numbers = {1, 4, 5, 9, 10, 50, 100, 500, 1000};
        String[] letters = {"I", "IV", "V", "IX", "X", "L", "C", "D", "M"};
        StringBuilder romanValue = new StringBuilder();
        int N = number;
        while (N > 0) {
            for (int i = 0; i < numbers.length; i++) {
                if (N < numbers[i]) {
                    N -= numbers[i - 1];
                    romanValue.append(letters[i - 1]);
                    break;
                }
            }
        }
        return romanValue.toString();
    }
}
