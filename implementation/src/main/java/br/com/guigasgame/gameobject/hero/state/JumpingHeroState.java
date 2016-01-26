package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


class JumpingHeroState extends HeroState
{

	private boolean doubleJumpAllowed;

	protected JumpingHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_ASCENDING);
		doubleJumpAllowed = true;
	}

	@Override
	public void onEnter()
	{
		jumper.jump();
	}

	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (doubleJumpAllowed && key == HeroInputKey.JUMP)
		{
			jumper.doubleJump();
			doubleJumpAllowed = false;
		}
	}

	@Override
	public void isPressed(HeroInputKey key)
	{

		if (key == HeroInputKey.LEFT)
		{
			setHeroForwardSide(Side.LEFT);
			// moveLeft();
		}
		else if (key == HeroInputKey.RIGHT)
		{
			setHeroForwardSide(Side.RIGHT);
			// moveRight();
		}
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}
