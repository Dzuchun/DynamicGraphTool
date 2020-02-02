package com.dzuchun.graphing;

import com.dzuchun.math.graph.GraphInstance;

public class MathGraphData extends AbstractData 
{
	private MathGraphResult graph;
	public MathGraphData(GraphInstance graph) 
	{
		this.graph = new MathGraphResult(graph);
	}
	@Override
	public void startReading()	{	}

	@Override
	public AbstractResult getNext() 
	{
		return graph;
	}

	@Override
	public boolean hasNext() 
	{
		return false;
	}

}
