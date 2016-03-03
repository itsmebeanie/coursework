#include <iostream>     // std::cout
#include <algorithm>    // std::sort
#include <vector>       // std::vector
#include <utility>
#include <string.h>
#include <stdio.h>      /* printf */
#include <math.h>       /* ceil */

using namespace std;

// algorithm A.cc
/**
As Peter is perfectly aware of his abilities he made a table L. Each line of this table represents a
course and each column represents a number of hours Peter is preparing for a particular exam. Element
Lij of the table means, that for i-th exam Peter devotes j-th hours of his available time. It is known that
the more Peter is getting prepared for a particular exam the highest mark he receives, i.e. Lij = Lij+1
for each 1 ≤ i ≤ N ir 1 ≤ j ≤ M − 1.
Suddenly Peter remembers that he has a good friend. You’re actually the person he remembered.
So as he is completely desperate he asks for your help! Help Peter in his mission of passing exams and
achieving the maximum possible average mark.
*/
int term[11][101]; // term[][]
int memo[11][101]; // dp table memo [hours left <= 10][classes = /N]
int N,M;

double solve (int currClass, int hoursleft){
  if (hoursleft<0){
    return -1000000000.0; // fail, return a large negative number (1B)
  }
  if (currClass == N){    // we have reached the last class
    return 0.0; // done and insufficient return;
  }
  if (memo[currClass][hoursleft] != -1){ // if this state has been visited before
    return memo[currClass][hoursleft]; // simply return it
  }
  double maxValue = -1000000000.0;
  // try all possible hours
  int i;
  double temp = 0.0;
  for (i = 0; i < M; i++){
    if (term[currClass][i]>=5){
        temp = term[currClass][i] + solve(currClass+1, hoursleft - i-1);
        if (temp>maxValue){
          maxValue = temp;
        }
    }
  }
  memo[currClass][hoursleft] = maxValue;
  return maxValue;

}
int main(){
  int T;
  scanf("%d", &T);
  while (T-->0){
    //printf("%d\n",T);
    scanf("%d%d",&N,&M);
    //each line represents a course
    //each column represents number of hours
    // Lij->ith exam, j hours
    int i,j;

    // read into matrix
    for(i=0;i<N;i++){
      for (j=0;j<M;j++){
        scanf("%d",&term[i][j]);
        //printf("%d", term[i][j]);
      }
    }
    //Initialize a DP ‘memo’ table with dummy values, e.g. ‘-1’.
    //The dimension of the DP table must be the size of distinct sub-problems.
    memset(memo, -1, sizeof memo); // initialize DP memo table
    double S = solve(0,M);  //top down dp
  //  printf("!!!! %lf", S);
    if (S <= 0.0){
      printf("Peter, you shouldn't have played billiard that much.\n");
    }
    else {
      S = S/N;
      printf("Maximal possible average mark - %.2lf.\n", S + 1e-9);
    }
  }
  return 0;


}
