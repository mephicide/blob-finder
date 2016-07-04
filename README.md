# blob-finder

A sample program for finding a 'blob' : a contiguous collection of 1's in a binary integer coordinate space. The space is always a 10 x 10 grid of boolean values (0's or 1's).  A cell is in the blob (there is only one blob in a given space instance) if it is either a '1' or if it is surrounded by 1's.  

e.g.:

<pre>
 0 0 0 0
 1 1 1 0
 0 0 1 0
 1 1 1 0
</pre>

Contains a 3x3 blob in a 4x4 space, but:

<pre>
 0 0 0 0 0
 1 1 1 0 0
 0 0 1 0 0
 0 0 1 0 0
 1 1 1 0 0
</pre>

contains a non-rectangular blob with 8 member cells (all of the 1's) because the 0's in the center are not directly surrounded by 1's.

On the basis of the above understanding this sample program can be built and run as follows:

1. `git clone https://github.com/mephicide/blob-finder.git blob-finder/`
2. `cd blob-finder/`
3. `mvn package`
4. `java -jar target/blob-finder.jar [/path/to/blob-file.txt]`

The optional 4th step can specify a "blob file" where the file is a UTF-8-encoded text file defining the 10x10 blob space containing one blob. Each row is newline delimited, and contains a whitespace-separated sequence of 10 0's or 1's.  The main entrypoint to the application invokes the 'BlobFinder' class which performs its operations on a boolean double array.  The App entrypoint adapts the file format to a boolean double array.

<h3>Assumptions</h3>
* The specification of a blob is as outlined above.
* The blob space would always be square, and contain only one blob.
* Overall computation time does not matter, and cell reads are to be minimized.
* There is a uniform distribution of blob configurations in both size and location such that a given generated blob space will contain a blob about half as big as the whole space ocated about in the middle of the space.
