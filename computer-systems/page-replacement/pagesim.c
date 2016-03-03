#include "algorithms.c"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* 
Author: Kaitlin Gu 
Description: Page replacement simulator
Pagesim reads all the memory references from the input file and store them in a local array. 
It uses these memory references to simulate physical memory frame allocation. 
Page sim prints the number of page faults and the miss rates along with the allocation state. 

Usage: frames file algorithm
pagesim accepts three command-line arguments in the following order: 
frames - total number of physical memory frames (maximum 100)
file - input filename where a sequence of page references are stored
algorithm  - the chosen algorithm (lru, fifo, lfu)

*/ 


const char * usage = "Usage:"
"   pagesim frames file algorithm \n"
"\n"
"pagesim accepts three command-line arguments in the\n"
"following order:\n"
"frames - total number of physical memory frames (maximum 100)\n"
"file - input filename where a sequence of page references are stored\n"
"algorithm  - the chosen algorithm (lru, fifo, lfu) \n"
"\n";


 
int main(int argc, char*argv[])
{
	int *refarray;
	int frames; /* Number of physical memory frames */
	int currentpage; /* Current page */ 
	FILE *pr; /*File containing page references*/  
	char* replacement; /*replacement policy*/
	int arraySize = 10000;
	if (argc < 3){
		printf("Required parameters: frames, file, policy\n\n%s",usage);
		exit(1);
	}
	frames = atoi(argv[1]);
	if (frames <= 0 || frames > 99 ){
		printf("Frames must be a positive integer between 0-99\n\n%s",usage);
		exit(1);
	}
	

	refarray = (int*)malloc(arraySize*sizeof(int));
	if (!refarray)
	{
		printf("Cannot create file!\n");
		exit(1);
	}
	/*Open page ref file*/
	pr = fopen(argv[2],"r");
	if (!pr){
		printf("Error opening file %s\n", argv[2]);
	}

	// Read in page references
	fscanf(pr, "%d", &currentpage);
	int pageSize = 0;
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
	fclose(pr);

	/* Decide on the policy */ 
	replacement = argv[3];
	if (strcmp("lru", replacement)!= 0 && strcmp("fifo", replacement) != 0 && strcmp("lfu", replacement) !=0){
		printf("Algorithm must be lru fifo or lfu\n\n%s",usage);
		exit(1);
	}	
	// LRU
	if (strcmp("lru", replacement)== 0){
		lru (refarray, frames, pageSize, 1);
	}
	// FIFO
	else if (strcmp("fifo", replacement) == 0){
		fifo (refarray, frames, pageSize, 1);
	}
	// LFU
	else if (strcmp("lfu", replacement)== 0){
		lfu (refarray, frames, pageSize, 1);

	}

	free(refarray);
	return 0;


}
