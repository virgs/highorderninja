package br.com.guigasgame.gameobject.hero.action;

import br.com.guigasgame.gameobject.hero.PlayableGameHero;


public class RegeneratesLifeAction extends GameHeroAction
{

	@Override
	protected void childExecute(PlayableGameHero hero)
	{
		hero.regeneratesLife();
	}

}
