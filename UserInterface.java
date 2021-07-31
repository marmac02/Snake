import processing.core.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class UserInterface extends PApplet {
	
	public int sqSize = 30, elSize = 20, frame = 50, boardWidth = 900, boardHeight = 600;
	private snake mySnake;
	private String UIMode;
	private int snakeDir;
	private PImage startButton, backgroundImg, verticalWall, horizontalWall, gameOverImg;
	private List<boardTile> blockedTiles, allFoodPos;
	public List<boardTile> displayedFood;
	public int numOfPoints = 0;
	
	public void settings() {
		size(1000, 700);
	}
	
	public void setup() {
		backgroundImg = loadImage("/data/bricks.jpg");
		startButton = loadImage("/data/startButton.jpg");
		verticalWall = loadImage("/data/verticalWall.jpg");
		horizontalWall = loadImage("/data/horizontalWall.jpg");
		gameOverImg = loadImage("/data/gameOver.jpg");
		background(backgroundImg);
		drawPlayArea();
		initBlocked();
		initFoodPos();
		mySnake = new snake(5, this);
		snakeDir = DOWN;
		UIMode = "START";
	}
	
	private void initBlocked() {
		blockedTiles = new ArrayList<>();
		for(int i = 0; i < 12; i++) {
			boardTile currLeft = new boardTile(5*sqSize + sqSize/2 + frame, 4*sqSize + sqSize / 2 + frame + sqSize*i, sqSize, frame, boardWidth, boardHeight);
			boardTile currRight = new boardTile(785, 185 + sqSize*i, sqSize, frame, boardWidth, boardHeight);
			blockedTiles.add(currLeft);
			blockedTiles.add(currRight);
		}
		for(int i = 0; i < 11; i++) {
			boardTile currLeft = new boardTile(245 + sqSize*i, 515, sqSize, frame, boardWidth, boardHeight);
			boardTile currRight = new boardTile(455 + sqSize*i, 185, sqSize, frame, boardWidth, boardHeight);
			blockedTiles.add(currLeft);
			blockedTiles.add(currRight);
		}
	}
	
	private void initFoodPos() {
		allFoodPos = new ArrayList<>();
		displayedFood = new ArrayList<>();
		String line = "";
		BufferedReader reader;
		reader = createReader("/data/foodPos.txt");
		while(line != null) {
			try {
				line = reader.readLine();
				if(line == null)
					break;
				String[] pieces = split(line, TAB);
				int x = Integer.parseInt(pieces[0]);
				int y = Integer.parseInt(pieces[1]);
				allFoodPos.add(new boardTile(x, y, sqSize, frame, boardWidth, boardHeight));
			} catch(IOException e) {
				e.printStackTrace();
				line = null;
			}
		}
	}
	
	private void addFood() {
		if(displayedFood.size() >= 2) {
			return;
		}
		Random randGen = new Random();
		int pos = randGen.nextInt(allFoodPos.size());
		if(displayedFood.contains(allFoodPos.get(pos))) {
			return;
		}
		for(boardTile tile : mySnake.snakeDeq) {
			if(allFoodPos.get(pos).equals(tile)) {
				return;
			}
		}
		displayedFood.add(allFoodPos.get(pos));
	}
	
	public void draw() {
		drawPlayArea();
		drawNumOfPoints();
		if(UIMode == "PLAY") {
			playMode();
			addFood();
			drawFood();
		}
		else if(UIMode == "START") {
			imageMode(CENTER);
			image(startButton, 500, 350);
		}
		else if(UIMode == "GAME OVER") {
			imageMode(CENTER);
			image(gameOverImg, 500, 350);
			try {
				Thread.sleep(700);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
		
	}
	
	private void drawNumOfPoints() {
		rectMode(CENTER);
		fill(213, 224, 230);
		rect(500, 25, 170, 40);
		fill(0, 0, 0);
		textAlign(CENTER, CENTER);
		String msg = "POINTS: " + String.valueOf(numOfPoints);
		textSize(30);
		text(msg, 500, 25);
	}
	
	private void drawPlayArea() {
		fill(213, 224, 230);
		strokeWeight(0);
		rectMode(CORNER);
		rect(50, 50, 900, 600);
		imageMode(CORNER);
		image(verticalWall, 5*sqSize + frame, 4*sqSize + frame);
		image(horizontalWall, 180 + frame, 452 + frame);
		image(verticalWall, 770, 170);
		image(horizontalWall, 440, 170);
	}
	
	private void drawFood() {
		fill(233, 155, 96);
		for(boardTile tile : displayedFood) {
			ellipse(tile.currPos().get(0), tile.currPos().get(1), elSize, elSize);
		}
	}
	
	
	private void playMode() {
		fill(0, 0, 0);
		for(boardTile tile : mySnake.snakeDeq) {
			ellipse(tile.currPos().get(0), tile.currPos().get(1), elSize, elSize);
		}
		
		try {
			Thread.sleep(70);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
		
		snakeDir = keyCode;
		boolean avlMove = true;
		if(snakeDir == UP) {
			avlMove = mySnake.moveSnake('U');
		}
		else if(snakeDir == DOWN) {
			avlMove = mySnake.moveSnake('D');
		}
		else if(snakeDir == LEFT) {
			avlMove = mySnake.moveSnake('L');
		}
		else if(snakeDir == RIGHT) {
			avlMove = mySnake.moveSnake('R');
		}
		
		if(!avlMove || blockedTiles.contains(mySnake.snakeDeq.getFirst()) || blockedTiles.contains(mySnake.snakeDeq.getLast())) {
			UIMode = "GAME OVER";
		}
		
	}
	
	private boolean startClicked() {
		int buttonRad = 60;
		int squaredDist = (500 - mouseX)*(500 - mouseX) + (350 - mouseY)*(350 - mouseY);
		if(squaredDist <= buttonRad*buttonRad) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public void keyPressed() {
		
	}
	
	@Override
	public void mousePressed() {
		if(UIMode == "START" && startClicked()) {
			UIMode = "PLAY";
		}
	}
	
	
}
