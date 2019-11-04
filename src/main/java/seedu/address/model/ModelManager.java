package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;

import seedu.address.logic.FunctionMode;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final StudyBuddyPro studyBuddyPro;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Flashcard> filteredFlashcards;
    private final FilteredList<Note> filteredNotes;
    private final FilteredList<CheatSheet> filteredCheatSheets;
    private final FilteredList<Tag> filteredTags;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyStudyBuddyPro addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.studyBuddyPro = new StudyBuddyPro(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.studyBuddyPro.getPersonList());
        filteredFlashcards = new FilteredList<>(this.studyBuddyPro.getFlashcardList());
        filteredNotes = new FilteredList<>(this.studyBuddyPro.getNoteList());
        filteredCheatSheets = new FilteredList<>(this.studyBuddyPro.getCheatSheetList());
        filteredTags = new FilteredList<>(this.studyBuddyPro.getTagList());
    }

    public ModelManager() {
        this(new StudyBuddyPro(), new UserPrefs());
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
    public void setStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro) {
        this.studyBuddyPro.resetData(studyBuddyPro);
    }

    @Override
    public ReadOnlyStudyBuddyPro getStudyBuddyPro() {
        return studyBuddyPro;
    }


    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return studyBuddyPro.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        studyBuddyPro.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        studyBuddyPro.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        studyBuddyPro.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return studyBuddyPro.hasFlashcard(flashcard);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        studyBuddyPro.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        studyBuddyPro.setFlashcard(target, editedFlashcard);
    }

    @Override
    public boolean hasNote(Note note) {
        requireNonNull(note);
        return studyBuddyPro.hasNote(note);
    }

    @Override
    public void deleteNote(Note target) {
        studyBuddyPro.removeNote(target);
    }

    @Override
    public void addNote(Note note) {
        studyBuddyPro.addNote(note);
        updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
    }

    @Override
    public void setNote(Note target, Note editedNote) {
        requireAllNonNull(target, editedNote);

        studyBuddyPro.setNote(target, editedNote);
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
        return studyBuddyPro.equals(other.studyBuddyPro)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredNotes.equals(other.filteredNotes);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        studyBuddyPro.removeFlashcard(target);
    }

    /**
     * Formats string for output
     * @return String formatted flashcard display
     */
    public String formatOutputListString(FunctionMode mode) {
        String msg = "";

        switch (mode) {
        case CHEATSHEET:
            msg = formatList(filteredCheatSheets);
            break;

        case FLASHCARD:
            msg = formatList(filteredFlashcards);
            break;

        case NOTE:
            msg = formatList(filteredNotes);
            break;

        default:
            // error?
        }

        return msg;
    }

    /**
     * Formats string for output.
     * @param object the filteredlist to read
     * @param <T> the different features: cheatsheet, flashcard, notes
     * @return list of all the objects
     */
    public <T> String formatList(FilteredList<T> object) {
        int size = object.size();

        if (size == 0) {
            return "[Empty list]";
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= size; i++) {
            T feature = object.get(i - 1);
            sb.append(i)
                    .append(". ")
                    .append(feature.toString());

            if (i != size) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    //===================CheatSheet============================================================

    @Override
    public void addCheatSheet(CheatSheet cheatSheet) {
        studyBuddyPro.addCheatSheet(cheatSheet);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean hasCheatSheet(CheatSheet cheatSheet) {
        requireNonNull(cheatSheet);
        return studyBuddyPro.hasCheatSheet(cheatSheet);
    }

    @Override
    public void setCheatSheet(CheatSheet target, CheatSheet editedCheatSheet) {
        requireAllNonNull(target, editedCheatSheet);

        studyBuddyPro.setCheatSheet(target, editedCheatSheet);
    }

    @Override
    public void deleteCheatSheet(CheatSheet cheatSheet) {
        studyBuddyPro.deleteCheatSheet(cheatSheet);
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

    //========================COLLECT TAGGED ITEMS TO DISPLAY======================================
    @Override
    public ArrayList<String> collectTaggedItems(Predicate<StudyBuddyItem> predicate) {
        ArrayList<String> taggedItems = new ArrayList<>();
        int flashcardIndex = 0;
        int cheatSheetIndex = 0;
        int noteIndex = 0;
        int noteFragmentIndex = 0;
        for (Flashcard fc : studyBuddyPro.getFlashcardList()) {
            flashcardIndex++;
            if (predicate.test(fc)) {
                taggedItems.add("Flashcard: " + flashcardIndex + ". " + fc.toString());
            }
        }
        for (CheatSheet cs : studyBuddyPro.getCheatSheetList()) {
            cheatSheetIndex++;
            if (predicate.test(cs)) {
                taggedItems.add("CheatSheet: " + cheatSheetIndex + ". " + cs.toString());
            }
        }
        for (Note n : studyBuddyPro.getNoteList()) {
            noteIndex++;
            if (predicate.test(n)) {
                taggedItems.add("Note: " + noteIndex + ". " + n.toString());
            }
            for (Note noteFrag : n.getFilteredNoteFragments(predicate)) {
                noteFragmentIndex++;
                taggedItems.add("Note Fragment: " + noteIndex + "-" + noteFragmentIndex + ". " + noteFrag.toString());
            }
            noteFragmentIndex = 0;
        }
        return taggedItems;
    }

    @Override
    public ArrayList<String> collectTaggedCheatSheets(Predicate<CheatSheet> predicate) {
        ArrayList<String> taggedItems = new ArrayList<>();
        int cheatSheetIndex = 0;
        for (CheatSheet cs : studyBuddyPro.getCheatSheetList()) {
            cheatSheetIndex++;
            if (predicate.test(cs)) {
                taggedItems.add(cheatSheetIndex + ". " + cs.toString());
            }
        }
        return taggedItems;
    }

    @Override
    public ArrayList<String> collectTaggedFlashcards(Predicate<Flashcard> predicate) {
        ArrayList<String> taggedItems = new ArrayList<>();
        int flashcardIndex = 0;
        for (Flashcard fc : studyBuddyPro.getFlashcardList()) {
            flashcardIndex++;
            if (predicate.test(fc)) {
                taggedItems.add(flashcardIndex + ". " + fc.toString());
            }
        }
        return taggedItems;
    }

    @Override
    public ArrayList<Flashcard> getTaggedFlashcards(Predicate<Flashcard> predicate) {
        ArrayList<Flashcard> taggedFlashcards = new ArrayList<>();
        for (Flashcard fc : studyBuddyPro.getFlashcardList()) {
            if (predicate.test(fc)) {
                taggedFlashcards.add(fc);
            }
        }
        return taggedFlashcards;
    }

    @Override
    public ArrayList<String> collectTaggedNotes(Predicate<Note> predicate) {
        ArrayList<String> taggedItems = new ArrayList<>();
        int noteIndex = 0;
        int noteFragmentIndex = 0;
        for (Note n : studyBuddyPro.getNoteList()) {
            noteIndex++;
            if (predicate.test(n)) {
                taggedItems.add(noteIndex + ". " + n.toString());
            }
            for (Note noteFrag : n.getFilteredNoteFragments(predicate)) {
                noteFragmentIndex++;
                taggedItems.add(noteIndex + "-" + noteFragmentIndex + ". " + noteFrag.toString());
            }
            noteFragmentIndex = 0;
        }
        return taggedItems;
    }

    @Override
    public ArrayList<String> getListOfTags() {
        ArrayList<String> listOfTags = new ArrayList<>();
        for (Tag t : studyBuddyPro.getTagList()) {
            listOfTags.add(t.toString());
        }
        return listOfTags;
    }
}
