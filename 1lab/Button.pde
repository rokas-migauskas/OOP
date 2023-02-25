class Button {
  int x, y, w , h;
  String text;
  boolean isHovered;

  
  Button(int x, int y, int w, int h, String text){
    
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    isHovered = false;
    this.text = text;
  }
  

  void drawButton() {
    if(mouseX > x && mouseX < x+w && mouseY > y && mouseY < y+h){
      isHovered = true;
    } else {
      isHovered = false;
    }
    if(this.isHovered == true){
      fill(100);
    } else {
      fill(50);
    }
    
    rect(x, y, w, h);
    fill(200);
    textSize(20);
    text(text, x+w/2, y+h/2, w, h);
    
    
  }
}
