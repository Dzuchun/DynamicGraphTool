package com.dzuchun.graphing;

import java.util.Vector;
import java.util.ListIterator;

public class TwoDimedPointData extends AbstractData
{
	private Vector<TwoDimedPointResult> results;
	public TwoDimedPointData()
	{
		this.results = new Vector<TwoDimedPointResult>(0);
	}
	public void addResult(TwoDimedPointResult res)
	{
		this.results.add(res.copy());
	}
	@SuppressWarnings("unchecked")
	public TwoDimedPointData (Vector<TwoDimedPointResult> src)
	{
		this.results = (Vector<TwoDimedPointResult>)src.clone();
	}
	private ListIterator<TwoDimedPointResult> resultIterator;
	@Override
	public void startReading() 
	{
		this.resultIterator = this.results.listIterator();
	}
	@Override
	public TwoDimedPointResult getNext() 
	{
		return(resultIterator.next().copy());
	}
	@Override
	public boolean hasNext() 
	{
		return(this.resultIterator.hasNext());
	}

}
