package de.mirkosertic.gamecomposer.contentarea.gamescene;

import de.mirkosertic.gamecomposer.FXMLLoaderFactory;
import de.mirkosertic.gamecomposer.ObjectSelectedEvent;
import de.mirkosertic.gamecomposer.contentarea.ContentAreaFactory;
import de.mirkosertic.gamecomposer.contentarea.ContentAreaFactoryType;
import de.mirkosertic.gameengine.camera.CameraBehavior;
import de.mirkosertic.gameengine.camera.SetScreenResolution;
import de.mirkosertic.gameengine.core.*;
import de.mirkosertic.gameengine.event.GameEventManager;
import de.mirkosertic.gameengine.physic.GamePhysicsManager;
import de.mirkosertic.gameengine.type.Size;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

@ContentAreaFactoryType(clazz = GameScene.class)
public class GameSceneEditorControllerFactory implements ContentAreaFactory<GameScene, GameSceneEditorController> {

    @Inject
    Event<ObjectSelectedEvent> objectSelectedEventEvent;

    @Inject
    FXMLLoaderFactory fxmlLoaderFactory;

    @Override
    public GameSceneEditorController create(GameScene aScene) {

        final GameEventManager theEventManager = aScene.getRuntime().getEventManager();
        GameRuntime theRuntime = aScene.getRuntime();

        GameObject theDefaultCamera = aScene.cameraObjectProperty().get();
        if (theDefaultCamera == null) {
            throw new IllegalArgumentException("No camera set");
        }

        // Detect and create a camera
        GameObjectInstance theCameraObject = aScene.createFrom(theDefaultCamera);
        CameraBehavior theCameraComponent = theCameraObject.getComponent(CameraBehavior.class);
        if (theCameraComponent == null) {
            throw new IllegalArgumentException("No camera component in camera object");
        }

        GamePhysicsManager thePhysicsManager = null;
        for (GameSystem theSystem : theRuntime.getSystems()) {
            if (theSystem instanceof GamePhysicsManager) {
                thePhysicsManager = (GamePhysicsManager) theSystem;
            }
        }

        EditorFXGameView theGameView = new EditorFXGameView(theRuntime, theCameraComponent, thePhysicsManager);

        GameLoopFactory theGameLoopFactory = new GameLoopFactory();
        GameLoop theMainLoop = theGameLoopFactory.create(aScene, theGameView, theRuntime);

        final CameraBehavior theFinalCameraComponent = theCameraComponent;

        // Set defaults, this will be overridden
        theEventManager.fire(new SetScreenResolution(new Size(200, 200)));

        try (InputStream fxml = GameSceneEditorController.class.getResourceAsStream("GameSceneEditor.fxml")) {
            FXMLLoader theLoader = fxmlLoaderFactory.createLoader();
            ResourceBundle theBundle = ResourceBundle.getBundle("de.mirkosertic.gamecomposer.contentarea.gamescene.GameSceneEditor");
            theLoader.setResources(theBundle);
            BorderPane root = theLoader.load(fxml);

            GameSceneEditorController theController = theLoader.getController();
            theController.centerBorderPane.widthProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    theEventManager.fire(new SetScreenResolution(new Size((int) ((double) number2), theFinalCameraComponent.getScreenSize().height)));
                }
            });
            theController.centerBorderPane.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                    theEventManager.fire(new SetScreenResolution(new Size(theFinalCameraComponent.getScreenSize().width, (int) ((double) number2))));
                }
            });
            theGameView.widthProperty().bind(theController.centerBorderPane.widthProperty());
            theGameView.heightProperty().bind(theController.centerBorderPane.heightProperty());

            return theController.initialize(theRuntime, aScene, root, theGameView, theMainLoop, theCameraComponent, objectSelectedEventEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}