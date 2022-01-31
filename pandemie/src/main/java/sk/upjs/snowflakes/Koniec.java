package sk.upjs.snowflakes;


import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

public class Koniec extends Scene {

	public static final String NAME = "Koniec";

	private  Turtle zacat;

	private File file;

	private Turtle skoncit;

	private MusicOnOffSwitch musicOnOff;

	private Turtle kresli;;

	private Turtle zobrazit;

	private int p;

	private int r;

	public Koniec(Stage stage) {
		super(stage);
		prepareScreen();
	}

	private void prepareScreen() {
		setBorderWidth(0);

		// paint background
		Turtle painter = new Turtle();
		painter.setShape(new ImageShape("images", "Skončiť.png"));
		add(painter);
		painter.center();
		painter.stamp();
		remove(painter);

		// vypise text
		/* file = new File("a");
		kresli = new Turtle();
		kresli.setPosition(353,240);
		kresli.turn(90);
		kresli.setFont(new Font("Lucida Sans", Font.BOLD, 16));
		add(kresli); */
		zobrazit = new Turtle();
		zobrazit.setPosition(250,180);
		zobrazit.turn(90);
		zobrazit.setFont(new Font("Lucida Sans", Font.BOLD, 16));
		add(zobrazit);
		zobrazit.printCenter("ZOBRAZIŤ VÝSLEDKY");
		remove(zobrazit);

		//r = loadBodiky(file);
		StringBuilder q = new StringBuilder();
		String s1 = "Gratulujem, získal si ";
		String s2 = Integer.toString(r);
		String s3 = " bodov!";
		q.append(s1);
		q.append(s2);
		q.append(s3);
		//kresli.printCenter(q.toString());
		System.out.println("koniec");
		//System.out.println(loadBodiky(file));
		//remove(kresli);

		ImageShape snowflakeShape = new ImageShape("images", "mask.png");

		zacat = new Turtle();
		zacat.setShape(snowflakeShape);
		zacat.setPosition(70, 528);
		add(zacat);

		skoncit = new Turtle();
		skoncit.setShape(snowflakeShape);
		skoncit.setPosition(512, 528);
		skoncit.setFrameIndex((int) (Math.random() * skoncit.getFrameCount()));
		add(skoncit);

		musicOnOff = new MusicOnOffSwitch(getStage());
		musicOnOff.setPosition(5, 5);
		add(musicOnOff);
	}

	public void start() {
		musicOnOff.updateView();
		zacat.setShapeAnimation(true);
		skoncit.setShapeAnimation(true);
	}

	public void ukazSkore(){
		file = new File("a");
		kresli = new Turtle();
		kresli.setPosition(353,240);
		kresli.turn(90);
		kresli.setFont(new Font("Times New Roman", Font.BOLD, 16));
		add(kresli);
		r = loadBodiky(file);
		System.out.println(r);
		StringBuilder q = new StringBuilder();
		String s1 = "Gratulujem, získal si ";
		String s2 = Integer.toString(r);
		String s3 = " bodov!";
		q.append(s1);
		q.append(s2);
		q.append(s3);
		kresli.printCenter(q.toString());
		remove(kresli);
	}

	public void stop() {
		zacat.setShapeAnimation(false);
		skoncit.setShapeAnimation(false);
	}

	private boolean isOverSTART(int x, int y) {
		return (zacat.getX() - 20 <= x) && (x <= zacat.getX() + 180)
				&& (zacat.getY() - 20 <= y) && (y <= zacat.getY() + 20);
	}

	private boolean isOverEXIT(int x, int y) {
		return (skoncit.getX() - 20 <= x) && (x <= skoncit.getX() + 180) && (skoncit.getY() - 20 <= y)
				&& (y <= skoncit.getY() + 20);
	}

	private boolean isOverZOBRAZ(int x, int y) {
		return (zobrazit.getX() - 20 <= x) && (x <= zobrazit.getX() + 180) && (zobrazit.getY() - 20 <= y)
				&& (y <= zobrazit.getY() + 20);
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
			if (isOverZOBRAZ(x, y)) {
				ukazSkore();
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
		return super.onCanClick(x, y) || isOverSTART(x, y) || isOverEXIT(x, y) || isOverZOBRAZ(x, y);
	}

	public int loadBodiky(File file)  {
		try (Scanner sc = new Scanner(file)) {
			p = sc.nextInt();
		} catch (FileNotFoundException e) {
			System.out.println("problem so suborom");
		}
		return p;
	}
}