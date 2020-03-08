# TUMGAD
<!-- favicon by Becris from flaticon.com -->
Exercise generation and helpful materials for the course Introduction to Algorithms and Data Structures.
Made by fellow students from the Technical University of Munich.
## About
TUMGAD is a tool for anyone wanting to learn about the foundations of Algorithms and Data Structures, though it 
was mainly developed for Students of the Technical University of Munich [Course IN0007](https://campus.tum.de/tumonline/WBMODHB.wbShowMHBReadOnly?pKnotenNr=452818).

Furthermore it was created using Lecture Resources of the TUM provided in the summer
semester of 2019 and 2020 as well as the Book [Algorithms and Data Structures: The Basic Toolbox](https://www.springer.com/gp/book/9783540779773).
Hence there may be different definitions in this project than you are used to.

Please be aware that this is still very much a work in progress and a lot of things still have to be figured out.

### How it works
In the source code of TUMGAD all the data strucures and algorithms are already
implemented, as well as a method that takes the products of these components 
and visualizes them using LaTeX, whereby the placeholders in the LaTeX templates 
will be replaced with the generated structures.

Easy example: The implementation for QuickSort generates a random array and converts it
to a string (simple enough) and then that string replaces the placeholder "$INITARRAY$" in the 
exercise template. Later, the LaTeX will be compiled and the output written to PDF. 
## How to Use
There are 2 main ways to use this resource:
1. You can look at the [MarkDown (.md) files](https://sebastianoner.github.io/TUMGAD/src/routes) to find a description on how the Algorithm/Data Structure
works.
2. You can generate exercises and their solutions by executing the main method in the TumgadCLI.java file.
For this the only prerequisite is a working version of [pdflatex](https://www.latex-project.org/get/) added 
to your PATH.

Visit [sebastianoner.github.io/TUMGAD](https://sebastianoner.github.io/TUMGAD)
## Contributing
If you spot an issue you can report it [here](https://github.com/SebastianOner/TUMGAD/issues/new?assignees=&labels=&template=bug_report.md&title=)

If you have an idea for a feature you can submit it [here](https://github.com/SebastianOner/TUMGAD/issues/new?assignees=&labels=&template=feature_request.md&title=)

Or just submit a pull request if you're familiar with GitHub
