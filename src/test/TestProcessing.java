package test;

import processing.core.PApplet;

public class TestProcessing extends PApplet {
	
    public static void main(String[] args) {
        PApplet.main("test.TestProcessing");
    }

    public void settings() {
        size(400, 400);
    }

    public void draw() {
        background(255, 255, 0); // set the background color to yellow
        ellipse(width/2, height/2, 200, 200); // draw a circle in the center of the screen
    }

}
