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

public class QuizGame extends Scene {

	public static final String NAME = "QuizGame";

	/** list og questions **/
	private ArrayList<String> questions = new ArrayList<String>(Arrays.asList("Koľko ľudí sa nakazilo Španielskou chrípkou?",
			"Aký národ a v akom storočí začal ako prvý praktizovať karanténu?",
			"Odkiaľ sa začala šíriť cholera?",
			"Kto bol prvou zapísanou obeťou španielskej chrípky(H1N1)?",
			"Aké sú príznaky eboly?"));

	/** list of correct answears **/
	private ArrayList<Integer> answears = new ArrayList<Integer>(Arrays.asList(0,1,2,3,0));

	String[] o1 = {"500 miliónov","50 miliónov","100 miliónov","420 miliónov"};
	String[] o2 = {"Francúzi - 16. storočie","Talianni - 14. storčie","Francúzi - 15. storočie","Taliani - 13. storočie"};
	String[] o3 = {"z Dunaja","z prítoku rieky Tigris","z delty indického veľtoku Ganga","z rieky Amazonka"};
	String[] o4 = {"španielska učiteľka Bianca Bernardo","robotník z Talianska Fernando Gavallini","kuchár z Kansasu Abert Gitchel","identitia tohto človeka nie je známa"};
	String[] o5 = {"horúčky, bolesť svalov a hlavy, vnútorné a vonkjašie krvácanie","horučky, silný zahlienený kašeľ, strata chuti","bolesť hrdla, horúčka, zápal pľúč","zvracane, horúčka, zápal pľúc, zvracanie"};

	/** list of possible options **/
	private ArrayList<String[]> options = new ArrayList<String[]>(Arrays.asList(o1,o2,o3,o4,o5));

	/** counts how many questions have already been asked **/
	public int count;

	/** saves the player's answer **/
	private int answear;

	/** create new scoreboard **/
	private Scoreboard scoreboard = new Scoreboard();

	/** turtles that display options **/
	private Turtle a;
	private Turtle b;
	private Turtle c;
	private Turtle d;
	private Turtle A;
	private Turtle B;
	private Turtle C;
	private Turtle D;

	/** music switch **/
	private MusicOnOffSwitch musicOnOff;

	/** back button - return you to intro scene **/
	private BackButton backButton;

	/** index - store random generated question **/
	private int index;

	/** file in which the resulting score is saved **/
	private File file = new File("finalScore");

	/** stores if game is running or not **/
	private boolean isGameRunning;

	/** saved image, which displays a selection of options **/
	ImageShape virus = new ImageShape("images", "optionButton.png");

	/** constructor **/
	public QuizGame(Stage stage) {
		super(stage);
		prepareScreen();
	}

	/** prepares screen **/
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
		index = (int) (Math.random() * questions.size());
		String ot = questions.get(index);
		question.printCenter(ot);
		remove(question);
		System.out.println("spravna odpoved " + answears.get(index));

		// option buttons
		a = createTurtleAnswearButton(90, 310, virus);
		b = createTurtleAnswearButton(90, 380, virus);
		c = createTurtleAnswearButton(90, 450, virus);
		d = createTurtleAnswearButton(90, 520, virus);

		A = createTurtleAnswear(115, 314, options.get(index)[0]);
		B = createTurtleAnswear(115, 384, options.get(index)[1]);
		C = createTurtleAnswear(115, 454, options.get(index)[2]);
		D = createTurtleAnswear(115, 524, options.get(index)[3]);

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

	private void stopGame() {
		if (!isGameRunning)
			return;
	}

    /** the game ends after 3 questions **/
	private void endGame(){
		if (count == 3){
			isGameRunning = false;
			count = 0;
			System.out.println("body");
			System.out.println(scoreboard.getScore());
			saveScore(file , scoreboard.getScore());
			stopGame();
			getStage().changeScene(EndScene.NAME, TransitionEffect.FADE_OUT_WHITE_FADE_IN, 1500);
			return;
		}
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

	private void nextGame(){
		count += 1;
		endGame();
		prepareScreen();
	}

	/** checks whether the player answered correctly **/
	private boolean correctAnswear(int x, int y){
		/** ok - save correct answear of current question **/
		Integer ok = answears.get(index);
		if (isOverA(x,y)) {
			answear = 0;
		}
		else if (isOverB(x,y)) {
			answear = 1;
		}
		else if (isOverC(x,y)) {
			answear = 2;
		}
		else if (isOverD(x,y)) {
			answear = 3;
		}
		else {System.err.println("Chybny vstup. X: " + x + "Y: " + y);}
		if (answear == ok) {
			scoreboard.increaseScore(1);
			return true;
		}
		else {
			System.out.println(scoreboard.getScore());
			return false;
		}
	}

	private Turtle createTurtleAnswear(int x, int y, String odpoved) {
		Turtle turtle = new Turtle();
		turtle.setPosition(x, y);
		turtle.setFont(new Font("Times new Roman", Font.PLAIN, 11));
		add(turtle);
		turtle.turn(90);
		turtle.print(odpoved);
		remove(turtle);
		return turtle;
	}

	private Turtle createTurtleAnswearButton(int x, int y, ImageShape virus) {
		Turtle turtle = new Turtle();
		turtle.setShape(virus);
		turtle.setPosition(x,y);
		add(turtle);
		return turtle;
	}

	/** verifies which option was clicked **/
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
			System.out.println(count);
			if (isOverA(x, y)) {
				System.out.println("ok");
				correctAnswear(x,y);
				nextGame();
				return;
			}
			if (isOverB(x, y)) {
				System.out.println("ok");
				correctAnswear(x,y);
				nextGame();
				return;
			}
			if (isOverC(x, y)) {
				System.out.println("ok");
				correctAnswear(x,y);
				nextGame();
				return;
			}
			if (isOverD(x, y)) {
				System.out.println("ok");
				correctAnswear(x,y);
				nextGame();
				return;
			}
			endGame();
		}
	}

	@Override
	protected boolean onCanClick(int x, int y) {
		return super.onCanClick(x, y) || isOverA(x, y) || isOverB(x, y) || isOverC(x, y) || isOverD(x, y);
	}

	/** save final score into file **/
	public void saveScore(File file, int body){
		try (PrintWriter pw = new PrintWriter(file)) {
			pw.println(body);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}