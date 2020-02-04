package com.dzuchun.graphing;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;

import com.dzuchun.math.graph.GraphInstance;
import com.dzuchun.math.graph.GraphLink;
import com.dzuchun.math.graph.GraphPoint;

public class MathGraphResultGrapher extends AbstractResultGrapher 
{
	private static final long serialVersionUID = 1L;
	protected static final boolean POINT_STYLE_SWICH = true;
	protected static int pointBorderWidth = 2;
	protected static Color pointColor = Color.BLACK;
	protected static Color pointBackGroundColor = Color.GREEN;
	protected static Color pointColorHigh = Color.CYAN;
	protected static int pointRadius = 10;
	protected static Color linkColorHigh = Color.BLUE;
	protected static Color linkColor = Color.DARK_GRAY;
	/**
	 * !!UNRELEASED!!
	 */
	protected static int linkWidth = 1;
	protected GraphInstance graph;
	public MathGraphResultGrapher(Dimension size, boolean resizable, Vector<AbstractData> dataArray,
			Dimension lowerCornerPosition, Dimension higherCornerPosition) 
	{
		super(size, resizable, dataArray, lowerCornerPosition, higherCornerPosition);
		this.graph = ((MathGraphResult)(this.dataArray.get(0).getNext())).getGraphOriginal();
		//this.redefineCorners();
	}
	private int getXFor(int result, Canvas base)
	{
		return(int)(0 + base.getSize().getWidth() * (result - this.lowerCornerPosition.getWidth()) / (this.higherCornerPosition.getWidth() - this.lowerCornerPosition.getWidth()));
	}
	private int getYFor(int result, Canvas base)
	{
		return(int)(base.getSize().getHeight() - 0 - base.getSize().getHeight() * (result - this.lowerCornerPosition.getHeight()) / (this.higherCornerPosition.getHeight() - this.lowerCornerPosition.getHeight()));
	}
	@Override
	public void drawData(Canvas base, Graphics g) 
	{
		g.setColor(linkColor);
		for (GraphLink link : this.graph.getLinks())
		{
			g.drawLine
			(
					getXFor(link.getBegin().x().intValue(), base),
					getYFor(link.getBegin().y().intValue(), base),
					getXFor(link.getEnd().x().intValue(), base),
					getYFor(link.getEnd().y().intValue(), base)
			);
		}
		g.setColor(pointColor);
		for (GraphPoint point : this.graph.getPoints())
		{
			
			if (POINT_STYLE_SWICH)
			{
				g.fillOval
				(
						getXFor(point.x().intValue(), base) - pointRadius - pointBorderWidth,
						getYFor(point.y().intValue(), base) - pointRadius - pointBorderWidth,
						2*pointRadius + 2 * pointBorderWidth,
						2*pointRadius + 2 * pointBorderWidth
				);
			}
			else
			{
				g.fillOval
				(
						getXFor(point.x().intValue(), base) - pointRadius/2,
						getYFor(point.y().intValue(), base) - pointRadius/2,
						pointRadius,
						pointRadius
				);
			}
		}
		if (POINT_STYLE_SWICH)
		{
			g.setColor(pointBackGroundColor);
			for (GraphPoint point : this.graph.getPoints())
			{
				g.fillOval
				(
						getXFor(point.x().intValue(), base) - pointRadius,
						getYFor(point.y().intValue(), base) - pointRadius,
						2*pointRadius,
						2*pointRadius
				);	
			}
		}
	}
	public void redefineCorners()
	{
		Vector<GraphPoint> points = this.graph.getPoints();
		int lx = 0, ly = 0, hx = 0, hy = 0;
		Iterator<GraphPoint> iter = points.iterator();
		if (!iter.hasNext())
		{
			lx-=pointRadius;
			ly-=pointRadius;
			hx+=pointRadius;
			hy+=pointRadius;
			this.lowerCornerPosition = new Dimension(lx, hy);
			this.higherCornerPosition = new Dimension(hx, ly);
			return;
		}
		GraphPoint current = iter.next();
		hx = current.x().intValue();
		lx = current.x().intValue();
		hy = current.y().intValue();
		ly =current.y().intValue();
		if (!iter.hasNext())
		{
			lx-=pointRadius;
			ly-=pointRadius;
			hx+=pointRadius;
			hy+=pointRadius;
			this.lowerCornerPosition = new Dimension(lx, hy);
			this.higherCornerPosition = new Dimension(hx, ly);
			return;
		}
		int cx, cy;
		for(current = iter.next(); iter.hasNext();current=iter.next())
		{
			cx = current.x().intValue();
			cy = current.y().intValue();
			if (cx < lx)
			{
				lx=cx;
			}
			else if (cx>hx)
			{
				hx=cx;
			}
			if (cy < ly)
			{
				ly=cy;
			}
			else if (cy>hy)
			{
				hy=cy;
			}
		}
		lx-=pointRadius;
		ly-=pointRadius;
		hx+=pointRadius;
		hy+=pointRadius;
		this.lowerCornerPosition = new Dimension(lx, hy);
		this.higherCornerPosition = new Dimension(hx, ly);
		log.log(Level.FINER, "Corners set: lower {0}, higher {1}", new Object[]{this.lowerCornerPosition, this.higherCornerPosition});
		return;
	}
}
