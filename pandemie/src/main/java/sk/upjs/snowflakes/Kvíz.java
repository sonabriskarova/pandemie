package sk.upjs.snowflakes;

import sk.upjs.jpaz2.ImageShape;
import sk.upjs.jpaz2.TransitionEffect;
import sk.upjs.jpaz2.Turtle;
import sk.upjs.jpaz2.theater.Scene;
import sk.upjs.jpaz2.theater.Stage;
import java.io.File;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Kvíz extends Scene {

	private ArrayList<String> otazky = new ArrayList<String>(Arrays.asList("Koľko ľudí sa nakazilo Španielskou chrípkou?",
			"Aký národ a v akom storočí začal ako prvý praktizovať karanténu?",
			"Odkiaľ sa začala šíriť cholera?",
			"Kto bol prvou zapísanou obeťou španielskej chrípky(H1N1)?",
			"Aké sú príznaky eboly?"));

	private ArrayList<Integer> odpovede = new ArrayList<Integer>(Arrays.asList(0,1,2,3,0));

	String[] o1 = {"500 miliónov","50 miliónov","100 miliónov","420 miliónov"};
	String[] o2 = {"Francúzi - 16. storočie","Talianni - 14. storčie","Francúzi - 15. storočie","Taliani - 13. storočie"};
	String[] o3 = {"z Dunaja","z prítoku rieky Tigris","z delty indického veľtoku Ganga","z rieky Amatonka"};
	String[] o4 = {"španielska učiteľka Bianca Bernardo","robotník z Talianska Fernando Gavallini","kuchár z Kansasu Abert Gitchel","identitia tohto človeka nie je známa"};
	String[] o5 = {"horúčky, bolesť svalov a hlavy, vnútorné a vonkjašie krvácanie","horučky, silný zahlienený kašeľ, strata chuti","bolesť hrdla, horúčka, zápal pľúč","zvracane, horúčka, zápal pľúc, zvracanie"};

	private ArrayList<String[]> moznosti = new ArrayList<String[]>(Arrays.asList(o1,o2,o3,o4,o5));

	public int pocet;

	public static final String NAME = "Game2";

	private int odpoved;
	//public int body;

	private Scoreboard scoreboard = new Scoreboard();

	private Turtle a;
	private Turtle b;
	private Turtle c;
	private Turtle d;
	private Turtle A;
	private Turtle B;
	private Turtle C;
	private Turtle D;

	//private int skore;

	private MusicOnOffSwitch musicOnOff;

	private BackButton backButton;

	private int index = 0;

	private File file = new File("finalScore");

	private boolean isGameRunning;

	private QuizQuestion currentQuestion;

	ImageShape virus = new ImageShape("images", "optionButton.png");

	public Kvíz(Stage stage) {
		super(stage);
		prepareScreen();
	}

	private void prepareScreen() {
		setBorderWidth(0);
		index = 0;
		// paint background
		Turtle painter = new Turtle();
		painter.setShape(new ImageShape("images", "kv.png"));
		add(painter);
		painter.center();
		painter.stamp();
		remove(painter);

		// print question
		Turtle question = new Turtle();
		question.setPosition(322, 160);
		add(question);
		question.turn(90);
		question.setFont(new Font("Times New Roman", Font.BOLD, 15));
		index = (int) (Math.random() * otazky.size());
		String ot = otazky.get(index);
		question.printCenter(ot);
		remove(question);
		System.out.println("spravna odpoved " + odpovede.get(index));

		// option buttons
		a = createTurtleOdpovedButton(90, 310, virus);
		b = createTurtleOdpovedButton(90, 380, virus);
		c = createTurtleOdpovedButton(90, 450, virus);
		d = createTurtleOdpovedButton(90, 520, virus);

		A = createTurtleOdpoved(115, 314, moznosti.get(index)[0]);
		B = createTurtleOdpoved(115, 384, moznosti.get(index)[1]);
		C = createTurtleOdpoved(115, 454, moznosti.get(index)[2]);
		D = createTurtleOdpoved(115, 524, moznosti.get(index)[3]);

		currentQuestion = new QuizQuestion(ot, moznosti.get(index), index);
		System.out.print("current " + currentQuestion);
		// create and add the music on/off switch
		musicOnOff = new MusicOnOffSwitch(getStage());
		musicOnOff.updateView();
		musicOnOff.setPosition(5, 5);
		add(musicOnOff);

		// create and place back button
		backButton = new BackButton(getStage());
		backButton.setPosition(9, 55);
		add(backButton);

		// create scoreboard
		scoreboard.setPosition(655, 7);
		add(scoreboard);
	}

	private void startGame() {
		scoreboard.resetScore();
		musicOnOff.updateView();
		isGameRunning = true;
	}

	// hra skonci ked prebehnu akekolvek 3 otazky
	private void koniec(){
		if (pocet == 3){
			isGameRunning = false;
			pocet = 0;
			System.out.println("body");
			System.out.println(scoreboard.getScore());
			saveBody(file , scoreboard.getScore());
			stopGame();
			getStage().changeScene(Koniec.NAME, TransitionEffect.FADE_OUT_WHITE_FADE_IN, 1500);
			return;
		}
	}

	private void stopGame() {
		if (!isGameRunning)
			return;
	}

	public void start() {
		startGame();
		a.setShapeAnimation(true);
		b.setShapeAnimation(true);
		c.setShapeAnimation(true);
		d.setShapeAnimation(true);
	}

	@Override
	public void stop() {
		a.setShapeAnimation(false);
		b.setShapeAnimation(false);
		c.setShapeAnimation(false);
		d.setShapeAnimation(false);
	}

	private void dalsiaOtazka(){
		//odpovede.remove(index);
		pocet += 1;
		System.out.println("pocet");
		System.out.println(pocet);
		koniec();
		prepareScreen();
	}

	private boolean spravnaOdpoved(int x, int y){
		// spravna odpoved sa nachadza na mieste:
		Integer ok = odpovede.get(index);
		if (isOverA(x,y)) {
			odpoved = 0;
		}
		else if (isOverB(x,y)) {
			odpoved = 1;
		}
		else if (isOverC(x,y)) {
			odpoved = 2;
		}
		else if (isOverD(x,y)) {
			odpoved = 3;
		}
		else {System.err.println("Chybny vstup. X: " + x + "Y: " + y);}
		if (odpoved == ok) {
			scoreboard.increaseScore(1);
			return true;
		}
		else {
			System.out.println(scoreboard.getScore());
			return false;
		}
	}

	private Turtle createTurtleOdpoved(int x, int y, String odpoved) {
		Turtle turtle = new Turtle();
		turtle.setPosition(x, y);
		turtle.setFont(new Font("Times new Roman", Font.PLAIN, 11));
		add(turtle);
		turtle.turn(90);
		turtle.print(odpoved);
		remove(turtle);
		return turtle;
	}

	private Turtle createTurtleOdpovedButton(int x, int y, ImageShape virus) {
		//ImageShape virus = new ImageShape("images", "k.png");
		Turtle turtle = new Turtle();
		turtle.setShape(virus);
		turtle.setPosition(x,y);
		add(turtle);
		return turtle;
	}

	// vrati ci je sucastou tlacidla
	private boolean isOverA(int x, int y) {
		return ((int)(a.getX()) - 20 <= x) && (x <= (int)(a.getX()) + 50)
				&& (a.getY() - 20 <= y) && (y <= a.getY() + 20);
	}

	private boolean isOverB(int x, int y) {
		return (b.getX() - 20 <= x) && (x <= b.getX() + 50)
				&& (b.getY() - 20 <= y) && (y <= b.getY() + 20);
	}

	private boolean isOverC(int x, int y) {
		return (c.getX() - 20 <= x) && (x <= c.getX() + 50)
				&& (c.getY() - 20 <= y) && (y <= c.getY() + 20);
	}

	private boolean isOverD(int x, int y) {
		return (d.getX() - 20 <= x) && (x <= d.getX() + 50)
				&& (d.getY() - 20 <= y) && (y <= d.getY() + 20);
	}


	@Override
	protected void onMouseClicked(int x, int y, MouseEvent detail) {
		if (detail.getButton() == MouseEvent.BUTTON1) {
			System.out.println(pocet);
			if (isOverA(x, y)) {
				System.out.println("ok");
				spravnaOdpoved(x,y);
				dalsiaOtazka();
				return;
			}
			if (isOverB(x, y)) {
				System.out.println("ok");
				spravnaOdpoved(x,y);
				dalsiaOtazka();
				return;
			}
			if (isOverC(x, y)) {
				System.out.println("ok");
				spravnaOdpoved(x,y);
				dalsiaOtazka();
				return;
			}
			if (isOverD(x, y)) {
				System.out.println("ok");
				spravnaOdpoved(x,y);
				dalsiaOtazka();
				return;
			}
			koniec();
		}
	}

	@Override
	protected boolean onCanClick(int x, int y) {
		return super.onCanClick(x, y) || isOverA(x, y) || isOverB(x, y) || isOverC(x, y) || isOverD(x, y);
	}

	public void saveBody(File file, int body){
		try (PrintWriter pw = new PrintWriter(file)) {
			pw.println(body);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}