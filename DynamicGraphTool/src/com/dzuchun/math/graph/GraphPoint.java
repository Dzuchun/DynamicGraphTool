package com.dzuchun.math.graph;

import com.dzuchun.math.GeneralValue;
import com.dzuchun.math.Point;
import com.dzuchun.math.sort.Sortable;

public class GraphPoint extends Point implements Cloneable, Sortable
{
	private static long generalId = 0;
	public static int getMaxId()
	{
		return (int)generalId;
	}
	public static void resetId()
	{
		generalId = 0;
	}
	private long id;
	public long getId()
	{
		return this.id;
	}
	private GraphPoint (long id)
	{
		super();
		this.id = id;
	}
	public GraphPoint (GeneralValue x, GeneralValue y)
	{
		super(x, y);
		generalId++;
		this.id = generalId;
	}
	@Override
	public GraphPoint clone()
	{
		GraphPoint res = new GraphPoint(this.id);
		res.setTo(this._x, this._y);
		res.number = this.number;
		return res;
	}
	public String toString()
	{
		return "point-" + this.number;
	}
	public Integer number;
	@Override
	public Boolean sortGreater(Sortable comp) 
	{
		GraphPoint comp_ = (GraphPoint)comp;
		if (this.number == null)
		{
			if (comp_.number == null)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		if (comp_.number == null)
		{
			return false;
		}
		if (this.number < comp_.number)
		{
			return true;
		}
		return false;
	}
	@Override
	public Boolean sortEquals(Sortable comp) 
	{
		GraphPoint comp_ = (GraphPoint)comp;
		if (this.number == comp_.number)
		{
			return true;
		}
		return false;
	}
	@Override
	public Boolean sortLesser(Sortable comp) 
	{
		GraphPoint comp_ = (GraphPoint)comp;
		if (this.number == null)
		{
			return false;
		}
		if (comp_.number == null)
		{
			return true;
		}
		if (this.number > comp_.number)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public Integer sortValue() 
	{
		if (this.number == null)
		{
			return this.number;
		}
		else
		{
			return new Integer(this.number);
		}
	}
}
