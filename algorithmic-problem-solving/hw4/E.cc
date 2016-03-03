#include <iostream>     // std::cout
#include <algorithm>    // std::sort
#include <vector>       // std::vector
#include <utility>
#include <string.h>
#include <stdio.h>      /* printf */
#include <math.h>       /* ceil */

using namespace std;

long long modu = 1000000007;

long long i, j, R, G, H;
// Memo table that keeps track of number of ways given a height
long long memo[10000000];
int main(){
  scanf("%lld%lld",&R,&G);
  //printf("%lld,%lld\n",R,G);
  long long total = R+G;
  H = 1;
  // Calculate the height
  // H ( H + 1) / 2 = Sh
  //   H (H+1) =      Sh * 2
  while (H*(H+1) <= (total*2)){
    H+=1;
  }
  //printf("%d\n",N*(N+1));
  //printf("%d\n",total*2);
  if (H*(H+1) > total * 2){
    H-=1;
  }
  memo[0]=memo[1]=1;

  // if its uneven than only these number of blocks can be made
  long long totalPossible = H*(H+1)/2;
  long long ways = 0;
  // iterate through all the possible heights
  for (i = 2; i<=H;i++){
    // iterate through RED pieces
    for(j=R; j>=0;j--){
      // get the difference
      if (j-i>=0){
        memo[j] = (memo[j] + memo[j-i])%modu;
      }
    }
  }

  // if there are remaining green pieces
  for (i=0; i<=R;i++){
    if(totalPossible-i<=G){
      ways = (ways + memo[i]) % modu;
    }
  }
  printf("%lld\n", ways);
  return 0;
}
