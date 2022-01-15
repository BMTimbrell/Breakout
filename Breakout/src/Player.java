import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
	
	private int speed;
	private int missileTimer;
	private int poweredUpTimer;
	private List<Missile> missiles = new ArrayList<>();
	private boolean poweredUp;
	
	Player(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		velX = 0;
		speed = 9;
		missileTimer = 80;
		poweredUp = false;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);	
	}

	@Override
	public void tick(Game game) {
		move();
		
		if (game.isKeyPressed(KeyEvent.VK_A)) {
			if (x <= 0) velX = 0;
			else velX = -speed;
		} else if (game.isKeyPressed(KeyEvent.VK_D)) {
			if (x >= Commons.GAME_WIDTH - Commons.PLAYER_WIDTH) velX = 0;
			else velX = speed;
		} else velX = 0;
		
		if (poweredUpTimer == 0) poweredUp = false;
		
		if (poweredUp) {
			shoot(game);
			poweredUpTimer--;
		}
	}
	
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
	
	public void move() {
		x += velX;
	}
	
	public void shoot(Game game) {
		if (missileTimer == 0) {
			Missile newMissile = new Missile(this.getX(), this.getY(), Commons.MISSILE_DIAMETER, Commons.MISSILE_DIAMETER);
			Missile newMissile2 = new Missile(this.getX() + (Commons.PLAYER_WIDTH - 10), this.getY(), 10, 10);
			missiles.add(newMissile);
			missiles.add(newMissile2);
			game.addEntity(newMissile);
			game.addEntity(newMissile2);
			missileTimer = 80;
		}
		missileTimer--;
	}
	
	public List<Missile> getMissiles() {
		return missiles;
	}
	
	public void setPowerUp(boolean powerUp, int timer) {
		this.poweredUp = powerUp;
		this.poweredUpTimer = timer;
	}
	
}
