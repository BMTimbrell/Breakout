import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {
	
	protected int x, y, width, height, velX, velY;
	
	public Entity(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public abstract void render(Graphics g);
	public abstract void tick(Game game);
	public abstract Rectangle getBounds();
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getVelX() {
		return velX;
	}
	
	public int getVelY() {
		return velY;
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}
	
	public void setVelY(int velY) {
		this.velY = velY;
	}

}
