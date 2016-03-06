package br.com.guigasgame.gameobject.item.life;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.contacts.Contact;

import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.gameobject.item.GameItem;
import br.com.guigasgame.gameobject.item.GameItemIndex;

public class LifeItem extends GameItem
{

	public LifeItem(Vec2 position)
	{
		super(GameItemIndex.LIFE, position);
	}
	
	@Override
	public void beginContact(Object me, Object other, Contact contact)
	{
		Body otherBody = (Body) other;
		if (otherBody.getUserData() != null)
		{
			List<CollidableCategory> categoryList = CollidableCategory.fromMask(otherBody.getFixtureList().getFilterData().categoryBits);
			for( CollidableCategory category : categoryList )
			{
				System.out.println("Life collided with: " + category.name());
			}
		}
	}


}
