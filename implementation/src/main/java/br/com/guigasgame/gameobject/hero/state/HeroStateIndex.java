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
	
	HERO_DUCKING,
	HERO_DIVING,
	HERO_STOPDIVING,
	HERO_ROPE
}