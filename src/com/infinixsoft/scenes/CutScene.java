package com.infinixsoft.scenes;

import java.io.IOException;
import java.io.InputStream;

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

import com.infinixsoft.asdragrupos.GameActivity;
import com.infinixsoft.asdragrupos.SceneManager;
import com.infinixsoft.utils.SceneType;
import com.infinixsoft.utils.SoundType;

public class CutScene {

	private static Scene scene;
	private static GameActivity context;
	private static TextureRegion bgTextureRegion, btnJugarTextureRegion;;

	// ===========================================================
	// Set Resources
	// ===========================================================

	private static String backgroundPath = "gfx/bgcutscene.png";
	private static String btnJugarPath = "gfx/btnjugar.png";
	private static Sprite btnJugarSprite;
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
//				case TouchEvent.ACTION_DOWN:
//					this.setAlpha(0.4f);
//					break;
//				case TouchEvent.ACTION_MOVE:
//
//					break;
				case TouchEvent.ACTION_UP:
					btnJugarSprite.setAlpha(1f);
					break;
				}
				return true;
			}
		};
		scene.attachChild(bgSprite);
		scene.registerTouchArea(bgSprite);
		scene.setOnAreaTouchTraversalFrontToBack();
		
		//getinfobutton
		scene = context.getInfoButtonSprite(scene, SceneType.CUTSCENE);

		btnJugarSprite = new Sprite(780f, 480f, btnJugarTextureRegion, context.getVertexBufferObjectManager()) {
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
					toNextScene();
					break;
				}
				return true;
			}
		};
		scene.attachChild(btnJugarSprite);
		scene.registerTouchArea(btnJugarSprite);

	}

	// ===========================================================
	// Methods
	// ===========================================================

	public static void toNextScene(){
		switch (mFromScene) {
		case NIVELFACIL:
			NivelMedioScene.load(context);
			SceneManager.setScene(NivelMedioScene.run());
			break;
		case NIVELMEDIO:
			NivelDificilScene.load(context);
			SceneManager.setScene(NivelDificilScene.run());
			break;
		case NIVELDIFICIL:
//			CortoLargoScene.load(context);
//			SceneManager.setScene(CortoLargoScene.run());
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

	//overloading
	public static Scene run() {
		
		return scene;
	}

	/**
	 * Unload any assets here - to be called later.
	 */
	public static void unload() {

	}
}
