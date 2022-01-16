package brichev.sd.bridge.draw;

import brichev.sd.bridge.figure.Circle;
import brichev.sd.bridge.figure.Line;
import brichev.sd.bridge.utils.Configuration;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class FxDrawingApi extends Application implements DrawingApi {
    private static final List<Circle> circles = new ArrayList<>();
    private static final List<Line> lines = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("FX Graph");
        var root = new Group();
        var canvas = new Canvas(Configuration.WIDTH.doubleValue(), Configuration.HEIGHT.doubleValue());
        var gc = canvas.getGraphicsContext2D();
        lines.forEach(line -> {
                    gc.setFill(Color.DARKGREY);
                    gc.moveTo(line.getFrom().getX(), line.getFrom().getY());
                    gc.lineTo(line.getTo().getX(), line.getTo().getY());
                    gc.stroke();
                }
        );
        circles.forEach(
                circle -> {
                    gc.setFill(Color.ORANGE);
                    gc.fillOval(
                            circle.getCenter().getX() - circle.getRadius(),
                            circle.getCenter().getY() - circle.getRadius(),
                            circle.getRadius() * 2,
                            circle.getRadius() * 2
                    );
                }
        );
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Override
    public long getDrawingAreaWidth() {
        return Configuration.WIDTH;
    }

    @Override
    public long getDrawingAreaHeight() {
        return Configuration.HEIGHT;
    }

    @Override
    public void drawCircle(Circle circle) {
        circles.add(circle);
    }

    @Override
    public void drawLine(Line line) {
        lines.add(line);
    }

    @Override
    public void show() {
        launch();
    }
}
