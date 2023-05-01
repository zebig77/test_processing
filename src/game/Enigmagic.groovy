package game

import processing.core.PApplet

class Map001 extends PApplet {
	
	int tile_width = 30
	int tile_height = 30
	
	def gameover = false
	
	def current_map = '001'
	
	def lines = []

	int player_col, player_line
	int player_moves = 0

	void find_player() {
		for(int line_num = 0; line_num < lines.size(); line_num++) {
			def line = lines[line_num].toString()
			int col_num = line.indexOf("P")
			if (col_num >= 0) {
				player_col = col_num
				player_line = line_num
				return
			}
		}
		abort("Player not found in map")
	}

	void abort(msg) {
		System.err << msg
		System.exit(1)
	}

	public void settings() {
		size(600,600)
	}

	public void setup() {
		def f = new File('src/maps/001.txt')
		if (!f.exists()) {
			abort "Missing map file"
		}
		lines.clear()
		f.text.eachLine { String line -> 
			lines << line.toCharArray()
		}
		lines.each {
			println it.toString()
		}
		find_player()
	}

	public void draw() {
		background(0)
		if (!gameover) {
			doPlay()
		}
		else {
			doGameover()
		}
	}
	
	boolean invalid_pos(col_num,line_num) {
		if (line_num < 0 || line_num >= lines.size()) { 
			return true 
		}
		if (col_num < 0 || col_num >= lines[line_num].size()) {
			return true
		}
		return false
	}
	
	void set_map(int col_num, int line_num, String c) {
		def new_line = lines[line_num]
		new_line[col_num] = c.charAt(0)
	}
	
	char get_map(int col_num, int line_num) {
		def line = lines[line_num]
		return line[col_num]
	}
	
	boolean cannot_enter(int col_num, int line_num) {
		def c = get_map(col_num, line_num)
		return (c != ' ' && c != 'S')
	}
	
	void move_player(int new_player_col, int new_player_line) {
		if (invalid_pos(new_player_col, new_player_line)) {
			return
		}
		if (cannot_enter(new_player_col, new_player_line)) {
			return
		}
		if (get_map(new_player_col, new_player_line) == 'S') {
			gameover = true
		}
		set_map(player_col, player_line, ' ') // ground
		player_col = new_player_col
		player_line = new_player_line
		set_map(player_col, player_line, 'P')
		player_moves++
	}

	public void keyPressed() {
		switch(keyCode) {
			case DOWN: 	move_player(player_col, player_line+1); break
			case UP: 	move_player(player_col, player_line-1); break
			case RIGHT:	move_player(player_col+1, player_line); break
			case LEFT:	move_player(player_col-1, player_line)
		}	
	}
	
	void draw_rect(int color, int line_num, int col_num) {
		fill(color)
		int pos_x = col_num * tile_width
		int pos_y = line_num * tile_height
		rect( pos_x+1, pos_y+1, tile_width-1, tile_height-1)
	}
	
	void draw_rect(int red, int green, int blue, int line_num, int col_num) {
		fill(red, green, blue)
		int pos_x = col_num * tile_width
		int pos_y = line_num * tile_height
		rect( pos_x+1, pos_y+1, tile_width-1, tile_height-1)
	}
	
	void draw_wall(int col_num, int line_num) {
		draw_rect(30, line_num, col_num)
	}

	void draw_ground(int col_num, int line_num) {
		draw_rect(124, line_num, col_num)
	}

	void draw_player(int col_num, int line_num) {
		draw_rect(30, 225, 30, line_num, col_num)
	}

	void draw_exit(int col_num, int line_num) {
		draw_rect(30, 30, 225, line_num, col_num)
	}

	void doPlay() {
		lines.eachWithIndex { line, int line_num ->
			for(int col_num=0; col_num < line.size(); col_num++) {
				char c = line[col_num]
				switch(c) {
					case '*': draw_wall(col_num, line_num); break
					case ' ': draw_ground(col_num, line_num); break
					case 'P': draw_player(col_num, line_num); break
					case 'S': draw_exit(col_num, line_num); break
					default: abort("unknown character $c")
				}
			}
		}
	}

	void doGameover() {
		background(45, 45, 255)
		fill(250, 250, 10)
		textSize(30)
		textAlign(CENTER)
		text("VICTORY \n You found the exit in $player_moves moves!!!\n Press ENTER", width/2, height/3)
		if (keyCode == ENTER) {
			System.exit(0)
		}
	}
}

PApplet.main("game.Map001")