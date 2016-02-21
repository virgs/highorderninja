package br.com.guigasgame.gameobject.hero.sensors;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableContactListener;


public class SensorController implements CollidableContactListener
{

	private int touchingContacts;

	@Override
	public void endContact(Collidable collidable)
	{
		--touchingContacts;
	}

	@Override
	public void beginContact(Collidable collidable)
	{
		++touchingContacts;
	}

	public boolean isTouching()
	{
		return touchingContacts > 0;
	}
}
