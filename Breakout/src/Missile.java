import java.awt.*;
import java.util.List;

public class Missile extends Entity {

	private Rectangle lastBounds;
	private List<Block> blocks;
	private int score;

	public Missile(int x, int y, int width, int height) {
		super(x, y, width, height);

		velY = -4;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(x, y, width, height);
	}

	@Override
	public void tick(Game game) {
		move();
		checkCollision(game);
	}

	@Override
	public Rectangle getBounds() {
		Rectangle bounds = new Rectangle(x, y, width, height);
		Rectangle stretched = lastBounds == null ? bounds : bounds.union(lastBounds);
		lastBounds = bounds;
		return stretched;
	}

	public void move() {
		y += velY;
	}

	public void checkCollision(Game game) {
		blocks = game.getBlocks();
		score = game.getScore();
		
		// Ball Collision with blocks
		for (Block block : blocks) {

			// Missile collision with blocks
			if (getBounds().intersects(block.getBounds())) {
				// Sturdy blocks
				if (block.getID() == 1) {
					block.setID(0);
				} else {
					// Powerup blocks
					if (block.getID() == 2) game.addEntity(new PowerUp(block.getX(), block.getY(), 10, 10));
					// Removing blocks and incrementing score on collision
					game.removeEntity(block);
					game.setScore(score + 1);
				}
				game.removeEntity(this);
				break;
			}
		}
		// Remove missiles that leave the screen
		if (this.getY() < 0) game.removeEntity(this);
	}

}
