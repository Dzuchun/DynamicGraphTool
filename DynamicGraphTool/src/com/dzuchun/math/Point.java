package com.dzuchun.math;

public class Point implements Cloneable
{
	protected GeneralValue _x;
	protected GeneralValue _y;
	public GeneralValue x()
	{
		return _x.clone();
	}
	public GeneralValue y()
	{
		return _y.clone();
	}
	public Point (GeneralValue x_, GeneralValue y_)
	{
		this._x = x_;
		this._y = y_;
	}
	public Point ()
	{
		this._x = null;
		this._y = null;
	}
	public void moveFor (GeneralValue x_, GeneralValue y_)
	{
		this._x.add(x_);
		this._y.add(y_);
	}
	public void setTo (GeneralValue x_, GeneralValue y_)
	{
		this._x = x_;
		this._y = y_;
	}
	public Point clone()
	{
		return new Point(this._x.clone(), this._y.clone());
	}
}
