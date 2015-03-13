package de.mirkosertic.gameengine.physic;

import de.mirkosertic.gameengine.type.*;

public class PlatformClassInformation extends ClassInformation {

  public static final Method<de.mirkosertic.gameengine.physic.Platform> DELETE = new Method<de.mirkosertic.gameengine.physic.Platform>("delete", void.class, new Class[] {}) {
    @Override
    public Object invoke(Platform aObject, Object[] aArguments) {
      aObject.delete();
      return null;
    }
  };

  public static final Field<Platform, de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode>> MOVELEFTKEYPROPERTY = new Field<Platform, de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode>>("moveLeftKeyProperty", de.mirkosertic.gameengine.event.Property.class) {
    @Override
    public de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode> getValue(Platform aObject) {
      return aObject.moveLeftKeyProperty();
    }
  };

  public static final Field<Platform, de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode>> MOVERIGHTKEYPROPERTY = new Field<Platform, de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode>>("moveRightKeyProperty", de.mirkosertic.gameengine.event.Property.class) {
    @Override
    public de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode> getValue(Platform aObject) {
      return aObject.moveRightKeyProperty();
    }
  };

  public static final Field<Platform, de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode>> JUMPKEYPROPERTY = new Field<Platform, de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode>>("jumpKeyProperty", de.mirkosertic.gameengine.event.Property.class) {
    @Override
    public de.mirkosertic.gameengine.event.Property<de.mirkosertic.gameengine.type.GameKeyCode> getValue(Platform aObject) {
      return aObject.jumpKeyProperty();
    }
  };

  public static final Field<Platform, de.mirkosertic.gameengine.event.Property<java.lang.Float>> LEFTRIGHTIMPULSEPROPERTY = new Field<Platform, de.mirkosertic.gameengine.event.Property<java.lang.Float>>("leftRightImpulseProperty", de.mirkosertic.gameengine.event.Property.class) {
    @Override
    public de.mirkosertic.gameengine.event.Property<java.lang.Float> getValue(Platform aObject) {
      return aObject.leftRightImpulseProperty();
    }
  };

  public static final Field<Platform, de.mirkosertic.gameengine.event.Property<java.lang.Float>> JUMPIMPULSEPROPERTY = new Field<Platform, de.mirkosertic.gameengine.event.Property<java.lang.Float>>("jumpImpulseProperty", de.mirkosertic.gameengine.event.Property.class) {
    @Override
    public de.mirkosertic.gameengine.event.Property<java.lang.Float> getValue(Platform aObject) {
      return aObject.jumpImpulseProperty();
    }
  };

  public PlatformClassInformation() {
    register(DELETE);
    register(MOVELEFTKEYPROPERTY);
    register(MOVERIGHTKEYPROPERTY);
    register(JUMPKEYPROPERTY);
    register(LEFTRIGHTIMPULSEPROPERTY);
    register(JUMPIMPULSEPROPERTY);
  }
}
