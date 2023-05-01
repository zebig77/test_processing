package test;

import processing.core.PApplet;

public class DrawRTSphere extends PApplet {

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
		size(400, 400);
		background(255);
		noStroke();
		smooth();
	}

	public void draw() {
		float px = 0; // pixel x coordinate
		float py = 0; // pixel y coordinate
		float x = -200; // world x coordinate
		float y = -200; // world y coordinate
		float z = 500; // world z coordinate
		float radius = 100; // radius of the sphere
		float zoom = 20; // zoom factor

		// loop through every pixel in the canvas
		for (int i = 0; i < width * height; i++) {
			px = i % width;
			py = i / width;

			// calculate the direction of the ray from the camera to the current pixel
			float dx = (px - width / 2) / zoom;
			float dy = (py - height / 2) / zoom;
			float dz = 1;

			// normalize the direction vector
			float length = sqrt(dx * dx + dy * dy + dz * dz);
			dx /= length;
			dy /= length;
			dz /= length;

			// find the closest intersection of the ray with the sphere
			float a = dx * dx + dy * dy + dz * dz;
			float b = 2 * (dx * (x - px) + dy * (y - py) + dz * (z - radius));
			float c = x * x + y * y + z * z - 2 * z * radius + radius * radius - px * px - py * py;
			float discriminant = b * b - 4 * a * c;

			if (discriminant > 0) {
				// ray intersects the sphere, color the pixel
				fill(255, 0, 0); // red
				ellipse(px, py, 1, 1);
			} else {
				// ray misses the sphere, color the pixel white
				fill(255);
				ellipse(px, py, 1, 1);
			}
		}

	}

	public static void main(String[] args) {
		PApplet.main("test.DrawRTSphere"); // run the sketch
	}

}
