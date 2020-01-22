# rubiks-cube-solver

Rubiks Cube Solver in Java!

This program was written to solve the popular Rubiks cube from any starting position. The algorithm uses a staged working of the solution that can be broadly described as below:

1. Solve the top face.
2. Solve the middle face.
3. Solve the center pieces of the 4 bottom sides.
4. Solve the remaining corner pieces of the 4 bottom sides.

It uses brute force to solve each of the phases, and stacks the solutions together to solve the cube!
