package brichev.sd.bridge.draw;

import brichev.sd.bridge.figure.Circle;
import brichev.sd.bridge.figure.Line;

public interface DrawingApi {
    long getDrawingAreaWidth();

    long getDrawingAreaHeight();

    void drawCircle(Circle circle);

    void drawLine(Line line);

    void show();
}
