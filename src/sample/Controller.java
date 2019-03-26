package sample;


import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.util.Callback;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;
import javafx.collections.FXCollections;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElement;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javafx.scene.input.KeyCode.F;


public class Controller {

    //Current Stage
    Stage thisStage;


    //stores the list of Contact Objects
    private final ObservableList<Contact> contactsObservableList = FXCollections.observableArrayList();


    @FXML
    private Button exportButton;

    @FXML
    private Label imageLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addButton;

    @FXML
    private Button saveButton;

    @FXML
    public Button importButton;


    @FXML
    private TextField firstNameTextField;

    @FXML
    private VBox contactsVbox;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField homeAddressTextField;

    @FXML
    private ImageView contactImageView;


    @FXML
    private ListView<Contact> contactsListView;


    @FXML
    public void contactSelected(MouseEvent mouseEvent) {


        int contactIndex = contactsObservableList.indexOf(contactsListView.getSelectionModel().getSelectedItem());
        Contact currentContact;
        currentContact = contactsObservableList.get(contactIndex);
        homeAddressTextField.setText(currentContact.getHomeAddress());
        emailTextField.setText(currentContact.getEmail());
        phoneNumberTextField.setText(currentContact.getPhoneNumber());
        lastNameTextField.setText(currentContact.getLastName());
        firstNameTextField.setText(currentContact.getFirstName());
        contactImageView.setImage(currentContact.getImage());


    }


    @FXML
    private void addButtonPressed(ActionEvent event) {

        //sets current selection to null
        contactsListView.getSelectionModel().select(null);

        //Sets all fields to either empty string or null value
        homeAddressTextField.setText("");
        emailTextField.setText("");
        phoneNumberTextField.setText("");
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        contactImageView.setImage(null);


    }


    @FXML
    void onDeleteButtonPressed(ActionEvent event) {
        int contactIndex = contactsObservableList.indexOf(contactsListView.getSelectionModel().getSelectedItem());
        contactsObservableList.remove(contactIndex);
        homeAddressTextField.setText("");
        emailTextField.setText("");
        phoneNumberTextField.setText("");
        lastNameTextField.setText("");
        firstNameTextField.setText("");
        contactImageView.setImage(null);


    }


    @FXML
    void saveButtonPressed(ActionEvent event) {

        //Checks if at least first name or last name is filled in
        if (firstNameTextField.getText().isEmpty() && lastNameTextField.getText().isEmpty()) {
            System.out.println("Please Enter either a First Name or Last Name");

        } else {

            //Checks if selection is currently in the list
            if (contactsObservableList.contains(contactsListView.getSelectionModel().getSelectedItem())) {

                //Index of selected contact
                int contactIndex = contactsObservableList.indexOf(contactsListView.getSelectionModel().getSelectedItem());

                //Get contact object of current contact
                Contact contact = contactsListView.getSelectionModel().getSelectedItem();

                // set all fields of contact
                contact.setImage(contactImageView.getImage());
                contact.setEmail(emailTextField.getText());
                contact.setFirstName(firstNameTextField.getText());
                contact.setLastName(lastNameTextField.getText());
                contact.setHomeAddress(homeAddressTextField.getText());
                contact.setPhoneNumber(phoneNumberTextField.getText());

                //Replace value in list
                contactsObservableList.set(contactIndex, contact);


            } else {

                String firstName = firstNameTextField.getText();
                String lastName = lastNameTextField.getText();
                String email = emailTextField.getText();
                String homeAddress = homeAddressTextField.getText();
                String phoneNumber = phoneNumberTextField.getText();
                Image image = contactImageView.getImage();
                Contact contact = new Contact(firstName, lastName, email, homeAddress, phoneNumber, image);

                //add contact object to observable list
                contactsObservableList.add(contact);
            }


        }


    }


    @FXML
    public void imageViewClicked(MouseEvent mouseEvent) {

        //Open file chooser window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(thisStage);

        //Check if file was selected
        if (selectedFile != null) {
            BufferedImage image = null;
            try {
                //Convert file object to image object
                image = ImageIO.read(selectedFile);
                Image new_image = SwingFXUtils.toFXImage(image, null);

                contactImageView.setImage(new_image);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    @FXML
    public void importButtonPressed(ActionEvent actionEvent) {

        //Open file chooser window
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(thisStage);
        if (selectedFile.exists()) {
            Contacts contacts = JAXB.unmarshal(selectedFile, Contacts.class);
            for (Contact contact : contacts.getContacts()) {
                contactsObservableList.add(contact);
            }
        }


    }

    @FXML
    public void exportButtonPressed(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("XML Files", "*.xml"));
        File selectedFile = fileChooser.showOpenDialog(thisStage);

        //Stores the Contacts before XML serialization
        Contacts contacts = new Contacts();


        try {
            // create new record
            int indexOfContact = contactsListView.getSelectionModel().getSelectedIndex();
            Contact selectedContact = contactsObservableList.get(indexOfContact);
            Contact record = selectedContact;

            // add to ContactList
            contacts.getContacts().add(record);
        } catch (NoSuchElementException elementException) {
            System.err.println("Invalid input. Please try again.");
        }
        //marshal file contents
        JAXB.marshal(contacts, selectedFile);


    }

    public void initialize() {
        //Listener for when observable list is changed
        contactsObservableList.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change change) {


                contactsListView.setItems(contactsObservableList.sorted());
                contactsListView.setCellFactory(
                        new Callback<ListView<Contact>, ListCell<Contact>>() {
                            @Override
                            public ListCell<Contact> call(ListView<Contact> listView) {
                                return new ImageTextCell();
                            }
                        });
            }
        });


    }

}
