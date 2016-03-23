package br.com.guigasgame.gameobject.hero.state;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum HeroStateIndex
{
	HERO_STANDING,
	HERO_RUNNING,
	HERO_ASCENDING,
	HERO_FALLING,
	HERO_WALLRIDING,
	HERO_WALLGRABBING,
	HERO_SLIDING,
	HERO_SUPERJUMP,
	HERO_SUPER_RUNNING,
	
	HERO_DUCKING,
	HERO_DIVING,
	HERO_STOP,
	HERO_ROPE, 
	
	HERO_AIR_SPIN,
	HERO_BACKFLIPPING, 
	HERO_ROPE_SHOOTING,
	
	HERO_DEAD
}