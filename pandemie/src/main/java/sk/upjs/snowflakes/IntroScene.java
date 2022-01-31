package sk.upjs.snowflakes;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

public class IntroScene extends Scene {

	public static final String NAME = "Intro";

	private Turtle zacat;

	private Turtle skoncit;

	private MusicOnOffSwitch musicOnOff;

	public IntroScene(Stage stage) {
		super(stage);
		prepareScreen();
	}

	private void prepareScreen() {
		setBorderWidth(0);

		// paint background
		Turtle painter = new Turtle();
		painter.setShape(new ImageShape("images", "pp.png"));
		add(painter);
		painter.center();
		painter.stamp();
		remove(painter);

		// create animated snowflakes
		ImageShape snowflakeShape = new ImageShape("images", "c1.png");

		zacat = new Turtle();
		zacat.setShape(snowflakeShape);
		zacat.setPosition(40, 560);
		add(zacat);

		skoncit = new Turtle();
		skoncit.setShape(snowflakeShape);
		skoncit.setPosition(550, 560);
		// set initial animation frame randomly
		skoncit.setFrameIndex((int) (Math.random() * skoncit.getFrameCount()));
		add(skoncit);

		// create and add the music on/off switch
		musicOnOff = new MusicOnOffSwitch(getStage());
		musicOnOff.setPosition(5, 5);
		add(musicOnOff);
	}

	private boolean isOverSTART(int x, int y) {
		return (zacat.getX() - 20 <= x) && (x <= zacat.getX() + 180)
				&& (zacat.getY() - 20 <= y) && (y <= zacat.getY() + 20);
	}

	private boolean isOverEXIT(int x, int y) {
		return (skoncit.getX() - 20 <= x) && (x <= skoncit.getX() + 180) && (skoncit.getY() - 20 <= y)
				&& (y <= skoncit.getY() + 20);
	}

	public void start() {
		musicOnOff.updateView();
		zacat.setShapeAnimation(true);
		skoncit.setShapeAnimation(true);
	}

	public void stop() {
		zacat.setShapeAnimation(false);
		skoncit.setShapeAnimation(false);
	}

	@Override
	protected void onMouseClicked(int x, int y, MouseEvent detail) {
		// simulation of buttons without creating a button as an extension of
		// the Pane class
		if (detail.getButton() == MouseEvent.BUTTON1) {
			if (isOverSTART(x, y)) {
				getStage().changeScene(Kvíz.NAME, TransitionEffect.FADE_OUT_WHITE_FADE_IN, 1500);
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
