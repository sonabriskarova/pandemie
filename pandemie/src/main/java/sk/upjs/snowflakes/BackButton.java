package sk.upjs.snowflakes;

import java.awt.event.MouseEvent;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

public class BackButton extends Pane {

	private Stage stage;

	public BackButton(Stage stage) {
		// create pane with proper size
		super(32, 32);
		this.stage = stage;

		setBorderWidth(0);
		setTransparentBackground(true);

		// paint button image
		Turtle painter = new Turtle();
		painter.setShape(new ImageShape("images", "back_icon.png"));
		add(painter);
		painter.center();
		painter.stamp();
		remove(painter);
	}

	@Override
	protected boolean onCanClick(int x, int y) {
		return true;
	}

	@Override
	protected void onMouseClicked(int x, int y, MouseEvent detail) {
		if (detail.getButton() == MouseEvent.BUTTON1) {
			stage.changeScene(IntroScene.NAME, TransitionEffect.MOVE_DOWN, 1500);
		}
	}
}
