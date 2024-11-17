import java.util.ArrayList;

class ContactManager {
    private ArrayList<Contact> contacts = new ArrayList<>();

    public ContactManager() {
        contacts.add(new Contact(1, "M.Sameer"));
        contacts.add(new Contact(2, "Khadija Noor"));
        contacts.add(new Contact(3, "Aamna"));
    }

    public void displayContacts() {
        System.out.println("Available Contacts:");
        for (Contact contact : contacts) {
            contact.displayContact();
        }
    }

    public Contact getContactById(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }
}
