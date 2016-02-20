package br.com.guigasgame.input;

public interface InputListener<T>
{

	public default void inputPressed(T inputValue)
	{

	}

	public default void inputReleased(T inputValue)
	{

	}

	public default void isPressing(T inputValue)
	{

	}
	
	public default void doubleTapInput(T inputValue)
	{

	}

}
