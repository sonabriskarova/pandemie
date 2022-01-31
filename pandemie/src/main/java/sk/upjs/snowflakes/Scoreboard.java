package sk.upjs.snowflakes;

import java.awt.Color;
import java.awt.Font;

import sk.upjs.jpaz2.*;

public class Scoreboard extends Pane {

	private int score = 0;

	private Turtle painter;

	public Scoreboard() {
		// set size calling the constructor of superclass
		super(60, 35);
		setBorderWidth(0);
		setTransparentBackground(true);

		painter = new Turtle();
		painter.setVisible(false);
		painter.setDirection(90);
		painter.setPenColor(Color.black);
		painter.setFont(new Font("Lucida Sans", Font.BOLD, 20));
		add(painter);
		painter.center();

		resetScore();
	}

	public void resetScore() {
		score = 0;
		repaintScore();
	}

	public void increaseScore(int points) {
		this.score += points;
		repaintScore();
	}

	public int getScore() {
		return score;
	}

	private void repaintScore() {
		clear();
		painter.printCenter(Integer.toString(score));
	}
}
