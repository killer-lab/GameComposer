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
package de.mirkosertic.gameengine.bytecoder;

import de.mirkosertic.gameengine.core.GameResource;
import de.mirkosertic.gameengine.core.GameResourceLoader;
import de.mirkosertic.gameengine.core.GameResourceType;
import de.mirkosertic.gameengine.core.LoadedSpriteSheet;
import de.mirkosertic.gameengine.core.Promise;
import de.mirkosertic.gameengine.core.PromiseRejector;
import de.mirkosertic.gameengine.core.PromiseResolver;
import de.mirkosertic.gameengine.type.ResourceName;

public class BytecoderGameResourceLoader implements GameResourceLoader {

    private final String sceneId;

    public BytecoderGameResourceLoader(String sceneId) {
        this.sceneId = sceneId;
    }

    @Override
    public Promise<GameResource, String> load(ResourceName aResourceName) {
        BytecoderLogger.INSTANCE.info("Finishing Game resource loading");
        return new Promise<>(new Promise.Executor() {
            @Override
            public void process(PromiseResolver aResolver, PromiseRejector aRejector) {
                aResolver.resolve(new GameResource() {
                    @Override
                    public GameResourceType getType() {
                        return GameResourceType.BITMAP;
                    }
                });
            }
        });
    }

    @Override
    public Promise<LoadedSpriteSheet, String> loadSpriteSheet(ResourceName aResourceName) {
        BytecoderLogger.INSTANCE.info("Trying to load spritesheet");
        return new Promise<>(new Promise.Executor() {
            @Override
            public void process(PromiseResolver aResolver, PromiseRejector aRejector) {
                BytecoderLogger.INSTANCE.info("Finishing Sprite Sheet loading");
                aResolver.resolve(new LoadedSpriteSheet() {
                    @Override
                    public GameResource getResourceFor(ResourceName aResourceName) {
                        return new GameResource() {
                            @Override
                            public GameResourceType getType() {
                                return GameResourceType.BITMAP;
                            }
                        };
                    }
                });
            }
        });
    }

    @Override
    public void flush() {
    }
}