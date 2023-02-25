// Rokas Migauskas PS 1 kursas 4gr. 2 pogr.

Tilemap tilemap;
Player p;
Platform[] platform;

int levelCompleteTile, clearTile, chestClosedTile, chestOpenTile;
int points; 
int[] pointsNeeded;

boolean left, right, up, down, space, e, editor, victoryFlag, menu, game;

PImage[] spriteImages;
int frames;

PImage background, victory;
FrameObject camera, gameWorld;
ImageObject backgroundImage;
ImageObject victoryImage;
Button menuButton, editorButton, leaveEditorButton;

int backgroundWidth, backgroundHeight;
int editorLevel, currentLevel, levelNum;

PrintWriter output;

BufferedReader input;

String[] lines;

void setup(){
  size(640, 640);
  editorLevel = 0;
  currentLevel = 0;
  levelNum = 3;
  points = 0;
  pointsNeeded = new int[levelNum];
      
  
  //movement values
  left = false;
  right = false;
  up = false;
  down = false;
  space = false;
  editor = false;
  game = false;
  victoryFlag = false;
  
  menu = true;
  
  //background and camera
  backgroundWidth = 960;
  backgroundHeight = 640;
  background = loadImage("background.png");
  victory = loadImage("victoryscreen.png"); 
  backgroundImage = new ImageObject(0, 0, backgroundWidth, backgroundHeight, background);
  victoryImage = new ImageObject(0, 0, backgroundWidth, backgroundHeight, victory); 
  gameWorld = new FrameObject(0, 0, backgroundWidth, backgroundHeight);
  camera = new FrameObject(0, 0, width, height);
  
  camera.x = (gameWorld.x + gameWorld.w/2) - camera.w/2;
  camera.x = (gameWorld.y + gameWorld.h/2) - camera.h/2;
  
  //player values
  p = new Player();
  frames = 26;
  spriteImages = new PImage[frames];
  for(int i = 0; i < frames; i++) {
    spriteImages[i] = loadImage("Walk"+nf(i)+".png");
  }
  
  
  //tilemap values
  levelCompleteTile = 6;
  clearTile = 14;
  chestClosedTile = 3;
  chestOpenTile = 12;
  tilemap = new Tilemap();
  tilemap.createTileArray();
  
  //platform values
  initPlatforms();
  
  
  //buttons
  menuButton = new Button (200, 200, 100, 50, "Play");
  editorButton = new Button (200, 400, 100, 50, "Editor");
  leaveEditorButton = new Button (100, 200, 100, 50, "Menu");
  
  
  initLevels();
  
}
void initPlatforms(){ 
  platform = new Platform[tilemap.gridColumns*tilemap.gridRows];
  
  for(int i = 0; i < tilemap.gridColumns*tilemap.gridRows; i++) {
      platform[i] = new Platform();
      if(i == levelCompleteTile) {
        platform[i].type = "levelComplete";
      } else if(i == clearTile) {
        platform[i].type = "clear";
      } else if(i == chestOpenTile) {
        platform[i].type = "chestOpen";
      } else if(i == chestClosedTile){
        platform[i].type = "chestClosed";
      } else {
        platform[i].type = "safe";
      }
    }
}

void draw(){
  if(menu) {
    background(255);
    menuButton.drawButton();
    editorButton.drawButton();
  }
  else if(editor) {
    displayEditor();
    leaveEditorButton.drawButton();
  } else if (game) {
    p.update();
    for(int i = 0; i < platform.length; ++i) {
      if(platform[i].type != "clear") {
          p.collisionSide = rectangleCollisions(p, platform[i]);        
          p.checkPlatforms();
          
          if(p.collisionSide != "none" && platform[i].type == "levelComplete" && currentLevel == (levelNum-1) && points >= pointsNeeded[currentLevel]) {
            victoryFlag = true;            
          } else if (p.collisionSide != "none" && platform[i].type == "levelComplete" && points >= pointsNeeded[currentLevel])  {
            currentLevel++;
            p = new Player();
            points = 0;
            initPlatforms();
            inputLevel(currentLevel);
          } else if (p.collisionSide != "none" && platform[i].type == "chestClosed") {
            points++;
            platform[i].tileNum = chestOpenTile;
            platform[i].type = "chestOpen";
          }     
      }
    }
    // move camera
    camera.x = floor(p.x + p.halfWidth - camera.w/2);
    camera.y = floor(p.y + p.halfHeight - camera.h/2);
  
    //camera boundaries
    if(camera.x < gameWorld.x) {
      camera.x = gameWorld.x;
    }
    if(camera.y < gameWorld.y) {
      camera.y = gameWorld.y;
    }
    if(camera.x + camera.w > gameWorld.x + gameWorld.w) {
      camera.x = gameWorld.x + gameWorld.w - camera.w;
    }
    if(camera.y + camera.h > gameWorld.h) {
      camera.y = gameWorld.h - camera.h;
    }
    
    translate(-camera.x, -camera.y);
        
    backgroundImage.display();
    if(victoryFlag == true){
      victoryImage.display();
    }
    displayPositionData();
    p.display();
    
    for(int i = 0; i < platform.length; i++) {
      platform[i].display();
    }        
  }  
}

