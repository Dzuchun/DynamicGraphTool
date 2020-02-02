package com.dzuchun.graphing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

public class TwoDimedPointResultGrapher extends AbstractResultGrapher 
{
	public TwoDimedPointResultGrapher(Dimension size, boolean resizable, Vector<AbstractData> data, Dimension lowerCornerPosition, Dimension higherCornerPosition) 
	{
		super(size, resizable, data, lowerCornerPosition, higherCornerPosition);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1014623720918343495L;

	@Override
	public void drawData(Canvas base, Graphics g)
	{
		TwoDimedPointData data;
		TwoDimedPointResult prevResult;
		TwoDimedPointResult currentResult;
		for (int i = 0; i<this.dataArray.size(); i++)
		{
			g.setColor(this.getColorFor(i));
			data = (TwoDimedPointData)this.dataArray.get(i);
			data.startReading();
			prevResult = data.getNext();
			while (data.hasNext())
			{
				currentResult = data.getNext();
				g.drawLine(getXFor(prevResult, base), getYFor(prevResult, base), getXFor(currentResult, base), getYFor(currentResult, base));
				prevResult = currentResult;
			}
		}
	}
	private int getXFor(TwoDimedPointResult result, Canvas base)
	{
		return(int)(0 + base.getSize().getWidth() * (result.getX() - this.lowerCornerPosition.getWidth()) / (this.higherCornerPosition.getWidth() - this.lowerCornerPosition.getWidth()));
	}
	private int getYFor(TwoDimedPointResult result, Canvas base)
	{
		return(int)(base.getSize().getHeight() - 0 - base.getSize().getHeight() * (result.getY() - this.lowerCornerPosition.getHeight()) / (this.higherCornerPosition.getHeight() - this.lowerCornerPosition.getHeight()));
	}
	private Color getColorFor(int num)
	{
		return(new Color
		(
			(int)(127*Math.cos(2*Math.PI * num / this.dataArray.size())) + 128,
			(int)(127*Math.cos(2*Math.PI * num / this.dataArray.size() + 2 * Math.PI / 3)) + 128,
			(int)(127*Math.cos(2*Math.PI * num / this.dataArray.size() + 4 * Math.PI / 3)) + 128
		));
	}
}
