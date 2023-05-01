package test

def s = "12345"

assert s[2] == '3'

def s2 = s.toCharArray()
s2[2] = 'X'
assert s2.toString() == "12X45"

print "ok"