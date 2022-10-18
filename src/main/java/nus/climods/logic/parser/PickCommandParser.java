package nus.climods.logic.parser;

import nus.climods.logic.commands.PickCommand;
import nus.climods.logic.parser.exceptions.ParseException;
import nus.climods.logic.parser.parameters.LessonTypeParameter;
import nus.climods.logic.parser.parameters.ModuleCodeParameter;

import java.util.Arrays;

/**
 * Parses input arguments and creates a new PickCommand object
 */
public class PickCommandParser implements Parser<PickCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PickCommand and returns an PickCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public PickCommand parse(String args) throws ParseException {
        String[] arg = args.split(" ", 4);

        if (arg.length != 4) {
            throw new ParseException("You need 3 arguments: <module code> <lesson type> <classNo>");
        }

        System.out.println(Arrays.toString(arg));

        ModuleCodeParameter mcp = new ModuleCodeParameter(args);
        String mc = mcp.getArgValue();

        LessonTypeParameter ltp = new LessonTypeParameter(args);
        String lt = ltp.getArgValue();

        String classNo = arg[3].trim();
        //TODO: add other parameter
        return new PickCommand(mc, lt, classNo);
    }
}
