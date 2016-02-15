package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.SideOrientationHeroSetter;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.NinjaRope;
import br.com.guigasgame.side.Side;

public class NinjaRopeSwingingState extends HeroState
{
	private NinjaRope ninjaRope;
	
	public NinjaRopeSwingingState(GameHero gameHero, NinjaRope ninjaRope)
	{
		super(gameHero, HeroAnimationsIndex.HERO_RUNNING);

		gameHero.getAnimation().setColor(Color.CYAN);
		this.ninjaRope = ninjaRope; 
	}
	
	@Override
	protected void rope()
	{
		//do nothing
	}
	
	private void ropeRelease()
	{
		if (gameHero.getCollidableHero().isTouchingGround() && !gameHero.getCollidableHero().isFallingDown())
		{
			setState(new StandingHeroState(gameHero));
		}
		else
		{
			setState(new JumpingHeroState(gameHero));
		}
		ninjaRope.markToDestroy();
	}
	
	@Override
	protected void stateInputReleased(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.ROPE)
		{
			ropeRelease();
		}
	}
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		Side currentSide = gameHero.getForwardSide();
		Side newSide = Side.LEFT;
		if (gameHero.getCollidableHero().getBodyLinearVelocity().x > 0)
		{
			newSide = Side.RIGHT;
		}
		if (newSide != currentSide)
		{
			gameHero.addAction(new SideOrientationHeroSetter(newSide, heroStatesProperties));
		}
		
		if (!getInputMap().get(HeroInputKey.ROPE))
		{
			ropeRelease();
		}
	}
	
	@Override
	protected void stateInputIsPressing(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.UP)
		{
			ninjaRope.enshort();
		}
		else if (inputValue == HeroInputKey.DOWN)
		{
			ninjaRope.enlarge();
		}
	}

}