% Kaitlin Gu
% kg1292@nyu.edu
% Cory Plock - HW 4, 8/2

% 1.) Users
user(@rupikaur).
user(@yrsa).
user(@nayyirahwaheed).
user(@shamayn).
user(@jjorrM).
user(@kaitlin).

% 2.) Follows
follows(@rupikaur,@nayyirahwaheed).
follows(@yrsa, @nayyirahwaheed).
follows(@yrsa, @rupikaur).
follows(@nayyirahwaheed, @kaitlin).
follows(@shamayn, @yrsa).
follows(@jjorrM, @yrsa).
follows(@kaitlin, @jjorrM).
follows(@kaitlin, @shamayn).

% 3.) Tweets
tweet(@rupikaur,1,['you','bet','i','did']).
tweet(@rupikaur,2,['love','is','love']).
tweet(@yrsa,3,['just','be','more','you']).
tweet(@nayyirahwaheed,4,['how','beautiful','love','is']).
tweet(@nayyirahwaheed,5,['it','would','be','love']).
tweet(@shamayn,6,['an','inspiring','saturday']).
tweet(@jjorrM,7,['groove','love','pizza']).
tweet(@kaitlin,8,['it','is','okay']).

% 4.) Retweets
retweet(@kaitlin, 2).
retweet(@shamayn, 2).
retweet(@yrsa, 2).

% 5.) feedhelper (U, F, M, I)
feedhelper(U, F, M, I) :- user(U), follows(U, F), tweet(F, I, M).
feedhelper(U, F, M, I) :- user(U), follows(U, F), retweet(F, I), tweet(_, I, M).
% Given code from assignment
feed(U,M) :- uniquefeed(U,O),remove_ident(O,M).
uniquefeed(U,R) :- setof([I,F|M],feedhelper(U,F,M,I),R).
remove_ident([],[]).
remove_ident([[_|Y]|T1],[H2|T2]) :- Y=H2,remove_ident(T1,T2).

% 6.) search (K, U, M)
search(K,U,M) :- tweet(U,_,M), member(K,M).

% 7.) isviral (S, I, R)
isviral(S, I, R) :- tweet(S,I,_), follows(R,S).
isviral(S, I, R) :- retweet(R,I), follows(R,U), isviral(S,I,U).

% 8.) isviral (S, I, R, M)
isviral(S, I, R, M) :- tweet(S,I,_), follows(R,S), M =< 1.
isviral(S, I, R, M) :- retweet(R, I), follows(R,U), isviral(S, I, U, M-1).
