package com.dzuchun.math.graph;

public class GraphLink implements Cloneable
{
	GraphPoint begin;
	GraphPoint end;
	public GraphLink(GraphPoint begin_, GraphPoint end_)
	{
		this.begin = begin_;
		this.end = end_;
	}
	public GraphPoint getBegin()
	{
		return this.begin.clone();
	}
	public GraphPoint getEnd()
	{
		return this.end.clone();
	}
	public GraphLink reverse()
	{
		return new GraphLink (this.end, this.begin);
	}
	public GraphLink clone()
	{
		return new GraphLink(this.begin.clone(), this.end.clone());
	}
	public boolean equals(GraphLink link)
	{
		if ((this.begin.equals(link.begin) && this.end.equals(link.end)) || (this.begin.equals(link.end) && this.end.equals(link.begin)))
		{
			return true;
		}
		System.out.println("Links " + this + " and " + link + " are not equal");
		return false;
	}
	public String toString()
	{
		return "link[" + this.begin + " to " + this.end + "]";
	}
}