void displayEditor() {
    background(255);
    tilemap.drawGrid();
    image(tilemap.tile[tilemap.tileNum],50, 100, 64, 64);
    fill(0);
    String s = "tileNum: "+ tilemap.tileNum +" type: "+ tilemap.type[tilemap.tileNum] + "\nlevel :" + (editorLevel+1) + 
    "\nP - next level \nO - previous level \nI - save current level \n U - load current level";
    text(s, 400, 50);

    getLevel();  
}

void getLevel() {
  for(int i = 0; i < tilemap.gridColumns; i++) {
      for(int j = 0; j < tilemap.gridRows; j++) {
        if(tilemap.grid[currentLevel][i][j] != clearTile) {
          if(tilemap.grid[currentLevel][i][j] == levelCompleteTile) {
            platform[i*tilemap.gridRows+j] = new Platform(tilemap.tileCellWidth*i, tilemap.tileCellHeight*j,
            tilemap.tileCellWidth, tilemap.tileCellHeight, "levelComplete", tilemap.grid[currentLevel][i][j]);
          } else if (tilemap.grid[currentLevel][i][j] == chestClosedTile) {
            platform[i*tilemap.gridRows+j] = new Platform(tilemap.tileCellWidth*i, tilemap.tileCellHeight*j,
            tilemap.tileCellWidth, tilemap.tileCellHeight, "chestClosed", tilemap.grid[currentLevel][i][j]);
            
          }  else {
            platform[i*tilemap.gridRows+j] = new Platform(tilemap.tileCellWidth*i, tilemap.tileCellHeight*j,
            tilemap.tileCellWidth, tilemap.tileCellHeight, "safe", tilemap.grid[currentLevel][i][j]);          
          }
                   
        }
      }
    }
}

void displayPositionData() {
  fill(0);
  String s = "\n vx: " + p.vx +"  vy: " + p.vy + "\ncollisionSide: " + p.collisionSide + "\nimg num: "+ p.currentFrame +
  " key code: "+ keyCode + "\nisOnGround: "+ p.isOnGround + " AccelerationX: " + p.accelerationX + " points: "+ points + "\n needed: "+ pointsNeeded[currentLevel];
  text(s, 400, 50);
}

String rectangleCollisions(Player player, Platform platform) {
  //returns collisionSide
  
  //distances between centers
  float dx = (player.x + player.w / 2) - (platform.x + platform.w/2);
  float dy = (player.y + player.h / 2) - (platform.y + platform.h/2);
  
  //max possible distance without overlap
  float combinedHalfWidths = player.halfWidth + platform.halfWidth;
  float combinedHalfHeights = player.halfHeight + platform.halfHeight;
  
  if(abs(dx) < combinedHalfWidths) {
    if(abs(dy) < combinedHalfHeights) {
      //collision happened
      float overlapX = combinedHalfWidths - abs(dx);
      float overlapY = combinedHalfHeights - abs(dy);
      //collision happens on the axis with smallest overlap
      if(overlapX >= overlapY) {
        if(dy > 0) {
          //move hitbox back to eliminate overlap before calling display
          player.y += overlapY;
          return "top";
        } else {
          player.y -= overlapY;
          player.isOnGround = true;
          return "bottom";
        }
      } else {
        if(dx > 0) {
          player.x += overlapX;
          return "left";
        } else {
          player.x -= overlapX;
          return "right";
        }
      }
    } else {
      //no collision on y axis
      return "none";
    }
  } else {
    //no collision on x axis
    return "none";
  }
  
  
}

