package com.infinixsoft.utils;

import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SpriteType extends Sprite {

	int type;
	float initialX, initialY;

	public SpriteType(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager,
			int typeObject) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.type = typeObject;
		this.initialX = pX;
		this.initialY = pY;
	}

	public void moveToInitialPosition() {
		this.registerEntityModifier(new MoveModifier(1, this.getX(), initialX, this.getY(), initialY));
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getInitialX() {
		return initialX;
	}

	public void setInitialX(float initialX) {
		this.initialX = initialX;
	}

	public float getInitialY() {
		return initialY;
	}

	public void setInitialY(float initialY) {
		this.initialY = initialY;
	}

}
