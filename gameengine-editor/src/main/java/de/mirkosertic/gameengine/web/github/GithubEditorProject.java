package de.mirkosertic.gameengine.web.github;

import de.mirkosertic.gameengine.AbstractGameRuntimeFactory;
import de.mirkosertic.gameengine.core.Game;
import de.mirkosertic.gameengine.core.GameResource;
import de.mirkosertic.gameengine.teavm.TeaVMGameLoader;
import de.mirkosertic.gameengine.teavm.TeaVMGameResourceLoader;
import de.mirkosertic.gameengine.teavm.TeaVMGameSceneLoader;
import de.mirkosertic.gameengine.type.ResourceName;
import de.mirkosertic.gameengine.web.EditorProject;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.browser.Window;

import java.io.IOException;

public class GithubEditorProject implements EditorProject {

    private static final Window window = Window.current();

    private final String username;
    private final String repository;
    private final String relativePath;

    public GithubEditorProject(String aUsername, String aRepository, String aRelativePath) {
        username = aUsername;
        repository = aRepository;
        relativePath = aRelativePath;
    }

    @Override
    public TeaVMGameSceneLoader createSceneLoader(TeaVMGameSceneLoader.GameSceneLoadedListener aListener,
            AbstractGameRuntimeFactory aRuntimeFactory) {
        return new TeaVMGameSceneLoader(aListener, aRuntimeFactory) {
            @Override
            public void loadFromServer(Game aGame, String aSceneName, TeaVMGameResourceLoader aResourceLoader) {
                final XMLHttpRequest theRequest = XMLHttpRequest.create();
                theRequest.overrideMimeType("text/plain");
                theRequest.open("GET", "https://raw.githubusercontent.com/" + username + "/" + repository + "/master" + relativePath + "/" + aSceneName + "/scene.json");
                theRequest.onComplete(() -> listener.onGameSceneLoaded(parse(aGame, theRequest.getResponseText(), aResourceLoader)));
                theRequest.send();
            }
        };
    }

    @Override
    public TeaVMGameLoader createGameLoader(TeaVMGameLoader.GameLoadedListener aListener) {
        return new TeaVMGameLoader(aListener) {
            @Override
            public void loadFromServer() {
                final XMLHttpRequest theRequest = XMLHttpRequest.create();
                theRequest.overrideMimeType("text/plain");
                theRequest.open("GET", "https://raw.githubusercontent.com/" + username + "/" + repository + "/master" + relativePath + "/game.json");
                theRequest.onComplete(() -> listener.onGameLoaded(parse(theRequest.getResponseText())));
                theRequest.send();
            }
        };
    }

    @Override
    public TeaVMGameResourceLoader createResourceLoaderFor(String aSceneID) {
        return new TeaVMGameResourceLoader(aSceneID) {
            @Override
            public GameResource load(ResourceName aResourceName) throws IOException {
                ResourceName theNewResourceName = new ResourceName("https://raw.githubusercontent.com/" + username + "/" + repository + "/master" + relativePath + "/" + aSceneID + aResourceName.get());
                return convert(theNewResourceName);
            }
        };
    }

    @Override
    public void setCurrentPreview(String aSceneDataAsJSON) {
        window.getLocalStorage().setItem("previewscene", aSceneDataAsJSON);
    }

    @Override
    public String getPreviewDataAsJSON() {
        return window.getLocalStorage().getItem("previewscene");
    }
}