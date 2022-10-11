package nus.climods.logic.parser;

import static nus.climods.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static nus.climods.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static nus.climods.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static nus.climods.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static nus.climods.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static nus.climods.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static nus.climods.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static nus.climods.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static nus.climods.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static nus.climods.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static nus.climods.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static nus.climods.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static nus.climods.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static nus.climods.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static nus.climods.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static nus.climods.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static nus.climods.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static nus.climods.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static nus.climods.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static nus.climods.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static nus.climods.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static nus.climods.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static nus.climods.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static nus.climods.logic.parser.CommandParserTestUtil.assertParseFailure;
import static nus.climods.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static nus.climods.testutil.TypicalPersons.AMY;
import static nus.climods.testutil.TypicalPersons.BOB;

import com.fasterxml.jackson.annotation.JsonIgnore;
import nus.climods.logic.parser.exceptions.ParseException;
import nus.climods.model.module.UserModule;
import org.junit.jupiter.api.Test;

import nus.climods.commons.core.Messages;
import nus.climods.logic.commands.AddCommand;
import nus.climods.model.person.Address;
import nus.climods.model.person.Email;
import nus.climods.model.person.Name;
import nus.climods.model.person.Person;
import nus.climods.model.person.Phone;
import nus.climods.model.tag.Tag;
import nus.climods.testutil.PersonBuilder;

public class AddCommandParserTest {

    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        UserModule expectedModule = new UserModule("CS2103");

        // whitespace only preamble
        assertParseSuccess(parser, "CS2103", new AddCommand(expectedModule));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws ParseException {
        // zero tags
        UserModule expectedModule = new UserModule("CS2103");

        // whitespace only preamble
        assertParseSuccess(parser, "CS2103", new AddCommand(expectedModule));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing Modulecode prefix
        assertParseFailure(parser, "", expectedMessage);
    }

    @JsonIgnore
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
            + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
            + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
            Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
