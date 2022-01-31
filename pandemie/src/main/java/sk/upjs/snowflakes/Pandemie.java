package sk.upjs.snowflakes;

import sk.upjs.jpaz2.*;
import sk.upjs.jpaz2.theater.*;

public class Pandemie extends Stage {

	public Pandemie() {
		super("Pandémie", 715, 600, new ImageShape("images", "virusButton.png"));
	}

	@Override
	protected void initialize() {
		// background music
		AudioClip clip = new AudioClip("audio", "LetItSnow.mid", true);
		clip.setVolume(0.5);
		setBackgroundMusic(clip);

		// scenes
		addScene(IntroScene.NAME, new IntroScene(this));
		addScene(QuizGame.NAME, new QuizGame(this));
		addScene(EndScene.NAME, new EndScene(this));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pandemie game = new Pandemie();
		game.run(IntroScene.NAME);
	}
}
