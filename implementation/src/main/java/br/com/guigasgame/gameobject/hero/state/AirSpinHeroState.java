package br.com.guigasgame.gameobject.hero.state;

import org.jsfml.graphics.Color;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.action.SideImpulseAction;

public class AirSpinHeroState extends HeroState
{

	private float secondsRemaining;

	public AirSpinHeroState(GameHero gameHero)
	{
		super(gameHero, HeroStateIndex.HERO_AIR_SPIN);
		setAnimationsColor(Color.BLUE);
		
		Float duration = heroStatesProperties.property.get("duration");
		secondsRemaining = duration != null? duration.floatValue(): 0.5f;

	}
	
	@Override
	protected void stateOnEnter()
	{
		gameHero.addAction(new SideImpulseAction(heroStatesProperties, gameHero.getForwardSide().opposite()));
	}
	
	@Override
	public void stateUpdate(float deltaTime)
	{
		secondsRemaining -= deltaTime;
		if (secondsRemaining <= 0)
		{
			setState(new FallingHeroState(gameHero));
		}
	}

}