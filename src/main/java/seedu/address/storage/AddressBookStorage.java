package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyBook;
import seedu.address.model.ReadOnlyCheatSheetBook;
import seedu.address.model.ReadOnlyFlashcardBook;
import seedu.address.model.StudyBuddyBook;

/**
 * Represents a storage for {@link StudyBuddyBook}.
 */

public interface AddressBookStorage {
    // TO RENAME THE INTERFACE NAME
    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();
    Path getCheatSheetFilePath();
    Path getFlashcardFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyStudyBuddyBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyBook> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyStudyBuddyBook> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudyBuddyBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyStudyBuddyBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyStudyBuddyBook)
     */
    void saveAddressBook(ReadOnlyStudyBuddyBook addressBook, Path filePath) throws IOException;

    //============================CheatSheet Methods =====================

    /**
     * Returns AddressBook data as a {@link ReadOnlyStudyBuddyBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCheatSheetBook> readCheatSheetBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyCheatSheetBook> readCheatSheetBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudyBuddyBook} to the storage.
     * @param cheatSheetBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyStudyBuddyBook)
     */
    void saveCheatSheetBook(ReadOnlyCheatSheetBook cheatSheetBook, Path filePath) throws IOException;

    //============================Flashcard Methods =====================

    /**
     * Returns AddressBook data as a {@link ReadOnlyStudyBuddyBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFlashcardBook> readFlashcardBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyFlashcardBook> readFlashcardBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudyBuddyBook} to the storage.
     * @param flashcardBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFlashcardBook(ReadOnlyFlashcardBook flashcardBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyStudyBuddyBook)
     */
    void saveFlashcardBook(ReadOnlyFlashcardBook flashcardBook, Path filePath) throws IOException;
}

