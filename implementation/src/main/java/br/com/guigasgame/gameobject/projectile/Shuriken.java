package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableConstants;
import br.com.guigasgame.collision.CollidableFilter;
import br.com.guigasgame.collision.IntegerMask;
import br.com.guigasgame.gameobject.hero.GameHero;

public class Shuriken extends Projectile
{
	
	private int collisionCounter;
	private GameHero owner;
	private IntegerMask targetMask;

	public Shuriken(Vec2 direction, IntegerMask targetCategory, GameHero gameHero)
	{
		super(ProjectileIndex.SHURIKEN, direction, gameHero.getCollidableHero().getBody().getWorldCenter());
		owner = gameHero;
		collisionCounter = 0;
		targetMask = gameHero.getEnemiesMask();
	}

	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body otherBody = (Body) other;
		if (otherBody.getUserData() != null)
		{
			System.out.println("Hit player!");
			markToDestroy();
		}
		++collisionCounter;
		if (collisionCounter >= properties.numBounces)
		{
			markToDestroy();
		}
	}
	
	@Override
	public void onEnter()
	{
		shoot();
	}
	
	@Override
	protected IntegerMask editTarget(IntegerMask target) 
	{
		return targetMask;
	}

	@Override
	protected CollidableFilter createCollidableFilter()
	{
		// shuriken doesn't collides with owner hero
		collidableFilter = (CollidableConstants.getShurikenCollidableFilter().removeCollisionWith(CollidableConstants.getPlayerCategory(owner.getPlayerID())));
		
		return collidableFilter;
	}

	
}
