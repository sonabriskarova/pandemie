package sk.upjs.snowflakes;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

public class EndScene extends Scene {

	public static final String NAME = "End";

	private  Turtle start;

	private File file;

	private Turtle end;

	private MusicOnOffSwitch musicOnOff;

	private Turtle results;;

	private Turtle show;

	private int loadScore;

	private int scoreFromFile;

	public EndScene(Stage stage) {
		super(stage);
		prepareScreen();
	}

	private void prepareScreen() {
		setBorderWidth(0);

		scoreFromFile = 0;

		// paint background
		Turtle painter = new Turtle();
		painter.setShape(new ImageShape("images", "Skončiť.png"));
		add(painter);
		painter.center();
		painter.stamp();
		remove(painter);

		show = new Turtle();
		show.setPosition(250,180);
		show.turn(90);
		show.setFont(new Font("Lucida Sans", Font.BOLD, 16));
		add(show);
		show.printCenter("ZOBRAZIŤ VÝSLEDKY");
		remove(show);

		StringBuilder result = new StringBuilder();
		String s1 = "Tvoje skóre: ";
		String s2 = Integer.toString(scoreFromFile);
		result.append(s1);
		result.append(s2);

		ImageShape mask = new ImageShape("images", "mask.png");

		start = new Turtle();
		start.setShape(mask);
		start.setPosition(70, 528);
		add(start);

		end = new Turtle();
		end.setShape(mask);
		end.setPosition(512, 528);
		end.setFrameIndex((int) (Math.random() * end.getFrameCount()));
		add(end);

		musicOnOff = new MusicOnOffSwitch(getStage());
		musicOnOff.setPosition(5, 5);
		add(musicOnOff);
	}

	public void start() {
		musicOnOff.updateView();
		start.setShapeAnimation(true);
		end.setShapeAnimation(true);
	}

	public void showScore(){
		file = new File("finalScore");
		results = new Turtle();
		results.setPosition(353,240);
		results.turn(90);
		results.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(results);
		scoreFromFile = loadScore(file);
		System.out.println(scoreFromFile);
		StringBuilder q = new StringBuilder();
		String s1 = "Tvoje skóre: ";
		String s2 = Integer.toString(scoreFromFile);
		q.append(s1);
		q.append(s2);
		results.printCenter(q.toString());
		remove(results);
	}

	public void stop() {
		start.setShapeAnimation(false);
		end.setShapeAnimation(false);
	}

	private boolean isOverSTART(int x, int y) {
		return (start.getX() - 20 <= x) && (x <= start.getX() + 180)
				&& (start.getY() - 20 <= y) && (y <= start.getY() + 20);
	}

	private boolean isOverEXIT(int x, int y) {
		return (end.getX() - 20 <= x) && (x <= end.getX() + 180) && (end.getY() - 20 <= y)
				&& (y <= end.getY() + 20);
	}

	private boolean isOverSHOW(int x, int y) {
		return (show.getX() - 20 <= x) && (x <= show.getX() + 180) && (show.getY() - 20 <= y)
				&& (y <= show.getY() + 20);
	}

	@Override
	protected void onMouseClicked(int x, int y, MouseEvent detail) {
		// simulation of buttons without creating a button as an extension of
		// the Pane class
		if (detail.getButton() == MouseEvent.BUTTON1) {
			if (isOverSTART(x, y)) {
				prepareScreen();
				getStage().changeScene(IntroScene.NAME, TransitionEffect.FADE_OUT_WHITE_FADE_IN, 1500);
				return;
			}
			if (isOverSHOW(x, y)) {
				showScore();
				return;
			}
			if (isOverEXIT(x, y)) {
				System.exit(0);
			}
		}
	}

	@Override
	protected boolean onCanClick(int x, int y) {
		// super.onCanClick(x, y) can be replaced with musicOnOff.contains(x, y)
		// - indeed, there are no other subpanes in this pane
		return super.onCanClick(x, y) || isOverSTART(x, y) || isOverEXIT(x, y) || isOverSHOW(x, y);
	}

	public int loadScore(File file)  {
		try (Scanner sc = new Scanner(file)) {
			loadScore = sc.nextInt();
		} catch (FileNotFoundException e) {
			System.out.println("problem so suborom");
		}
		return loadScore;
	}
}