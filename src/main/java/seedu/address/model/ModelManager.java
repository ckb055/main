package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudyBuddyBook studyBuddyBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Flashcard> filteredFlashcards;
    private final FilteredList<Note> filteredNotes;
    private final FilteredList<CheatSheet> filteredCheatSheets;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyStudyBuddyBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.studyBuddyBook = new StudyBuddyBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.studyBuddyBook.getPersonList());
        filteredFlashcards = new FilteredList<>(this.studyBuddyBook.getFlashcardList());
        filteredNotes = new FilteredList<>(this.studyBuddyBook.getNoteList());
        filteredCheatSheets = new FilteredList<>(this.studyBuddyBook.getCheatSheetList());
        filteredTags = new FilteredList<>(this.studyBuddyBook.getTagList());
    }

    public ModelManager() {
        this(new StudyBuddyBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setStudyBuddyBook(ReadOnlyStudyBuddyBook studyBuddyBook) {
        this.studyBuddyBook.resetData(studyBuddyBook);
    }

    @Override
    public ReadOnlyStudyBuddyBook getStudyBuddyBook() {
        return studyBuddyBook;
    }


    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return studyBuddyBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        studyBuddyBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        studyBuddyBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        studyBuddyBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return studyBuddyBook.hasFlashcard(flashcard);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        studyBuddyBook.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return studyBuddyBook.hasNote(note);
    }

    @Override
    public void deleteNote(Note target) {
        studyBuddyBook.removeNote(target);
    }

    @Override
    public void addNote(Note note) {
        studyBuddyBook.addNote(note);
        updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        studyBuddyBook.setNote(target, editedNote);
    }

    //=========== Filtered Tag List Accessors =============================================================

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }

    @Override
    public void updateFilteredTagList(Predicate<Tag> predicate) {
        requireNonNull(predicate);
        filteredTags.setPredicate(predicate);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Note} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return filteredNotes;
    }

    @Override
    public void updateFilteredNoteList(Predicate<Note> predicate) {
        requireNonNull(predicate);
        filteredNotes.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return studyBuddyBook.equals(other.studyBuddyBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredNotes.equals(other.filteredNotes);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        studyBuddyBook.removeFlashcard(target);
    }

    //===================CheatSheetBook============================================================

    @Override
    public void addCheatSheet(CheatSheet cheatSheet) {
        studyBuddyBook.addCheatSheet(cheatSheet);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasCheatSheet(CheatSheet cheatSheet) {
        requireNonNull(cheatSheet);
        return studyBuddyBook.hasCheatSheet(cheatSheet);
    }

    @Override
    public void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet) {
        requireAllNonNull(target, editedCheatSheet);

        studyBuddyBook.setCheatSheet(target, editedCheatSheet);
    }

    @Override
    public void deleteCheatSheet(CheatSheet cheatSheet) {
        studyBuddyBook.deleteCheatSheet(cheatSheet);
    }



    //=========== Filtered CheatSheet List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<CheatSheet> getFilteredCheatSheetList() {
        return filteredCheatSheets;
    }

    @Override
    public void updateFilteredCheatSheetList(Predicate<CheatSheet> predicate) {
        requireNonNull(predicate);
        filteredCheatSheets.setPredicate(predicate);
    }
}
