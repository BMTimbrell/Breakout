import java.awt.*;

public class PowerUp extends Entity {

	private Player player;

	public PowerUp(int x, int y, int width, int height) {
		super(x, y, width, height);

		velY = 2;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.green);
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
		y += velY;
	}

	public void checkCollision(Game game) {

		player = game.getPlayer();

		// Powerup collision with player
		if (this.getBounds().intersects(player.getBounds())) {
			player.setPowerUp(true, 800);
			game.removeEntity(this);
			game.setScore(game.getScore() + 5);
		}

		if (this.getY() > Commons.GAME_HEIGHT)
			game.removeEntity(this);

	}

}
