package de.mirkosertic.gameengine.input;

import de.mirkosertic.gameengine.type.*;

public class MousePressedClassInformation extends de.mirkosertic.gameengine.event.GameEventClassInformation {

  public static final Field<MousePressed, de.mirkosertic.gameengine.type.Position> POSITION = new Field<MousePressed, de.mirkosertic.gameengine.type.Position>("position", de.mirkosertic.gameengine.type.Position.class) {
    @Override
    public de.mirkosertic.gameengine.type.Position getValue(MousePressed aObject) {
      return aObject.position;
    }
  };

  public static final Field<MousePressed, de.mirkosertic.gameengine.core.GameObjectInstance[]> AFFECTEDINSTANCES = new Field<MousePressed, de.mirkosertic.gameengine.core.GameObjectInstance[]>("affectedInstances", de.mirkosertic.gameengine.core.GameObjectInstance[].class) {
    @Override
    public de.mirkosertic.gameengine.core.GameObjectInstance[] getValue(MousePressed aObject) {
      return aObject.affectedInstances;
    }
  };

  public MousePressedClassInformation() {
    register(POSITION);
    register(AFFECTEDINSTANCES);
  }
}
