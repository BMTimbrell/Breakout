import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public final class Renderer extends JPanel {
	
	private final Game game;
	
	public Renderer(Game game) {
		this.game = game;
		
		setMinimumSize(Commons.RESOLUTION);
		setPreferredSize(Commons.RESOLUTION);
		setMaximumSize(Commons.RESOLUTION);
		setSize(Commons.RESOLUTION);
	}
	
	public void paintComponent(Graphics g) {
		game.render(g);
	}
	
	public void start() {
		JFrame frame = new JFrame("Breakout");
		frame.setLayout(new BorderLayout());
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				game.addKey(e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				game.removeKey(e.getKeyCode());
			}
			
		});
		
		frame.setVisible(true);
		
		final Renderer r = this;
		
		new Thread(() -> {
			while(true) {
				long start = System.currentTimeMillis();
				int time = (1000 / 60) - (int) (System.currentTimeMillis() - start);
				game.tick();
				SwingUtilities.invokeLater(() -> {
					r.repaint();
				});
				
				try {
	                Thread.sleep(time < 0 ? 0 : time);
	            } catch (InterruptedException e) {
	                throw new RuntimeException(e);
	            }
			}
		}).start();	
	}
}
