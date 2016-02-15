package br.com.guigasgame.gameobject.projectile;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.gameobject.hero.GameHero;


public class RopeHook extends Collidable
{
	private GameHero gameHero;
	private DistanceJoint joint;

	public RopeHook(Vec2 position, GameHero gameHero)
	{
		super(position);

		this.gameHero = gameHero;

		bodyDef.fixedRotation = true;
		bodyDef.type = BodyType.STATIC;
		bodyDef.bullet = true;

	}

	@Override
	public void attachToWorld(World world)
	{
		super.attachToWorld(world);
		createJoint(world);
	}

	public void removeJoint()
	{
		joint.getBodyA().getWorld().destroyJoint(joint);
	}

	private void createJoint(World world)
	{
		DistanceJointDef distDef = new DistanceJointDef();

		distDef.bodyA = gameHero.getCollidable().getBody();
		distDef.bodyB = body;
		distDef.collideConnected = false;
		distDef.length = body.getPosition().sub(gameHero.getCollidable().getBody().getPosition()).length();

		joint = (DistanceJoint) world.createJoint(distDef);
	}

	public void enshort(float value)
	{
		joint.setLength(joint.getLength() - value);
	}

	public void enlarge(float value)
	{
		joint.setLength(joint.getLength() + value);
	}
	
	public DistanceJoint getJoint()
	{
		return joint;
	}

}