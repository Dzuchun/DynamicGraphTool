package com.dzuchun.math.sort;

import java.util.Vector;

public abstract class AbstractSort<T extends Sortable>
{
	public abstract Vector<T> sort (Vector<T> res);
}
