package com.dzuchun.math.sort;

import com.dzuchun.math.GeneralValue;

public interface Sortable 
{	
	public Boolean sortGreater(Sortable comp);
	public Boolean sortEquals(Sortable comp);
	public Boolean sortLesser(Sortable comp);
	public Integer sortValue();
}
