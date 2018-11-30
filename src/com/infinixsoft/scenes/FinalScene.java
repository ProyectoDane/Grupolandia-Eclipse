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

import android.content.Intent;

import com.infinixsoft.asdragrupos.GameActivity;
import com.infinixsoft.asdragrupos.SplashGameActivity;
import com.infinixsoft.utils.SceneType;
import com.infinixsoft.utils.SoundType;

public class FinalScene {

	private static Scene scene;
	private static GameActivity context;
	private static TextureRegion bgTextureRegion, btnJugarTextureRegion, btnMenuTextureRegion;

	// ===========================================================
	// Set Resources
	// ===========================================================

	private static String backgroundPath = "gfx/bgfinalscene.png";
	private static String btnJugarPath = "gfx/btnotravez.png";
	private static String btnMenuPath = "gfx/btnmenu.png";
	private static Sprite btnJugarSprite, btnMenuSprite;
//	private static SceneType mFromScene;

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
			ITexture btnMenuTexture = new BitmapTexture(context.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return context.getAssets().open(btnMenuPath);
				}
			}, TextureOptions.BILINEAR);

			bgTexture.load();
			btnJugarTexture.load();
			btnMenuTexture.load();

			bgTextureRegion = TextureRegionFactory.extractFromTexture(bgTexture);
			btnJugarTextureRegion = TextureRegionFactory.extractFromTexture(btnJugarTexture);
			btnMenuTextureRegion = TextureRegionFactory.extractFromTexture(btnMenuTexture);

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
					btnJugarSprite.setAlpha(1f);
					btnMenuSprite.setAlpha(1f);
					break;
				}
				return true;
			}
		};
		scene.attachChild(bgSprite);
		scene.registerTouchArea(bgSprite);
		scene.setOnAreaTouchTraversalFrontToBack();
		
		//getinfobutton
		scene = context.getInfoButtonSprite(scene, SceneType.FINALSCENE);

		btnJugarSprite = new Sprite(489f, 458f, btnJugarTextureRegion, context.getVertexBufferObjectManager()) {
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
					Intent i = new Intent(context, SplashGameActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
					context.startActivity(i);
					context.finish();
					// toNextScene();
					break;
				}
				return true;
			}
		};
		bgSprite.attachChild(btnJugarSprite);
		scene.registerTouchArea(btnJugarSprite);

		btnMenuSprite = new Sprite(797f, 458f, btnMenuTextureRegion, context.getVertexBufferObjectManager()) {
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
					int pid = android.os.Process.myPid();
					android.os.Process.killProcess(pid);
					// context.finish();
					// toNextScene();
					break;
				}
				return true;
			}
		};
		scene.attachChild(btnMenuSprite);
		scene.registerTouchArea(btnMenuSprite);

	}

	// ===========================================================
	// Methods
	// ===========================================================

//	public static void toNextScene() {
//		switch (mFromScene) {
//		case BAJOALTO:
//			ChicoGrandeScene.load(context);
//			SceneManager.setScene(ChicoGrandeScene.run());
//			break;
//		case CHICOGRANDE:
//			ClaroOscuroScene.load(context);
//			SceneManager.setScene(ClaroOscuroScene.run());
//			break;
//		case CLAROOSCURO:
//			CortoLargoScene.load(context);
//			SceneManager.setScene(CortoLargoScene.run());
//			break;
//		case CORTOLARGO:
//			LentoRapidoScene.load(context);
//			SceneManager.setScene(LentoRapidoScene.run());
//			break;
//		case LENTORAPIDO:
//			LivianoPesadoScene.load(context);
//			SceneManager.setScene(LivianoPesadoScene.run());
//			break;
//		case LIVIANOPESADO:
//			PocoMuchoScene.load(context);
//			SceneManager.setScene(PocoMuchoScene.run());
//			break;
//		case POCOMUCHO:
//			RotoSanoScene.load(context);
//			SceneManager.setScene(RotoSanoScene.run());
//			break;
//		case ROTOSANO:
//			// ChicoGrandeScene.load(context);
//			// SceneManager.setScene(ChicoGrandeScene.run());
//			break;
//
//		default:
//			break;
//		}
//	}

	/**
	 * Return the scene for when the scene is called to become active.
	 */
	public static Scene run() {

		return scene;
	}

	/**
	 * Unload any assets here - to be called later.
	 */
	public static void unload() {

	}
}
