package aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day3Part1 {

    private static String[] line1;
    private static String[] line2;
    private static Set<MoveablePoint> line1points;
    private static Set<MoveablePoint> line2points;
    private static Set<MoveablePoint> intersections;
    private static MoveablePoint curPos;

    public static void main(String[] args) {

        try {
            readFile();
            line1points = processLine(line1);
            line2points = processLine(line2);
            
            intersections = new HashSet<>(line1points);
            intersections.retainAll(line2points);
            
            System.out.println(intersections.stream()
                    .map(i -> i.calculateManhattenDistance(0, 0))
                    .sorted()
                    .findFirst());
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private static void readFile() throws IOException {

        FileReader fileReader = new FileReader("input.txt");
        BufferedReader in = new BufferedReader(fileReader);

        line1 = in.readLine().split(",");
        line2 = in.readLine().split(",");
    }
    
    private static Set<MoveablePoint> processLine(String[] line) {
        
        curPos = new MoveablePoint(0, 0);
        
        Set<MoveablePoint> result = new HashSet<>();
        
        Arrays.stream(line).forEach(x -> result.addAll(move(x)));
        
        return result;
    }
    
    private static Set<MoveablePoint> move(String instruction) {
        
        char c = instruction.charAt(0);
        int places = Integer.parseInt(instruction.substring(1));
        Set<MoveablePoint> points = new HashSet<>();
        
        switch(c) {
            case 'R':
                while(places > 0) {
                    curPos.moveRight();
                    points.add(new MoveablePoint(curPos.getX(), curPos.getY()));
                    places--;
                }
                break;
            case 'L':
                while(places > 0) {
                    curPos.moveLeft();
                    points.add(new MoveablePoint(curPos.getX(), curPos.getY()));
                    places--;
                }
                break;
            case 'U':
                while(places > 0) {
                    curPos.moveUp();
                    points.add(new MoveablePoint(curPos.getX(), curPos.getY()));
                    places--;
                }
                break;
                
            case 'D':
                while(places > 0) {
                    curPos.moveDown();
                    points.add(new MoveablePoint(curPos.getX(), curPos.getY()));
                    places--;
                }
                break;
        }
        
        return points;
    }
    
    public static class MoveablePoint {
        
        private int x;
        private int y;
        
        public MoveablePoint(int x, int y) {
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
        
        public void moveUp() {
            y++;
        }
        
        public void moveDown() {
            y--;
        }
        
        public void moveLeft() {
            x--;
        }
        
        public void moveRight() {
            x++;
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

