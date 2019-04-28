from functools import reduce

def is_increasing(lst):
    return reduce(lambda i, j: j if i <= j else 9999999, lst) != 9999999

n = int(input())
tb = [int(i) for i in input().split()]

#print(n)
#print(len(tb))

if(is_increasing(tb)):
    print("YES")
else:
    print("NO")
