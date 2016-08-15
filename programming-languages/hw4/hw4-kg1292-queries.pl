% Kaitlin Gu
% kg1292@nyu.edu
% Cory Plock - HW 4, 8/2

% Write a query that shows who is following a specified user.
follows(X, @kaitlin).

% Write a query that shows all tweets posted by a specified user.
tweet(@kaitlin, I, M).

% Write a query that shows how many users retweeted a specified tweet.
retweet(X, 2).

%  Write a query that shows a particular user feed.
feed(@kaitlin, M).

% Write a query that searches a keyword in the universe of tweets.
search(love, U, M).

% Write a query that shows if a particular tweet is viral between the sender and a specified receiver.
isviral(@rupikaur, 2, @kaitlin).

% Write a query that shows if a particular tweet is viral between the sender and a specified receiver.
isviral(@rupikaur, 2, @kaitlin, 3).
