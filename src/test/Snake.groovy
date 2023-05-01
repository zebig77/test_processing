package test

import processing.core.PApplet

class MySnake extends PApplet {

	def body = []
	def w=30, h=30, blocks=20, direction=2, foodx=15, foody=15, speed = 8, fc1 = 255, fc2 = 255, fc3 = 255

	def x_direction = [0, 0, 1, -1]
	def y_direction = [1, -1, 0, 0]

	def gameover = false

	public void settings() {
		size(600,600)
	}

	public void setup() {
		body << [0, 15] // initial position
	}

	public void draw() {
		background(0)
		fill(0, 255, 0) // snake color
		body.each { pos ->
			def x = pos[0]
			def y = pos[1]
			rect(x*blocks, y*blocks, blocks, blocks) // draw snake
		}
		if (!gameover) {
			doPlay()
		}
		else {
			doGameover()
		}
	}

	public void keyPressed() {
		int newdir=keyCode == DOWN? 0:(keyCode == UP?1:(keyCode == RIGHT?2:(keyCode == LEFT?3:-1)));
		if (newdir != -1) direction = newdir;
	}

	void doPlay() {
		fill(fc1,fc2,fc3) // food color
		ellipse(foodx*blocks+10, foody*blocks+10, blocks, blocks)
		textAlign(LEFT); // score
		textSize(25)
		fill(255)
		text("Score: ${body.size()}", 10, 10, width-20, 50)
		if (frameCount%speed==0) {
			def new_x = body[0][0] + x_direction[direction]
			def new_y = body[0][1] + y_direction[direction]
			body.add(0, [new_x, new_y])
			// check if outside bounds
			if (new_x < 0 || new_y < 0 || new_x >= w || new_y >= h) {
				gameover = true
			}
			// check if head touches body
			for (int i=1; i<body.size(); i++) {
				if (new_x == body[i][0] && new_y == body[i][1]) {
					gameover = true
				}
			}
			// check if food touched
			if (new_x==foodx && new_y==foody) {
				//new food if we touch
				if (body.size() %5==0 && speed>=2) speed-=1;  // every 5 points speed increase
				foodx = (int)random(0, w); //new food
				foody = (int)random(0, h);
				fc1 = (int)random(255); fc2 = (int)random(255); fc3 = (int)random(255); //new food color
			} else {
				body.remove(body.size()-1);
			}
		}
	}

	void doGameover() {
		fill(200, 200, 0);
		textSize(30);
		textAlign(CENTER);
		text("GAME OVER \n Your Score is: "+ body.size() +"\n Press ENTER", width/2, height/3);
		if (keyCode == ENTER) {
			body = []
			body << [0, 15] // initial position
			direction = 2;
			speed = 8;
			gameover = false;
		}
	}
}

PApplet.main("test.MySnake")