package nus.climods.logic.parser;

import nus.climods.logic.commands.ListCommand;
import nus.climods.logic.parser.exceptions.ParseException;
import nus.climods.logic.parser.parameters.FacultyCodeParameter;
import nus.climods.logic.parser.parameters.UserFlagParameter;
import org.junit.jupiter.api.Test;

import static nus.climods.logic.parser.CommandParserTestUtil.assertParseSuccess;

class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() throws ParseException {
        String userArg = "CS";
        ListCommand expectedListCommand =
            new ListCommand(new FacultyCodeParameter(userArg), new UserFlagParameter(userArg));
        assertParseSuccess(parser, userArg, expectedListCommand);
    }

    @Test
    public void parse_invalidArgs_returnsListCommand() throws ParseException {
        String userArg = "CSss";
        ListCommand expectedListCommand =
                new ListCommand(new FacultyCodeParameter(userArg), new UserFlagParameter(userArg));
        assertParseSuccess(parser, userArg, expectedListCommand);
    }
}
