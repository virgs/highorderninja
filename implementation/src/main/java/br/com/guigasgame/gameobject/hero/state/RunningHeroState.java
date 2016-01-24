package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;


class RunningHeroState extends HeroState
{

	protected RunningHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_RUNNING);
	}

	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			setState(new JumpingHeroState(gameHero));
		}
		else
			if (key == HeroInputKey.SLIDE)
			{
				setState(new SlidingHeroState(gameHero));
			}
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (!gameHero.isMoving())
		{
			setState(new StandingHeroState(gameHero));
		}
		else if (gameHero.isFallingDown())
		{
			setState(new FallingHeroState(gameHero));
		}

	}

	@Override
	public void isPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.LEFT)
		{
			moveLeft();
		}
		else
			if (key == HeroInputKey.RIGHT)
			{
				moveRight();
			}
			/*else
				if (gameHero.isTouchingGround() && key == HeroInputKey.DOWN)
				{
					setState(new DuckingState(gameHero));
				}*/
	}

}