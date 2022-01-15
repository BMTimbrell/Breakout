import java.awt.*;

public final class Block extends Entity {

	private int blockID;

	public Block(int x, int y, int width, int height, int blockID) {
		super(x, y, width, height);
		this.blockID = blockID;
	}

	@Override
	public void render(Graphics g) {
		if (blockID == 0)
			g.setColor(Color.red);
		else if (blockID == 1)
			g.setColor(Color.gray);
		else
			g.setColor(Color.green);

		g.fillRect(x, y, width, height);
		
		g.setColor(Color.black);
		((Graphics2D) g).setStroke(new BasicStroke(3));
		g.drawRect(x, y, width, height);
	}

	@Override
	public void tick(Game game) {

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public void setID(int id) {
		this.blockID = id;
	}

	public int getID() {
		return blockID;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