void countPointsNeeded() {
  for(int i = 0; i < tilemap.gridColumns; i++) {
      for(int j = 0; j < tilemap.gridRows; j++) {
        for(int k = 0; k < levelNum; k++) {
            if (tilemap.grid[k][i][j] == chestClosedTile) {
            pointsNeeded[k]++;
          }
        }        
      }
    }
}
void mouseClicked() {
  if(editor) {
    if(mouseX < tilemap.tileset.width && mouseY < tilemap.tileset.height) {
    tilemap.tileNum = mouseX/32+mouseY/32*tilemap.columns;
    }
    if(mouseX > tilemap.gridOffsetX && mouseY > tilemap.gridOffsetY &&
    mouseX < tilemap.gridOffsetX+16*tilemap.gridColumns && mouseY < tilemap.gridOffsetY+16*tilemap.gridRows){
      tilemap.gridX = (mouseX - tilemap.gridOffsetX)/16;
      tilemap.gridY = (mouseY - tilemap.gridOffsetY)/16;
      tilemap.grid[editorLevel][tilemap.gridX][tilemap.gridY] = tilemap.tileNum;
    }
  }
  if(menu) {
    if(mouseX > menuButton.x && mouseX < menuButton.x + menuButton.w && mouseY > menuButton.y && mouseY < menuButton.y + menuButton.h) {
      game = true;
      editor = false;
      menu = false;
      initLevels();
      countPointsNeeded();
    }
    if(mouseX > editorButton.x && mouseX < editorButton.x + editorButton.w && mouseY > editorButton.y && mouseY < editorButton.y + editorButton.h) {
      editor = true;
      menu = false;
      game = false; 
    }
  }
  if(editor) {
    if(mouseX > leaveEditorButton.x && mouseX < leaveEditorButton.x + leaveEditorButton.w && mouseY > leaveEditorButton.y && mouseY < leaveEditorButton.y + leaveEditorButton.h) {
      editor = false;
      menu = true;
      game = false; 
    }
  }
}

void keyPressed(){
  switch (keyCode){
    case 37: //left
      left = true;
      break;
    case 39: //right
      right = true;
      break;
    case 38: //up
      up = true;
      break;
    case 40: //down
      down = true;
      break;
    case 32: //space
      space = true;
      break;
    case 69: //e
      editor = true;
      e = true;
      break;
    case 88: //x
    if(game && victoryFlag) {
      victoryFlag = false;
      currentLevel = 0;
      initPlatforms();
      menu = true;
      game = false;
    }
      
      
      break; 
    case 79: //o
      if(editor && editorLevel > 0) {
        editorLevel--;
        break;
      } else {
        break;
      }
    case 80: //p
      if(editor && editorLevel < (levelNum-1)) {
        editorLevel++;
        break;
      } else {
        break;
      }
    case 73: //i, save
      if(editor) {
        outputLevel();
        break;
      } else {
        break;
      }
    case 85: //u, load
      if(editor) {
        inputLevel(editorLevel);
        break;
      } else {
        break;
      }
     
      
  }
}

void initLevels() {
  for(int i = 0; i<levelNum; i++)  {
    inputLevel(i);
  }
}

void inputLevel(int level) {
  lines = loadStrings("level"+(level+1)+".txt");
  for(int i = 0; i < lines.length; i++){
    tilemap.grid[level][i] = int(split(lines[i], ' '));
  }
  getLevel();  
}

void outputLevel() {
  output = createWriter("level"+(editorLevel+1)+".txt");
  for(int i = 0; i < tilemap.gridColumns; i++) {
    for(int j = 0; j < tilemap.gridRows; j++) {
      output.print(tilemap.grid[editorLevel][i][j] + " ");
    }
    output.print("\n");
  }
  output.flush();
  output.close();
}

void keyReleased(){
  switch (keyCode){
    case 37: //left
      left = false;
      break;
    case 39: //right
      right = false;
      break;
    case 38: //up
      up = false;
      break;
    case 40: //down
      down = false;
      break;
    case 32: //space
      space = false;
      break;
    
  }
}



 
