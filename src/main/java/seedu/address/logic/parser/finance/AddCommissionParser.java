package seedu.address.logic.parser.finance;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.stream.Stream;

import seedu.address.logic.commands.finance.AddCommissionCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.Amount;
import seedu.address.model.finance.ClientName;
import seedu.address.model.finance.Commission;
import seedu.address.model.finance.Description;

/**
 * Parses input arguments and creates a new AddCommissionCommand object
 */
public class AddCommissionParser implements Parser<AddCommissionCommand> {
    @Override
    public AddCommissionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_CLIENT, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_AMOUNT, PREFIX_CLIENT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommissionCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT, PREFIX_CLIENT, PREFIX_DESCRIPTION);
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        ClientName client = ParserUtil.parseClientName(argMultimap.getValue(PREFIX_CLIENT).get());
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).orElse(""));

        Commission commission = new Commission(amount, client, description);
        return new AddCommissionCommand(commission);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
