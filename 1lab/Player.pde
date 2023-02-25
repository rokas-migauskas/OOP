class Player {
  
  //hitbox and movement variables
  float w, h, x, y, vx, vy;
  float accelerationX, accelerationY, speedLimit;
  //world variables
  float friction, bounce, gravity;
  float defaultFriction, defaultBounce, defaultGravity;
  boolean isOnGround;
  float jumpForce;
  //collision variables
  float halfWidth, halfHeight;
  String collisionSide;
  //animation variables
  boolean facingRight;
  int currentFrame;
  int frameSequence;
  int frameOffset;
  
  Player(){
    //default variables
    w = 45;
    h = 60;
    x = 20;
    y = 600;
    vx = 0;
    vy = 0;
    accelerationX = 0;
    accelerationY = 0;
    speedLimit = 10;
    isOnGround = false;
    
    //world variables
    defaultFriction = 0.96;
    defaultBounce = -0.7;
    defaultGravity = 0.5;
    gravity = defaultGravity;
    bounce = defaultBounce;
    friction = defaultFriction;
    jumpForce = -15;
    
    halfWidth = w/2;
    halfHeight = h/2;
    
    collisionSide = "";
    
    currentFrame = 0;
    facingRight = true;
    frameSequence = 12;
    frameOffset = 0;
  }
  
  void update() {
    if (left && !right){
      accelerationX = -0.2;
      friction = 1;
      facingRight = false;
    }
    if (right && !left){
      accelerationX = 0.2;
      friction = 1;
      facingRight = true;
    }
    if (!left && !right){
      accelerationX = 0;
    }
    
    if (up && !down && isOnGround){
      vy = jumpForce;
      isOnGround = false;
      friction = 1;
      //gravity = 0;
    }
    if (down && !up){
      accelerationY = 0.2;
      friction = 1;
    }
    if (!up && !down){
      accelerationY = 0;
    }
    //no buttons pressed inertia
    if(!up && !down && !left && !right){
      friction = defaultFriction;
      gravity = defaultGravity;
    }
    //adjust velocity
    vx += accelerationX;
    vy += accelerationY;
    
    //friction adjustment
    vx *= friction;
    
    
    //apply gravity 
    vy += gravity;
    
    // max speed enforcement
    if (vx > speedLimit){
      vx = speedLimit;
    }
    if (vx < -speedLimit){
      vx = -speedLimit;
    }
    //gravity stronk
    if (vy > 3 * speedLimit){
      vy = speedLimit;
    }
    //jumping?
    if (vy < -speedLimit){
      //vy = -speedLimit;
    }
    
    // stop decimal calculations
    if (abs(vx) < 0.2){
      vx = 0;
    }
    
    //moving
    //x += vx;
    //y += vy;
    x = Math.max(0, Math.min(x + vx, gameWorld.w - w));
    y = Math.max(0, Math.min(y + vy, gameWorld.h - h));
    
    
    checkBoundaries();
    //checkPlatforms();
    
    
    
  }
  
  void checkBoundaries(){
    //left
    if (x <= 0){
      vx *= defaultBounce;
      //x = 0;
    }
    //right
    if (x >= gameWorld.w - w){
      vx *= defaultBounce;
      //x = gameWorld.w - w;
      facingRight = !facingRight;
    }
    //top
    if (y <= 0){
      vy *= defaultBounce;
      y = 0;
    }
    //bottom
    if (y >= gameWorld.h - h){
      isOnGround = true;
      vy = 0;
      y = gameWorld.h - h;
    }
  }
  
  //testing movement
  void display(){
    
    
    if(facingRight) {
      if (abs(vx) > 0) {
        image(spriteImages[currentFrame], x, y+10);
      } else {
        image(spriteImages[0], x, y+10);
      }
    } else {
      if(abs(vx) > 0) {
        image(spriteImages[currentFrame + 12], x, y+10);
      } else {
        image(spriteImages[25], x, y+10);
      }        
    }
    if(isOnGround) {
      currentFrame = ((currentFrame + 1) % frameSequence)+1;
    } else {
      if(facingRight) {
        currentFrame = 0;
      } else {
        currentFrame = 25;
      }
      currentFrame = ((currentFrame + 1) % frameSequence)+1;
    } 
  }
  
  //check platform collisions
  void checkPlatforms() {
    if(collisionSide == "bottom" && vy >= 0) {
      if(vy > 0) {
        isOnGround = true;
        vy = 0;
      } else if(collisionSide == "top" && vy <= 0) {
        vy = 0;
      } else if(collisionSide == "right" && vx >= 0) {
        vx = 0;
      } else if(collisionSide == "left" && vx <= 0) {
        vx = 0;
      }
      if(collisionSide != "bottom" && vy > 0) {
        isOnGround = false;
      }
    }
  }
}
