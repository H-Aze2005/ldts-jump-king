package jumpking.control;

import jumpking.Application;
import jumpking.gui.GUI;
import jumpking.model.game.elements.King;
import jumpking.model.game.scene.Scene;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class KingController extends Controller {

    private boolean upKeyPressed = false;
    private Instant keyPressStartTime;
    public static final int MIN_JUMP_HEIGHT = 10;
    public static final int MAX_JUMP_HEIGHT = 230;
    private static final int refreshRate = 5;

    public KingController(Scene scene) {
        super(scene);
    }

    @Override
    public void step(Application app, GUI.Act act, long time) throws IOException {
        Scene scene = (Scene) getModel();
        King king = scene.getKing();
        try {
            switch (act) {
                case UP:
                    if (!upKeyPressed) {
                        upKeyPressed = true;
                        keyPressStartTime = Instant.now();
                        king.setIsJumping(true);
                    } else {
                        Duration keyPressDuration = Duration.between(keyPressStartTime, Instant.now());
                        int jumpHeight = (int) keyPressDuration.toMillis() / 20;
                        jumpHeight = Math.max(MIN_JUMP_HEIGHT, Math.min(jumpHeight, MAX_JUMP_HEIGHT));
                        scene.moveUp(jumpHeight);
                        upKeyPressed = false;
                        king.setIsJumping(false);
                    }
                    break;
                case LEFT:
                    if (upKeyPressed) {
                        Duration keyPressDuration = Duration.between(keyPressStartTime, Instant.now());
                        int jumpHeight = (int) keyPressDuration.toMillis() / 20;
                        jumpHeight = Math.max(MIN_JUMP_HEIGHT, Math.min(jumpHeight, MAX_JUMP_HEIGHT));
                        scene.jump(jumpHeight, -1);
                        upKeyPressed = false;
                        king.setIsJumping(false);
                    } else {
                        scene.moveLeft(5);
                        king.setIsRunning(true);
                    }
                    break;
                case RIGHT:
                    if (upKeyPressed) {
                        Duration keyPressDuration = Duration.between(keyPressStartTime, Instant.now());
                        int jumpHeight = (int) keyPressDuration.toMillis() / 20;
                        jumpHeight = Math.max(MIN_JUMP_HEIGHT, Math.min(jumpHeight, MAX_JUMP_HEIGHT));
                        scene.jump(jumpHeight, 1);
                        upKeyPressed = false;
                        king.setIsJumping(false);
                    } else {
                        scene.moveRight(5);
                        king.setIsRunning(true);
                    }
                    break;
                case QUIT:
                    app.setRunning(false);
                    break;
                default:
                    break;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
