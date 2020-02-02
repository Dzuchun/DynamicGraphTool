package com.dzuchun.graphing;

import java.awt.Canvas;

import com.dzuchun.math.graph.GraphInstance;

public class MathGraphResult extends AbstractResult 
{
	private GraphInstance graph;
	public MathGraphResult(GraphInstance graph_) 
	{
		this.graph = graph_;
	}
	@Override
	public AbstractResult copy() 
	{
		return new MathGraphResult(this.graph.clone());
	}
	public GraphInstance getGraphOriginal()
	{
		return this.graph;
	}
	public GraphInstance getGraphCopy()
	{
		return this.graph.clone();
	}
}
