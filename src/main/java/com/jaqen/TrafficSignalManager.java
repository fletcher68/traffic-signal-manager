package com.jaqen;

import java.util.LinkedList;
import java.util.Queue;

public class TrafficSignalManager
{

	private Queue<Car> weaverEastBound = new LinkedList<Car>();
	private Queue<Car> weaverWestBound = new LinkedList<Car>();
	private Queue<Car> snellNorthBound = new LinkedList<Car>();
	private Queue<Car> snellSouthBound = new LinkedList<Car>();

	private static String snellCurrentState = "GREEN";
	private static String weaverCurrentState = "RED";
	private static String snellPreviousState = "";
	private static String weaverPreviousState = "";

	private static Integer numSeconds = 0;
	final Integer MAX_TIME = 30;

	public TrafficSignalManager()
	{

	}

	public void startTrafficSimulator() throws InterruptedException
	{
		startClock();
		startLightRegulator();
		startLogging();
		startTrafficGenerator(weaverEastBound);
		startTrafficGenerator(weaverWestBound);
		startTrafficGenerator(snellNorthBound);
		startTrafficGenerator(snellSouthBound);
		startTrafficRegulator();
	}

	public void startClock() throws InterruptedException
	{
		new Thread(() ->
		{

			try
			{
				while (numSeconds < MAX_TIME + 1)
				{
					Thread.sleep(1000);
					numSeconds++;
				}
			} catch (InterruptedException e)
			{
			}

		}).start();
	}

	public void startLogging() throws InterruptedException
	{
		new Thread(() ->
		{

			try
			{
				while (numSeconds < MAX_TIME)
				{
					logTraffic();
					Thread.sleep(1000);
				}
			} catch (InterruptedException e)
			{
			}

		}).start();
	}

	public void logTraffic()
	{
		System.out.print(numSeconds + ":");
		System.out.print("N = " + snellNorthBound.size() + "; ");
		System.out.print("S = " + snellSouthBound.size() + "; ");
		System.out.print("E = " + weaverEastBound.size() + "; ");
		System.out.print("W = " + weaverWestBound.size() + "");
		System.out.println();
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

	public void startTrafficRegulator()
	{
		/*
		 * Thread for Snell Road
		 */
		new Thread(() ->
		{
			snellPreviousState = snellCurrentState;
			try
			{
				while (numSeconds < MAX_TIME)
				{
					if ("GREEN".equals(snellCurrentState) && "GREEN".equals(snellPreviousState))
					{
						snellNorthBound.poll();
						snellSouthBound.poll();
					} else if ("GREEN".equals(snellCurrentState) && "RED".equals(snellPreviousState))
					{
						Thread.sleep(1000);
						snellNorthBound.poll();
						snellSouthBound.poll();
					}

					snellPreviousState = snellCurrentState;
					Thread.sleep(1000);
				}
			} catch (InterruptedException e)
			{
			}
		}).start();

		/*
		 * Thread for Weaver Road
		 */
		new Thread(() ->
		{
			weaverPreviousState = weaverCurrentState;

			try
			{
				while (numSeconds < MAX_TIME)
				{
					if ("GREEN".equals(weaverCurrentState) && "GREEN".equals(weaverPreviousState))
					{
						weaverEastBound.poll();
						weaverWestBound.poll();
					} else if ("GREEN".equals(weaverCurrentState) && "RED".equals(weaverPreviousState))
					{
						Thread.sleep(1000);
						weaverEastBound.poll();
						weaverWestBound.poll();
					}
					weaverPreviousState = weaverCurrentState;
					Thread.sleep(1000);
				}
			} catch (InterruptedException e)
			{
			}
		}).start();

	}

	public void startLightRegulator()
	{

		snellCurrentState = "GREEN";
		weaverCurrentState = "RED";
		new Thread(() ->
		{

			try
			{
				while (numSeconds < MAX_TIME)
				{
					Thread.sleep(3000);
					snellCurrentState = "RED";
					Thread.sleep(1000);
					weaverCurrentState = "GREEN";
					Thread.sleep(3000);
					weaverCurrentState = "RED";
					Thread.sleep(1000);
					snellCurrentState = "GREEN";
				}

			} catch (Exception e)
			{

			}
		}).start();

	}

}
