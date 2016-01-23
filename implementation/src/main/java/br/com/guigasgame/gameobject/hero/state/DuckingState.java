package br.com.guigasgame.gameobject.hero.state;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.animation.Animation;
import br.com.guigasgame.animation.AnimationsRepositoryCentral;
import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.sensors.HeroSensorsController.FixtureSensorID;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputKey;

public class DuckingState extends OnGroundState {

	protected DuckingState(GameHero gameHero) {
		super(new Vec2(0,0), false, false, Animation.createAnimation(AnimationsRepositoryCentral
				.getHeroAnimationRepository().getAnimationsProperties(HeroAnimationsIndex.HERO_STANDING)),
				gameHero, 0,
				30);
	}
	
	@Override
	public void onEnter() {
		gameHero.disableCollision(FixtureSensorID.HEAD);
	}

	@Override
	public void onQuit()
	{
		gameHero.enableCollision(FixtureSensorID.HEAD);
	}
	
	@Override
	public void inputReleased(HeroInputKey inputValue) {
		if (inputValue == HeroInputKey.DOWN)
		{
			setState(new StandingState(gameHero));
		}
	}

	@Override
	public void inputPressed(HeroInputKey inputValue)
	{
		if (inputValue == HeroInputKey.JUMP)
		{
			setState(new SuperJumpingHeroState(gameHero));
		}
	}
}
