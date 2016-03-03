#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

/* 
 * Author: Joanna Klukowska
 * Date created: Apr. 21, 2015
 * 
 * Description:
 * pagegenerator generates a sequence of the desired length containing
 * random page numbers univformly distributed between 0 and the range
 * minus 1. No page number in the sequence is ever equal to the number
 * that preceeds it in the sequence. 
 * 
 * Usage:
 *   pagegenerator range count file [seed]
 *
 * pagegenerator accepts four command-line arguments in the
 * following order:
 * range - the range of page references (maximum 100)
 * count - the length of sequence to be generated
 * file  - the name of the output file that will be generated
 * seed  - (optional) the seed to be used for the random number
 *         generator
 * 
 */



const char * usage = "Usage:"
"   pagegenerator range count file [seed]\n"
"\n"
"pagegenerator accepts four command-line arguments in the\n"
"following order:\n"
"range - the range of page references (maximum 100)\n"
"count - the length of sequence to be generated            \n"
"file  - the name of the output file that will be generated     \n"
"seed  - (optional) the seed to be used for the random number   \n"
"        generator \n"
"\n";




/***************************************************************/
int main(int argc, char * argv[])
{
	int seed = time(NULL);	/*seed for the random number generator*/
	FILE *fp;   /*output file */
	int range;	/*range of page references (max 100) */
	int count;	/*length of sequence to be generated */
	char file_name [256]; /*name of the output file*/
	
	
	/* Checking the input from the command line */
	if(argc < 4 ) 	{
		printf("Error: Invalid number of paramters.\n\n%s", usage);
		exit(1);
	}
	
	/* Get the values of range, count, file name and seed */
	range = atoi(argv[1]);
	if (range > 100 || range < 1 )	{
		printf("Error: Invalid range specification.\n\n%s", usage);
		exit(1);
	}
	count = atoi(argv[2]);
	if (count < 1 ) 	{
		printf("Error: Invalid count specification.\n\n%s", usage);
		exit(1);
	}
	strncpy (file_name, argv[3], 256);
	if (argc >= 5 )
		seed = atoi(argv[4]);
		
	/* Create the output file */
	fp = fopen(file_name, "w");
	if(!fp)
	{
		printf("Error: cannot create file %s\n", file_name);
		exit(1);
	}

	/* Start random number generator with a given seed */
	srand(seed);
	
	/* Write a random sequence to the output file */
	int i = 0;
	int current=0, previous=0;
	for (i = 0; i < count; i++ ) {
		current = random()%range;
		/* make sure we do not have repeated values */
		while (current == previous) 
			current = random()%range;
		previous = current;
		/* write value of current to the output file */
		fprintf ( fp, "%d ", current);
	}

	fclose ( fp );
	
	return 0;

}
