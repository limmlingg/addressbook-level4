package guitests.guihandles;


import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.commons.core.index.Index;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.ui.PersonCard;

/**
 * Provides a handle for {@code PersonListPanel} containing the list of {@code PersonCard}.
 */
public class PersonListPanelHandle extends NodeHandle<ListView<PersonCard>> {
    public static final String PERSON_LIST_VIEW_ID = "#personListView";

    public PersonListPanelHandle(ListView<PersonCard> personListPanelNode) {
        super(personListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     */
    public Optional<PersonCardHandle> getHandleToSelectedCard() {
        List<PersonCard> personList = getRootNode().getSelectionModel().getSelectedItems();

        if (personList.size() > 1) {
            throw new AssertionError("Person list size expected 0 or 1.");
        }

        return personList.isEmpty() ? Optional.empty() : Optional.of(new PersonCardHandle(personList.get(0).getRoot()));
    }

    /**
     * Scrolls the list to the {@code index} given.
     */
    public void scrollTo(Index index) {
        guiRobot.interact(() -> getRootNode().scrollTo(index.getZeroBased()));
    }

    /**
     * Navigates the listview to display and select the person.
     */
    public void navigateToCard(ReadOnlyPerson person) throws PersonNotFoundException {
        List<PersonCard> cards = getRootNode().getItems();
        Optional<PersonCard> matchingCard = cards.stream().filter(card -> card.person.equals(person)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new PersonNotFoundException();
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    public PersonCardHandle getPersonCardHandle(int index) throws PersonNotFoundException {
        return getPersonCardHandle(getRootNode().getItems().get(index).person);
    }

    /**
     * Returns the {@code PersonCardHandle} of the specified {@code person} in the list.
     */
    public PersonCardHandle getPersonCardHandle(ReadOnlyPerson person) throws PersonNotFoundException {
        Optional<PersonCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.person.equals(person))
                .map(card -> new PersonCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(PersonNotFoundException::new);
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
