/*
 * Copyright 2016 Mirko Sertic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mirkosertic.gamecomposer.contentarea.eventsheet.systemtickcondition;

import de.mirkosertic.gamecomposer.FXMLLoaderFactory;
import de.mirkosertic.gamecomposer.contentarea.eventsheet.ConditionControllerFactory;
import de.mirkosertic.gamecomposer.contentarea.eventsheet.ConditionEditorType;
import de.mirkosertic.gameengine.action.SystemTickCondition;
import de.mirkosertic.gameengine.core.GameScene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;
import javax.inject.Inject;

@ConditionEditorType(clazz = SystemTickCondition.class)
public class SystemTickConditionEditorControllerFactory implements ConditionControllerFactory<SystemTickConditionEditorController, SystemTickCondition> {

    @Inject
    FXMLLoaderFactory fxmlLoaderFactory;

    @Override
    public SystemTickConditionEditorController createFor(GameScene aGameScene, SystemTickCondition aCondition) {
        try (InputStream fxml = SystemTickConditionEditorController.class.getResourceAsStream("SystemTickConditionEditor.fxml")) {
            FXMLLoader theLoader = fxmlLoaderFactory.createLoader();
            ResourceBundle theBundle = ResourceBundle.getBundle("de.mirkosertic.gamecomposer.contentarea.eventsheet.systemtickcondition.SystemTickConditionEditor");
            theLoader.setResources(theBundle);
            BorderPane theRoot = theLoader.load(fxml);

            SystemTickConditionEditorController theController = theLoader.getController();
            return theController.initialize(theRoot, aCondition);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
