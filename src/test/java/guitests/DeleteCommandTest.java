package guitests;

import static seedu.address.logic.commands.DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.TYPICAL_PERSONS;
import static seedu.address.ui.testutil.GuiTestAssert.assertListMatching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.ReadOnlyPerson;

public class DeleteCommandTest extends AddressBookGuiTest {

    @Test
    public void delete() throws Exception {

        //delete the first in the list
        ArrayList<ReadOnlyPerson> expectedList = new ArrayList<>(Arrays.asList(TYPICAL_PERSONS));
        Index targetIndex = INDEX_FIRST_PERSON;
        ReadOnlyPerson toDelete = expectedList.get(targetIndex.getZeroBased());
        expectedList.remove(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, toDelete, expectedList);

        //delete the last in the list
        targetIndex = Index.fromOneBased(expectedList.size());
        toDelete = expectedList.get(targetIndex.getZeroBased());
        expectedList.remove(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, toDelete, expectedList);

        //delete from the middle of the list
        targetIndex = Index.fromOneBased(expectedList.size() / 2);
        toDelete = expectedList.get(targetIndex.getZeroBased());
        expectedList.remove(targetIndex.getZeroBased());
        assertDeleteSuccess(targetIndex, toDelete, expectedList);

        //invalid index
        runCommand(DeleteCommand.COMMAND_WORD + " " + expectedList.size() + 1);
        assertResultMessage("The person index provided is invalid");

    }

    /**
     * Runs the delete command to delete the person at {@code index} and confirms resulting list equals to
     * {@code expectedList} and that the displayed result message is correct.
     * @param personToDelete the person located at {@code index} of the original list
     */
    private void assertDeleteSuccess(Index index, ReadOnlyPerson personToDelete,
                                     final List<ReadOnlyPerson> expectedList) throws Exception {
        runCommand(DeleteCommand.COMMAND_WORD + " " + index.getOneBased());

        //confirm the list now contains all previous persons except the deleted person
        assertListMatching(getPersonListPanel(), expectedList.toArray(new ReadOnlyPerson[expectedList.size()]));

        //confirm the result message is correct
        assertResultMessage(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
    }

}
