package tests;

import Commands.DefineCommand;
import Constants.Constants;
import Contexts.Context;
import Exceptions.BadNumOfArgsException;
import Exceptions.BadVarNameException;
import Exceptions.EmptyVarException;
import Exceptions.MapException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DefineCommandTest {

    @Test
    void executeWorkTest1() throws EmptyVarException, BadVarNameException, BadNumOfArgsException, MapException {
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE a 1".split(" "))));
        Context context = new Context();

        defineCommand.execute(context);

        assertEquals(1.0, context.searchValue("a"));
    }

    @Test
    void executeWorkTest2() throws EmptyVarException, BadVarNameException, BadNumOfArgsException, MapException{
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE aasd 1".split(" "))));

        Context context = new Context();

        defineCommand.execute(context);

        assertEquals(1.0, context.searchValue("aasd"));

    }

    @Test
    void executeWorkTest3() throws EmptyVarException, BadVarNameException, BadNumOfArgsException, MapException{
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE a 1.0".split(" "))));

        Context context = new Context();

        defineCommand.execute(context);

        assertEquals(1.0, context.searchValue("a"));

    }

    @Test
    void executeWorkTest4() throws EmptyVarException, BadVarNameException, BadNumOfArgsException, MapException{
        DefineCommand defineCommand1 = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE a 1.0".split(" "))));
        DefineCommand defineCommand2 = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE b a".split(" "))));

        Context context = new Context();

        defineCommand1.execute(context);
        defineCommand2.execute(context);

        assertEquals(1.0, context.searchValue("a"));
        assertEquals(1.0, context.searchValue("b"));

    }

    @Test
    void executeEmptyVarExceptionTest1() throws BadNumOfArgsException {
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE a a".split(" "))));

        Context context = new Context();

        Exception exception1 = assertThrows(EmptyVarException.class, () -> defineCommand.execute(context));
        Exception exception2 = assertThrows(MapException.class, () -> context.searchValue("a"));

        String expectedMessage1 = Constants.EMPTY_VAR_EXCEPTION_ERROR_TEXT;
        String expectedMessage2 = Constants.EMPTY_STR;

        String actualMessage1 = exception1.getMessage();
        String actualMessage2 = exception2.getMessage();

        assertEquals(actualMessage1, expectedMessage1);
        assertEquals(actualMessage2, expectedMessage2);
    }

    @Test
    void executeEmptyVarExceptionTest2() throws BadNumOfArgsException{
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE a B".split(" "))));
        Context context = new Context();

        Exception exception1 = assertThrows(EmptyVarException.class, () -> defineCommand.execute(context));
        Exception exception2 = assertThrows(MapException.class, () -> context.searchValue("B"));

        String expectedMessage1 = Constants.EMPTY_VAR_EXCEPTION_ERROR_TEXT;
        String expectedMessage2 = Constants.EMPTY_STR;

        String actualMessage1 = exception1.getMessage();
        String actualMessage2 = exception2.getMessage();

        assertEquals(actualMessage1, expectedMessage1);
        assertEquals(actualMessage2, expectedMessage2);
    }

    @Test
    void executeBadVarNameExceptionTest1() throws  BadNumOfArgsException {
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE 4 23".split(" "))));

        Context context = new Context();
        Exception exception = assertThrows(BadVarNameException.class, () -> defineCommand.execute(context));
        String expectedMessage = Constants.BAD_VAR_NAME_ERROR_TEXT;
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

    }

    @Test
    void executeBadVarNameExceptionTest2() throws  BadNumOfArgsException {
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE /a 23".split(" "))));

        Context context = new Context();
        Exception exception = assertThrows(BadVarNameException.class, () -> defineCommand.execute(context));
        String expectedMessage = Constants.BAD_VAR_NAME_ERROR_TEXT;
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

    }

    /*@Test
    void executeBadNumOfArgsExceptionTest1() throws  BadNumOfArgsException {
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE ".split(" "))));

        Context context = new Context();
        Exception exception = assertThrows(BadNumOfArgsException.class, () -> defineCommand.execute(context));

        String expectedMessage = Constants.BAD_AMOUNT_OF_COMMAND_ARGS_EXCEPTION_ERROR_TEXT;
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

    }

    @Test
    void executeBadNumOfArgsExceptionTest2() throws  BadNumOfArgsException {
        DefineCommand defineCommand = new DefineCommand(new ArrayList<>(Arrays.asList("DEFINE a".split(" "))));

        Context context = new Context();

        Exception exception = assertThrows(BadNumOfArgsException.class, () -> defineCommand.execute(context));

        String expectedMessage = Constants.BAD_AMOUNT_OF_COMMAND_ARGS_EXCEPTION_ERROR_TEXT;
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

    }*/
}