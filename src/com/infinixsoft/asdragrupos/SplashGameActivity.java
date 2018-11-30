package com.infinixsoft.asdragrupos;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.color.Color;

import android.content.Intent;

import com.infinixsoft.utils.Timer;
import com.infinixsoft.utils.Timer.ITimerCallback;

public class SplashGameActivity extends SimpleBaseGameActivity {

	private static final float CAMERA_WIDTH = 1024;
	private static final float CAMERA_HEIGHT = 600;
	private Camera mCamera;
	private Scene scene;
	private TextureRegion splashTextureRegion;
	private Timer splashTimer;

	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
//		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH,
//				CAMERA_HEIGHT), mCamera);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), mCamera);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		try {
			ITexture boxTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return getAssets().open("gfx/bgsplash.png");
				}
			}, TextureOptions.BILINEAR);
			boxTexture.load();
			this.splashTextureRegion = TextureRegionFactory.extractFromTexture(boxTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene() {
		scene = new Scene();
		Background background = new Background(Color.BLACK);
		scene.setBackground(background);

		Sprite bgSplash = new Sprite(0, 0, splashTextureRegion, getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				Intent i = new Intent(SplashGameActivity.this, GameActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				startActivity(i);
				finish();
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};

		scene.attachChild(bgSplash);
		scene.registerTouchArea(bgSplash);

		splashTimer = new Timer(3, new ITimerCallback() {

			@Override
			public void onTick() {
				Intent i = new Intent(SplashGameActivity.this, GameActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				startActivity(i);
				getEngine().unregisterUpdateHandler(splashTimer);
				finish();
			}
		});
		
		this.getEngine().registerUpdateHandler(splashTimer);

		return scene;
	}

}
