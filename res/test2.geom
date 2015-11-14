// Testing the .geom file syntax

point 'A' 100 100
point 'B' 200 200
point 'C' 300 150
line 'l1' A B
line-perpendicular 'p1' l1 C

background #FFFFFF

color #00AAAA
draw A
draw B
draw C

color #000000
draw l1
draw p1



