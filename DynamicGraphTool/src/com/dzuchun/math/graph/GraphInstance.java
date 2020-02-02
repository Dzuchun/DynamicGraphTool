package com.dzuchun.math.graph;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphInstance implements Cloneable
{
	private static Logger log = Logger.getGlobal();
	private Vector<GraphPoint> points;
	public boolean hasPoint (GraphPoint point)
	{
		return this.points.contains(point);
	}
	public void addPoint (GraphPoint point) throws IllegalArgumentException
	{
		if (this.points.contains(point))
		{
			throw (new IllegalArgumentException("Can't add point, already present in graph"));
		}
		this.points.add(point);
		log.log(Level.FINER, "Point {0} added to graph {1}", new Object[] {point, this});
	}
	public void removePoint(GraphPoint point) throws IllegalArgumentException
	{
		if (!this.points.contains(point))
		{
			throw(new IllegalArgumentException("Can't remove point, it's not present"));
		}
		Vector<GraphLink> a = this.getLinksForPoint(point);
		this.links.removeAll(a);
		this.points.remove(point);
		log.log(Level.FINER, "Point {0} and it's links {1} removed from graph {2}", new Object[] {point, a, this});
	}
	public Vector<GraphPoint> getPoints()
	{
		return this.points;
	}
	public void deLink(GraphPoint point) throws IllegalArgumentException
	{
		if (!this.points.contains(point))
		{
			throw(new IllegalArgumentException("Can't remove point, it's not present"));
		}
		this.links.removeAll(this.getLinksForPoint(point));
	}
	
	
	private Vector<GraphLink> links;
	public boolean hasLink(GraphLink link)
	{
		for (GraphLink lnk : this.links)
		{
			if (lnk.equals(link))
			{
				return true;
			}
		}
		return false;
	}
	public void addLink(GraphLink link) throws IllegalArgumentException
	{
		if (this.hasLink(link))
		{
			throw(new IllegalArgumentException("Can't add link, it's already present"));
		}
		this.links.add(link);
		log.log(Level.FINER, "Link {0} added to graph {1} succesfully", new Object[] {link, this});
	}
	public void removeLink (GraphLink link)
	{
		if (!this.hasLink(link))
		{
			throw(new IllegalArgumentException("Can't remove link, it's not present"));
		}
		for (GraphLink lnk : this.links)
		{
			if (lnk.equals(link))
			{
				this.links.remove(lnk);
				break;
			}
		}
		System.out.println("Removed " + link + ", links left - " + this.links);
	}
	public Vector<GraphLink> getLinksForPoint(GraphPoint point) throws IllegalArgumentException
	{
		if (!this.points.contains(point))
		{
			//TODO define for different hashcode
			throw(new IllegalArgumentException("Can't get for point not present in graph"));
		}
		Vector<GraphLink> res = new Vector<GraphLink>(0);
		for (GraphLink link : this.links)
		{
			log.log(Level.FINER, "Checking for link {0}", link);
			if (point.equals(link.begin) || point.equals(link.end))
			{
				log.log(Level.FINER, "Found link {0} for point {1}", new Object[] {link, point});
				res.add(link);
			}
		}
		return res;
	}
	public Vector<GraphLink> getLinks()
	{
		return this.links;
	}
	public GraphInstance()
	{
		this.links = new Vector<GraphLink>(0);
		this.points = new Vector<GraphPoint>(0);
	}
	public GraphInstance (Vector<GraphPoint> points_, Vector<GraphLink> links_)
	{
		this.points = points_;
		this.links = links_;
	}
	@SuppressWarnings("unchecked")
	public GraphInstance clone()
	{
		return (new GraphInstance((Vector<GraphPoint>)this.points.clone(), (Vector<GraphLink>)this.links.clone()));
	}
	public Vector<GraphPoint> searchWidth (GraphPoint begin, GraphPoint end)
	{
		if (!this.hasPoint(begin) || !this.hasPoint(end))
		{
			System.out.println("Can't search, points do not belong to graph");
			return null;
		}
		Vector<GraphPoint> found = new Vector<GraphPoint>(0);
		found.add(begin);
		Vector<Integer> steps = new Vector<Integer>(0);
		steps.add(0);
		steps.add(1);
		
		int i=-1;
		int j;
		while (!(found.containsAll(this.points) || found.contains(end) || (i==0)))
		{
			i=0;
			for (j=steps.get(steps.size()-2); i<steps.get(steps.size()-1); i++)
			{
				for (GraphLink link : this.getLinksForPoint(found.get(j)))
				{
					if (!found.contains(link.getBegin()))
					{
						found.add(link.getBegin());
						i++;
					}
					else if (!found.contains(link.getEnd()))
					{
						found.add(link.getEnd());
						i++;
					}
				}
				System.out.println("Search:Added " + i + " elements at last step");
			}
			steps.add(found.size());
		}
		if (i==0)
		{
			System.out.println("Way does not exists");
			return null;
		}

		Vector<GraphPoint> res = new Vector<GraphPoint>(0);
		res.add(0, end);
		GraphPoint current = end;
		
		for (i=steps.size()-2; i==1; i--)
		{
			for (j=steps.get(i-1); j<steps.get(i); j++)
			{
				if (this.getLinkForPoints(current, found.get(j)) != null)
				{
					current = found.get(j);
					res.add(0, current);
					break;
				}
			}
		}
		if (res.get(0) != begin)
		{
			System.out.println("Your search is broken, Dzuchun!");
		}
		String s="[";
		for (GraphPoint point : res)
		{
			s+= point.toString() + ", ";
		}
		s = s.substring(0, Math.max(1, s.length()-2));
		s+="]";
		System.out.println(s);
		
		return res;
	}
	public Vector<GraphPoint> searchDepth (GraphPoint begin, GraphPoint end)
	{
		//TODO depth search
		return null;
	}
	public GraphLink getLinkForPoints(GraphPoint begin, GraphPoint end)
	{
		if (!this.hasPoint(begin) || !this.hasPoint(end))
		{
			System.out.println("Points do not belong to graph");
			return null;
		}
		Vector<GraphLink> endLinks = this.getLinksForPoint(end);
		for (GraphLink link : this.getLinksForPoint(begin))
		{
			if (endLinks.contains(link))
			{
				return link;
			}
		}
		return null;
	}
}
