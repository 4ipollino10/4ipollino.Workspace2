package tests;

import Commands.PrintCommand;
import Constants.Constants;
import Contexts.Context;
import Exceptions.BadNumOfArgsException;
import Exceptions.EmptyStackSectionException;
import org.junit.jupiter.api.Test;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PrintCommandTest {
    private final PrintCommand printCommand = new PrintCommand(new ArrayList<>(Arrays.asList("PRINT".split(" "))));

    PrintCommandTest() throws BadNumOfArgsException{}


    @Test
    void executeWorkTest1() throws EmptyStackSectionException {
        Context context = new Context();

        context.push(1.0);

        assertEquals("1.0", printCommand.execute(context));
        assertEquals(1.0, context.pop());

    }

    @Test
    void executeWorkTest2(){
        Context context = new Context();

        Exception exception = assertThrows(EmptyStackSectionException.class, () -> printCommand.execute(context));

        String expectedMessage = Constants.EMPTY_STACK_EXCEPTION_ERROR_TEXT;
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

}