package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Joystick;
import org.jsfml.window.Keyboard.Key;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

import br.com.guigasgame.box2d.debug.SFMLDebugDraw;
import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.camera.CameraController;
import br.com.guigasgame.collision.CollidableCategory;
import br.com.guigasgame.collision.CollisionManager;
import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.frag.HeroFragCounter;
import br.com.guigasgame.gameobject.GameObject;
import br.com.guigasgame.gameobject.hero.playable.PlayableGameHero;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.gameobject.item.GameItemCreationController;
import br.com.guigasgame.round.RoundAttributes;
import br.com.guigasgame.round.hud.controller.HudController;
import br.com.guigasgame.round.hud.dynamic.heroattributes.HeroAttributesHudController;
import br.com.guigasgame.round.hud.fix.HeroFragCounterHud;
import br.com.guigasgame.round.hud.fix.TimerStaticHud;
import br.com.guigasgame.scenery.SceneController;
import br.com.guigasgame.team.HeroTeam;
import br.com.guigasgame.time.ReverseTimeCounter;


public class RoundGameState implements GameState
{

	private float timeFactor;
	private final World world;
	private final List<GameObject> gameObjectsList;
	private final GameItemCreationController gameItemCreator;
	private final SceneController scenery;
	private final CameraController cameraController;
	private final ColorBlender backgroundColor;
	private final HudController hudController;
	private final ReverseTimeCounter reverseTimeCounter;

	public RoundGameState(RoundAttributes roundAttributes)
	{
		CollidableCategory.display();
		gameObjectsList = new ArrayList<>();
		this.scenery = new SceneController(roundAttributes.getSceneryInitializer());
		timeFactor = 1;

		Vec2 gravity = new Vec2(0, (float) 9.8);
		world = new World(gravity);
		world.setContactListener(new CollisionManager());
		gameItemCreator = new GameItemCreationController(scenery);
		this.backgroundColor = scenery.getBackgroundColor();
		reverseTimeCounter = new ReverseTimeCounter(roundAttributes.getTime());
		
		scenery.attachToWorld(world);
		scenery.onEnter();
		hudController = new HudController(roundAttributes.getHudPositioner());
		cameraController = new CameraController();
		initializeHeros(scenery, roundAttributes);
	}
	
	private void initializeHeros(SceneController scenery, RoundAttributes roundAttributes)
	{
		for( HeroTeam team : roundAttributes.getTeams() )
		{
			team.setFriendlyFire(true);
			List<PlayableHeroDefinition> heros = team.getHerosList();
			for (PlayableHeroDefinition gameHeroProperties : heros) 
			{
				gameHeroProperties.setSpawnPosition(WorldConstants.sfmlToPhysicsCoordinates(scenery.popRandomSpawnPoint()));
				gameHeroProperties.setHeroAttributes(roundAttributes.getHeroAttributes().clone());
				PlayableGameHero gameHero = new PlayableGameHero(gameHeroProperties);

				HeroFragCounter fragCounter = gameHero.getFragCounter();				
				Vector2f position = roundAttributes.getHudPositioner().getFragCounterPosition(gameHeroProperties);
				HeroFragCounterHud fragCounterHud = new HeroFragCounterHud(position, fragCounter, gameHero.getHeroProperties().getColor());
				fragCounter.addListener(fragCounterHud);
				HeroAttributesHudController hud = roundAttributes.initializeHeroAttributes(gameHero); 
				hud.addAsHudController(gameHeroProperties.getRoundHeroAttributes());
				hudController.addDynamicHud(hud);
				hudController.addStaticHud(fragCounterHud);
				
				
				
				initializeGameObject(Arrays.asList(gameHero));
				cameraController.addPlayerToControl(gameHero);
			}
		}
	}

	@Override
	public void load()
	{
	}

	@Override
	public void enterState(RenderWindow renderWindow)
	{
		for ( int i = 0; i < 4; ++i)
			System.out.println("Joystick ("+i+") is conected: " +Joystick.isConnected(i));
		
		SFMLDebugDraw sfmlDebugDraw = new SFMLDebugDraw(new OBBViewportTransform(), renderWindow);
		world.setDebugDraw(sfmlDebugDraw);
		// sfmlDebugDraw.appendFlags(DebugDraw.e_aabbBit);
		// sfmlDebugDraw.appendFlags(DebugDraw.e_centerOfMassBit);
		// sfmlDebugDraw.appendFlags(DebugDraw.e_dynamicTreeBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_jointBit);
//		 sfmlDebugDraw.appendFlags(DebugDraw.e_pairBit);
		sfmlDebugDraw.appendFlags(DebugDraw.e_shapeBit);
        cameraController.setViewSize(renderWindow.getSize());
        setHudUp(renderWindow.getSize());
	}
	
