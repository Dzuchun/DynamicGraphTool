package com.dzuchun.graphing;

public abstract class AbstractData 
{
	public abstract void startReading();
	public abstract AbstractResult getNext();
	public abstract boolean hasNext();
}
