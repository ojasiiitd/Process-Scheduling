/*input
4
1 0 2
2 0 3
3 0 4 
4 0 5 
*/

// oju

import java.util.*;

class Process
{
 int pid;
 int start;
 int priority;
 int left;
 public Process(int pid, int start, int priority)
 {
 this.pid = pid;
 this.start = start;
 this.priority = priority;
 this.left = 5;
 }
}

// Custom Comparator for priority queue
class Sortbystart implements Comparator<Process>
{
	public int compare(Process a, Process b)
	{	
		return a.start - b.start;
	}
}

public class scheduler
{
	public Process[] Heap;
	public int size = 0;

	public Lab9()
	{
		Heap = new Process[1000000];
	}

	public void swap(int a , int b)
	{
			Process temp = 	Heap[a];
			Heap[a] = Heap[b];
			Heap[b] = temp;
	}

	public void insert(Process elt)
	{
		size++;
		Heap[size] = elt;

		int cur = size;
		while(Heap[cur].priority > Heap[cur/2].priority)
		{
			swap(cur , cur/2);
			cur = cur/2;
		}
	}

	public void heapify(int pos)
	{
		if(size == 0)
			return ;
		while(!(pos > size/2 && pos <= size))
		{
			if(Heap[pos].priority < Heap[2*pos].priority || Heap[pos].priority < Heap[(2*pos)+1].priority)
			{
				if(Heap[2*pos].priority > Heap[(2*pos)+1].priority)
				{
					swap(pos , 2*pos);
					pos = 2*pos;
				}
				else
				{
					swap(pos , (2*pos)+1);
					pos = (2*pos)+1;
				}
			}
			else
				break;
		}
	}


	public Process deleteMax()
	{
		Process ret = Heap[1];
		Heap[1] = Heap[size--];
		heapify(1);
		return ret;
	}

	public void print()
	{
		for(int i=1 ; i<=size ; i++)
			System.out.print(Heap[i].pid + "." + Heap[i].left + " ");
		System.out.println();
	}

	public static int partition(Process a[] , int s , int e)
	{
		int i = s-1 , j;
		int pivot = a[e].pid;
		for( j=s ; j<e ; j++ )
		{
			if(a[j].pid < pivot)
			{
				i++;
				Process t = a[i];
				a[i] = a[j];
				a[j] = t;
			}
		}
		Process t = a[i+1];
		a[i+1] = a[e];
		a[e] = t;

		return i+1;
	}

	public static void quickSort(Process a[] , int s , int e)
	{
		if(s >= e)
			return ;
		int p = partition(a,s,e);
		quickSort(a,s,p-1);
		quickSort(a,p+1,e);
	}

	public static void main(String[] args)
	{
		Scanner cin = new Scanner(System.in);
		ArrayList<Process> processes = new ArrayList<Process>();
		int n = cin.nextInt();
		for (int i=0 ; i<n ; i++)
		{
			int id = cin.nextInt();
			int st = cin.nextInt();
			int pr = cin.nextInt();
			processes.add(new Process(id,st,pr));
		}
		Collections.sort(processes, new Sortbystart());

		Lab9 h = new Lab9();
		h.Heap[0] = new Process(Integer.MAX_VALUE , Integer.MAX_VALUE , Integer.MAX_VALUE);
		int out[] = new int[n];
		
		int t = -1;
		int i = 0;
		int s = 0;
		while(true)
		{
			t++;
			while(s < n && t == processes.get(s).start)
			{
					h.insert(processes.get(s));
					s++;
			}

			if(h.size > 0)
			{	
				h.Heap[1].left--;

				if(h.Heap[1].left == 0)
				{
					Process temp = h.deleteMax();
					out[temp.pid - 1] = t+1;
					if(h.size == 0)
						break;
				}
			}
		}

		for(int ls=0 ; ls<n ; ls++)
			System.out.println(out[ls]);
	}
}
