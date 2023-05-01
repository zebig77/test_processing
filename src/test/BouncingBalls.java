package test;

import processing.core.PApplet;

public class BouncingBalls extends PApplet {

	// Ball 1 properties
	float x1, y1; // position
	float dx1, dy1; // velocity
	float r1; // radius

	// Ball 2 properties
	float x2, y2; // position
	float dx2, dy2; // velocity
	float r2; // radius

	public void settings() {
		size(400, 400); // set the size of the display window
	}

	public void setup() {
		// set the initial positions, velocities, and radii of the balls
		x1 = 100;
		y1 = 200;
		dx1 = 5;
		dy1 = 2;
		r1 = 30;

		x2 = 300;
		y2 = 200;
		dx2 = -3;
		dy2 = -4;
		r2 = 40;
	}

	public void draw() {
		background(255); // set the background color to white

		// update the positions of the balls
		x1 += dx1;
		y1 += dy1;
		x2 += dx2;
		y2 += dy2;

		// check if the balls have hit the walls and reverse their direction if they
		// have
		if (x1 - r1 < 0 || x1 + r1 > width) {
			dx1 *= -1;
		}
		if (y1 - r1 < 0 || y1 + r1 > height) {
			dy1 *= -1;
		}
		if (x2 - r2 < 0 || x2 + r2 > width) {
			dx2 *= -1;
		}
		if (y2 - r2 < 0 || y2 + r2 > height) {
			dy2 *= -1;
		}
		
	    // check if the balls collide with each other
	    float d = dist(x1, y1, x2, y2);
	    if (d < r1 + r2) {
	      // balls have collided, reverse their velocities
	      float tempdx = dx1;
	      float tempdy = dy1;
	      dx1 = dx2;
	      dy1 = dy2;
	      dx2 = tempdx;
	      dy2 = tempdy;
	    }

		// draw the balls
		fill(0, 0, 255);
		ellipse(x1, y1, r1 * 2, r1 * 2);
		fill(255, 0, 0);
		ellipse(x2, y2, r2 * 2, r2 * 2);
	}

	public static void main(String[] args) {
		PApplet.main("test.BouncingBalls"); // run the sketch
	}

}
