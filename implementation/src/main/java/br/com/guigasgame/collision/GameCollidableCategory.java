package br.com.guigasgame.collision;

import java.util.ArrayList;
import java.util.List;

///What I am
public enum GameCollidableCategory
{
	SCENERY(	getNextCategory(), 			new CollidableFilter().addCollisionWithEveryThing()),
	ROPE_NODE(	getNextCategory(), 			new CollidableFilter().addCollisionWith(SCENERY)),
	ROPE_BODY(	getNextCategory(), 			new CollidableFilter().addCollisionWith(SCENERY)),
	SHURIKEN(	getNextCategory(), 			new CollidableFilter().addCollisionWithEveryThing()),
	SMOKE_BOMB(	getNextCategory(), 			new CollidableFilter().addCollisionWith(SCENERY)),
	HEROS(		getAllPlayersCategory(),	new CollidableFilter().addCollisionWith(SCENERY).and(SHURIKEN));

	private final static int NUM_MAX_PLAYERS = 8;
	private static int categoriesUsed = 0;

	final CollidableFilter filter;

	private GameCollidableCategory(IntegerMask categoryMask, CollidableFilter filter)
	{
		this.filter = filter;
		filter.setCategory(categoryMask);
	}

	public CollidableFilter getFilter()
	{
		return filter;
	}

	public IntegerMask getCategoryMask()
	{
		return filter.getCategory();
	}

	public static List<GameCollidableCategory> fromMask(int value)
	{
		List<GameCollidableCategory> retorno = new ArrayList<>();
		for( GameCollidableCategory category : GameCollidableCategory.values() )
		{
			if (category.getCategoryMask().matches(value))
				retorno.add(category);
		}
		return retorno;
	}

	public static CollidableFilter getPlayerFilter(int playerID)
	{
		return new CollidableFilter(getPlayerCategory(playerID), HEROS.filter.collider);
	}

	public static IntegerMask getPlayerCategory(int playerID)
	{
		if (playerID > NUM_MAX_PLAYERS - 1)
			return null;
		int valueToMatch = 1 << (playerID);
		return new IntegerMask(HEROS.filter.getCategory().and(valueToMatch).value);
	}

	public static IntegerMask getOtherPlayersCategory(int playerID)
	{
		if (playerID > NUM_MAX_PLAYERS - 1)
			return null;
		return getAllPlayersCategory().clear(getPlayerCategory(playerID).value);
	}

	public static IntegerMask getAllPlayersCategory()
	{
		int playersMask = 1;
		IntegerMask retorno = new IntegerMask();
		for( int i = 0; i < NUM_MAX_PLAYERS; ++i )
		{
			retorno = retorno.set(playersMask);
			playersMask = (playersMask << 1);
		}
		return retorno;
	}

	private static IntegerMask getNextCategory()
	{
		IntegerMask retorno = new IntegerMask((1 << categoriesUsed) << NUM_MAX_PLAYERS);
		++categoriesUsed;
		return retorno;
	}

	public static void display()
	{
		for( GameCollidableCategory category : GameCollidableCategory.values() )
		{
			String msg = "Category " + category.name() + ":\t";
			List<GameCollidableCategory> categoryList = GameCollidableCategory.fromMask(category.filter.collider.value);
			for( GameCollidableCategory gameCollidableCategory : categoryList )
			{
				msg += gameCollidableCategory.name() + "; ";
			}

			System.out.println(msg);
		}
	}

}
