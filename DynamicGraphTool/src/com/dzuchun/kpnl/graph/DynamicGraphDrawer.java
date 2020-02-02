package com.dzuchun.kpnl.graph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JLabel;

import com.dzuchun.graphing.AbstractData;
import com.dzuchun.graphing.MathGraphResultGrapher;
import com.dzuchun.math.GeneralValue;
import com.dzuchun.math.graph.GraphInstance;
import com.dzuchun.math.graph.GraphLink;
import com.dzuchun.math.graph.GraphPoint;

public class DynamicGraphDrawer extends MathGraphResultGrapher 
{
	private static final long serialVersionUID = 1L;
	protected static int labelSize = 10;
	private LinkageMatrixFrame linkageMatrix;
	private GraphSearchFrame searchFrame;
	public DynamicGraphDrawer(Dimension size, boolean resizable, Vector<AbstractData> dataArray,
			Dimension lowerCornerPosition, Dimension higherCornerPosition) 
	{
		super(size, resizable, dataArray, lowerCornerPosition, higherCornerPosition);
		this.setTitle("DGT (Dynamic Graph Tool)");
		this.linkageMatrix = new LinkageMatrixFrame(this);
		this.linkageMatrix.updateMatrix(this.graph, highlightedPoint);
		this.searchFrame = new GraphSearchFrame(this, this.graph);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	private int getXFor(int result, Canvas base)
	{
		return(int)(0 + base.getSize().getWidth() * (result - this.lowerCornerPosition.getWidth()) / (this.higherCornerPosition.getWidth() - this.lowerCornerPosition.getWidth()));
	}
	private int getYFor(int result, Canvas base)
	{
		return(int)(base.getSize().getHeight() - 0 - base.getSize().getHeight() * (result - this.lowerCornerPosition.getHeight()) / (this.higherCornerPosition.getHeight() - this.lowerCornerPosition.getHeight()));
	}
	
	
	private double transformationCoeffitientY()
	{
		return base.getSize().getHeight() / (this.higherCornerPosition.getHeight() - this.lowerCornerPosition.getHeight());
	}
	private double transformationCoeffitientX()
	{
		return base.getSize().getWidth() / (this.higherCornerPosition.getWidth() - this.lowerCornerPosition.getWidth());
	}
	
	
	private double transformBackY (int result)
	{
		return (-result+base.getSize().getHeight())/this.transformationCoeffitientY() + this.lowerCornerPosition.getHeight();
	}
	private double transformBackX(int result)
	{
		return result/this.transformationCoeffitientX() + this.lowerCornerPosition.getWidth();
	}
	GraphPoint highlightedPoint = null;
	private Vector<GraphLink> highlightedLinks = null;
	GraphLink highLightedWayLink = null;
	private static Color linkColorWay = Color.YELLOW;
	@Override
	public void drawData(Canvas base, Graphics g)
	{
		super.drawData(base, g);
		if (this.highlightedLinks != null)
		{
			g.setColor(linkColorHigh);
			for (GraphLink link : this.highlightedLinks)
			{
				g.drawLine
				(
						getXFor(link.getBegin().x().intValue(), base),
						getYFor(link.getBegin().y().intValue(), base),
						getXFor(link.getEnd().x().intValue(), base),
						getYFor(link.getEnd().y().intValue(), base)
				);
			}
		}
		for (GraphPoint point : this.graph.getPoints())
		{
			if (point.number != null)
			{
				this.labels.get(point.number).setLocation(point.x().intValue() + pointRadius/2, point.y().intValue() + pointRadius/2);
			}
		}
		if (this.highlightedPoint != null)
		{
			g.setColor(pointColorHigh);
			g.fillOval
			(
					getXFor(this.highlightedPoint.x().intValue(), base) - pointRadius/2,
					getYFor(this.highlightedPoint.y().intValue(), base) - pointRadius/2,
					pointRadius,
					pointRadius
			);
		}
		if (this.highLightedWayLink != null)
		{
			g.setColor(linkColorWay);
			g.drawLine
			(
					getXFor(highLightedWayLink.getBegin().x().intValue(), base),
					getYFor(highLightedWayLink.getBegin().y().intValue(), base),
					getXFor(highLightedWayLink.getEnd().x().intValue(), base),
					getYFor(highLightedWayLink.getEnd().y().intValue(), base)
			);
		}
	}
	private boolean actionPerformedFlag;
	private Vector<JLabel> labels = new Vector<JLabel>(0);
	private int getNextFreeNumber()
	{
		for (int i=0; i<this.labels.size(); i++)
		{
			if (this.labels.get(i) == null)
			{
				return i;
			}
		}
		return this.labels.size();
	}
	@Override
	protected void initCanvas(Canvas base)
	{
		base.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) 
			{
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) 
			{
				actionPerformedFlag = false;
				GraphPoint point = pointed(e.getX(), e.getY());
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					System.out.println("Event detected");
					if (point == null)
					{
						if (e.isShiftDown())
						{
							GraphPoint graphPoint = new GraphPoint((new GeneralValue()).setValue(transformBackX(e.getX())), (new GeneralValue()).setValue(transformBackY(e.getY())));
							graph.addPoint(graphPoint);
							highlightedPoint = graphPoint;
							actionPerformedFlag = true;
						}
						else if (e.isControlDown())
						{
							actionPerformedFlag = true;
						}
						else
						{
							highlightedPoint = null;
						}
					}
					else
					{
						if (highlightedPoint != null)
						{
							if (e.isControlDown())
							{
								actionPerformedFlag = true;
								if (!highlightedPoint.equals(point))
								{
									GraphLink link = new GraphLink(point, highlightedPoint);
									try
									{
										System.out.println("Trying to add link");
										graph.addLink(link);
									}
									catch (IllegalArgumentException ex)
									{
										System.out.println("Got error, removing...");
										graph.removeLink(link);
									}
								}
								else
								{
									graph.removePoint(point);
									if (point.number != null)
									{
										floor.remove(labels.get(point.number));
										labels.set(point.number, null);
										point.number = null;
									}
								}
							}
							else if (e.isShiftDown() &&(highlightedPoint.equals(point)))
							{
								graph.deLink(highlightedPoint);
								actionPerformedFlag = true;
							}
							else
							{
								highlightedPoint = point;
							}
						}
						else
						{
							highlightedPoint = point;
						}
					}
					if (!graph.hasPoint(highlightedPoint))
					{
						highlightedLinks = null;
						highlightedPoint = null;
					}
					else if (highlightedPoint != null)
					{
						highlightedLinks = graph.getLinksForPoint(highlightedPoint);
					}
					base.repaint(0);
					linkageMatrix.updateMatrix(graph, highlightedPoint);
					System.out.println("Event consumed");
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) 
			{
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				GraphPoint point = pointed(e.getX(), e.getY());
				if (e.getClickCount() == 2)
				{
					System.out.println("Double click detected");
					if ((highlightedPoint != null) && (point == highlightedPoint) && (point.number == null))
					{
						point.number = getNextFreeNumber();
						System.out.println("Next free - " + point.number);
						if (labels.size() >= point.number)
						{
							labels.setSize(labels.size() + 1);
						}
						JLabel label = new JLabel((point.number+1) + "");
						label.setSize(labelSize * (label.getText().length()), labelSize);
						label.setLocation(point.x().intValue() + pointRadius/2, point.y().intValue() + pointRadius/2);
						labels.set(point.number, label);
						floor.add(label, 0);
						linkageMatrix.updateMatrix(graph, highlightedPoint);
						System.out.println("Added label");
						floor.repaint(0);
					}
					else
					{
						if (highlightedPoint == null)
						{
							if (e.isControlDown() && e.isShiftDown())
							{
								graph = new GraphInstance();
								for (JLabel label : labels)
								{
									try
									{
										floor.remove(label);
									}
									catch (NullPointerException ex)
									{
										System.out.println("Dios?");
									}
								}
								labels.clear();
								highlightedPoint = null;
								highlightedLinks = null;
								linkageMatrix.updateMatrix(graph, highlightedPoint);
								base.repaint();
								return;
							}
							else
							{
								linkageMatrix.setLocation(e.getLocationOnScreen());
								linkageMatrix.updateMatrix(graph, highlightedPoint);
								linkageMatrix.setVisible(true);
							}
						}
					}
				}
			}
		});
		base.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) 
			{
			}
			
			@Override
			public void mouseDragged(MouseEvent e) 
			{
				//System.out.println("DragEvent detected, " + (e.getButton() == MouseEvent.BUTTON1) + ", " + (highlightedPoint != null));
				if ((highlightedPoint != null) && !actionPerformedFlag)
				{
					//System.out.println("Got inside");
					highlightedPoint.setTo((new GeneralValue()).setValue(transformBackX(e.getX())), (new GeneralValue()).setValue(transformBackY(e.getY())));
					base.repaint(0);
				}
				else
				{
					if (e.isControlDown())
					{
						if (searchFrame.isVisible())
						{
							return;
						}
						System.out.println("Opening search window");
						searchFrame.setLocation(e.getLocationOnScreen());
						searchFrame.setVisible(true);
						return;
					}
				}
			}
		});
	}
	GraphPoint pointed(int x, int y)
	{
		Vector<GraphPoint> points = graph.getPoints();
		for (GraphPoint point : points)
		{
			if ((pointRadius* pointRadius) >= ((x-getXFor(point.x().intValue(), base))*(x-getXFor(point.x().intValue(), base)) + ((y-getYFor(point.y().intValue(), base))*(y-getYFor(point.y().intValue(), base)))))
			{
				return point;
			}
		}
		return null;
	}
}
