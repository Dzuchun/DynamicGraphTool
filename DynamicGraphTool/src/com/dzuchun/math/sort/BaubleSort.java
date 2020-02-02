package com.dzuchun.math.sort;

import java.util.Vector;

public class BaubleSort<T extends Sortable> extends AbstractSort<T>
{
	@SuppressWarnings("unchecked")
	@Override
	public Vector<T> sort(Vector<T> res) 
	{
		T a;
		Boolean b;
		for (int i=0; i<res.size(); i++)
		{
			for (int j = 0; j<i; j++)
			{
				b = ((T)res.get(j)).sortLesser((T)res.get(j+1));
				if (b)
				{
					a = res.get(j);
					res.set(j, res.get(j+1));
					res.set(j+1, a);
				}
			}
		}
		/**String s = "[";
		for (Sortable p : res)
		{
			s += p.sortValue() + ", ";
		}
		s = s.substring(0, Math.max(s.length()-2, 1));
		s+="]";
		System.out.println(s);
		*/
		return res;
	}

}