	private void setHudUp(Vector2i windowSize)
	{
		hudController.setViewSize(windowSize);
		TimerStaticHud timerStaticHud = new TimerStaticHud(new Vector2f(windowSize.x/2, 10));
		reverseTimeCounter.addListener(timerStaticHud);
		hudController.addStaticHud(timerStaticHud);
	}


	@Override
	public void handleEvent(Event event, RenderWindow renderWindow)
	{
		//Axis.Z R2/L2
		//Axis.U R3x
		//Axis.R R3y

//		for ( int i = 0; i < 4; ++i)
//		{
//			if (Joystick.isConnected(i))
//			{
//				for( Axis axis : Axis.values() )
//				{
//					if (Joystick.getAxisPosition(i, axis) > 60 || Joystick.getAxisPosition(i, axis) < -60)
//					{
//						if (axis != Axis.V)
//							System.out.println("Joys(" + i + "): " + axis.toString() + " -> " + (Joystick.getAxisPosition(i, axis) > 0 ? 1 : -1));
//					}
//					
//				}
//				
//			}
//		}

		if (event.type == Type.KEY_PRESSED)
		{
			if (event.asKeyEvent().key == Key.I)
			{
				timeFactor = 0.3f;
			}
			if (event.asKeyEvent().key == Key.O)
			{
				timeFactor = 1f;
			}
			if (event.asKeyEvent().key == Key.P)
			{
				timeFactor = 2;
			}
		}
		
		// catch the resize events
	    if (event.type == Type.RESIZED)
	    {
	        // update the view to the new size of the window
	        FloatRect visibleArea = new FloatRect(0, 0, event.asSizeEvent().size.x, event.asSizeEvent().size.y);
	        renderWindow.setView(new View(visibleArea));
	        cameraController.setViewSize(renderWindow.getSize());
	        hudController.setViewSize(renderWindow.getSize());
	    }
	}

	private void verifyNewObjectsToLists()
	{
		initializeGameObject(gameItemCreator.checkReproduction());
		List<GameObject> objsToAdd = new ArrayList<>();
		objsToAdd.addAll(gameObjectsList);
		for( GameObject gameObject : objsToAdd )
		{
			initializeGameObject(gameObject.reproduce());
		}
	}

	private void initializeGameObject(Collection<? extends GameObject> collection)
	{
		for( GameObject child : collection )
		{
			child.attachToWorld(world);
			child.onEnter();
			gameObjectsList.add(child);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		float updateTime = deltaTime * timeFactor;
		updateObjects(updateTime);
	}

	private void updateObjects(float updateTime)
	{
		// float deltaTime = timeMaster.getElapsedTime().asSeconds();
		world.step(updateTime, 8, 3);
		world.clearForces();
		
		scenery.update(updateTime);
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.update(updateTime);
		}
		gameItemCreator.update(updateTime);

		
		cameraController.update(updateTime);
		reverseTimeCounter.update(updateTime);
		hudController.update(updateTime);
		verifyNewObjectsToLists();
		checkGameOjbectsAgainsSceneryBoundaries();
		Destroyable.clearDestroyable(gameObjectsList);
	}

	private void checkGameOjbectsAgainsSceneryBoundaries()
	{
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.checkAgainstSceneryBoundaries(scenery.getBoundaries());
		}
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		renderWindow.setView(cameraController.getCameraView());

		scenery.drawBackgroundItems(renderWindow);
		world.drawDebugData();
		
//		cameraController.draw(renderWindow);

		scenery.draw(renderWindow);
		for( GameObject gameObject : gameObjectsList )
		{
			gameObject.draw(renderWindow);
		}
		
		scenery.drawForegroundItems(renderWindow);
		
		hudController.drawDynamicHud(renderWindow);
		renderWindow.setView(hudController.getView());
		hudController.drawStaticHud(renderWindow);
	}

	@Override
	public ColorBlender getBackgroundColor()
	{
		return backgroundColor;
	}
	
	
}
