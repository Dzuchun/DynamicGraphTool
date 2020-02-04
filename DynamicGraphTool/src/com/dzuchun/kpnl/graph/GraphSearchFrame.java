package com.dzuchun.kpnl.graph;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.dzuchun.math.graph.GraphInstance;
import com.dzuchun.math.graph.GraphLink;
import com.dzuchun.math.graph.GraphPoint;

@SuppressWarnings("serial")
public class GraphSearchFrame extends JFrame 
{

	private JScrollPane paneWay;
	private JLabel labelBegin;
	private JLabel labelEnd;
	private GraphPoint pointBegin;
	private GraphPoint pointEnd;
	private GraphInstance graph;
	private DynamicGraphDrawer parent;
	private JPanel panel;
	private boolean isWidth = true; 
	public GraphSearchFrame(DynamicGraphDrawer parent, GraphInstance graph) throws HeadlessException 
	{
		this.setSize(400, 400);
		this.parent = parent;
		this.graph = graph;
		this.setTitle("Graph Search");
		this.setLayout(new BorderLayout());
		Font font = new Font("Big font", Font.BOLD, 15);
		
		this.panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1, 0, 10));
		
		panel.add(new JPanel() {{
			
			this.setLayout(new GridLayout(1, 2, 10, 0));
			this.add(new JLabel("Select search type") {{
				this.setFont(font);
			}});
			
			this.add(new JPanel()	{{
				this.setLayout(new GridLayout(2, 1, 0, 0));
				
				ButtonGroup group = new ButtonGroup();
				this.add(new JRadioButton("Width")	{{
					this.setFont(font);
					group.add(this);
					this.setSelected(true);
					this.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							isWidth = true;
						}
					});
				}});
				
				this.add(new JRadioButton("Depth")	{{
					this.setFont(font);
					group.add(this);
					this.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							isWidth = false;
						}
					});
				}});
			}});
		}});
		
		panel.add(new JPanel() {{
			
			this.setLayout(new GridLayout(1, 2, 0, 10));
			this.add(new JPanel() {{
				
				this.setLayout(new GridLayout(2, 1, 10, 0));
				
				this.add(new JLabel() {{
					this.setText("Begin point");
					this.setFont(font);
				}});
				
				this.add(labelBegin = new JLabel("N/A"));
				
			}});
			
			this.add(new JPanel()
			{{
				this.setLayout(new GridLayout(1, 2, 0, 10));
				this.add(new JButton("Choose")
				{{
					this.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							if (parent.highlightedPoint != null)
							{
								pointBegin = parent.highlightedPoint;
								if (pointBegin.number != null)
								{
									labelBegin.setText(pointBegin.number + 1 + "");
								}
								else
								{
									labelBegin.setText("N/A");
								}
							}
						}
					});
				}});
			}});
		}});
		
		panel.add(new JPanel() {{
			
			this.setLayout(new GridLayout(1, 2, 0, 10));
			this.add(new JPanel() {{
				
				this.setLayout(new GridLayout(2, 1, 10, 0));
				
				this.add(new JLabel() {{
					this.setText("End point");
					this.setFont(font);
				}});
				
				this.add(labelEnd = new JLabel("N/A"));
				
			}});
			
			this.add(new JPanel()
			{{
				this.setLayout(new GridLayout(1, 2, 0, 10));
				this.add(new JButton("Choose")
				{{
					this.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) 
						{
							if (parent.highlightedPoint != null)
							{
								pointEnd = parent.highlightedPoint;
								if (pointEnd.number != null)
								{
									labelEnd.setText(pointEnd.number + 1 + "");
								}
								else
								{
									labelEnd.setText("N/A");
								}
							}
						}
					});
				}});
			}});
		}});

		panel.add(new JPanel()	{{
			this.setLayout(new GridLayout(2, 1, 10, 0));
			
			this.add(new JButton("Swap")	{{
				this.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						GraphPoint a = pointEnd;
						pointEnd = pointBegin;
						pointBegin = a;
						a = null;
						if ((pointEnd != null) && (pointEnd.number != null))
						{
							labelEnd.setText(pointEnd.number + 1 + "");
						}
						else
						{
							labelEnd.setText("N/A");
						}
						if ((pointBegin != null) && (pointBegin.number != null))
						{
							labelBegin.setText(pointBegin.number + 1 + "");
						}
						else
						{
							labelBegin.setText("N/A");
						}
					}
				});
			}});
			
			this.add(new JButton("Search")	{{
				this.addActionListener(new ActionListener() 
				{
					
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						
						if ((pointBegin == null) ||
								(pointEnd == null) ||
								(pointBegin.number == null) ||
								(pointEnd.number == null))
						{
							System.err.println("Please, specify point enumeration");
							return;
						}
						
						parent.highLightedWayLink = null;
						parent.repaintBase();
						Vector<GraphPoint> way;
						if (isWidth)
						{
							way = graph.searchWidth(pointBegin, pointEnd);
						}
						else
						{
							//TODO define
							way = graph.searchDepth(pointBegin, pointEnd);
						}
						Vector<GraphLink> wayLinks = new Vector<GraphLink>(0);
						if (way != null)
						{
							for (int i=0; i<way.size()-2; i++)
							{
								wayLinks.add(graph.getLinkForPoints(way.get(i), way.get(i+1)));
							}
						}
						
				        DefaultListModel<GraphLink> listModel = new DefaultListModel<GraphLink>();
				        for (GraphLink link : wayLinks)
				        {
				        	listModel.addElement(link);
				        }
				        JList<GraphLink> list = new JList<GraphLink>(listModel);
				        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				        list.addListSelectionListener(new ListSelectionListener() {
							
							@Override
							public void valueChanged(ListSelectionEvent e) 
							{	
								parent.highLightedWayLink = list.getSelectedValue();
								parent.repaintBase();
							}
						});
						paneWay.setViewportView(list);
					}
				});
			}});
		}});
		
		panel.add(paneWay = new JScrollPane()	{{
			this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
			this.setSize(this.getWidth(), this.getHeight());
		}}, BorderLayout.CENTER);
		
		this.add(panel);
		this.setMinimumSize(this.getSize());
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}
}
