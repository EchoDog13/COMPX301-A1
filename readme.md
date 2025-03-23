COMPX301 Assignment 1: 2 Way Merge Sort

Completed by: Kyle Barker (ID: 1316197)

Program works by creating two inital runs using Heap.java. The output of the heaps alternative between the two files, and each heap is the size of the run length.

Once the min heap sort stage is complete, the Merge.java file continually merges, comparing between the files until the order of each run breaks. It will switch the input and output files are continue merging. This will hapen until

Please note that tempFile_1, tempFile_2, tempFile_3, tempFile_4, will only reflect the final stages of the merges as the program constantly overwrites the files as they switch between being input and output files.

The final sorted output is set to standard out. The final output will also remove any additional blank lines from the original files as these can not be sorted lexicographically.

Usage:

cat <inputFile> | java XSort <runLength> <k-way-sort> ><outputFile>
