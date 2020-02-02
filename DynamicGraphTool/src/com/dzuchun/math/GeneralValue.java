package com.dzuchun.math;
/**
 * 
 * @author dzuchun
 * Ingeneral realization, will be rewritten.
 */
public class GeneralValue implements MathRing
{
	private int intValue;
	private double doubleValue;
	private long longValue;

	private boolean intCalculated;
	private boolean doubleCalculated;
	private boolean longCalculated;
	
	public GeneralValue setValue(int a)
	{
		this.intValue = a;
		this.intCalculated = true;
		this.doubleCalculated = false;
		this.longCalculated = false;
		return this;
	}
	public GeneralValue setValue(long a)
	{
		this.longValue = a;
		this.longCalculated = true;
		this.doubleCalculated = false;
		this.intCalculated = false;
		return this;
	}
	public GeneralValue setValue(double a)
	{
		this.doubleValue = a;
		this.doubleCalculated = true;
		this.intCalculated = false;
		this.longCalculated = false;
		return this;
	}
	
	public Integer intValue()
	{
		if (this.intCalculated)
		{
			return this.intValue;
		}
		if (this.doubleCalculated)
		{
			this.intValue = (int)this.doubleValue;
			this.intCalculated = true;
			return this.intValue();
		}
		if (this.longCalculated)
		{
			this.intValue = (int)this.longValue;
			this.intCalculated=true;
			return this.intValue();
		}
		return null;
	}
	public Long longValue()
	{
		if (this.longCalculated)
		{
			return this.longValue();
		}
		if (this.intCalculated)
		{
			this.longValue = (long)this.intValue;
			this.longCalculated = true;
			return this.longValue;
		}
		if (this.doubleCalculated)
		{
			this.longValue = (long)this.doubleValue;
			this.longCalculated = true;
			return this.longValue();
		}
		return null;
	}
	public Double doubleValue()
	{
		if (this.doubleCalculated)
		{
			return this.doubleValue;
		}
		if (this.intCalculated)
		{
			this.doubleValue = (double)this.intValue;
			this.doubleCalculated = true;
			return this.doubleValue;
		}
		if (this.longCalculated)
		{
			this.doubleValue = (double)this.longValue;
			this.doubleCalculated = true;
			return this.doubleValue;
		}
		return null;
	}
	
	public GeneralValue add(MathRing addition) throws IllegalArgumentException
	{
		this.setValue((this.doubleValue + ((GeneralValue)addition).doubleValue));
		return this;
	}
	
	public GeneralValue multiply(MathRing multiplier) throws IllegalArgumentException
	{
		this.setValue((this.doubleValue * ((GeneralValue)multiplier).doubleValue));
		return this;
	}
	
	public GeneralValue clone()
	{
		return (new GeneralValue()).setValue(this.doubleValue());
	}
}
