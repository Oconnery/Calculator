package oisin.connery;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComputerTest { // extend a class (abstract or not) (static?) that just contains the provideInput methods and their implementations // called CalculateTestData or something. // or could just import it as static import and then reference it possibly. // ComputerTestBase?

    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
    }

    //todo: test nulls and empty value inputs

    @ParameterizedTest
    @MethodSource({"provideAdditionsInput"}) // Ideally I would just be able to add the sources here and only have this 1 method and remove submethod and other methods.
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

    private void assertComputerCalculatesOutputFromFormula(String formula, String expectedOutput){
        Computer computer = new Computer();
        assertEquals(expectedOutput, computer.calculate(formula));
    }

    private static Stream<Arguments> provideAdditionsInput(){
        return Stream.of(
                Arguments.of("2 + 5", "7"),
                Arguments.of("30 + 23", "53"));
    }

    private static Stream<Arguments> provideSubtractionsInput(){
        return Stream.of(
                Arguments.of("8 - 3", "5"));
    }

    private static Stream<Arguments> provideMultiplicationsInput(){
        return Stream.of(
                Arguments.of("5 * 4", "20"));
    }

    private static Stream<Arguments> provideDivisionsInput(){
        return Stream.of(
                Arguments.of("8 / 2", "4"));
    }

    private static Stream<Arguments> provideExponentsInput(){
        return Stream.of(
                Arguments.of("90 ^ 3", "729000"),
                Arguments.of("2 ^ 3", "8"),
                Arguments.of("1 ^ 1", "1"),
                Arguments.of("1 ^ 0", "1"),
                Arguments.of("0 ^ 0", "1"),
                Arguments.of("0 ^ 2147483647", "0"),
                Arguments.of("2147483647 ^ 0", "1"),
                Arguments.of("0 ^ 0", "1"),
                Arguments.of("4 ^ 4 ^ 2", "65536"),
                Arguments.of("1 ^ 1 ^ 1 ^ 1 ^ 1 ^ 1 ^ 1", "1"),
                Arguments.of("2 ^ 3 ^ 2 ^ 2", "4096"),
                Arguments.of("4 ^ 2 ^ 4 ^ 1", "65536")); // this isn't working because 2147483647 is the max integer


    }

    private static Stream<Arguments> provideMixedOperationsInput(){
        return Stream.of(
                Arguments.of("1 + 2 * 3", "7"),
                //Arguments.of("4 ^ 2", "9"),
                Arguments.of("6 + 3 - 2 + 12", "19"),
                Arguments.of("2 * 15 + 23", "53"),
                Arguments.of("10 - 3 ^ 2", "1"));
    }

    private static Stream<Arguments> provideDecimalsInput(){
        return Stream.of(
                Arguments.of("4 ^ 2", "16"));
    }

    private static Stream<Arguments> provideWhiteSpaceGoodInput(){
        return Stream.of(
                Arguments.of("4 ^ 2", "16"));
    }

    private static Stream<Arguments> provideParenthesesGoodInput(){
        return Stream.of(
                Arguments.of("4 ^ 2", "16"));
    }

    private static Stream<Arguments> provideNegativeNumbersGoodInput(){
        return Stream.of(
                Arguments.of("4 ^ 2", "16"));
    }


    private void testCalculateReturnsExceptionWhen(){
        // worry about exceptions and handling after
    }

    // Mockito tests if necessary
}