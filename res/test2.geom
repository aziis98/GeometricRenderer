// Testing the .geom file syntax

point 'A' 100 100
point 'B' 200 200
line 'l1' A B
line-perpendicular 'p1' l1 A

background #FFFFFF

color #FF0000
draw A
draw B

color #000000
draw l1
draw p1



