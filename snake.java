import java.util.*;

public class snake {
	
	public Deque<boardTile> snakeDeq; // snake's head is at the end of the list
	private int sqSize, frame, boardWidth, boardHeight;
	private UserInterface UI;
	
	public snake(int startLen, UserInterface UI) {
		this.sqSize = UI.sqSize;
		this.frame = UI.frame;
		this.boardWidth = UI.boardWidth;
		this.boardHeight = UI.boardHeight;
		this.UI = UI;
		snakeDeq = new ArrayDeque<boardTile>();
		for(int i = 0; i < startLen; i++) {
			snakeDeq.addLast(new boardTile(frame + 10*sqSize + sqSize/2, frame + 5*sqSize + sqSize/2 + i*sqSize, 
					  this.sqSize, this.frame, this.boardWidth, this.boardHeight));
		}
	}
	
	public boolean moveSnake(char dir) {
		boardTile nextTile = null;
		boardTile head = snakeDeq.peekLast();
		if(dir == 'U') {
			ArrayList<Integer> coords = head.upPos();
			nextTile = new boardTile(coords.get(0), coords.get(1), sqSize, frame, boardWidth, boardHeight);
		}
		else if(dir == 'D') {
			ArrayList<Integer> coords = head.downPos();
			nextTile = new boardTile(coords.get(0), coords.get(1), sqSize, frame, boardWidth, boardHeight);
		}
		else if(dir == 'L') {
			ArrayList<Integer> coords = head.leftPos();
			nextTile = new boardTile(coords.get(0), coords.get(1), sqSize, frame, boardWidth, boardHeight);
		}
		else if(dir == 'R') {
			ArrayList<Integer> coords = head.rightPos();
			nextTile = new boardTile(coords.get(0), coords.get(1), sqSize, frame, boardWidth, boardHeight);
		}
		
		if(nextTile.checkIfOut() || checkIfCollision(nextTile)) {
			return false;
		}
		
		snakeDeq.addLast(nextTile);
		if(!checkIfFood(nextTile)) {
			snakeDeq.removeFirst();
		}
		
		return true;
	}
	
	private boolean checkIfFood(boardTile nextTile) {
		if(UI.displayedFood.contains(nextTile)) {
			UI.displayedFood.remove(UI.displayedFood.indexOf(nextTile));
			UI.numOfPoints++;
			return true;
		}
		return false;
	}
	
	private boolean checkIfCollision(boardTile nextTile) {
		for(boardTile currSegm : snakeDeq) {
			if(currSegm.equals(nextTile)) {
				return true;
			}
		}
		return false;
	}
}
