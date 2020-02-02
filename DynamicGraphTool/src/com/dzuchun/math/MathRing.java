package com.dzuchun.math;

public interface MathRing 
{
	public MathRing add(MathRing addition) throws IllegalArgumentException;
	public MathRing multiply(MathRing multiplier) throws IllegalArgumentException;
}
