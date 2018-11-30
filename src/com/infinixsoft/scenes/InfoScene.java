package com.infinixsoft.scenes;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;

import android.content.Intent;
import android.net.Uri;

import com.infinixsoft.asdragrupos.GameActivity;
import com.infinixsoft.asdragrupos.SceneManager;
import com.infinixsoft.utils.SceneType;
import com.infinixsoft.utils.SoundType;

public class InfoScene {

	private static Scene scene;
	private static GameActivity context;
	private static TextureRegion bgTextureRegion, btnJugarTextureRegion;;

	// ===========================================================
	// Set Resources
	// ===========================================================

	private static String backgroundPath = "gfx/bginfo.png";
	private static String btnJugarPath = "gfx/btnsalirinfo.png";
	private static Sprite btnSalirSprite;
	private static SceneType mFromScene;

	/**
	 * Load the scene and any assets we need.
	 */
	public static void load(GameActivity myActivity) {

		context = myActivity;

		try {
			ITexture bgTexture = new BitmapTexture(context.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return context.getAssets().open(backgroundPath);
				}
			}, TextureOptions.BILINEAR);
			ITexture btnJugarTexture = new BitmapTexture(context.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return context.getAssets().open(btnJugarPath);
				}
			}, TextureOptions.BILINEAR);

			bgTexture.load();
			btnJugarTexture.load();

			bgTextureRegion = TextureRegionFactory.extractFromTexture(bgTexture);
			btnJugarTextureRegion = TextureRegionFactory.extractFromTexture(btnJugarTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}

		scene = new Scene();

		Background background = new Background(org.andengine.util.color.Color.WHITE);
		scene.setBackground(background);
		Sprite bgSprite = new Sprite(0, 0, bgTextureRegion, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				// case TouchEvent.ACTION_DOWN:
				// this.setAlpha(0.4f);
				// break;
				// case TouchEvent.ACTION_MOVE:
				//
				// break;
				case TouchEvent.ACTION_UP:
					btnSalirSprite.setAlpha(1f);
					break;
				}
				return true;
			}
		};
		scene.attachChild(bgSprite);
		scene.registerTouchArea(bgSprite);
		scene.setOnAreaTouchTraversalFrontToBack();

		btnSalirSprite = new Sprite(455f, 497f, btnJugarTextureRegion, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					context.playSound(SoundType.PICK);
					this.setAlpha(0.4f);
					break;
				case TouchEvent.ACTION_MOVE:

					break;
				case TouchEvent.ACTION_UP:
					this.setAlpha(1f);
					toPrevScene();
					break;
				}
				return true;
			}
		};
		scene.attachChild(btnSalirSprite);
		scene.registerTouchArea(btnSalirSprite);

		Rectangle btnInfinixWeb = new Rectangle(800, 485, 200, 100, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					context.playSound(SoundType.PICK);
					break;
				case TouchEvent.ACTION_MOVE:

					break;
				case TouchEvent.ACTION_UP:
					String infinixUrl = "http://infinixsoft.com/";
					Uri uri = Uri.parse(infinixUrl);
					context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
					break;
				}
				return true;
			}
		};
		btnInfinixWeb.setVisible(false);
		scene.attachChild(btnInfinixWeb);
		scene.registerTouchArea(btnInfinixWeb);
		
		Rectangle btnAsdraWeb = new Rectangle(50, 490, 180, 80, context.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					context.playSound(SoundType.PICK);
					break;
				case TouchEvent.ACTION_MOVE:

					break;
				case TouchEvent.ACTION_UP:
					String infinixUrl = "http://www.asdra.org.ar/";
					Uri uri = Uri.parse(infinixUrl);
					context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
					break;
				}
				return true;
			}
		};
		btnAsdraWeb.setVisible(false);
		scene.attachChild(btnAsdraWeb);
		scene.registerTouchArea(btnAsdraWeb);

	}

	// ===========================================================
	// Methods
	// ===========================================================

	public static void toPrevScene() {
		switch (mFromScene) {
		case NIVELFACIL:
			SceneManager.setScene(NivelFacilScene.run());
			break;
		case NIVELMEDIO:
			SceneManager.setScene(NivelMedioScene.run());
			break;
		case NIVELDIFICIL:
			SceneManager.setScene(NivelDificilScene.run());
			break;

		case CUTSCENE:
			SceneManager.setScene(CutScene.run());
			break;

		case FINALSCENE:
			SceneManager.setScene(FinalScene.run());
			break;

		default:
			break;
		}
	}

	/**
	 * Return the scene for when the scene is called to become active.
	 */
	public static Scene run(SceneType fromScene) {

		mFromScene = fromScene;

		return scene;
	}

	/**
	 * Unload any assets here - to be called later.
	 */
	public static void unload() {

	}
}
