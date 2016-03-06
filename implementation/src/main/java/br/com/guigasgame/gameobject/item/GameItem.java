package br.com.guigasgame.gameobject.item;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;


public abstract class GameItem extends GameObject
{

	protected final GameItemIndex index;
	protected final GameItemProperties properties;
	protected GameItemCollidable collidable;
	private float lifeTime;

	protected GameItem(GameItemIndex index, Vec2 position)
	{
		this.index = index;
		this.properties = GameItemPropertiesPool.getGameItemProperties(index);

		collidable = new GameItemCollidable(position, properties);
		collidable.addListener(this);
		collidableList.add(collidable);

		lifeTime = properties.lifeTime;

		initializeAnimation();

	}

	private void initializeAnimation()
	{
		
		Animation animation = Animation.createAnimation(AnimationsCentralPool.getGameItemsAnimationRepository().getAnimationsProperties(index));
		drawableList.add(animation);
		float animationWidth = animation.getWidth(); 
		
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( Drawable drawable : drawableList )
		{
			drawable.draw(renderWindow);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		if (alive)
		{
			final float angleInDegrees = (float) WorldConstants.radiansToDegrees(collidable.getAngleRadians());

			lifeTime -= deltaTime;
			for( Drawable drawable : drawableList )
			{
				Animation animation = (Animation) drawable;
				animation.setPosition(WorldConstants.physicsToSfmlCoordinates(collidable.getPosition()));
				animation.update(deltaTime);
				animation.setOrientation(angleInDegrees);
			}
			if (lifeTime / properties.lifeTime < 0.4) // Less than 20% of
														// lifeTime left
			{
				almostAtTheEnd();
			}
		}
	}
	
	private void almostAtTheEnd()
	{
		for( Drawable drawable : drawableList )
		{
			Animation animation = (Animation) drawable;
			animation.setAlpha(50);
		}
		if (lifeTime <= 0)
			markToDestroy();

	}
	
	public abstract void acts(PlayableGameHero playableGameHero);

}