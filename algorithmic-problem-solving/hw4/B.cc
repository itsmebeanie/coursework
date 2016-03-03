#include <iostream>     // std::cout
#include <algorithm>    // std::sort
#include <vector>       // std::vector
#include <utility>
#include <string.h>
#include <stdio.h>      /* printf */
#include <math.h>       /* ceil */

using namespace std;

// memo table contains l and s

// maximum value that can be obtained is 1 + 2 + 3... + 26 for 26 letters
int memo[27][27][352];
int L,S;
int main(){
  // all at 0
  memset(memo, 0, sizeof memo);
  int letter, length, sum;
  int i;

  // each letter has 1 way ofrepresentation
  for (i = 1; i <= 26; i++){
    memo[i][1][i] = 1;
  }
  // for great than 1 letters
  for (letter = 2; letter<=26; letter++){
    for (length = 1; length<=letter; length++){
      // keep going for sum
      for(sum = 1; sum <= 351; sum++){
        memo[letter][length][sum] += memo[letter-1][length][sum];
        if (sum>=letter){
          memo[letter][length][sum] += memo[letter-1][length-1][sum-letter];
        }
      }
    }
  }
  int C = 1;
  int ans;
  while (true){
    scanf("%d%d",&L,&S);
    // terminating case
    if (L == 0 && S ==0){
      break;
    }

    // can't have more than 26 letters or a value greater than 351
    if (L>26 || S > 351){
      ans=0;
    }
    else{
      ans = memo[26][L][S];
    }
    // for 26 letters at length s and l

    printf("Case %d: %d\n",C, ans);
    C++;
  }
  return 0;
}
