import java.awt.*;
import java.util.List;
import java.util.Random;

public class Ball extends Entity {

	private final int speed;
	private Player player;
	private Random r;
	private List<Block> blocks;
	private int score;

	public Ball(int x, int y, int width, int height) {
		super(x, y, width, height);

		speed = 5;
		velX = 0;
		velY = speed;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(x, y, width, height);
	}

	@Override
	public void tick(Game game) {
		move();
		checkCollision(game);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void move() {
		x += velX;
		y += velY;
	}

	public void checkCollision(Game game) {

		player = game.getPlayer();
		blocks = game.getBlocks();
		score = game.getScore();

		// Ball Collision with player
		if (getBounds().intersects(player.getBounds())) {

			int playerLPos = (int) player.getBounds().getMinX();
			int ballLPos = (int) this.getBounds().getMinX();

			int first = playerLPos + (Commons.PLAYER_WIDTH / 5);
			int second = playerLPos + ((Commons.PLAYER_WIDTH / 5) * 2);
			int third = playerLPos + ((Commons.PLAYER_WIDTH / 5) * 3);
			int fourth = playerLPos + ((Commons.PLAYER_WIDTH / 5) * 4);

			if (ballLPos < first) {

				this.setVelX(-this.getSpeed());
				this.setVelY(-this.getVelY());
			}

			if (ballLPos >= first && ballLPos < second) {

				this.setVelX(-this.getSpeed() + 3);
				this.setVelY(-this.getVelY());
			}

			if (ballLPos >= second && ballLPos < third) {
				this.setVelY(-this.getVelY());
				r = new Random();
				int randomXDirection = r.nextInt(2);
				if (randomXDirection == 0)
					randomXDirection--;
				setVelX(randomXDirection);
			}

			if (ballLPos >= third && ballLPos < fourth) {

				setVelX(this.getSpeed() - 3);
				setVelY(-this.getVelY());
			}

			if (ballLPos > fourth) {

				setVelX(this.getSpeed());
				setVelY(-this.getVelY());
			}

		}

		// Ball Collision with blocks
		for (Block block : blocks) {

			if (getBounds().intersects(block.getBounds())) {
				int ballLeft = (int) this.getBounds().getMinX();
				int ballHeight = (int) this.getBounds().getHeight();
				int ballWidth = (int) this.getBounds().getWidth();
				int ballTop = (int) this.getBounds().getMinY();

				Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
				Point pointLeft = new Point(ballLeft - 1, ballTop);
				Point pointTop = new Point(ballLeft, ballTop - 1);
				Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

				if (block.getBounds().contains(pointRight)) {
					setVelX(-getVelX());
				} else if (block.getBounds().contains(pointLeft)) {
					setVelX(-getVelX());
				}

				if (block.getBounds().contains(pointTop)) {
					setVelY(-getVelY());
				} else if (block.getBounds().contains(pointBottom)) {
					setVelY(-getVelY());
				}

				if (block.getID() == 1) {
					// Sturdy blocks
					block.setID(0);
				} else {
					// Powerup blocks
					if (block.getID() == 2) game.addEntity(new PowerUp(block.getX(), block.getY(), 10, 10));
					// Removing blocks and incrementing score on collision
					game.removeEntity(block);
					game.setScore(score + 1);
				}
				break;
			}
		}

		// Collision with screen
		if (x <= 0 || x >= Commons.GAME_WIDTH - Commons.BALL_DIAMETER)
			velX = -velX;
		if (y <= 0)
			velY = -velY;

		// Collision with bottoms of screen
		if (y >= Commons.GAME_HEIGHT - Commons.BALL_DIAMETER) {
			Game.gameState = Game.STATE.GameOver;
		}
	}

	public int getSpeed() {
		return speed;
	}
}