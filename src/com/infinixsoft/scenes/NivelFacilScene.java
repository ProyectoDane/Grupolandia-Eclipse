package com.infinixsoft.scenes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePack;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackLoader;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.TexturePackTextureRegionLibrary;
import org.andengine.extension.texturepacker.opengl.texture.util.texturepacker.exception.TexturePackParseException;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import com.infinixsoft.TexturePacker.Canastos;
import com.infinixsoft.TexturePacker.Level1;
import com.infinixsoft.asdragrupos.GameActivity;
import com.infinixsoft.asdragrupos.SceneManager;
import com.infinixsoft.utils.SceneType;
import com.infinixsoft.utils.SoundType;
import com.infinixsoft.utils.SpriteType;

public class NivelFacilScene {

	private static Scene scene;
	private static GameActivity context;
	private static TextureRegion bgTextureRegion;
	private static TexturePackTextureRegionLibrary mSpritesheetTexturePackTextureRegionLibrary;
	private static TexturePackTextureRegionLibrary mSpritesheetBoxTexturePackTextureRegionLibrary;
	public static Sprite boxRemerasSprite, boxPantalonesSprite;

	// ===========================================================
	// Set Resources
	// ===========================================================

	private static int placedItems = 0, totalItems = 6;
	private static String backgroundPath = "gfx/bgniveles.png";
	private static String texturePackerName = "level1.xml";
	private static String boxTexturePackerName = "canastos.xml";

