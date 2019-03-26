package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;


public class ImageTextCell extends ListCell<Contact> {
    private VBox vBox = new VBox(8.0);
    private ImageView thumbnailImageView = new ImageView();
    private Label label = new Label();

    public ImageTextCell() {
        vBox.setAlignment(Pos.CENTER);
        thumbnailImageView.setPreserveRatio(true);
        thumbnailImageView.setFitHeight(100.0);
        vBox.getChildren().add(thumbnailImageView);

        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        vBox.getChildren().add(label);
        setPrefWidth(USE_PREF_SIZE);
    }

    @Override
    protected void updateItem(Contact item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);


        } else {
            thumbnailImageView.setImage(item.getImage());
            label.setText(item.toString());
            setGraphic(vBox);
        }
    }


}


