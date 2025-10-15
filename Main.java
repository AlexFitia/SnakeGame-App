package git3;
import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		int boardWidth = 600;
		int boardHeight = boardWidth;
		
	
		
		JFrame frame = new JFrame("Snake");
		frame.setVisible(true);
		frame.setSize(boardWidth, boardHeight);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		SnakeGame game = new SnakeGame(boardWidth,boardHeight);
		frame.add(game);
		frame.pack();
		game.requestFocus();
	}

}
