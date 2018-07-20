package com.jaqen;

public class Car
{
	private Integer id;

	private static Integer ctr=0;

	public Car()
	{
		setId();
	}

	private synchronized void setId()
	{
		this.id = ctr;
		ctr++;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
}
