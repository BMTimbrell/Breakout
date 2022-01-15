import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public final class Game {

	private final List<Entity> entities = new ArrayList<>();
	private  Player player;
	private  Ball ball;
	private final List<Block> blocks = new ArrayList<>();
	private List<Missile> missiles;
	private final List<Entity> entitiesToRemove = new ArrayList<>();
	private final List<Entity> entitiesToAdd = new ArrayList<>();
	private final List<Integer> keysPressed = new ArrayList<>();
	private int score;
	private int level;
	private int numberOfRows;
	private int numberOfCollumns;
	private boolean gameCompleted;
	
	public enum STATE {
		Game,
		GameOver,
	};
	
	public static STATE gameState = STATE.Game;
	
	public static void main(String[] args) {
		Game game = new Game();
		Renderer renderer = new Renderer(game);
		game.start();
		renderer.start();
	}
	
	public void start() {
		newPlayer();
		newBall();
		missiles = player.getMissiles();
		score = 0;
		level = 1;
		gameCompleted = false;
		generateBlocks();
	}
	
	public void addEntity(Entity entity) {
		entitiesToAdd.add(entity);
	}
	
	public void removeEntity(Entity entity) {
		entitiesToRemove.add(entity);
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public List<Block> getBlocks() {
		return this.blocks;
	}
	
	public synchronized void addKey(int key) {
		if (keysPressed.contains(key)) return; //key is already pressed
		keysPressed.add(key);
	}
	
	public synchronized void removeKey(int key) {
		keysPressed.remove((Object)key);
	}
	
	public boolean isKeyPressed(int key) {
		return keysPressed.contains(key);
	}
	
	public void newBall() {
		ball = new Ball((Commons.GAME_WIDTH / 2) - (Commons.BALL_DIAMETER / 2), 300, Commons.BALL_DIAMETER, Commons.BALL_DIAMETER);
		entities.add(ball);
	}
	
	public void newPlayer() {
		player = new Player((Commons.GAME_WIDTH / 2) - (Commons.PLAYER_WIDTH / 2), Commons.GAME_HEIGHT - Commons.PLAYER_HEIGHT, Commons.PLAYER_WIDTH, Commons.PLAYER_HEIGHT);
		entities.add(player);
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, (int)Commons.RESOLUTION.getWidth(), (int)Commons.RESOLUTION.getHeight());
		
		if (gameState == STATE.Game) {
			g.setColor(Color.white);
			g.setFont(new Font("Consolas", Font.PLAIN, 20));
			g.drawString("Level: " + String.valueOf(level), 10, 20);
			g.drawString("Score: " + String.valueOf(score), 120, 20);
		
			for (Entity entity : entities) {
				entity.render(g);
			}
		} else {
			//Game over text
			g.setColor(Color.white);
			g.setFont(new Font("Consolas", Font.PLAIN, 40));
			if (!gameCompleted)
				g.drawString("Game Over", (Commons.GAME_WIDTH / 2) - 110, (Commons.GAME_HEIGHT / 2) - 100);
			else 
				g.drawString("You Win!!", (Commons.GAME_WIDTH / 2) - 110, (Commons.GAME_HEIGHT / 2) - 100);
			//Display score
			g.setFont(new Font("Consolas", Font.PLAIN, 30));
			g.drawString("Your Score: " + String.valueOf(score), (Commons.GAME_WIDTH) / 2 - 120, (Commons.GAME_HEIGHT / 2));
			//Press enter
			g.drawString("Press Enter To Restart", (Commons.GAME_WIDTH) / 2 - 180, (Commons.GAME_HEIGHT / 2) + 100);
		}
	}
		
	public void generateBlocks() {
		if (level == 1) {
			numberOfRows = 7;
			numberOfCollumns = 10;
			boolean isGreenBlock;
			int blockID = 0;
			
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfCollumns; j++) {
					isGreenBlock = (i == 6 && (j == 5 || j == 3)) || (i == 3 && (j == 7 || j == 4) || (i == 0 && j == 5)
							|| (i == 2 && j == 2) || (i == 1 && j == 1));
					if (isGreenBlock) {
						blockID = 2;
					} else {
						blockID = 0;
					}
					blocks.add(new Block((j * Commons.BLOCK_WIDTH) + ((Commons.GAME_WIDTH / 2) - 
							((numberOfCollumns * Commons.BLOCK_WIDTH) / 2)), ((i * Commons.BLOCK_HEIGHT) + (Commons.GAME_HEIGHT / 3)) 
							- (numberOfRows * Commons.BLOCK_HEIGHT), Commons.BLOCK_WIDTH, Commons.BLOCK_HEIGHT, blockID));
				}
			}
		} else if (level == 2) {
			numberOfRows = 9;
			numberOfCollumns = 10;
			boolean isGreenBlock;
			int blockID = 0;
			
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfCollumns; j++) {
					isGreenBlock = (i == 2 && (j == 5 || j == 3)) || (i == 3 && (j == 1 || j == 8) || (i == 1 && (j == 6 || j == 4)));
					if (isGreenBlock) {
						blockID = 2;
					} else if (i == 8 || i == 7) {
						blockID = 1;
					} else {
						blockID = 0;
					}
					blocks.add(new Block((j * Commons.BLOCK_WIDTH) + ((Commons.GAME_WIDTH / 2) - 
							((numberOfCollumns * Commons.BLOCK_WIDTH) / 2)), ((i * Commons.BLOCK_HEIGHT) + (Commons.GAME_HEIGHT / 3)) 
							- (numberOfRows * Commons.BLOCK_HEIGHT), Commons.BLOCK_WIDTH, Commons.BLOCK_HEIGHT, blockID));
				}
			}
		} else if (level == 3) {
			numberOfRows = 10;
			numberOfCollumns = 12;
			boolean isGreenBlock;
			int blockID = 0;
			
			for (int i = 0; i < numberOfRows; i++) {
				for (int j = 0; j < numberOfCollumns; j++) {
					isGreenBlock = (i == 5 && (j == 5 || j == 3)) || (i == 3 && (j == 7 || j == 4));
					if (isGreenBlock) {
						blockID = 2;
					} else if (i == 9 || i == 8 || i == 0 || j == 0 || j == 11) {
						blockID = 1;
					} else {
						blockID = 0;
					}
					blocks.add(new Block((j * Commons.BLOCK_WIDTH) + ((Commons.GAME_WIDTH / 2) - 
							((numberOfCollumns * Commons.BLOCK_WIDTH) / 2)), ((i * Commons.BLOCK_HEIGHT) + (Commons.GAME_HEIGHT / 3)) 
							- (numberOfRows * Commons.BLOCK_HEIGHT), Commons.BLOCK_WIDTH, Commons.BLOCK_HEIGHT, blockID));
				}
			}
		}
		
		for (Block block : blocks) {
			entities.add(block);
		}
	}
	
	//New level
	public void newLevel() {
		level++;
		if (level > 3) {
			gameCompleted = true;
			gameState = STATE.GameOver;
		}
		entities.clear();
		blocks.clear();
		newPlayer();
		newBall();
		generateBlocks();
	}
	
	public void tick() {
		if (gameState == STATE.Game) {
			for (Entity entity : entities) entity.tick(this);
			blocks.removeAll(entitiesToRemove);
			missiles.removeAll(entitiesToRemove);
			entities.removeAll(entitiesToRemove);
			entities.addAll(entitiesToAdd);
			entitiesToAdd.clear();
			entitiesToRemove.clear();
			//Start new level when all blocks have been destroyed
			if (blocks.size() == 0) newLevel();
		} else {
			//Restart game when player presses enter
			entities.clear();
			blocks.clear();
			if (isKeyPressed(KeyEvent.VK_ENTER)) {
				gameState = STATE.Game;
				start();
			}
		}
	}
}
