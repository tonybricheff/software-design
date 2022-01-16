package brichev.sd.bridge.graph;

import brichev.sd.bridge.draw.DrawingApi;
import brichev.sd.bridge.figure.Circle;
import brichev.sd.bridge.figure.Edge;
import brichev.sd.bridge.figure.Line;
import brichev.sd.bridge.figure.Point;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.*;
import static java.lang.Math.sin;

public abstract class Graph {
    /**
     * Bridge to drawing api
     */
    private final DrawingApi drawingApi;

    protected Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void drawGraph();

    protected void drawListGraph(Integer vertexesNumber, List<Edge> edges) {
        var points = pointsFromVertexesNumber(vertexesNumber);
        drawVertexes(points);
        drawEdges(points, edges);
        drawingApi.show();
    }

    private List<Point> pointsFromVertexesNumber(Integer vertexesNumber) {
        var a = drawingApi.getDrawingAreaWidth() / 2;
        var b = drawingApi.getDrawingAreaHeight() / 2;
        var r = min(a, b) * 0.8;
        var points = new ArrayList<Point>();
        for (int i = 0; i < vertexesNumber; i++) {
            double t = 2 * Math.PI * i / vertexesNumber;
            var x = a + r * cos(t);
            var y = b + r * sin(t);
            points.add(new Point(x, y));
        }
        return points;
    }

    private void drawVertexes(List<Point> points) {
        var vertexRadius = 10.0;
        points.forEach(point -> drawingApi.drawCircle(new Circle(vertexRadius, point)));
    }

    private void drawEdges(List<Point> points, List<Edge> edges) {
        edges.forEach(edge -> drawingApi.drawLine(new Line(points.get(edge.getFrom()), points.get(edge.getTo()))));
    }
}
