package mfpj;

import java.util.ArrayList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class PseudoAngleDemo extends BasicGame {
	
	private int LINE_SIZE;
	private float HORIZONTAL_OFFSET, VERTICAL_OFFSET, mouseX, mouseY;
	private ArrayList<PseudoAnglePoint> points;
	private boolean showPointsPosition, showLineOrOval;
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer container = new AppGameContainer(new PseudoAngleDemo("PSEUDO-ANGLE DEMO by KeizerZilla"));
		container.setDisplayMode(760, 660, false);
		container.setVSync(true);
		container.setShowFPS(false);
		container.start();
	}
	
	public PseudoAngleDemo(String title) throws SlickException {
		super(title);
	}
	
	@Override
	public void init(GameContainer container) throws SlickException {
		LINE_SIZE = 100;
		HORIZONTAL_OFFSET = container.getWidth() / 2;
		VERTICAL_OFFSET = container.getHeight() / 2;
		mouseX = mouseY = 0.0f;
		points = new ArrayList<PseudoAnglePoint>();
		showPointsPosition = true;
		showLineOrOval = true;
	}
	
	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		Input input = container.getInput();
		
		// atualiza posicao do mouse
		mouseX = input.getMouseX() - HORIZONTAL_OFFSET;
		mouseY = input.getMouseY() - VERTICAL_OFFSET;
		
		// adiciona um novo ponto ao clique do mouse
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			points.add(new PseudoAnglePoint(mouseX, mouseY));
		}
		
		// limpa o array de pontos
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			points.clear();
		}
		
		if(input.isKeyPressed(Input.KEY_1) || input.isKeyDown(Input.KEY_NUMPAD1)) {
			showPointsPosition = !showPointsPosition;
		}
		
		if(input.isKeyPressed(Input.KEY_2) || input.isKeyDown(Input.KEY_NUMPAD2)) {
			showLineOrOval = !showLineOrOval;
		}
	}
	
	public void drawHud(Graphics g) {
		g.setColor(new Color(139, 0, 139));
		g.drawString("ACTUAL MOUSE POSITION PSEUDO-ANGLE: " + PseudoAnglePoint.calculatePseudoAngle(mouseX, mouseY), -HORIZONTAL_OFFSET + 2, -VERTICAL_OFFSET);
		g.setColor(new Color(50, 205, 50));
		g.drawString("MOUSE LEFT-BUTTON: ADD NEW POINT", -HORIZONTAL_OFFSET + 2, -VERTICAL_OFFSET + 20);
		g.setColor(new Color(32, 178, 170));
		g.drawString("ESC KEY: CLEAR POINTS", -HORIZONTAL_OFFSET + 2, -VERTICAL_OFFSET + 40);
		g.setColor(new Color(255, 165, 0));
		g.drawString("NUM 1: SHOW POINTS POSITION ON/OFF", -HORIZONTAL_OFFSET + 2, -VERTICAL_OFFSET + 60);
		g.setColor(new Color(178, 34, 34));
		g.drawString("NUM 2: SWAP LINE OR OVAL MARKS", -HORIZONTAL_OFFSET + 2, -VERTICAL_OFFSET + 80);
	}
	
	public void drawSquareReference(Graphics g) {
		g.setColor(Color.white);
		g.drawRect(-1 * (LINE_SIZE / 2), -1 * (LINE_SIZE / 2), LINE_SIZE, LINE_SIZE);
		g.drawLine(0, -LINE_SIZE, 0, LINE_SIZE);
		g.drawLine(-LINE_SIZE, 0, LINE_SIZE, 0);
	}
	
	public void drawPseudoPoints(Graphics g) {
		for(PseudoAnglePoint p : points) {
			// desenha uma linha da origem ateh o ponto
			if(showLineOrOval) {
				g.setColor(Color.red);
				g.drawLine(0, 0, p.getPosition().x, p.getPosition().y);
			} else {
				g.setColor(Color.red);
				g.drawOval(p.getPosition().x, p.getPosition().y, 10, 10);
			}
			
			// desenha informacoes do ponto: vetor posicao e pseudo-angulo associado
			g.setColor(Color.yellow);
			g.drawString(" @: " + p.getPseudoAngle(), p.getPosition().x, p.getPosition().y);
			
			if(showPointsPosition) {
				g.setColor(Color.orange);
				g.drawString("P(" + p.getPosition().x + ", " + p.getPosition().y + ")\n", p.getPosition().x, p.getPosition().y + 20);
			}
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		// centraliza o contexto na tela
		g.translate(HORIZONTAL_OFFSET, VERTICAL_OFFSET);
		// desenha HUD
		drawHud(g);
		// desenha a referencia quadrada e os eixos coordenados
		drawSquareReference(g);
		// desenha cada um dos pontos ateh agora criados
		drawPseudoPoints(g);
	}
	
}
