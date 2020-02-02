package com.dzuchun.kpnl.graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dzuchun.math.graph.GraphInstance;
import com.dzuchun.math.graph.GraphLink;
import com.dzuchun.math.graph.GraphPoint;
import com.dzuchun.math.sort.BaubleSort;

@SuppressWarnings("serial")
class LinkageMatrixFrame extends JFrame
{
	JPanel panel;
	DynamicGraphDrawer parent;
	GraphInstance graph;
	public LinkageMatrixFrame (DynamicGraphDrawer parent)
	{
		this.parent = parent;
		this.setTitle("Linkage matrix");
		this.setLayout(new BorderLayout());
		this.panel = new JPanel();
		this.add(panel);
		panel.setLayout(null);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
	private static Font borderFont = new Font("Border", Font.BOLD, 15);
	public void updateMatrix(GraphInstance graph, GraphPoint highlighted)
	{
		System.out.println("Updating matrix");
		this.graph = graph;
		this.panel.removeAll();
		Vector<GraphPoint> points = (new BaubleSort<GraphPoint>()).sort(this.graph.getPoints());
		int g = points.size();
		this.setSize((g + 1) * 30 + 50, (g + 1) * 30 + 50);
		boolean[][] linked = new boolean[g][g];
		int i, j;
		for (GraphLink link : graph.getLinks())
		{
			i = points.indexOf(parent.pointed(link.getBegin().x().intValue(), link.getBegin().y().intValue()));
			j = points.indexOf(parent.pointed(link.getEnd().x().intValue(), link.getEnd().y().intValue()));
			if ((i!=-1) && (j!=-1))
			{
				linked[i][j] = true;
				linked[j][i] = true;
			}
		}
		class BooleanLabel extends JLabel
		{
			public BooleanLabel(boolean b, int i, int j)
			{
				if (b)
				{
					this.setText("1");
				}
				else
				{
					this.setText("0");
				}
				this.setBounds(i * 30, j * 30, 30, 30);
				this.setHorizontalAlignment(CENTER);
			}
		}
		int high = -1;
		JLabel lab;
		if (highlighted != null)
		{
			high = points.indexOf(highlighted) + 1;
		}
		for (i=1; i<=g; i++)
		{
			for (j=1; j<=g; j++)
			{
				lab = new BooleanLabel(linked[i-1][j-1], i, j);
				if ((i == high) || (j == high))
				{
					lab.setForeground(new Color(0, 0, 255));
				}
				panel.add(lab);
			}
		}
		i=1;
		class NumberLabel extends JLabel
		{
			public NumberLabel (Integer a, int i, int j)
			{
				if (a == null)
				{
					this.setText("N/A");
				}
				else
				{
					this.setText(a + "");
				}
				this.setBounds(i * 30, j * 30, 30, 30);
				this.setHorizontalAlignment(CENTER);
			}
		}
		for (GraphPoint point : points)
		{
			if (point.number != null)
			{
				lab =new NumberLabel(point.number+1, 0, i);
				lab.setFont(borderFont);
				if (i == high)
				{
					lab.setForeground(new Color(0, 0, 255));
				}
				panel.add(lab);
				lab = new NumberLabel(point.number+1, i, 0);
				lab.setFont(borderFont);
				if (i == high)
				{
					lab.setForeground(new Color(0, 0, 255));
				}
				panel.add(lab);
			}
			else
			{
				lab =new NumberLabel(null, 0, i);
				lab.setFont(borderFont);
				if (i == high)
				{
					lab.setForeground(new Color(0, 0, 255));
				}
				panel.add(lab);
				lab = new NumberLabel(null, i, 0);
				lab.setFont(borderFont);
				if (i == high)
				{
					lab.setForeground(new Color(0, 0, 255));
				}
				panel.add(lab);
			}
			i++;
		}
		
		this.repaint();
	}
}
