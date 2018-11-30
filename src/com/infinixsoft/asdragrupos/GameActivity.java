package com.infinixsoft.asdragrupos;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import com.infinixsoft.scenes.InfoScene;
import com.infinixsoft.scenes.NivelFacilScene;
import com.infinixsoft.utils.SceneType;
import com.infinixsoft.utils.SoundType;

public class GameActivity extends SimpleBaseGameActivity {

	private static final float CAMERA_WIDTH = 1024;
	private static final float CAMERA_HEIGHT = 600;
	private Camera mCamera;
	private Music mMusic;
	private Sound pickSound, correctSound;
	private TextureRegion btnInfoTextureRegion;
	private String btnInfoPath = "gfx/btninfo.png";

	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		// EngineOptions engineOptions = new EngineOptions(true,
		// ScreenOrientation.LANDSCAPE_SENSOR, new
		// RatioResolutionPolicy(CAMERA_WIDTH,
		// CAMERA_HEIGHT), mCamera);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), mCamera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		MusicFactory.setAssetBasePath("mfx/");
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.mMusic = MusicFactory.createMusicFromAsset(this.mEngine.getMusicManager(), this, "music.mp3");
			this.mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
		try {
			this.pickSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "pick.mp3");
			this.correctSound = SoundFactory.createSoundFromAsset(this.mEngine.getSoundManager(), this, "correct.mp3");
		} catch (final IOException e) {
			Debug.e(e);
		}
		ITexture btnInfoTexture;
		try {
			btnInfoTexture = new BitmapTexture(getTextureManager(), new IInputStreamOpener() {

				public InputStream open() throws IOException {
					return getAssets().open(btnInfoPath);
				}
			}, TextureOptions.BILINEAR);

			btnInfoTexture.load();

			btnInfoTextureRegion = TextureRegionFactory.extractFromTexture(btnInfoTexture);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SceneManager.init(this);
		// call the load method from our first screen,
		// therefore loading all the assets we're going to need
		// at this moment in time
		// FirstScreen.load(TestGameActivity.this);
		InfoScene.load(GameActivity.this);
		
		NivelFacilScene.load(GameActivity.this);
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		return NivelFacilScene.run();
	}

	@Override
	public synchronized void onPauseGame() {
		// TODO Auto-generated method stub
		GameActivity.this.mMusic.pause();
		super.onPauseGame();
	}

	@Override
	public synchronized void onResumeGame() {
		// TODO Auto-generated method stub
		GameActivity.this.mMusic.play();
		super.onResumeGame();
	}

	public void playSound(SoundType selectedSound) {
		switch (selectedSound) {
		case PICK:
			this.pickSound.play();
			break;

		case CORRECT:
			this.correctSound.play();
			break;

		case BUTTON:

			break;

		default:
			break;
		}

	}

	public TextureRegion getInfoButtonTexture() {

		return btnInfoTextureRegion.deepCopy();
	}

	public Scene getInfoButtonSprite(final Scene scene, final SceneType fromScene) {

		Sprite btnInfoSprite = new Sprite(10, 10, getInfoButtonTexture(), getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				 case TouchEvent.ACTION_DOWN:
				 this.setAlpha(0.4f);
				 break;
				 case TouchEvent.ACTION_MOVE:
				
				 break;
				case TouchEvent.ACTION_UP:
					SceneManager.setScene(InfoScene.run(fromScene));
					this.setAlpha(1f);
					break;
				}
				return true;
			}
		};
		
		scene.attachChild(btnInfoSprite);
		scene.registerTouchArea(btnInfoSprite);
		
		return scene;
	}

}
