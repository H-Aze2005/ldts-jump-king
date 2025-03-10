package jumpking.states;

import jumpking.control.Controller;
import jumpking.control.KingController;
import jumpking.control.SceneController;
import jumpking.model.game.scene.Scene;
import jumpking.view.screens.GameViewer;
import jumpking.view.screens.ScreenViewer;
import jumpking.view.SpriteLoader;
import jumpking.view.ViewProvider;

import java.io.IOException;
import java.io.UncheckedIOException;

public class GameState extends State<Scene> {

    public GameState(Scene scene, SpriteLoader spriteLoader) throws IOException {
        super(scene, spriteLoader);
    }

    @Override
    protected ScreenViewer<Scene> createScreenViewer(ViewProvider viewProvider) {
        return new GameViewer(getModel(), viewProvider);
    }

    @Override
    protected Controller<Scene> createController() throws IOException {
        return new SceneController(getModel(), new KingController(getModel()));
    }

}
