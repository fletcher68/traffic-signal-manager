package com.jaqen;

import java.util.LinkedList;
import java.util.Queue;

public class TrafficSignalManager
{

	private Queue<Car> eastBound = new LinkedList<Car>();
	private Queue<Car> westBound = new LinkedList<Car>();
	private Queue<Car> northBound = new LinkedList<Car>();
	private Queue<Car> southBound = new LinkedList<Car>();

	private static String snell = "GREEN";
	private static String weaver = "RED";

	Integer numSeconds = 0;
	final Integer MAX_TIME = 20;

	public TrafficSignalManager()
	{

	}

	public void startTrafficSimulator() throws InterruptedException
	{
		startLights();
		startTrafficGenerator(eastBound);
		startTrafficGenerator(westBound);
		startTrafficGenerator(northBound);
		startTrafficGenerator(southBound);
		startLogging();
	}

	public void startLogging() throws InterruptedException
	{
		while (numSeconds < MAX_TIME)
		{
			logTraffic();
			regulateTraffic();
			Thread.sleep(1000);
			numSeconds++;
		}
	}

	public void logTraffic()
	{
		new Thread(() ->
		{
			System.out.print(numSeconds + ":");
			System.out.print("N = " + northBound.size() + "; ");
			System.out.print("S = " + southBound.size() + "; ");
			System.out.print("E = " + eastBound.size() + "; ");
			System.out.print("W = " + westBound.size() + "");
			System.out.println("");

		}).start();
	}

	public void startTrafficGenerator(Queue<Car> q)
	{
		new Thread(() ->
		{
			try
			{
				while (numSeconds < MAX_TIME)
				{

					Car c = new Car();
					q.add(c);

					Thread.sleep(1000);

				}
			} catch (InterruptedException e)
			{

			}
		}).start();
	}

	public void regulateTraffic()
	{
		new Thread(() ->
		{

			if ("GREEN".equals(snell))
			{
				northBound.poll();
				southBound.poll();
			}
			if ("GREEN".equals(weaver))
			{
				eastBound.poll();
				westBound.poll();
			}
		}).start();
	}

	public void startLights()
	{

		snell = "GREEN";
		weaver = "RED";
		new Thread(() ->
		{

			try
			{
				while (numSeconds < MAX_TIME)
				{
					Thread.sleep(3000);
					snell = "RED";
					Thread.sleep(1000);
					weaver = "GREEN";
					Thread.sleep(3000);
					weaver = "RED";
					Thread.sleep(1000);
					snell = "GREEN";
				}

			} catch (Exception e)
			{

			}
		}).start();

	}

}
