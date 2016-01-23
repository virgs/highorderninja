package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

class StandingHeroState extends OnGroundState
{

	public StandingHeroState(GameHero gameHero)
	{
		super(new Vec2(10, 10), true, true, HeroAnimationsIndex.HERO_STANDING,
				gameHero, 20, 10);
	}

	@Override
	public void inputPressed(HeroInputKey key)
	{
		if (key == HeroInputKey.JUMP)
		{
			setState(new JumpingHeroState(true, gameHero));
		}
		else if (key == HeroInputKey.ACTION)
		{
			System.out.println("Action");
		}
		else if (key == HeroInputKey.DOWN)
		{
			setState(new DuckingState(gameHero));
		}
		else if (key == HeroInputKey.SLIDE)
		{
			setState(new SlidingHeroState(gameHero));
		}
	}

	@Override
	public void isPressed(HeroInputKey key)
	{

		if (key == HeroInputKey.LEFT)
		{
			moveLeft();
		}
		else if (key == HeroInputKey.RIGHT)
		{
			moveRight();
		}
		else if (gameHero.isTouchingGround() && key == HeroInputKey.DOWN)
		{
			setState(new DuckingState(gameHero));
		}
	}

}
