class ImageObject {
  float h, w, x, y;
  PImage img;
  float halfWidth, halfHeight;
  
  ImageObject(float x, float y, float w, float h, PImage img) {
    this.w = w;
    this.h = h;
    this.x = x;
    this.y = y;
    this.img = img;
    
    halfWidth = w/2;
    halfHeight = h/2;
  }
  
  void display() {
    image(img, x, y);
    //image(img, x+w, y);
    //image(img, x+2*w, y);
  }
}
