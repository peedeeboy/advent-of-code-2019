package aoc;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Day3Part2 {

    private static int steps;
    private static String[] line1;
    private static String[] line2;
    private static Map<Point, Integer> line1points;
    private static Map<Point, Integer> line2points;
    private static Map<Point, Integer> intersections;
    private static MoveablePoint curPos;

    public static void main(String[] args) {

        try {
            readFile();
            line1points = processLine(line1);
            line2points = processLine(line2);
            
            
            intersections = new HashMap<>(line1points);
            intersections.keySet().retainAll(line2points.keySet());
            
            
            System.out.println(intersections.entrySet()
                    .stream()
                    .map(p -> calculateDistance(p.getKey()))
                    .sorted()
                    .findFirst());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
    
    private static int calculateDistance(Point point) {
        
        return line1points.get(point) + line2points.get(point);
    }
            
    private static void readFile() throws IOException {

        FileReader fileReader = new FileReader("input.txt");
        BufferedReader in = new BufferedReader(fileReader);

        line1 = in.readLine().split(",");
        line2 = in.readLine().split(",");
    }
    
    private static Map<Point, Integer> processLine(String[] line) {
        
        curPos = new MoveablePoint(0, 0, 0);
        
        Map<Point, Integer> result = new HashMap<>();
        
        Arrays.stream(line).forEach(x -> result.putAll(move(x)));
        
        return result;
    }
    
    private static Map<Point, Integer> move(String instruction) {
        
        char c = instruction.charAt(0);
        int places = Integer.parseInt(instruction.substring(1));
        Map<Point, Integer> points = new HashMap<>();
        Point point;
        
        switch(c) {
            case 'R':
                while(places > 0) {
                    curPos.moveRight();
                    point = new Point (curPos.getX(), curPos.getY());
                    if(!points.containsKey(point)) {
                        points.put(point, curPos.getSteps());
                    }
                    places--;
                }
                break;
            case 'L':
                while(places > 0) {
                    curPos.moveLeft();
                    point = new Point (curPos.getX(), curPos.getY());
                    if(!points.containsKey(point)) {
                        points.put(point, curPos.getSteps());
                    }
                    places--;
                }
                break;
            case 'U':
                while(places > 0) {
                    curPos.moveUp();
                    point = new Point (curPos.getX(), curPos.getY());
                    if(!points.containsKey(point)) {
                        points.put(point, curPos.getSteps());
                    }
                    places--;
                }
                break;
                
            case 'D':
                while(places > 0) {
                    curPos.moveDown();
                    point = new Point (curPos.getX(), curPos.getY());
                    if(!points.containsKey(point)) {
                        points.put(point, curPos.getSteps());
                    }
                    places--;
                }
                break;
        }
        
        return points;
    }
    
    public static class MoveablePoint {
        
        private int x;
        private int y;
        private int steps;
        
        public MoveablePoint(int x, int y, int steps) {
            this.x = x;
            this.y = y;
        }
        
        public int calculateManhattenDistance(int x, int y) {
            return Math.abs(this.x - x) + Math.abs(this.y - 0);
        }
        
        public int getX() {
            return x;
        }
        
        public int getY() {
            return y;
        }
        
        public int getSteps() {
            return steps;
        }
        
        public void moveUp() {
            y++;
            steps++;
        }
        
        public void moveDown() {
            y--;
            steps++;
        }
        
        public void moveLeft() {
            x--;
            steps++;
        }
        
        public void moveRight() {
            x++;
            steps++;
        }

        @Override
        public String toString() {
            return "[X=" + x + ", Y=" + y + "]";
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null) return false;
            if (getClass() != obj.getClass()) return false;
            MoveablePoint other = (MoveablePoint) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }
              
    }
}

