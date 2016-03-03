#include "algorithms.c"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* 
Author: Kaitlin Gu 
Description: Page statics program
Pagestats loops over the replacement algorithms.
For each metho,d it loops through the number of frames and calculates the page fault rate.
As a by-product pagestats also generates a result file containing a matrix of the calculated miss rates
for each frame size.

Usage: min max increment file
pagesim accepts four command-line arguments in the following order: 
min - min number of frames (no less than 2)
max - max number of frames (no more than 100)
increment - frame number increment (positive integer)
file - input filename where a sequence of page references are stored
*/ 


const char * usage = "Usage:"
"   pagestats min max increment file\n"
"\n"
"pagestats accepts four command-line arguments in the following order: \n"
"following order:\n"
"min - min number of frames (no less than 2)\n"
"max - max number of frames (no more than 100)\n"
"increment  - frame number increment (positive integer) \n"
"file  - input filename where a sequence of page references are stored \n"
"\n";

int *refarray;
int access,faults;
int main(int argc, char*argv[])
{
	char filename[40]={""}; 
	int frames; /* Number of physical memory frames */
	int minFrames; 
	int maxFrames; 
	int frameIncrement;
	int currentpage;
	int i;
	float ratew;
	int arraySize = 10000;
	FILE *pr; /*File containing page references*/
	FILE *stats;  

	if (argc < 4){
		printf("Incorrect number of parameters\n\n%s",usage);
		exit(1);
	}
	minFrames = atoi(argv[1]);
	if (minFrames < 2){
		printf("The minimum number of frames should be no less than 2\n\n%s",usage);
		exit(1);
	}
	maxFrames = atoi(argv[2]);
	if (maxFrames > 100){
		printf("The maximum number of frames should be no more than 100\n\n%s",usage);
		exit(1);
	}

	frameIncrement = atoi(argv[3]);
	if (frameIncrement<0){
		printf("The frame must be positive\n\n%s",usage);
		exit(1);
	}

	refarray = (int*)malloc(arraySize*sizeof(int));
	if (!refarray)
	{
		printf("Cannot allocate!\n");
		exit(1);
	}
	/*Open page ref file*/
	pr = fopen(argv[4],"r");
	if (pr == NULL){
		printf("Error opening file %s\n", argv[2]);
	}

	fscanf(pr, "%d", &currentpage);
	int pageSize = 0;

	strcat(filename, "pagerates.txt");
  	stats = fopen(filename, "w");
  	if(!stats)
  	{
    	printf("Cannot create file %s\n", filename);
    	exit(1);
  	}

  	// Read in page references
	while (!feof (pr)){
		refarray[pageSize] = currentpage;
		fscanf(pr,"%d", &currentpage);
		pageSize++;
		// reallocate if there's no more space
		if (pageSize == arraySize){
			arraySize = arraySize*2;
			refarray = (int*)realloc(refarray, arraySize*sizeof(int));
		}
	}

	// Printing page stats
	for (i = minFrames; i<=maxFrames; i+= frameIncrement){
		fprintf(stats, "%10d", i);

	}
	fprintf(stats, "\n\n");

	for (i = minFrames; i<=maxFrames; i+= frameIncrement){
		ratew = lru(refarray, i, pageSize, 0);
		access = getAccesses();
		faults = getPageFaults();
		printf("LRU, %4d frames:  Miss rate: %d/%d =  %.2f\n", i,faults,access, ratew);
		fprintf(stats, "%10.2f",ratew);

	}
	fprintf(stats,"\n");
	printf("\n");
	
	for (i = minFrames; i<=maxFrames; i+= frameIncrement){
		ratew = fifo(refarray,i,pageSize, 0);
		access = getAccesses();
		faults = getPageFaults();
		printf("FIFO, %3d frames:  Miss rate: %d/%d =  %.2f\n", i, faults,access, ratew);
		fprintf(stats, "%10.2f",ratew);


	}

	fprintf(stats,"\n");
	printf("\n");

	
	for (i = minFrames; i<=maxFrames; i+= frameIncrement){
		ratew = lfu(refarray,i,pageSize, 0);
		access = getAccesses();
		faults = getPageFaults();
		printf("LFU, %4d frames:  Miss rate: %d/%d =  %.2f\n", i,faults,access, ratew);
		fprintf(stats, "%10.2f",ratew);


	}

	fclose(pr);
	fclose(stats);

	free(refarray);

	return 0;

}
