package sk.upjs.snowflakes;

import java.awt.event.MouseEvent;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

public class IntroScene extends Scene {

	public static final String NAME = "IntroScene";

	private Turtle start;

	private Turtle end;

	private MusicOnOffSwitch musicOnOff;

	public IntroScene(Stage stage) {
		super(stage);
		prepareScreen();
	}

	private void prepareScreen() {
		setBorderWidth(0);

		// paint background
		Turtle painter = new Turtle();
		painter.setShape(new ImageShape("images", "introScene.png"));
		add(painter);
		painter.center();
		painter.stamp();
		remove(painter);

		// create animated snowflakes
		ImageShape virus = new ImageShape("images", "virusButton.png");

		start = new Turtle();
		start.setShape(virus);
		start.setPosition(40, 560);
		add(start);

		end = new Turtle();
		end.setShape(virus);
		end.setPosition(550, 560);
		// set initial animation frame randomly
		end.setFrameIndex((int) (Math.random() * end.getFrameCount()));
		add(end);

		// create and add the music on/off switch
		musicOnOff = new MusicOnOffSwitch(getStage());
		musicOnOff.setPosition(5, 5);
		add(musicOnOff);
	}

	private boolean isOverSTART(int x, int y) {
		return (start.getX() - 20 <= x) && (x <= start.getX() + 180)
				&& (start.getY() - 20 <= y) && (y <= start.getY() + 20);
	}

	private boolean isOverEXIT(int x, int y) {
		return (end.getX() - 20 <= x) && (x <= end.getX() + 180) && (end.getY() - 20 <= y)
				&& (y <= end.getY() + 20);
	}

	public void start() {
		musicOnOff.updateView();
		start.setShapeAnimation(true);
		end.setShapeAnimation(true);
	}

	public void stop() {
		start.setShapeAnimation(false);
		end.setShapeAnimation(false);
	}

	@Override
	protected void onMouseClicked(int x, int y, MouseEvent detail) {
		// simulation of buttons without creating a button as an extension of
		// the Pane class
		if (detail.getButton() == MouseEvent.BUTTON1) {
			if (isOverSTART(x, y)) {
				getStage().changeScene(QuizGame.NAME, TransitionEffect.FADE_OUT_WHITE_FADE_IN, 1500);
				return;
			}

			if (isOverEXIT(x, y)) {
				System.exit(0);
			}
		}
	}

	@Override
	protected boolean onCanClick(int x, int y) {
		return super.onCanClick(x, y) || isOverSTART(x, y) || isOverEXIT(x, y);
	}
}
