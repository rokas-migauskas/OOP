class Tilemap {
    //tileset variables
    int rows, columns;   
    int tileCellWidth, tileCellHeight;   
    PImage tileset;   
    PImage tile[];
    String type[];
    int tileNum;
    //grid variables
    int gridRows, gridColumns;
    int grid[][][];
    int gridOffsetX, gridOffsetY;
    int gridCellWidth, gridCellHeight;
    int gridX, gridY;
    
    Tilemap(){
    //initialize variables with default value
    //tileset variables
    tileset =loadImage("default_tiles.png");
    tileCellWidth=32;
    tileCellHeight=32;
    columns=tileset.width/tileCellWidth;
    rows=tileset.height/tileCellHeight;
    tile = new PImage[rows*columns+1];
    type = new String[rows*columns+1];
    //grid variables
    gridRows=20;
    gridColumns=30;
    gridOffsetX=100;
    gridOffsetY=300;
    gridCellWidth=16;
    gridCellHeight=16;
    grid = new int[levelNum][gridColumns][gridRows];
  }
  
  void createTileArray() {
    //one dimensional array to store separate tiles
     for(int i = 0; i < rows; i++){
         for(int j = 0; j < columns; j++){
            tile[i*columns + j] = tileset.get(j*tileCellWidth, i*tileCellHeight, tileCellWidth, tileCellHeight);
            if((i*columns+j) == levelCompleteTile) {
              type[i*columns + j] = "levelComplete"; 
            } else if ((i*columns+j) == chestClosedTile) {
              type[i*columns + j] = "chestClosed"; 
            } else if ((i*columns+j) == chestOpenTile) {
              type[i*columns + j] = "chestOpen"; 
            } else if ((i*columns+j) == clearTile) {
              type[i*columns + j] = "clear"; 
            } else {
              type[i*columns + j] = "safe"; 
            }
            
      }
    }
    //3D array to store tile numbers in grid
     for(int i = 0; i < gridColumns; i++){
         for(int j = 0; j < gridRows; j++){
           for(int k = 0; k < levelNum; k++) {
             grid[k][i][j]=tileNum;
           }
      }
    }
  }
  
  void drawGrid(){
    //displays tileset
    mapImage();
    image(tileset, 0, 0);
    image(tile[tilemap.tileNum], 100, 400, 100, 100);
    //draws grid
    for(int i = 0; i <= gridColumns; i++){
      line(gridOffsetX+gridCellWidth*i, gridOffsetY, gridOffsetX+gridCellWidth*i, gridOffsetY+gridCellHeight*gridRows);
    }
    for(int i = 0; i <= gridRows; i++){
      line(gridOffsetX, gridOffsetY+gridCellHeight*i, gridOffsetX+gridCellWidth*gridColumns, gridOffsetY+gridCellHeight*i);
    }
    //displays tile image in grid
    mapImage();
  
  }
  
  void mapImage(){
    //displays tile in grid
    for(int i = 0; i < gridRows; i++){
      for(int j = 0; j < gridColumns; j++){
        if(grid[editorLevel][j][i] >= 0 && grid[editorLevel][j][i] <= rows*columns){
          image(tile[grid[editorLevel][j][i]], gridOffsetX+j*gridCellWidth+1, gridOffsetY+i*gridCellHeight+1, 15, 15);
        }
      }
    }
  }
}  
