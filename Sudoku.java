class Sudoku{
  static int [][] exampleBoard = 
                       {{4, 8, 7, 2, 0, 0, 9, 0, 0},
                       {5, 2, 3, 4, 0, 1, 0, 8, 0},
                       {0, 1, 0, 8, 7, 3, 0, 0, 0},
                       {2, 3, 0, 0, 0, 6, 0, 0, 8},
                       {0, 0, 0, 3, 0, 7, 0, 0, 0},
                       {7, 0, 0, 1, 0, 0, 0, 5, 6},
                       {0, 0, 0, 6, 1, 8, 0, 4, 0},
                       {0, 6, 0, 9, 0, 2, 7, 1, 3},
                       {0, 0, 5, 0, 0, 4, 8, 6, 2}};
  public static void main(String [] args) throws java.io.IOException{
    if(args.length == 1){
      switch(args[0]){
        case "-h": case "--h": case "-help": case "--help":
          System.out.println("Usage: java Sudoku [file_name]");
          System.out.println("File must have each row of the sudoku separated by new lines" +
            " and each column separated by a single space character");
          System.out.println("Example:");
          new Sudoku(exampleBoard).printBoard();
          break;
        default:
          new Sudoku(args[0]).solve();
          break;
      }
    }else{
      new Sudoku(exampleBoard).solve();
    }
  }
  int [][] board;
  public Sudoku(int [][] board){
    if(board.length != 9){
      throw new IllegalArgumentException("Board must have 9 rows, has " + board.length);
    }
    for(int i = 0; i < board.length; i++){
      if(board[i].length != 9){
        throw new IllegalArgumentException("Board must have 9 cols in each row, row " + i + " has " + board[i].length + " cols");
      }
    }
    this.board = board;
  }
  public Sudoku(String fileName) throws java.io.IOException{
    java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(fileName));
    StringBuilder sb = new StringBuilder();
    String line;
    while((line = reader.readLine()) != null){
      sb.append(line + "\n"); 
    }
    String [] lines = sb.toString().split("\n");
    if(lines.length != 9){
      throw new IllegalArgumentException("Board must have 9 rows, has " + lines.length);
    }
    board = new int[9][];
    for(int i = 0; i < lines.length; i++){
      String l = lines[i];
      String [] nums = l.split(" ");
      if(nums.length != 9){
        throw new IllegalArgumentException("Board must have 9 cols in each row, row " + i + " has " + nums.length + " cols");
      }
      board[i] = new int[9];
      for(int j = 0; j < nums.length; j++){
        board[i][j] = Integer.parseInt(nums[j]);
      } 
    }
  }
  public void solve(){
    if(solveNext(0, 0)){
      System.out.println("==========SUDOKU SOLVED==========");
    }else{
      System.out.println("==========NO SOLUTION==========");
    }
    printBoard();
  }
  
  class Pair{
    int r, c;
    public Pair(int i, int j){
      this.r = i;
      this.c = j;
    } 
  }

  private Pair findNext(int r, int c){
    for(int i = r; i < board.length; i++){
      int j;
      if(i == r){
        j = c;
      }else{
        j = 0; 
      }
      for(;j < board[i].length; j++){
        if(board[i][j] == 0){
          return new Pair(i, j);
        }
      }
    }
    return null;
  }
  private boolean solveNext(int r, int c){
    Pair nextLoc = findNext(r, c);
    if(nextLoc == null){
      return true; //end of board
    }
    r = nextLoc.r;
    c = nextLoc.c;
    for(int n = 1; n <= 9; n++){
      boolean checkRow = true, checkCol = true, checkSquare = true;
      for(int i = 0; i < board.length; i++){
        if(board[i][c] == n){
          checkRow = false;
        }
      }
      for(int j = 0; j < board[r].length; j++){
        if(board[r][j] == n){
          checkCol = false;
        }
      }
      for(int i = r / 3 * 3; i < r / 3 * 3 + 3; i++){
        for(int j = c / 3 * 3; j < c / 3 * 3 + 3; j++){
          if(board[i][j] == n){
            checkSquare = false;
          }   
        }
      }
      
      if(checkRow && checkCol && checkSquare){
        board[r][c] = n;
        if(solveNext(r,c)){
          return true;
        }else{
          board[r][c] = 0;
        }
      }   
    }
    return false; 
  }

  public void printBoard(){
    for(int i = 0; i < 9; i++){
      System.out.print(board[i][0]);
      for(int j = 1; j < 9; j++){
        System.out.print(" " + board[i][j]);
      }
      System.out.print("\n");
    }
  }
}
