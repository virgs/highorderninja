package br.com.guigasgame.gameobject.hero.action;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.gameobject.hero.GameHero;
import br.com.guigasgame.gameobject.hero.state.HeroStateProperties;
import br.com.guigasgame.side.Side;


public class DiagonalJumpAction extends GameHeroAction
{

	Side side;

	public DiagonalJumpAction(HeroStateProperties heroStateProperties, Side side)
	{
		super(heroStateProperties);
		this.side = side;
	}
	
	@Override
	public boolean canExecute(GameHero hero)
	{
		return heroStateProperties.jump != null;
	}

	@Override
	public void execute(GameHero gameHero)
	{
		float impulse = heroStateProperties.jump.impulse;
		Vec2 jumpDirection = new Vec2(side.getHorizontalValue(), -2); //?
		jumpDirection.normalize();
		// Aponta para o local correto
		jumpDirection.mulLocal(impulse);

		gameHero.getCollidableHero().applyImpulse(jumpDirection);
	}

}
