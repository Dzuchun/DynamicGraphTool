package com.dzuchun.graphing;

public class TwoDimedPointResult extends AbstractResult 
{
	private double x;
	private double y;
	public TwoDimedPointResult (double x, double y) 
	{
		this.x = new Double(x);
		this.y = new Double(y);
	}
	public double getX()
	{
		return this.x;
	}
	public double getY()
	{
		return this.y;
	}
	@Override
	public TwoDimedPointResult copy() 
	{
		return(new TwoDimedPointResult(this.x, this.y));
	}
}
