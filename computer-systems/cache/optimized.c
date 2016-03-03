/* 
LAB 2: CACHE LAB

Kaitlin Gu 
Joanna Klukowska
Due Date: 4/18/15
*/ 
#include <stdlib.h>
#include <stdio.h>


#define N	1024
#define DIM     512
#define DIM2    128
#define LARGE   10000

int list[LARGE];

int level_1();
void level_2();
void level_3();
void level_4();

/* Do not change anything above this line */

/***********************************************/

/* Level 1: The function adds 2 then doubles each element in B. 
My improvement was to reverse loop order i.e. I switched i and j. 
This change improves the program's spatial locality since in C, 2D-Arrays are accessed row-major order.
Thus, this improvement allows elements that are in close storage location to be accessed.
When i was incremented in the inner loop, the array was accessed column wise and thus, caused
more cache misses because the jumps in memory access were disparate. */ 

int level_1()
{
  int B[N][N];
  
  int i, j;
  
  for(i = 0; i < N; i++)
    for(j = 0; j < N; j++)
      B[i][j] = 2*(B[i][j] + 2);
   
  /* Do NOT change the the next few lines till the end of this function */  
  i = random () % N;
  j = random () % N;
  
  return(B[i][j]);
  
}

/***********************************************/

/* Level 2: The function sets 0's along the diagonal of array A then adds the sums of the columns in B. 
The original function had bad spatial locality because again, elements in disparate location
were being accessed. In order to get around this, I created a new loop to 0 the diagonal of A. 
And had a seperate loop to make sure that B was accessed in sequential order thus increasing
the program's spatial locality. */ 

void level_2()
{
  
  int temp = 0;
  int i, j;
  int A[DIM][DIM];
  int B[DIM][DIM];
  
/* Put 0's along the diagonal */ 
for (i = 0; i < DIM; i++)
{
	A[i][i] = 0;
}

/* Add B to A */ 
 for(i = 0; i < DIM; i++)
 {
    for( j = 0; j < DIM; j++){
      A[j][j] += B[i][j];
    }

 }

  /* Do NOT change the the next few lines till the end of this function */  
  i = random () % DIM;
  for(j = 0; j < DIM; j++)
    temp += B[i][j];
  
  if( temp == A[i][i] )
    printf("level 2 ... completed!\n");
}
                                 
/***********************************************/

/* Level 3: The function reverses the order of each row.
Again, there was an issue of spatial locality with regard to how c was being accessed. 
By switching how c was accessed (column-wise to row-wise) the cache misses decreased 
because elements are being accessed sequentially. */ 

void level_3()
{
  int i, j;
  int temp;
  int c[N][N];
  
  for( i = 0; i < N; i++){
    for( j = 0; j < N >> 1; j++)
    {
      temp = c[i][j];
      c[i][j] = c[i][N-j-1];
      c[i][N-j-1] = temp;
    }
  }
/* Do NOT change the the next few lines till the end of this function */  
    printf("level 3 ... completed!\n");

}

/***********************************************/

/* Level 4: The function is an implementation of Bubble Sort.
My improvement to this function involves alternates between passing the list up and down 
(therefore alternating between placing the largest element to the end of list and the 
smallest element to the bottom). 
This increases the function's temporal locality: Since we are no longer going back to the 
beginning of the array after each iteration and instead reusing elements already loaded
into the cache, then there fewer cache misses. */

void level_4()
{
  int i, j;
  int temp;
  

for( i = 1; i <= LARGE/2; i++) {
/* Bubble up */ 
    for(j = i-1; j < LARGE - i; j++){
      if( list[j] > list[j+1] )
      {
		    temp = list[j];
	     	list[j] = list[j+1];
	     	list[j+1] = temp;
      }
    }
/* Bubble down */ 
   for (j = LARGE - i - 1; j >= i; j--){
    if( list[j] < list[j-1] )
      {
		    temp = list[j];
	     	list[j] = list[j-1];
	     	list[j-1] = temp;
      }
   	
   }
}
   
/* Do NOT change the the next few lines till the end of this function */  
  i = random () % LARGE;
  if( list[0] < list[i] )
    printf("level 4 ... completed!\n");

}

/***********************************************/

/* Level 5: This program is an example of matrix multplication.
My improvement employed blocking. This improvement decreases the number of cache misses because
instead of looking at the row of one matrix and the columns of another we deal with 
blocks from both matrices. This means that the data loaded from these blocks are more likely
to be reused and thus, the program's temporal locality is increased. The program's spatial
locality is also slightly improved because in the original implementation, each iteration 
accessed two spatially disparate elements (rows vs columns) which are now closer we are dealing 
with a smaller block of data */ 

void level_5()
{
	double A[DIM2*DIM2];
	double B[DIM2*DIM2];
	double C[DIM2*DIM2];
	int n = DIM2;
	int i, j, k;
	int ii, jj, kk;
	for (i = 0; i < n; i += 32){
		for (j=0; j < n; j += 32) {
			for (k=0; k < n; k += 32) {
			/* BxB mini matrix multiplications */
				for (ii = i; ii<i+32; ii++){
					for (jj = j; jj < j + 32; jj++){
						for (kk = k; kk<k+32; kk++){
							C[ii+jj*n] += A[ii+kk*n] * B[kk+jj*n];
			}
		}
	}
	
}
}
}
	        
/* Do NOT change the the next few lines till the end of this function */ 
   printf("level 5 ... completed!\n");

}

/***********************************************/



/* Do not change anything below this line */

int main(int argc, char *argv[])
{
  int i, j;
 
  for(i = 0; i < LARGE; i++)
    list[i] = random() % LARGE;
  
  if( level_1() % 2 == 0)
    printf("level 1 ... completed!\n");
  
  level_2();
  level_3();
  level_4();
	
  for(i = 0; i < LARGE; i++)
    list[i] = random() % LARGE;
 

  level_5( );
  
  
  return 1;
}
