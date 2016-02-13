package br.com.guigasgame.gameobject.hero.state;

import java.util.HashMap;
import java.util.Map;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsCentralPool;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.HeroStateSetterAction;
import br.com.guigasgame.gameobject.hero.action.JumpAction;
import br.com.guigasgame.gameobject.hero.action.MoveHeroAction;
import br.com.guigasgame.gameobject.hero.action.ShootAction;
import br.com.guigasgame.gameobject.hero.action.ShootRopeAction;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;
import br.com.guigasgame.gameobject.projectile.NinjaRopeProjectile;
import br.com.guigasgame.input.InputListener;
import br.com.guigasgame.math.Vector2;
import br.com.guigasgame.side.Side;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class HeroState implements InputListener<HeroInputKey>, UpdatableFromTime
{

	protected final GameHero gameHero;
	protected final HeroStateProperties heroStatesProperties;
	private Map<HeroInputKey, Boolean> inputMap;
	private NinjaRopeProjectile ninjaRopeProjectile;

	protected HeroState(GameHero gameHero, HeroAnimationsIndex heroAnimationsIndex)
	{
		super();
		this.heroStatesProperties = HeroStatesPropertiesPool.getStateProperties(heroAnimationsIndex);
		gameHero.setAnimation(Animation.createAnimation(AnimationsCentralPool.getHeroAnimationRepository().getAnimationsProperties(heroAnimationsIndex)));
		this.gameHero = gameHero;
		
		inputMap = new HashMap<>();
		for( HeroInputKey key : HeroInputKey.values() )
		{
			inputMap.put(key, false);
		}
	}

	public void onEnter()
	{
		// hook method
	}

	public void onQuit()
	{
		// hook method
	}

	public void stateUpdate(float deltaTime)
	{
		// hook method
	}

	protected void stateInputPressed(HeroInputKey inputValue)
	{
		// hook method
	}

	protected void stateInputReleased(HeroInputKey inputValue)
	{
		// hook method
	}
	
	protected void stateInputIsPressing(HeroInputKey inputValue)
	{
		// hook method
	}


	protected void rope()
	{
		if (ninjaRopeProjectile != null)
			ninjaRopeProjectile.markToDestroy();
		ninjaRopeProjectile = new NinjaRopeProjectile(pointingDirection(), gameHero);
		gameHero.addAction(new ShootRopeAction(heroStatesProperties, ninjaRopeProjectile));
	}
	
	protected void jump()
	{
		gameHero.addAction(new JumpAction(heroStatesProperties));
		setState(new JumpingHeroState(gameHero));
	}
	
	protected void shoot()
	{
		gameHero.addAction(new ShootAction(heroStatesProperties, gameHero.getNextProjectile(pointingDirection())));
	}

	@Override
	public final void inputPressed(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, true);
		if (inputValue == HeroInputKey.JUMP)
		{
			if (heroStatesProperties.jump != null)
			{
				jump();
			}
		}
		if (inputValue == HeroInputKey.ROPE)
		{
			if (heroStatesProperties.rope != null)
			{
				rope();
			}
		}
		stateInputPressed(inputValue);
	}
	
	@Override
	public final void isPressing(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.SHOOT)
		{
			System.out.println("Aiming!");
		}			
		if (inputValue == HeroInputKey.LEFT)
		{
			if (heroStatesProperties.move != null)
			{
				move(Side.LEFT);
			}
		}
		else if (inputValue == HeroInputKey.RIGHT)
		{
			if (heroStatesProperties.move != null)
			{
				move(Side.RIGHT);
			}
		}
		
		stateInputIsPressing(inputValue);
	}

	protected void move(Side side)
	{
		if (!gameHero.isTouchingWallAhead())
		{
			gameHero.addAction(new MoveHeroAction(side, heroStatesProperties));
		}
	}

	@Override
	public final void inputReleased(HeroInputKey inputValue)
	{
		inputMap.put(inputValue, false);
		if (inputValue == HeroInputKey.SHOOT)
		{
			shoot();
		}		
		stateInputReleased(inputValue);
	}

	@Override
	public final void update(float deltaTime)
	{
		stateUpdate(deltaTime);
	}

	protected final void setState(HeroState heroState)
	{
		gameHero.addAction(new HeroStateSetterAction(heroState));
	}

	public final Vector2 getMaxSpeed()
	{
		return heroStatesProperties.maxSpeed;
	}

	protected final Vec2 pointingDirection()
	{
		Vec2 retorno = new Vec2();

		if (inputMap.get(HeroInputKey.UP))
		{
			retorno.y = -1;
		}
		else if (inputMap.get(HeroInputKey.DOWN))
		{
			retorno.y = 1;
		}

		if (inputMap.get(HeroInputKey.RIGHT))
		{
			retorno.x = 1;
		}
		else if (inputMap.get(HeroInputKey.LEFT))
		{
			retorno.x = -1;
		}

		if (retorno.length() == 0) // Default
		{
			retorno.x = gameHero.getForwardSide().getHorizontalValue();
		}
		return retorno;
	}

	public Map<HeroInputKey, Boolean> getInputMap()
	{
		return inputMap;
	}

	public void setInputMap(Map<HeroInputKey, Boolean> inputMap)
	{
		this.inputMap = inputMap;
	}

}
