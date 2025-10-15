package git3;

import java.awt.*;
import java.util.random.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.*;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener {
	int boardWidth;
	int boardHeight;
	int tileSize = 25;

	private class Tile {
		int x;
		int y;

		Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	Tile SnakeHead;
	Tile food;
	Random random;
	Timer gameLoop;
	int velocityX;
	int velocityY;
	ArrayList<Tile> SnakeBody;
	boolean GameOver = false;

	SnakeGame(int boardWidth, int boardHeight) {
		this.boardWidth = boardWidth;
		this.boardHeight = boardHeight;
		setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
		setBackground(Color.black);
		SnakeHead = new Tile(5, 5);
		food = new Tile(10, 10);
		random = new Random();
		placeFood();
		velocityX = 0;
		velocityY = 0;
		addKeyListener(this);
		setFocusable(true);
		gameLoop = new Timer(100, this);
		gameLoop.start();
		SnakeBody = new ArrayList<Tile>();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);

	}

	private void draw(Graphics g) {

		/*
		 * for (in i = 0; i < boardWidth / tileSize; i++) { g.drawLine(i * tileSize, 0,
		 * i * tileSize, boardHeight); g.drawLine(0, i * tileSize, boardWidth, i *
		 * tileSize); }
		 */
		g.setColor(Color.RED);
		g.fill3DRect(food.x * tileSize, food.y * tileSize, tileSize, tileSize, true);
		g.setColor(Color.GREEN);
		g.fill3DRect(SnakeHead.x * tileSize, SnakeHead.y * tileSize, tileSize, tileSize, true);

		for (int i = 0; i < SnakeBody.size(); i++) {
			Tile SnakePart = SnakeBody.get(i);
			g.fill3DRect(SnakePart.x * tileSize, SnakePart.y * tileSize, tileSize, tileSize, true);
		}
		g.setFont(new Font("Arial", Font.PLAIN, 16));
		if (GameOver) {
			g.setColor(Color.red);
			g.drawString("<<< GAME OVER >>>", tileSize * 9, (int) (tileSize * 12));
			g.drawString("    Your score is " + String.valueOf(SnakeBody.size()), tileSize * 9, (int) (tileSize * 13));
			g.drawString("Press <SPACE> to try again ", tileSize * 8, (int) (tileSize * 14));
		} else {
			g.drawString("Score: " + String.valueOf(SnakeBody.size()), tileSize * 10, (int) (tileSize / 1.5));
		}

	}

	public void placeFood() {
		food.x = random.nextInt(boardWidth / tileSize);
		food.y = random.nextInt(boardHeight / tileSize);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if (GameOver) {
			gameLoop.stop();
		}
	}

	private void move() {

		if (collision(SnakeHead, food)) {
			SnakeBody.add(new Tile(food.x, food.y));
			placeFood();
		}
		for (int i = SnakeBody.size() - 1; i >= 0; i--) {
			Tile SnakePart = SnakeBody.get(i);
			if (i == 0) {
				SnakePart.x = SnakeHead.x;
				SnakePart.y = SnakeHead.y;
			} else {
				Tile prevSnakePart = SnakeBody.get(i - 1);
				SnakePart.x = prevSnakePart.x;
				SnakePart.y = prevSnakePart.y;
			}
		}
		SnakeHead.x += velocityX;
		SnakeHead.y += velocityY;
		for (int i = 0; i < SnakeBody.size(); i++) {
			Tile SnakePart = SnakeBody.get(i);
			if (collision(SnakeHead, SnakePart)) {
				GameOver = true;

			}
		}
		if (SnakeHead.x * tileSize < 0 || SnakeHead.x * tileSize == boardWidth || 
			SnakeHead.y * tileSize < 0 || SnakeHead.y * tileSize == boardHeight) {
			GameOver = true;
		}
	}

	public boolean collision(Tile tile1, Tile food2) {
		return tile1.x == food2.x && tile1.y == food2.y;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (GameOver) {
				SnakeBody.clear();
				SnakeHead = new Tile(5, 5);
				velocityX = 0;
				velocityY = 0;
				GameOver = false;
				gameLoop.start();
				placeFood();
			}
		}
		if ((e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) && velocityX != -1) {
			velocityX = 1;
			velocityY = 0;
		} else if ((e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) && velocityX != 1) {
			velocityX = -1;
			velocityY = 0;
		} else if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) && velocityY != 1) {
			velocityX = 0;
			velocityY = -1;
		} else if ((e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) && velocityY != -1) {
			velocityX = 0;
			velocityY = 1;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
