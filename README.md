# Cmpe160-Project3

## Explanation of the code:

For this project, I used 3 classes which are Block, Grid and the main class to run the code. I used Block class only to instantiate block objects which have properties like height, position and lake height. Block class mostly consists of getter and setter methods.  
  
In Grid class, I wrote the main algorithm to flood the terrain. In the constructor method of this class, I’ve read from the file and created blocks according to the input file. I have a method floodAll to call the recursive flood method on all blocks except the ones on the edge. In flood method, I checked if the current block had a neighbor with lower height. If there are no neighbor blocks lower than the current block, I marked that block as a lake. If there is a lower neighbor, I called the flood method with that neighbor. If the current block is on the edge, I returned immediately in order to make the water flow through the edge. Also, I excluded the last block visited from all the checks to prevent infinite loops. The floodAll method is also recursive, it calls itself until there are no changes in the terrain.  
  
After determining the blocks that are lakes, I grouped and labeled them using another recursive function. Firstly, I have a method to call the actual method with all blocks in the terrain. In the actual method, I check a lake block’s neighbors whether they are also lakes or not. If they are, I add them to an arraylist of Blocks. Then, I call the method with that neighbor. I also set their labels accordingly during this process.  
  
After grouping the lakes, I calculated lake heights and the final score. The lake height will be equal to the height of the lowest of all blocks that are surrounding the lake. Finally, I calculated the score using the formula given.  
  
In the main class, I took inputs from the user, instantiated the grid and called the methods I mentioned above.  
  
## Example inputs and outputs:  
Input 1: https://drive.google.com/file/d/1-7Q071AUlewPpboGoJXOpOJPRGxYOKjN/view?usp=sharing  
Output1:https://drive.google.com/file/d/1B_qgyGfNkgUjVbpfzXxc1q11Gm3tXWKT/view?usp=sharing  
Input 2: https://drive.google.com/file/d/1eGzgf82NtQYMBIsPU2LKQ7T9odfQxLuk/view?usp=sharing  
Output 2: https://drive.google.com/file/d/1INrw7F6gqz2A1txcvpLG7iHeguCATSs3/view?usp=sharing  
Input 3: https://drive.google.com/file/d/1yDIscsFqtz64dGSeZOXODjBPu3hZgsdv/view?usp=sharing  
Output 3: https://drive.google.com/file/d/1EOY2teqllI6FRlnN_HwNnjhedOesu3YP/view?usp=sharing
