package brichev.sd.bridge.draw;

import brichev.sd.bridge.figure.Circle;
import brichev.sd.bridge.figure.Line;
import brichev.sd.bridge.utils.Configuration;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.DARK_GRAY;
import static java.awt.Color.ORANGE;

public class AwtDrawingApi implements DrawingApi {
    private final List<Line> lines = new ArrayList<>();
    private final List<Circle> circles = new ArrayList<>();

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
        var frame = new JFrame("AWT Graph");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(new DrawLinesAndCircles(lines, circles));
        frame.pack();
        frame.setSize(new Dimension(Configuration.WIDTH, Configuration.HEIGHT));
        frame.setVisible(true);
    }

    private static class DrawLinesAndCircles extends Canvas {
        private final LinePainter linePainter;
        private final CirclePainter circlePainter;

        DrawLinesAndCircles(List<Line> lines, List<Circle> circles) {
            this.linePainter = new LinePainter(lines);
            this.circlePainter = new CirclePainter(circles);
        }

        @Override
        public void paint(Graphics g) {
            linePainter.paint(g);
            circlePainter.paint(g);
        }
    }

    @AllArgsConstructor
    private static class LinePainter extends Canvas {
        private final List<Line> lines;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2f));
            lines.forEach(line -> {
                        g2.setColor(DARK_GRAY);
                        g2.draw(
                                new Line2D.Double(
                                        line.getFrom().getX(), line.getFrom().getY(),
                                        line.getTo().getX(), line.getTo().getY())
                        );
                    }
            );
        }
    }

    @AllArgsConstructor
    private static class CirclePainter extends Canvas {
        private final List<Circle> circles;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2f));
            circles.forEach(circle -> {
                        g2.setColor(ORANGE);
                        g2.fill(
                                new Ellipse2D.Double(
                                        circle.getCenter().getX() - circle.getRadius(),
                                        circle.getCenter().getY() - circle.getRadius(),
                                        circle.getRadius() * 2,
                                        circle.getRadius() * 2
                                )
                        );
                    }
            );
        }
    }
}
