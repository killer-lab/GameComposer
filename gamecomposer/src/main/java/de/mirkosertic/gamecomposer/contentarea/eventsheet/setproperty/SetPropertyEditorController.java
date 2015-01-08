package de.mirkosertic.gamecomposer.contentarea.eventsheet.setproperty;

import de.mirkosertic.gamecomposer.StringConverterFactory;
import de.mirkosertic.gamecomposer.contentarea.eventsheet.ActionController;
import de.mirkosertic.gameengine.core.GameObject;
import de.mirkosertic.gameengine.core.GameRule;
import de.mirkosertic.gameengine.core.GameScene;
import de.mirkosertic.gameengine.core.SetPropertyAction;
import de.mirkosertic.gameengine.expression.PropertyDiscoverer;
import de.mirkosertic.gameengine.type.TextExpression;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetPropertyEditorController implements ActionController {

    @Inject
    StringConverterFactory stringConverterFactory;

    @FXML
    ComboBox gameObject;

    @FXML
    ComboBox propertyName;

    @FXML
    TextField propertyValueTextField;

    private GameScene gameScene;
    private SetPropertyAction action;
    private Node view;

    SetPropertyEditorController initialize(Node aView, GameScene aGameScene, GameRule aRule, SetPropertyAction aAction) {
        gameScene = aGameScene;
        view = aView;
        action = aAction;

        // Gather the known properties
        PropertyDiscoverer theDiscoverer = new PropertyDiscoverer();
        List<String> theSupportedProperties = theDiscoverer.gatherAllPossibleProperties();

        propertyName.getItems().clear();

        TextExpression theExpression = action.propertyValueProperty().get();
        if (theExpression != null) {
            propertyValueTextField.setText(theExpression.expression);
        }
        propertyValueTextField.setVisible(false);
        propertyValueTextField.textProperty().addListener((observableValue, s, s2) -> onTextfieldChange());

        propertyName.getItems().addAll(theSupportedProperties);
        if (action.propertyNameProperty().isNull()) {
            propertyName.getSelectionModel().select(0);
        } else {
            for (int i = 0; i < theSupportedProperties.size(); i++) {
                String theProp = theSupportedProperties.get(i);
                if (theProp.equals(action.propertyNameProperty().get())) {
                    propertyName.getSelectionModel().select(i);
                }
            }
        }

        List<GameObject> theSortedGameObjects = Arrays.asList(aGameScene.getObjects());
        Collections.sort(theSortedGameObjects, (o1, o2) -> o1.nameProperty().get().compareTo(o2.nameProperty().get()));


        gameObject.getItems().clear();
        gameObject.getItems().addAll(theSortedGameObjects);
        gameObject.setConverter(stringConverterFactory.createGameObjectStringConverter());
        gameObject.getSelectionModel().select(action.gameObjectProperty().get());

        gameObject.setOnAction(actionEvent -> onObjectTypeSelected());

        return this;
    }

    private void onObjectTypeSelected() {
        action.gameObjectProperty().set((GameObject) gameObject.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onSelectProperty() {
        String theSelectedProperty = (String) propertyName.getSelectionModel().getSelectedItem();
        action.propertyNameProperty().set(theSelectedProperty);

        propertyValueTextField.setVisible(true);
    }

    private void onTextfieldChange() {
        action.propertyValueProperty().set(new TextExpression(propertyValueTextField.getText()));
    }

    @Override
    public Node getView() {
        return view;
    }
}
