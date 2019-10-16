package seedu.address.testutil;

import seedu.address.model.StudyBuddyBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private StudyBuddyBook studyBuddyBook;

    public AddressBookBuilder() {
        studyBuddyBook = new StudyBuddyBook();
    }

    public AddressBookBuilder(StudyBuddyBook studyBuddyBook) {
        this.studyBuddyBook = studyBuddyBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        studyBuddyBook.addPerson(person);
        return this;
    }

    public StudyBuddyBook build() {
        return studyBuddyBook;
    }
}
