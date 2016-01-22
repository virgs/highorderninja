package br.com.guigasgame.gameobject.hero;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.Sprite;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.state.ForwardSide;
import br.com.guigasgame.gameobject.hero.state.ForwardSide.Side;


public class GameHero extends GameObject
{

	private final int playerID;

	ForwardSide forwardSide;
	GameHeroLogic gameHeroLogic;
	HeroPhysics physicHeroLogic;

	public GameHero(int playerID)
	{
		super();
		this.playerID = playerID;
		forwardSide = new ForwardSide(Side.LEFT);
		gameHeroLogic = new GameHeroLogic(this);
		physicHeroLogic = new HeroPhysics(this);
	}
	
	@Override
	public void load()
	{
		gameHeroLogic.load();
	}

	@Override
	public void unload()
	{
		gameHeroLogic.unload();
	}

	@Override
	public void update(float deltaTime)
	{
		gameHeroLogic.update(deltaTime);

		physicHeroLogic
				.checkSpeedLimits(gameHeroLogic.getState().getMaxSpeed());
		gameHeroLogic.adjustSpritePosition(WorldConstants
				.physicsToSfmlCoordinates(physicHeroLogic
						.getBodyPosition()), (float) WorldConstants
				.radiansToDegrees(physicHeroLogic.getAngleRadians()));
	}

	@Override
	public BodyDef getBodyDef(Vec2 position)
	{
		return physicHeroLogic.getBodyDef(position);
	}

	public void attachBody(World world, Vec2 bodyPosition)
	{
		body = world.createBody(getBodyDef(bodyPosition));
		physicHeroLogic.loadAndAttachFixturesToBody(body);
	}

	@Override
	public Sprite getSprite()
	{
		return gameHeroLogic.getAnimation().getSprite();
	}

	public void flipSide()
	{
		forwardSide.flip();
	}

	public Side getForwardSide()
	{
		return forwardSide.getSide();
	}

	public void applyImpulse(Vec2 impulse)
	{
		physicHeroLogic.applyImpulse(impulse);
	}

	public void applyForce(Vec2 force)
	{
		physicHeroLogic.applyForce(force);
	}

	public GameHeroLogic getGameHeroLogic()
	{
		return gameHeroLogic;
	}

	public HeroPhysics getPhysicHeroLogic()
	{
		return physicHeroLogic;
	}

	public int getPlayerID()
	{
		return playerID;
	}

}