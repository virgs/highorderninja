package br.com.guigasgame.gameobject.hero.state;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.DiagonalJumpAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.side.Side;


public class WallGrabHeroState extends HeroState
{

	private Side wallSide;

	protected WallGrabHeroState(GameHero gameHero)
	{
		super(gameHero, HeroAnimationsIndex.HERO_WALLGRABBING);
		wallSide = gameHero.getForwardSide();
	}

	@Override
	public void updateState(float deltaTime)
	{
		if (gameHero.isTouchingGround() && !gameHero.isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
		if (!gameHero.isTouchingWallAhead())
		{
			setState(new FallingHeroState(gameHero));
		}
	}
	
	@Override
	protected void jump()
	{
		if (heroStatesProperties.canJump)
		{
			gameHero.addAction(new DiagonalJumpAction(gameHero, -heroStatesProperties.jumpImpulse, wallSide.opposite()));
			setState(new JumpingHeroState(gameHero));
		}			
	}

	@Override
	public void stateInputPressed(HeroInputKey key)
	{
		if (wallSide == Side.LEFT && key == HeroInputKey.RIGHT)
		{
			setState(new FallingHeroState(gameHero));
		}
		if (wallSide == Side.RIGHT && key == HeroInputKey.LEFT)
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}
