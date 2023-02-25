class Platform {
  float x, y, w, h;
  String type;
  float halfWidth, halfHeight;
  int tileNum;
  
  
  Platform(float x, float y, float w, float h, String type, int tileNum) {
    this.w = w;
    this.y = y;
    this.h = h;
    this.x = x;
    this.type = type;
    this.tileNum = tileNum;
    
    halfWidth = w/2;
    halfHeight = h/2;
  }
  Platform() {
    this.w = 0;
    this.y = 0;
    this.h = 0;
    this.x = 0;
    this.type = "clear";
    this.tileNum = 14;
    
    halfWidth = w/2;
    halfHeight = h/2;
  }
  
  void display(){
    image(tilemap.tile[tileNum], x, y, w, h);
  }
}
