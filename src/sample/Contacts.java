package sample;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Contacts {

    @XmlElement(name = "contact")
    private List<Contact> contacts = new ArrayList<>();//Stores Contacts

    public List<Contact> getContacts() {
        return contacts;
    }

}
