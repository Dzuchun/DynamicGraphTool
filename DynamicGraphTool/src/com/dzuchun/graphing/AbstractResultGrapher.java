package com.dzuchun.graphing;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public abstract class AbstractResultGrapher extends JFrame 
{
	protected static Logger log = Logger.getGlobal();
	/**
	 * 
	 */
	private static final long serialVersionUID = 789927258782237469L;
	Vector<AbstractData> dataArray;
	protected Dimension lowerCornerPosition;
	protected Dimension higherCornerPosition;
	protected JLayeredPane floor;
	protected Canvas base;
	public AbstractResultGrapher (Dimension size, boolean resizable, Vector<AbstractData> dataArray, Dimension lowerCornerPosition, Dimension higherCornerPosition)
	{
		this.dataArray = dataArray;
		this.lowerCornerPosition = lowerCornerPosition;
		this.higherCornerPosition = higherCornerPosition;
		this.setTitle("Result grapher");
		this.setSize(size);
		this.setResizable(resizable);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.floor = new JLayeredPane();
		this.floor.setIgnoreRepaint(false);
		this.base = new Canvas() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			{
				//setBackground(new Color(230, 230, 230));
			}
			public void paint(Graphics g)
			{
				System.out.println("Repainting canvas");
				super.paint(g);
				drawData(this, g);
			}
		};
		this.initCanvas(this.base);
		floor.add(base, 0);
		this.add(floor);
		base.setSize(this.getSize());
		this.setVisible(true);
	}
	public abstract void drawData(Canvas base, Graphics g);
	protected void initCanvas(Canvas base) {	}
}
