#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/*
Author: Kaitlin Gu 
Program that holds replacement algorithms: FIFO, LRU and LFU.
*/
float accesses; 
float pagefaults; 
int size; 
int *queue; // Represents physical memory
 // Used as a pointer to track location in physical memory
int end; // Used as a pointer for adding to physical memory
int flag; // Used to hold different checks throught the algorithms
float rate; // fault rate 
int *usagetracker; // array to track page usage for  LRU
int *lfutracker; // array to track page usage for LFU

/* 
Method to check whether or not the array is full 
returns 0 if it is not full 
1 if it is
*/
int full()
{
	int i;
	for (i = 0; i < size; i++){
		if (queue[i]<0){
			return 0;
		}
	}
	return 1;
}

/* 
Returns the number of accesses made for a given algorithm
*/ 
int getAccesses(){
	return accesses;
}
/* 
Returns the number of faults for a given algorithm
*/ 
int getPageFaults(){
	return pagefaults; 
}

/* 
Displays the state of the physical memory frame.
*/ 
void display()
{
  int j;
  int k = full();
   printf("[");
  for(j = 0; j < size; j++){
  

  	if (queue[j]>=0 && queue[j]<=99)
		printf( "%2d", queue[j]);
	else{
		printf("  ");
	}
	if (j<size-1)
		printf ("|");
}
printf("]");
  
}

/*
Adds page to memory
*/ 
void enqueue(int page)
{
	end = (end)%size;
	queue[end] = page;
	end++;
}


/* 
Checks whether a page is in memory
1 - indicates page is already in memory
0 - indicates page is not memory
*/ 
int check (int page){
	int i;
	for (i = 0; i < size; i++){
		if (queue[i] == page){
			return 1; 
		}
	}
	return 0; 
}

/* 
Finds the index of a given page
*/ 

int findIndex(int page){
	int i;
	for (i = 0; i < size; i++){
		if (queue[i] == page){
			return i; 
		}
	}
	return -1; 

}


/* 
Finds the maximum value of a given array
Used to find the least recently used page for LRU algorithm
*/ 

int findMax(){
	int max = 0;
	int i;
	for (i = 0; i<size; i++){
		if (usagetracker[max] < usagetracker[i]){
			max=i;
		}
	}
	return max;

}

/* 
Finds the minimum value in a given array 
Used to find the least frequently used page for LFU algorithm. 
*/ 
int findMin(){
	int i;
	int min = lfutracker[0];
	for (i = 0; i<size; i++){
		if (lfutracker[i] < lfutracker[min]){
			min=i;
		}
	}
	return min;

}

/*********************************/

/* 
First In First Out algorithm: Evicts page that is inserted first.  
Page replacement is decided upon what page was loaded into memory primarily. 
*/ 

float fifo (int *ref, int frameSize, int pageSize, int print)
{
	accesses = 0.0;
	pagefaults = 0.0;
	size = frameSize;
	int i, j;
	int beg = 0; 
	queue = (int*)calloc(size,sizeof(int));
	for (j = 0; j<size; j++){
		queue[j] = -1;
	}
	for (i=0; i< pageSize;i++)
	{
	
		flag = check (ref[i]);

		if (flag == 1){
			if (print == 1){
					printf("%3d: ",ref[i]);
					display();
					printf("\n");
				}
		}
		if (flag == 0){
			if (full()){
				beg = beg%size;
				queue[beg] = ref[i];
				enqueue(ref[i]);

				beg++;
				pagefaults++;
				if (print == 1){
					printf("%3d: ",ref[i]);
					display();
					printf("  F\n");
				}

			}
			else{
				enqueue(ref[i]);
				if (print == 1){
					printf("%3d: ",ref[i]);
					display();
					printf("\n");
				}
			}
		}
		if (full()){
				accesses++;
		}
	}
	rate = (pagefaults/accesses)*100;
	if (print == 1){
		printf("Miss rate: %.0f/%.0f =  %.2f\n", pagefaults, accesses, rate);
	}
	free(queue);
	return rate;

 
}


