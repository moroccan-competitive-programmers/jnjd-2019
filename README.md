# Jnjd 2018 Editorial

## Introduction

- Contest was held like every year in `INPT`, with over 50 teams qualified to finals

## Miller and sequences

- **Author**: Youssef izikitne
- **Description**:
    - Given an array of numbers determine if it's increasing
- **Solution**
    - This was the easiest problem, you can do a linear scan, checking each integer with previous let's call it
    `last` and if `last > a[i]` then the sequence is not increasing, otherwise it is .
- **Complexity**:
    - `O(N)`


## Nizar And Grades

- **Author**: Morthada touzi
- **Description**:
    - Given an array find the number of elements  that are different from the min and max
- **Solution**
    - Another easy problem, just find `min` and `max` of the array, than have a `counter = 0` and scan the array, if an `a[i] != min && a[i] != max` then `counter++` .
    - make sure to take only **distinct** values .
- **Complexity**:
    - `O(N)`

## Joe and friends

- **Author**: Youssef izikitne
- **Description**:
  - it's a classical problem called `josephus problem` with `k = 1`
- **Solution**
  - You have to solve the math problem so you can come up with the recursion
- **Complexity**:
    - `O(log(N))`

## Lazy miller
- **Author**: Issam baskati
- **Description**:
    - Problem statement is pretty clear, given a list of subjects with their difficulties, hourly volume and allowed number
  of hours to absent, and number of hours that miller wants to absent, you need to find the minimum sum of difficulties of exams that he have to retake .

- **Solution**
    - This problem can be seen as the standard `Knapsack` problem .
    - To eliminate the case where the minimum sum is `0`, just do the sum of the allowed number of hours and if it's bigger than `M`, then the answer is `0`.
    - otherwise you can define a function `f(i, m)` where i is the index of the current subject, and m is the number of hours left, so we can define a recurrence like this:
    ```java
    // base case being m <= 0 ? 0: INF
    ans = INF;
    ans = min(
        ans,
        f(i + 1, m - a[i])
    );
    ans = min(
        ans,
        f(i + 1, m - h[i]) + d[i]
    );
    ```
- **Complexity**:
    - `O(N * M)`

## Dead code analysis

- **Author**: SGATS
- **Description**:
    - Given a set of classes and dependencies between them, and `Q` queries each query is in the form of `u v` meaning add a dependency between `u` and `v`, for each query output the number of unreachable nodes from node `1`

- **Solution**
    - The problem can be seen as a graph of dependencies, where each class represents a node, an each query can represent the addition of a new edge in this graph.
    - To simplify the answer, we will find the number of nodes reachable from `1` and since the total number of nodes is fixed the answer will be simply `N - count`.
    - Now the problem is pretty standard, we can use `Union find` datastructure to compute the size of each `Connected Component`, and we have to keep track of the size of the component containing `1` .

- **Complexity**:
    - `O(Q * log(N))`

- **Challenge**: Can you solve the problem with the addition of another type of queries: `u v` remove edge between u and v ?

## World war II

- **Author**: Morthada touzi
- **Description**:
    - Given a string containing lowercase characters, and value of each alphabet character, find the biggest value of a palindromic subsequence

- **Solution**
    - The problem is just straightforward Dynamic programming, you can build a function `f(i, j)` that computes the answer for
array between `i` and `j`, the recurrence is quite easy to get
  ```java
    // try skip right, and skip left
    ans = max(
        f(i, j - 1),
        f(i + 1, j),
    );

    // try to include the palindromic subsequence containing i and j
    if (s[i] == s[j]) {
      ans = max(
          ans,
          2 * val[s[i] - 'a'] + rec(i + 1, j - 1)
      );
    }
  ```
  - one interesting thing to notice was that the answer can never be smaller that `0`, as the empty string is considered a palindromic subsequence, and thus this makes our base case to be.

  ```java
  if(i == j) return max(0, val[s[i] - 'a']);
  ```

- **Complexity**:
    - `O(N^2)`

## Houda and Array Problem

- **Author**: Mehdi
- **Description**:
    - Given an array, you need to perform 2 types of queries on it:
      - Change the value at a given index
      - Compute the number of values that are strictly smaller than a given value `v`

- **Solution**
  - This problem can be solved with different ways, i'll explain the easiest .
  - We can split the array into `buckets` of size `X` and precalculate the answer for each `bucket`. every update operation will only change the answer for one given `bucket`, and for the query, we will need to traverse at most `N/X` bucket to know the final answer. only question left now, is how to choose `X` ?  *SQRT decomposition*
  - For each `bucket` keep an array of sorted items in it so we can use `binary search` to find the number of elements strictly smaller than a value `v` in `O(log(sqrt(N)))` time .
  - each update, we will need to find the element with the given index in the `bucket` and update it, and then do a sort in
  `O(sqrt(N) * log(sqrt(N)))` or simply do swaps to put the element in his position because we only change one element at a time, with a complexity of `O(sqrt(N))`.

- **Challenge**: find other ways to solve it 😂
- **Complexity**:
    - `O(sqrt(N) * log(sqrt(N)) * Q)`

## Ibrahim and tree

- **Author**: Khaled Sliti
- **Description**:
  - Given an tree, where each edge have a number assigned to it, you need to answer `Q` queries
    - `L R u v` can we traverse along the simple path bewtween `u` and `v` where all edges in the path have numbers between `L` and `R`

- **Solution**
  - You can solve the problem in 2 ways:
  - The first way is using **HLD** to decompose the tree into heavy and light edges, and have a `Segment tree` for each path,
and with that we can answer the queries in `O(log(N) ^ 2)`, but that would be **overkill** 😅
  - Since we don't have updates, we can solve it using regular old `sparse table` like approach .
  - compute 3 matrices: `parent`, `min` and `max`
  - each matrix will contain : information for level `2^i` for vertex u, an example would be `parent[1][u]` is the ancestor of u at level `2`, same thing with `min` and `max`, but they will contain the maximum encountered edge value for node `u` at level `2^i` .
  - Now for each query we do the same procedure for finding the `lca(u, v)`, but for each jump we keep the `min` and `max` over all jumps.
  - if the min and the maximum are bounded by `L` and `R` we say that we can, otherwise we know that there is an edge that is bigger or smaller the `R` or `L` accordingly .

- **Complexity**:
    - `O(log(N) * Q)`


