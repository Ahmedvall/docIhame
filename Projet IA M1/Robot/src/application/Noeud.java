package application;

public class Noeud 
{

	int x,y;  // Coordonnées
	int cout;  // g 
	int heuristique;  // h
	Noeud parent;
	
	public Noeud(int x, int y,int cout ,int heuristique) {
		super();
		this.x = x;
		this.y = y;
		this.cout=cout;
		this.heuristique = heuristique;
	}
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public int getCout() {
		return cout;
	}


	public void setCout(int cout) {
		this.cout = cout;
	}
	public int getHeuristique() {
		return heuristique;
	}
	public void setHeuristique(int heuristique) {
		this.heuristique = heuristique;
	}
	
	public Noeud getParent() {
		return parent;
	}


	public void setParent(Noeud parent) {
		this.parent = parent;
	}
	
}
