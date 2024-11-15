package jumpking.model.game.elements.king;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import jumpking.model.Position;
import jumpking.model.game.elements.Element;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class King extends Element {

    private String color = "#FFFFFF";
    private BufferedImage image;

    public King (int x, int y) {
        super(x, y);
        loadImage();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Position moveUp() {
        return new Position(position.getX(), position.getY() - 1);
    }

    public Position moveDown() {
        return new Position(position.getX(), position.getY() + 1);
    }

    public Position moveLeft() {
        return new Position(position.getX() - 1, position.getY());
    }

    public Position moveRight() {
        return new Position(position.getX() + 1, position.getY());
    }

    private int calculateX(double height) {
        double velocity = 1;
        double angle = 38;
        //velocity is 9.15 for running and 2.7 for standing
        //angle is 21 for running and 38 for standing
        // Convert angle to radians
        double angleRadians = Math.toRadians(angle);
        // Calculate the distance using the projectile motion formula
        double distance = velocity * Math.cos(angleRadians) * ((velocity * Math.sin(angleRadians) + Math.sqrt(Math.pow(velocity * Math.sin(angleRadians), 2) - (2 * 9.81 * height))) / 9.81);
        // Return the new position
        return position.getX() + (int) Math.round(distance);
    }

    public List<Position> projectileMotion(double height, int direction, int maxX) {
        List<Position> points = new ArrayList<>();

        // Vertex of the parabola is at (maxX/2, height)
        double h = maxX / 2.0;
        double k = height;

        // Calculate "a" for the parabola equation y = a * (x - h)^2 + k
        double a = -4.0 * height / (maxX * maxX);

        // Generate points along the arc
        for (double x = 0; x <= maxX; x += 0.1) {
            double y = a * Math.pow(x - h, 2) + k;
            // Round x and y to integers and add to the list as Position objects
            points.add(new Position(position.getX() + (int) Math.round(x * direction), position.getY() - (int) Math.round(y)));
        }

        return points;
    }

    private void loadImage() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("sprites/kingIdle.png")) {
            if (is != null) {
                image = ImageIO.read(is);
            } else {
                throw new IOException("Image not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(TextGraphics graphics) {
        if (image != null) {
            for (int i = 0; i < image.getWidth(); i++) {
                for (int j = 0; j < image.getHeight(); j++) {
                    int color = image.getRGB(i, j);
                    int alpha = (color >> 24) & 0xff;
                    if (alpha > 0) { // Only draw pixels that are not fully transparent
                        graphics.setBackgroundColor(TextColor.Factory.fromString(String.format("#%06X", (0xFFFFFF & color))));
                        graphics.fillRectangle(new TerminalPosition(position.getX() + i, position.getY() - image.getHeight() + j + 1), new TerminalSize(1, 1), ' ');
                    }
                }
            }
        }
    }

}