	/**
	 * Load the scene and any assets we need.
	 */
	@SuppressWarnings("static-access")
	public static void load(GameActivity myActivity) {

		context = myActivity;
		placedItems = 0;

		try {
			ITexture bgTexture = new BitmapTexture(context.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return context.getAssets().open(backgroundPath);
				}
			});
			bgTexture.load();
			bgTextureRegion = TextureRegionFactory.extractFromTexture(bgTexture);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			final TexturePack spritesheetTexturePack = new TexturePackLoader(context.getTextureManager(), "gfx/spritesheets/")
					.loadFromAsset(context.getAssets(), texturePackerName);
			spritesheetTexturePack.loadTexture();
			mSpritesheetTexturePackTextureRegionLibrary = spritesheetTexturePack.getTexturePackTextureRegionLibrary();
		} catch (final TexturePackParseException e) {
			Debug.e(e);
		}

		try {
			final TexturePack spritesheetBoxTexturePack = new TexturePackLoader(context.getTextureManager(), "gfx/spritesheets/")
					.loadFromAsset(context.getAssets(), boxTexturePackerName);
			spritesheetBoxTexturePack.loadTexture();
			mSpritesheetBoxTexturePackTextureRegionLibrary = spritesheetBoxTexturePack.getTexturePackTextureRegionLibrary();
		} catch (final TexturePackParseException e) {
			Debug.e(e);
		}

		scene = new Scene();

		Background background = new Background(org.andengine.util.color.Color.WHITE);
		scene.setBackground(background);
		Sprite bgSprite = new Sprite(0, 0, bgTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(bgSprite);
		scene.setOnAreaTouchTraversalFrontToBack();
		
		//getinfobutton
		scene = context.getInfoButtonSprite(scene, SceneType.NIVELFACIL);

		Level1 texturePackerInterface = new Level1() {
		};

		// Getting texture region from TexturePacker files

		TextureRegion item1TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM1_ID);
		TextureRegion item2TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM2_ID);
		TextureRegion item3TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM3_ID);
		TextureRegion item4TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM4_ID);
		TextureRegion item5TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM5_ID);
		TextureRegion item6TextureRegion = mSpritesheetTexturePackTextureRegionLibrary.get(texturePackerInterface.ITEM6_ID);

		// Add items to scene
		// fila inferior
		addItem(item4TextureRegion, 346, 269, 2);
		addItem(item5TextureRegion, 482, 269, 1);
		addItem(item6TextureRegion, 617, 268, 1);
		// fila superior
		addItem(item1TextureRegion, 341, 75, 2);
		addItem(item2TextureRegion, 478, 74, 1);
		addItem(item3TextureRegion, 591, 75, 2);

		scene.setTouchAreaBindingOnActionDownEnabled(true);

		TextureRegion boxRemerasTextureRegion = mSpritesheetBoxTexturePackTextureRegionLibrary.get(Canastos.REMERAS_ID);
		TextureRegion boxPantalonesTextureRegion = mSpritesheetBoxTexturePackTextureRegionLibrary.get(Canastos.PANTALONES_ID);

		// Add box sprites
		boxRemerasSprite = new Sprite(112f, 464f, boxRemerasTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(boxRemerasSprite);

		boxPantalonesSprite = new Sprite(740f, 469f, boxPantalonesTextureRegion, context.getVertexBufferObjectManager());
		scene.attachChild(boxPantalonesSprite);

	}

	// ===========================================================
	// Methods
	// ===========================================================

	private static void addItem(final TextureRegion textureRegion, final int pX, final int pY, final int type) {
		final SpriteType sprite = new SpriteType(pX, pY, textureRegion, context.getVertexBufferObjectManager(), type) {
			boolean mGrabbed = false;

			@Override
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
				switch (pSceneTouchEvent.getAction()) {
				case TouchEvent.ACTION_DOWN:
					context.playSound(SoundType.PICK);
					this.setScale(1.1f);
					this.mGrabbed = true;
					this.clearEntityModifiers();
					break;
				case TouchEvent.ACTION_MOVE:
					if (this.mGrabbed) {
						this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					}
					break;
				case TouchEvent.ACTION_UP:
					if (this.mGrabbed) {
						this.mGrabbed = false;
						this.setScale(1f);
						if (this.getType() == 1) {
							if (this.collidesWith(boxRemerasSprite)) {
								putInBox(this, 1);
							} else {
								this.moveToInitialPosition();
							}
						}
						if (this.getType() == 2) {
							if (this.collidesWith(boxPantalonesSprite)) {
								putInBox(this, 2);
							} else {
								this.moveToInitialPosition();
							}
						}
					}
					break;
				}
				return true;
			}

			private void putInBox(final SpriteType sprite, int box) {

				context.playSound(SoundType.CORRECT);

				if (box == 1) {
					float rotation = new Random().nextInt(20) - 10;
					sprite.registerEntityModifier(new RotationModifier(0.3f, sprite.getRotation(), rotation) {
						protected void onModifierFinished(org.andengine.entity.IEntity pItem) {

							int max = (int) (166 - (sprite.getWidth()));
							Random rnd = new Random();
							int posX = rnd.nextInt(max) + 15;
							// int posY = rnd.nextInt((int) (sprite.getHeight()
							// / 2));
							sprite.registerEntityModifier(new MoveModifier(0.5f, sprite.getX(), boxRemerasSprite.getX() + posX, sprite
									.getY(), boxRemerasSprite.getY() - (sprite.getHeight() - sprite.getHeight() / 3)));
						};
					});
				} else if (box == 2) {
					float rotation = new Random().nextInt(20) - 10;
					sprite.registerEntityModifier(new RotationModifier(0.3f, sprite.getRotation(), rotation) {
						protected void onModifierFinished(org.andengine.entity.IEntity pItem) {

							int max = (int) (166 - (sprite.getWidth()));
							Random rnd = new Random();
							int posX = rnd.nextInt(max) + 15;
							// int posY = rnd.nextInt((int) (sprite.getHeight()
							// / 2));
							sprite.registerEntityModifier(new MoveModifier(0.5f, sprite.getX(), boxPantalonesSprite.getX() + posX, sprite
									.getY(), boxPantalonesSprite.getY() - (sprite.getHeight() - sprite.getHeight() / 3)));
						};
					});

				}

				scene.unregisterTouchArea(sprite);

				placedItems = placedItems + 1;

				if (placedItems == totalItems) {

					sprite.registerEntityModifier(new DelayModifier(1f) {
						protected void onModifierFinished(org.andengine.entity.IEntity pItem) {
							CutScene.load(context);
							SceneManager.setScene(CutScene.run(SceneType.NIVELFACIL));
						};
					});

				}
			}
		};

		scene.attachChild(sprite);
		scene.registerTouchArea(sprite);
	}

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