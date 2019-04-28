from sys import stdin, stdout
from math import log2, floor

lines = stdin.read().strip().split("\n")
t = int(lines[0])
tb = [int(i) for i in lines[1:]]

for i in range(t):
    stdout.write("{}\n".format(1 + 2*tb[i] - 2**(floor(log2(tb[i])) + 1)))
stdout.flush()
