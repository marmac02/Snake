import java.util.*;

public class boardTile {
	private int sqSize, frame, boardWidth, boardHeight;
	private int midX, midY;
	
	public boardTile(int midX, int midY, int sqSize, int frame, int boardWidth, int boardHeight) {
		this.midX = midX;
		this.midY = midY;
		this.sqSize = sqSize;
		this.frame = frame;
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
	}
	
	public ArrayList<Integer> currPos() {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(midX); coords.add(midY);
		return coords;
	}
	
	public ArrayList<Integer> upPos() {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(midX); coords.add(midY - sqSize);
		return coords;
	}
	
	public ArrayList<Integer> downPos() {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(midX); coords.add(midY + sqSize);
		return coords;
	}
	
	public ArrayList<Integer> leftPos() {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(midX - sqSize); coords.add(midY);
		return coords;
	}
	
	public ArrayList<Integer> rightPos() {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(midX + sqSize); coords.add(midY);
		return coords;
	}
	
	public boolean checkIfOut() {
		if(midX < frame || midX > boardWidth + frame || midY < frame || midY > boardHeight + frame) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this.midX == ((boardTile)o).midX && this.midY == ((boardTile)o).midY)
			return true;
		return false;
	}
}
