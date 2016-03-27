package br.com.guigasgame.round.hud.fix;

import java.io.IOException;
import java.nio.file.Paths;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Text;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.frag.HeroFragCounter;
import br.com.guigasgame.frag.HeroFragCounter.HeroFragCounterListener;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.round.hud.controller.HudObject;

public class HeroFragCounterHud extends HudObject implements HeroFragCounterListener
{
	
	private Text text;
	private final HeroFragCounter fragCounter;
	private final Vector2f positionRatio;
	
	public HeroFragCounterHud(Vector2f positionRatio, PlayableGameHero gameHero)
	{
		this.positionRatio = positionRatio;
		this.fragCounter = gameHero.getFragCounter();
		try
		{
			this.text = new Text();
			Font font = new Font();
			font.loadFromFile(Paths.get(FilenameConstants.getCounterFontFilename()));
			text.setColor(gameHero.getHeroProperties().getColor().makeTranslucid(0.2f).getSfmlColor());
			text.setFont(font);
//			outlineText.setStyle(Text.BOLD);
			updateText();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void setViewSize(Vector2i size)
	{
		text.setPosition(size.x*positionRatio.x,
				size.y*positionRatio.y);

		text.setCharacterSize(size.x/50);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.draw(text);		
	}
	
	@Override
	public void onKillIncrement(int kills)
	{
		updateText();
	}

	@Override
	public void onShootIncrement(int shoots)
	{
		updateText();
	}

	@Override
	public void onDeathIncrement(int deaths)
	{
		updateText();
	}
	
	private void updateText()
	{
		final String newString = String.format("(%d)%02d/%02d", fragCounter.getShoots(), fragCounter.getKills(), fragCounter.getDeaths());
		text.setString(newString);
		text.setOrigin(text.getLocalBounds().width/2, text.getLocalBounds().height/2);
	}
}