/* 
Least Recently Used algorithm: Evicts page that is least recently used. 
There's a counter for each page loaded into memory. The counter increments 
for each iteration the page remains in memory without being referenced.
Page replacement is decided on the page with the highest counter - which 
indicates the page has been there the longest. 
The counter is reset for the new page. 
*/ 

float lru (int *refL, int frameSizeL, int pageSizeL, int print){

	accesses = 0.0;
	pagefaults = 0.0;
	int pointer = 0;
	int victim, i, j, k;
	size = frameSizeL;
	usagetracker = (int*)calloc(size,sizeof(int));
	queue = (int*)calloc(size,sizeof(int));
	for (k = 0; k<size; k++){
		queue[k] = -1;
	}

	for (i = 0; i<pageSizeL;i++)
	{
		flag = check(refL[i]);
		// if frame is present reset its count
		if (flag == 1){
			victim = findIndex(refL[i]);
			usagetracker[victim] = 1;
				if (print == 1){
					printf("%3d: ",refL[i]);
					display();

					printf("\n");
			}

		
		}


		// if frame is not present
		if (flag == 0){
			// check if its full
			if (full()){
				// pick a victim
				victim = findMax();
				queue[victim] = refL[i];
				usagetracker[victim] = 0;
				pagefaults++;
				if (print == 1){
					printf("%3d: ",refL[i]);
					display();
					printf("  F\n");
				}
				
			}
			// if not full
			else {
				// add a new frame 
				enqueue(refL[i]);
				usagetracker[pointer]++;
				pointer++;
				if (print == 1){
					printf("%3d: ",refL[i]);
					display();
					printf("\n");
				}
			}
		}
		
		for (j = 0; j<pointer;j++){
			usagetracker[j]++;
		}

		if (full()){
			accesses++;
		}

	}

	rate = (pagefaults/accesses)*100;
	if (print==1){
		printf("Miss rate: %.0f/%.0f =  %.2f\n", pagefaults,accesses, rate);
	}


	free(queue);
	free(usagetracker);
	return rate;

}



/* 
Least Frequently Used algorithm: Evicts page that is least frequently used. 
There's a counter for each page loaded into memory. Each time a page is referenced
then the counter is incremented. Once the memory is full then the victim for 
page replacement is the page with the lowest counter number (i.e. the one that has been used the least).
The counter is reset for the new page. 
*/ 
float lfu(int *refF, int frameSizeF, int pageSizeF, int print){
	accesses = 0.0;
	pagefaults = 0.0;
	int usage;
	int i, j, victim; // used for indexing
	size = frameSizeF;
	int pointer = 0;


	lfutracker = (int*)calloc(size,sizeof(int));
	queue = (int*)calloc(size,sizeof(int));

	for (j = 0; j<size; j++){
		queue[j] = -1;
	}

	for (i = 0; i < pageSizeF; i++)
	{
		flag = check(refF[i]);
		// if page is present
		if (flag == 1){
				// increment its usage
				victim = findMin();
				lfutracker[victim]++;
				if (print == 1){
					printf("%3d: ",refF[i]);
					display();
					printf("\n");
				}
		}
		// page is not present
		if (flag == 0){
			if (full()){
				// pick a victim
				victim = findMin();
				queue[victim] = refF[i];
				// reset usage
				lfutracker[victim] = 1;
				pagefaults++;
				if (print == 1){
					printf("%3d: ",refF[i]);
					display();
					printf("  F\n");
				}

			}
			else{
				// add page to frame
				enqueue(refF[i]);
				lfutracker[pointer]++;
				pointer++;
				if (print == 1){
					printf("%3d: ",refF[i]);
					display();
					printf("\n");
				}

			}
		}

		if (full()){
			accesses++;
		} 
		
	}

	// return rate
	rate = (pagefaults/accesses)*100;
	if (print == 1){
		printf("Miss rate: %.0f/%.0f =  %.2f\n", pagefaults, accesses, rate);
	}

	free(lfutracker);
	free(queue);

	return rate;

}

