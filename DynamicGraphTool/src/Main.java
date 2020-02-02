import com.dzuchun.graphing.AbstractData;
import com.dzuchun.graphing.MathGraphData;
import com.dzuchun.kpnl.graph.DynamicGraphDrawer;
import com.dzuchun.math.GeneralValue;
import com.dzuchun.math.graph.*;

import java.awt.Dimension;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main 
{
	public static void main(String[] args) 
	{
		if (args.length >0)
		{
			if (args[0].equals("snooze"))
			{
				System.out.close(); 
			}
		}
		Logger.getGlobal().setLevel(Level.ALL);
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new SimpleFormatter());
		Logger.getGlobal().addHandler(handler);
		int[][] ints = new int[][] {
			{100, 10},
			{200, 150},
			{30, 100}
		};
		@SuppressWarnings("serial")
		Vector<GraphPoint> points/** = new Vector<GraphPoint>(0) {{
			this.add(new GraphPoint(new GeneralValue().setValue(ints[0][0]), new GeneralValue().setValue(ints[0][1])));
			this.add(new GraphPoint(new GeneralValue().setValue(ints[1][0]), new GeneralValue().setValue(ints[1][1])));
			this.add(new GraphPoint(new GeneralValue().setValue(ints[2][0]), new GeneralValue().setValue(ints[2][1])));
		}};
		points*/ = new Vector<GraphPoint>(0);
		@SuppressWarnings("serial")
		Vector<GraphLink> links/** = new Vector<GraphLink>(0) {{
			this.add(new GraphLink(points.get(0), points.get(1)));
		}};
		links*/ = new Vector<GraphLink>(0);
		GraphInstance graph = new GraphInstance(points, links);
		@SuppressWarnings("serial")
		Vector<AbstractData> data = new Vector<AbstractData>(0){{
			this.add(new MathGraphData(graph));
		}};
		@SuppressWarnings("unused")
		DynamicGraphDrawer grapher = new DynamicGraphDrawer(new Dimension(1000, 1000), true, data, new Dimension(0, 1000), new Dimension(1000, 0));
	}
}
