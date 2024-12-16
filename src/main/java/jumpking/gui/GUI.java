package jumpking.gui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.BasicTextImage;
import jumpking.model.Position;

import java.io.IOException;

public interface GUI {
    enum Act {LEFT, UP, RIGHT, DOWN, QUIT, SELECT, JUMP, PAUSE, NONE};
    void drawPixel(Position position, TextColor color);
    void drawImage(BasicTextImage image);
    void drawTextImage(Position position, String[] image, TextColor color,boolean isCredits);
    void drawLine(Position position, String imageline,TextColor color,boolean isCredits);
    void clear();
    Act getNextAction();
    void refresh() throws IOException;
    void close() throws IOException;
    void draw() throws IOException;
}
