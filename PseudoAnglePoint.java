package mfpj;

import org.newdawn.slick.geom.Vector2f;

public class PseudoAnglePoint {
	
	private Vector2f position;
	private float pseudoAngle;
	
	public PseudoAnglePoint(float x, float y) {
		this.position = new Vector2f(x, y);
		this.pseudoAngle = calculatePseudoAngle();
	}
	
	public Vector2f getPosition() {
		return position;
	}

	public void setPosition(Vector2f position) {
		this.position = new Vector2f(position);
	}

	public float getPseudoAngle() {
		return pseudoAngle;
	}

	public void setPseudoAngle(float pseudoAngle) {
		this.pseudoAngle = pseudoAngle;
	}
	
	// coracao da classe
	// calcula o pseudo-angulo de uma certa coordenada (x, y)
	// statica para uso exterior a classe
	public static float calculatePseudoAngle(float x, float y) {
		if(y >= 0) {
			if(x >= 0) {
				if(x >= y) {
					return y / x;
				} else {
					return 2 - x / y;
				}
			} else if(-x <= y) {
				return 2 + (-x) / y;
			} else {
				return 4 - y / (-x);
			}
		}
		if(x < 0) {
			if(-x >= -y) {
				return 4 + (-y) / (-x);
			} else {
				return 6 - (-x) / (-y);
			}
		}
		if(x <= -y) {
			return 6 + x / (-y);
		} else {
			return 8 - (-y) / x;
		}
	}
	
	// usado no construtor
	public float calculatePseudoAngle() {
		return calculatePseudoAngle(position.x, position.y);
	}
	
	
	public static int relativePosition(Vector2f a, Vector2f b) {
		float result = calculatePseudoAngle(a.x, a.y) - calculatePseudoAngle(b.x, b.y);
		if(result > 0) {
			return 1;
		} else if(result < 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
