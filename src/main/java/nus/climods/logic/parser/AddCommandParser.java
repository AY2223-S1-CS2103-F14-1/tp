package nus.climods.logic.parser;

import org.openapitools.client.model.ModuleCondensed.SemestersEnum;

import nus.climods.logic.commands.AddCommand;
import nus.climods.logic.parser.exceptions.ParseException;
import nus.climods.logic.parser.parameters.ModuleCodeParameter;
import nus.climods.logic.parser.parameters.SemesterTypeParameter;


/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand and returns an AddCommand object
     * for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ModuleCodeParameter mcp = new ModuleCodeParameter(args);
        SemesterTypeParameter stp = new SemesterTypeParameter(args);
        String mc = mcp.getArgValue();
        SemestersEnum st = (SemestersEnum) stp.getArgValue();
        System.out.println(st);
        return new AddCommand(mc, st);
    }

}
