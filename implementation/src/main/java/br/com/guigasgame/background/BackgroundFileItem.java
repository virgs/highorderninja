package br.com.guigasgame.background;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import br.com.guigasgame.animation.AnimationProperties;
import br.com.guigasgame.shape.Point;

@XmlAccessorType(XmlAccessType.NONE)
public class BackgroundFileItem
{
	@XmlElement
	private AnimationProperties animationProperties;
	
	@XmlElement
	private Point speed;
	
	@XmlElement
	private Point origin;
	
	@XmlElement
	private float distanceFromCamera;

	@XmlAttribute
	private boolean regenerate;

	public BackgroundFileItem(AnimationProperties animationProperties, Point speed, Point origin, float distanceFromCamera, boolean regenerate)
	{
		this.animationProperties = animationProperties;
		this.speed = speed;
		this.origin = origin;
		this.distanceFromCamera = distanceFromCamera;
		this.regenerate = regenerate;
	}

	public BackgroundFileItem()
	{
	}

	
	public AnimationProperties getAnimationProperties()
	{
		return animationProperties;
	}

	
	public void setAnimation(AnimationProperties animationProperties)
	{
		this.animationProperties = animationProperties;
	}

	
	public Point getSpeed()
	{
		return speed;
	}

	
	public void setSpeed(Point speed)
	{
		this.speed = speed;
	}

	
	public Point getOrigin()
	{
		return origin;
	}

	
	public void setOrigin(Point origin)
	{
		this.origin = origin;
	}

	
	public float getDistanceFromCamera()
	{
		return distanceFromCamera;
	}

	
	public void setDistanceFromCamera(float distanceFromCamera)
	{
		this.distanceFromCamera = distanceFromCamera;
	}


	public boolean isRegenerate()
	{
		return regenerate;
	}

	
	public void setRegenerate(boolean regenerate)
	{
		this.regenerate = regenerate;
	}
	
	
}