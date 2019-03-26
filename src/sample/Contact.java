package sample;

import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;


public class Contact {
    private String firstName;
    private String lastName;
    private String homeAddress;
    private String email;
    private String phoneNumber;
    private Image image;


    public Contact() {

        this("", "", "", "", "", null);
    }


    public Contact(String firstName, String lastName, String email, String homeAddress, String phoneNumber, Image image) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.homeAddress = homeAddress;
        this.image = image;
        this.phoneNumber = phoneNumber;

    }


    public String getFirstName() {
        return firstName;

    }

    public String getLastName() {
        return lastName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        if (getFirstName().isEmpty()) {
            return getLastName();
        }
        if (getLastName().isEmpty()) {
            return getFirstName();
        } else {
            return getFirstName() + " " + getLastName();

        }

    }


}

