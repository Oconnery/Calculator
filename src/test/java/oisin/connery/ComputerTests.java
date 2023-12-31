package oisin.connery;

import oisin.connery.exceptions.ExpressionFormatException;
import oisin.connery.operators.ExponentOperator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ComputerTests {
    @ParameterizedTest
    @MethodSource({"provideAdditionsInput"})
    public void testCalculateReturnsCorrectAnswerOnAdditionsInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideSubtractionsInput"})
    public void testCalculateReturnsCorrectAnswerOnSubtractionsInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideMultiplicationsInput"})
    public void testCalculateReturnsCorrectAnswerOnMultiplicationsInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideDivisionsInput"})
    public void testCalculateReturnsCorrectAnswerOnDivisionsInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideExponentsInput"})
    public void testCalculateReturnsCorrectAnswerOnExponentsInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideMixedOperationsInput"})
    public void testCalculateReturnsCorrectAnswerOnMixedInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }


    @ParameterizedTest
    @MethodSource({"provideParenthesesInput"})
    public void testCalculateReturnsCorrectAnswerOnParenthesesInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideDecimalsInput"})
    public void testCalculateReturnsCorrectAnswerOnDecimalsInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideWhiteSpacesInput"})
    public void testCalculateReturnsCorrectAnswerOnWhiteSpaceInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideNegativeNumbersInput"})
    public void testCalculateReturnsCorrectAnswerOnNegativeNumbersInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideFactorialNumbersInput"})
    public void testFactorialReturnsCorrectAnswerOnNegativeNumbersInput(String formula, String expectedOutput){
        assertComputerCalculatesOutputFromFormula(formula, expectedOutput);
    }

    @ParameterizedTest
    @MethodSource({"provideBadFormatInput"})
    @NullSource
    public void testCalculateThrowsExceptionOnBadFormatInput(String formula){
        assertComputerThrowsExpressionFormatExceptionFromBadFormulas(formula);
    }

    @ParameterizedTest
    @MethodSource({"provideUndefinedNumbersInput"})
    public void testCalculateThrowsExceptionOnUndefinedFormatInput(String formula){
        assertComputerThrowsArithmeticNumberExceptionFromBadUndefinedFormula(formula);
    }

    private void assertComputerCalculatesOutputFromFormula(String formula, String expectedOutput){
        assertEquals(expectedOutput, Computer.calculate(formula));
    }

    private void assertComputerThrowsExpressionFormatExceptionFromBadFormulas(String formula){
        assertThrows(ExpressionFormatException.class, ()-> Computer.calculate(formula));
    }
    private void assertComputerThrowsArithmeticNumberExceptionFromBadUndefinedFormula(String formula){
        assertThrows(ArithmeticException.class, ()-> Computer.calculate(formula));
    }

    private static String generateTooLargeExponentPowerNumber(){
        return "10 ^ " + "9".repeat(ExponentOperator.MAX_PROCESSABLE_POWER_LENGTH);
    }

    private static Stream<Arguments> provideAdditionsInput(){
        return Stream.of(
                Arguments.of("2 + 5", "7"),
                Arguments.of("30 + 23", "53"),
                Arguments.of("999 + 1", "1000"),
                Arguments.of("20456 + 0", "20456"),
                Arguments.of("11 + 11", "22"),
                Arguments.of("2147483647 + 2147483646", "4294967293"));
    }

    private static Stream<Arguments> provideSubtractionsInput(){
        return Stream.of(
                Arguments.of("8 - 3", "5"),
                Arguments.of("11 - 2", "9"),
                Arguments.of("12 - 1", "11"),
                Arguments.of("120 - 1", "119"),
                Arguments.of("123 - 123", "0"),
                Arguments.of("10000 - 9999", "1"),
                Arguments.of("100000 - 99999", "1"),
                Arguments.of("1000000 - 999999", "1"),
                Arguments.of("10000000 - 9999999", "1"),
                Arguments.of("100000000 - 99999999", "1"),
                Arguments.of("2147483647 - 2147483646", "1"),
                Arguments.of("2147483647 - 2147483647", "0"),
                Arguments.of("2147483647 - 0", "2147483647"));
    }

    private static Stream<Arguments> provideMultiplicationsInput(){
        return Stream.of(
                Arguments.of("5 * 4", "20"),
                Arguments.of("55 * 44", "2420"),
                Arguments.of("0 * 4", "0"),
                Arguments.of("0 * 0", "0"),
                Arguments.of("5 * 4", "20"));
    }

    private static Stream<Arguments> provideDivisionsInput(){
        return Stream.of(
                Arguments.of("5 / 5", "1"),
                Arguments.of("124 / 2", "62"),
                Arguments.of("21 / 7 / 3", "1"),
                Arguments.of("99 / 33", "3"),
                Arguments.of("999 / 10", "99.9"));
    }

    private static Stream<Arguments> provideExponentsInput(){
        return Stream.of(
                Arguments.of("90 ^ 3", "729000"),
                Arguments.of("2 ^ 3", "8"),
                Arguments.of("1 ^ 1", "1"),
                Arguments.of("1 ^ 0", "1"),
                Arguments.of("0 ^ 0", "1"),
                Arguments.of("999999999 ^ 0", "1"),
                Arguments.of("2147483647 ^ 0", "1"),
                Arguments.of("0 ^ 0", "1"),
                Arguments.of("4 ^ 4 ^ 2", "65536"),
                Arguments.of("1 ^ 1 ^ 1 ^ 1 ^ 1 ^ 1 ^ 1", "1"),
                Arguments.of("2 ^ 3 ^ 2 ^ 2", "4096"),
                Arguments.of("4 ^ 2 ^ 4 ^ 1", "65536"));
    }

    private static Stream<Arguments> provideMixedOperationsInput(){
        return Stream.of(
                Arguments.of("1 + 2 * 3", "7"),
                Arguments.of("6 + 3 - 2 + 12", "19"),
                Arguments.of("2 * 15 + 23", "53"),
                Arguments.of("10 - 3 ^ 2", "1"),
                Arguments.of("11! + 17 - 9", "39916808"));
    }

    private static Stream<Arguments> provideParenthesesInput(){
        return Stream.of(
                Arguments.of("(4 + 1)", "5"),
                Arguments.of("(3 - 2)", "1"),
                Arguments.of("(9 * 4)", "36"),
                Arguments.of("(20 / 2)", "10"),
                Arguments.of("(4 ^ 3)", "64"),

                Arguments.of("(4 + 1) + (3 + 5)", "13"),
                Arguments.of("(4 - 1) + (3 - 5)", "1"),
                Arguments.of("(7 * 7) + (4 * 5)", "69"),
                Arguments.of("(10/2) + (100/10)", "15"),
                Arguments.of("(55 ^ 2) + (2 ^ 3)", "3033"),

                Arguments.of("(9+(7 * 7)) - (4 * 5)", "38"),
                Arguments.of("(7 * 7) - (4 * 5)", "29"),

                Arguments.of("(7 * 7) * (4 * 5)", "980"),

                Arguments.of("(7 * 7) / (4 * 5)", "2.45"),

                Arguments.of("(3 * 3) ^ (4 * 1)", "6561"),

                Arguments.of("(5!) - (6! -10)", "-590"),

                Arguments.of("(3!) + 7 + 4 * ((5*(6-4))+0-10)", "13")
        );
    }

    private static Stream<Arguments> provideDecimalsInput(){
        return Stream.of(
                Arguments.of("1.0 + 1.0", "2.0"),
                Arguments.of("4.0 - 2.0", "2.0"),
                Arguments.of("2 * 3.0", "6.0"),
                Arguments.of("8.0 / 2.0", "4"),
                Arguments.of("4.0 ^ 2.0", "16.00"),

                Arguments.of("1.0 + 1.0 + 1.0", "3.0"),
                Arguments.of( (Double.MAX_VALUE + " - 0.0"), (Double.MAX_VALUE) + ".0"),
                Arguments.of("22.5 * 33.66", "757.350"),
                Arguments.of(("999.99 / 2.55 / 0.1"), "3921.529411764706"),
                Arguments.of("4.0 ^ 2.0", "16.00"));
    }

    private static Stream<Arguments> provideWhiteSpacesInput(){
        return Stream.of(
                Arguments.of("4    ^2", "16"),
                Arguments.of("4+               2   ", "6"),
                Arguments.of(" 4  -   1 ", "3"));
    }

    private static Stream<Arguments> provideBadFormatInput(){
        return Stream.of(
                Arguments.of(""),
                Arguments.of(" "),

                Arguments.of("**"),
                Arguments.of("//4"),
                Arguments.of("1^^"),

                Arguments.of("+*"),
                Arguments.of("5+/1"),
                Arguments.of("4+^22"),

                Arguments.of("11-*999"),
                Arguments.of("2-/5"),
                Arguments.of("3-^4"),

                Arguments.of("*^"),
                Arguments.of("(11+4)*/12"),

                Arguments.of("100/^2"),
                Arguments.of("9/9/*9"),

                Arguments.of("1+3+5*6+*"),

                Arguments.of("9/9a/*9"),
                Arguments.of("9+b*9"),
                Arguments.of("b"),
                Arguments.of("9+1b"),
                Arguments.of("9+1b-9"),
                Arguments.of("9$-1"),
                Arguments.of("$4+1"),

                Arguments.of("2!!+2!"),

                Arguments.of(generateTooLargeExponentPowerNumber()),
                Arguments.of(generateTooLargeExponentPowerNumber().concat("9")));
    }

    private static Stream<Arguments> provideNegativeNumbersInput(){
        return Stream.of(
                Arguments.of("-4 + 2", "-2"),
                Arguments.of("-4-8", "-12"),
                Arguments.of("4 - 8", "-4"),
                Arguments.of("100 -- 2", "102"),
                Arguments.of("100 +- 2", "98"),
                Arguments.of("100 ++ 2", "102"),
                Arguments.of("100 -+ 2", "98"),
                Arguments.of("-4 -4 +2", "-6"),
                Arguments.of("-4 -4 -+ 2", "-10"));
    }

    private static Stream<Arguments> provideFactorialNumbersInput(){
        return Stream.of(
                Arguments.of("4!", "24"),
                Arguments.of("12!", "479001600"),
                Arguments.of("2!+2!", "4"),
                Arguments.of("2!-2!-6!", "-720"));
    }

    private static Stream<Arguments> provideUndefinedNumbersInput(){
        return Stream.of(
                Arguments.of("36/0"),
                Arguments.of("3/0"),
                Arguments.of("99999999/0"));
    }
}