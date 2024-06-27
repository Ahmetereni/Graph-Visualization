package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.HashSet;
import java.util.Set;

public class CircularDfs {


    public static void main(String[] args) {
        // Create nodes
        Birey A = new Birey("A");
        Birey B = new Birey("B");
        Birey C = new Birey("C");
        Birey D = new Birey("D");
        Birey E = new Birey("E");
        Birey F = new Birey("F");
        Birey G = new Birey("G");

        // Create edges
        A.komsuEkle(E);
        A.komsuEkle(F);
        C.komsuEkle(F);
        E.komsuEkle(B);
        E.komsuEkle(D);
        B.komsuEkle(F);
        B.komsuEkle(D);
        B.komsuEkle(G);

        // Set up the frame
        JFrame frame = new JFrame("DFS Visualization");
        org.example.DFSVisualizationPanel panel = new org.example.DFSVisualizationPanel();
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Perform DFS starting from node A
        Set<Birey> visited = new HashSet<>();
        panel.setNodes(A, B, C, D, E, F, G);
        panel.dfs(A, visited);
    }
}

class DFSVisualizationPanel extends JPanel {
    private Set<Birey> visited = new HashSet<>();
    private Birey[] nodes;
    private Point[] positions; // Store positions of nodes
    static final int delay = 800;
    static final int radius = 200;
    static final int centerX = 400;
    static final int centerY = 300;

    public void setNodes(Birey... nodes) {
        this.nodes = nodes;
        calculatePositions();
    }

    private void calculatePositions() {
        int n = nodes.length;
        positions = new Point[n];

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            positions[i] = new Point(x, y);
        }
    }

    public void dfs(Birey node, Set<Birey> visited) {
        if (node == null || visited.contains(node)) {
            return;
        }

        // Mark the current node as visited
        visited.add(node);
        this.visited.add(node);

        // Repaint the panel to update the visualization
        repaint();

        // Delay for visualization
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Recur for all the neighbors
        for (Birey neighbor : node.getKomsulari()) {
            dfs(neighbor, visited);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int nodeRadius = 25;

        if (nodes != null) {
            for (int i = 0; i < nodes.length; i++) {
                Birey node = nodes[i];
                int x = positions[i].x;
                int y = positions[i].y;

                // Draw edges
                for (Birey neighbor : node.getKomsulari()) {
                    int neighborIndex = -1;
                    for (int j = 0; j < nodes.length; j++) {
                        if (nodes[j].equals(neighbor)) {
                            neighborIndex = j;
                            break;
                        }
                    }
                    if (neighborIndex != -1) {
                        int nx = positions[neighborIndex].x;
                        int ny = positions[neighborIndex].y;
                        drawArrowLine(g2, x, y, nx, ny, nodeRadius, 10, 5);
                    }
                }

                // Draw nodes
                if (visited.contains(node)) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.BLUE);
                }
                g2.fillOval(x - nodeRadius, y - nodeRadius, nodeRadius * 2, nodeRadius * 2);
                g2.setColor(Color.BLACK);
                g2.drawString(node.getAd(), x - 5, y + 5);
            }
        }
    }

    private void drawArrowLine(Graphics2D g2, int x1, int y1, int x2, int y2, int radius, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double sin = dy / D, cos = dx / D;

        // Shorten the line by the radius of the circle
        double x1Adjusted = x1 + radius * cos;
        double y1Adjusted = y1 + radius * sin;
        double x2Adjusted = x2 - radius * cos;
        double y2Adjusted = y2 - radius * sin;

        double xm = D - d, xn = xm, ym = h, yn = -h;
        xm = x2Adjusted - d * cos;
        ym = y2Adjusted - d * sin;
        xn = x2Adjusted - (d + h) * cos;
        yn = y2Adjusted - (d + h) * sin;

        int[] xpoints = {(int) x2Adjusted, (int) xm, (int) xn};
        int[] ypoints = {(int) y2Adjusted, (int) ym, (int) yn};

        g2.drawLine((int) x1Adjusted, (int) y1Adjusted, (int) x2Adjusted, (int) y2Adjusted);
        g2.fillPolygon(xpoints, ypoints, 3);
    }
}


