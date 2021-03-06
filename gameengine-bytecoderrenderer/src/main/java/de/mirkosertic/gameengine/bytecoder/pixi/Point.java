/*
 * Copyright 2019 Mirko Sertic
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
package de.mirkosertic.gameengine.bytecoder.pixi;

import de.mirkosertic.bytecoder.api.OpaqueProperty;
import de.mirkosertic.bytecoder.api.OpaqueReferenceType;

public abstract class Point implements OpaqueReferenceType {

    public abstract void set(float aX, float aY);

    @OpaqueProperty
    public abstract void x(float aX);

    @OpaqueProperty
    public abstract void y(float aY);
}
