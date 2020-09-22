package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SceneController
{

    @FXML
    private Button start;

    @FXML
    private ImageView scene;

    @FXML
    private Pane ScenePane;

    @FXML
    private Button depart;

    @FXML
    private Label nbrNoeudp;

    @FXML
    private Label nbrNoeudpm;
    
    @FXML
    private Label nbrNoeud;
    
    @FXML
    private Button demoBtn;
    
    @FXML
    private Pane pane1;
    
    @FXML
    private Pane pane2;
    
    @FXML
    private Pane pane3;
    
    
    Noeud arrive = null;
    Noeud che;
    ArrayList<Noeud> ch = new ArrayList<>();
    int nnpm,nnp;
    int Compteur=1;

    int ximg=2000 - (95-20)*2;
	int yimg=2000 - (95-20)*2;
	
	
    @FXML
    void algo_Aetoile(ActionEvent event) throws IOException 
    {
 	
    	Noeud objectif = new Noeud(150, 150, 0,0);
		Noeud departt = new Noeud(1860, 1860, 0, 100);
		departt.setParent(null);
		Plus_Cour_Chemin(departt, objectif);
		che=arrive;
		while(che != null)
		{
			ch.add(che);
			che = che.getParent();
		}
		
		nnpm=ch.size();
		nbrNoeudp.setText(""+nnp);
		nbrNoeudpm.setText(""+nnpm);
		
		pane1.setVisible(true);
		pane2.setVisible(true);
		demoBtn.setVisible(true);
		
		
    }
    
    @FXML
    void demonstration(ActionEvent event) throws InterruptedException 
    {
    	if(!pane3.isVisible()) pane3.setVisible(true);
    	
    		if(ch.size() != 1)
    		{
 			
    			int x = ch.get(ch.size()-1).getX(); // ch.get(ch.size()-1).getX();
    			int y = ch.get(ch.size()-1).getY(); // ch.get(ch.size()-1).getY();

					if(x < ch.get(ch.size()-2).getX())
					{
						x=ch.get(ch.size()-2).getX();
						demo("right");
						
					}
					
					if(x > ch.get(ch.size()-2).getX())
					{
						x=ch.get(ch.size()-2).getX();
						demo("left");
					}
					
					if(y < ch.get(ch.size()-2).getY())
					{
						y=ch.get(ch.size()-2).getY();
						demo("down");
					}
					
					if(y > ch.get(ch.size()-2).getY())
					{
						y=ch.get(ch.size()-2).getY();
						demo("up");
					}
					
					ch.remove(ch.get(ch.size()-1));
					Compteur++;
					nbrNoeud.setText(""+Compteur);
 			
    		}
    		else
    				System.out.println("impossible");
    }
    
    
 // cette    
    public void demo (String direction)
    {  	
    	double x= depart.getTranslateX();
	    double y= depart.getTranslateY();
    	Node nodeDepart = depart;
    		
    	if(direction == "up")
    	{
    		y-=30;
        	nodeDepart.setTranslateY(y);
    	}
    	
    	if(direction == "down")
    	{
    		y+=30;
        	nodeDepart.setTranslateY(y);
    	}
    	if(direction == "left")
    	{
    		x-=30;
    	    nodeDepart.setTranslateX(x);
    	}	
    	if(direction == "right")
    	{
    		x+=30;
    	    nodeDepart.setTranslateX(x);
    	}
    }
    


    boolean chemin_libre(int x , int y) throws FileNotFoundException
    {
    	Image imgg = new Image(new FileInputStream("src/application/Labyrinth_Robot.jpg"));
    	
        Color color = imgg.getPixelReader().getColor(x,y);
        double red =  color.getRed();
        double blue=  color.getBlue();
        double green= color.getGreen();

        Color c = Color.WHITE;
        double redc =  c.getRed();
        double bluec=  c.getBlue();
        double greenc= c.getGreen();
        
        if (red == redc && blue == bluec && green == greenc)
        	return true; // white
        else
        	return false; // black
  
    }

    
    public void Plus_Cour_Chemin(Noeud depart, Noeud objectif) throws FileNotFoundException
    {
    	 // L'ensemble des noeuds déjà évalués 
        ArrayList<Noeud> Noeud_Evalues = new ArrayList<>();

        // L'ensemble des nœuds actuellement découverts qui n'ont pas encore été évalués. 
        // Initialement, seul le nœud de départ est connu. 
        ArrayList<Noeud> Noeud_Decouvert = new ArrayList<>();
        Noeud_Decouvert.add(depart);
        // Pour chaque nœud, à partir de quel nœud il peut être atteint de la manière la plus efficace. 
        // Si un nœud peut être atteint à partir de nombreux nœuds, arrive contiendra éventuellement l' 
        //étape la plus efficace. 
        Noeud courant;
        boolean existe1=false , existe2=false;
        int cout;
        System.out.println("depart: "+depart.getX() +"  "+depart.getY());
        System.out.println("objectif: "+objectif.getX() +"  "+objectif.getY());
        System.out.println("--------------------------------------------------");
        while (!Noeud_Decouvert.isEmpty()) 
        {
        	int minCout = Noeud_Decouvert.get(0).getCout() + Noeud_Decouvert.get(0).getHeuristique();
        	int indice_Noeud=0;
        	for (int i = 1; i < Noeud_Decouvert.size(); i++) 
        	{
				if(minCout > Noeud_Decouvert.get(i).getCout() + Noeud_Decouvert.get(i).getHeuristique())
				{
					minCout = Noeud_Decouvert.get(i).getCout() + Noeud_Decouvert.get(i).getHeuristique();
					indice_Noeud=i;
				}
			}
        	//System.out.println(minCout);
        	
        	courant=Noeud_Decouvert.get(indice_Noeud);
        	System.out.println("courant: "+courant.getX() +"  "+courant.getY());
        	
        	if(courant.getX() == objectif.getX() && courant.getY() == objectif.getY())
        	{
        		arrive = courant;
        		nnp=Noeud_Evalues.size();
        		Construire_chemain(arrive,Noeud_Evalues);
        		break;
        	}
        	
        	Noeud_Evalues.add(courant);    	
        	Noeud_Decouvert.remove(indice_Noeud);
        	
        	ArrayList<Noeud> voisin = get_Voisin(courant);


        	for (int i = 0; i < voisin.size(); i++) 
        	{
        		for (int j = 0; j < Noeud_Evalues.size(); j++) 
        		{
        			if(voisin.get(i).getX() == Noeud_Evalues.get(j).getX() && voisin.get(i).getY() == Noeud_Evalues.get(j).getY() )
        			{
        				existe1 = true;
        			}
				}
        		
        		if(existe1 == true) existe1 = false;
        		else
        		{
        			cout = courant.getCout() + 95;
        			//cout = 95 + 95;
        	        		//System.out.println("*** "+cout);
        	        		
        		        		for (int k = 0; k < Noeud_Decouvert.size(); k++) 
        		        		{
        		        			if(voisin.get(i).getX() == Noeud_Decouvert.get(k).getX() && voisin.get(i).getY() == Noeud_Decouvert.get(k).getY())
        		        			{
        		        				existe2 = true;
        		        			}
        						}
        		        		
        		        		if(existe2 != true)
        		        			Noeud_Decouvert.add(voisin.get(i));
        		        		else
        		        		{
        		        			existe2 = false;
//        		        			if(courant.getCout() == 0)
//        		        				{
//        		        					//voisin.get(i).setCout(courant.getCout() + voisin.get(i).getCout());
//        		        					voisin.get(i).setHeuristique(courant.getCout() + 95 +voisin.get(i).getHeuristique());
//        		        					//arrive.add(voisin.get(i));
//        		        					arrive.add(courant);
//        		        				}
//        		        				System.out.println();
        		        			if (cout < voisin.get(i).getCout()) 
        		        			{
        		        				System.out.println("hihi");
//        		        				voisin.get(i).setCout(cout);
//        		        				// il faut verifier le vraie cout
        		        				//arrive= voisin.get(i);

        							}
        		        			
        		        		}
        		        			
        		}
        		
        		
				
			}

        	
		}
        
    }
    
    
    public void Construire_chemain(Noeud chemain, ArrayList<Noeud> noeudParcourue) throws FileNotFoundException
    {
    	
    	System.out.println("----------> "+ noeudParcourue.size());
    	
    	Image img = new Image(new FileInputStream("src/application/Labyrinth_Robot.jpg"));
    	
		int width = (int)img.getWidth();
		int height = (int)img.getHeight();
		WritableImage wImage = new WritableImage(width, height);
		PixelWriter writer = wImage.getPixelWriter();
		Color color ;
        double red;
        double blue;
        double green;
        
        Color c = Color.WHITE;;
        double  redc =  c.getRed();;
        double bluec=  c.getBlue();
        double greenc= c.getGreen();;
        Noeud ch;
               
        
//        for (int i = 0; i < noeudParcourue.size(); i++) 
//        {
    	for (int i = 0; i < noeudParcourue.size(); i++) 
        {
  		color = img.getPixelReader().getColor(noeudParcourue.get(i).getX(),noeudParcourue.get(i).getY());
  		  red =  color.getRed();
	    	  blue=  color.getBlue();
	    	  green= color.getGreen();
  		if (red == redc && blue == bluec && green == greenc) {
  			for (int x = noeudParcourue.get(i).getX() - 30; x < noeudParcourue.get(i).getX() +30; x++) 
		    	      for (int y = noeudParcourue.get(i).getY() - 30; y < noeudParcourue.get(i).getY() + 30; y++) 
		    	      {
		    	    	  writer.setColor(x, y, Color.BLUE);
		    	      }
			}
        }
    	
    	
			 ch = chemain;
		    	while (ch != null) 
		    	{
		    		
		    			for (int x = 0; x < width; x++) {
		    	      for (int y = 0; y < height; y++) {
		    	    	  
		    	    	  color = img.getPixelReader().getColor(x,y);
		    	    	  red =  color.getRed();
		    	    	  blue=  color.getBlue();
		    	    	  green= color.getGreen();
		    	    	  if (red == redc && blue == bluec && green == greenc)
		    	    	  {
//		    	    		  if(noeudParcourue.get(i).getX() == ch.getX() && noeudParcourue.get(i).getY() == ch.getY())
//		    	    		  {
		    	    			  if(y <= ch.getY() +30 &&  y >= ch.getY()-30)
			    	    			  if(x <= ch.getX() +30 &&  x >= ch.getX()-30)
			    	    				  	writer.setColor(x, y, Color.RED);
		    	    		  //}
		    	    		  
		    	    		 
		    	    	  }
		    	    	  else
		    	    		  writer.setColor(x, y, color);
		    	          
		    	      }
		    	  }
		        	
		    				ch=ch.getParent();		    		
				}

    	scene.setImage(wImage);

    }
    
    public ArrayList<Noeud> get_Voisin(Noeud n) throws FileNotFoundException
    {
    	ArrayList<Noeud> voisin = new ArrayList<>();
    	Noeud noeud=null;
    	Random rand = new Random();
    	int h_Aleatoire;
    	
    	if(Nord(n.getX(), n.getY())) 	
    	{   
    		//System.out.println("Nord");
    		h_Aleatoire = rand.nextInt(95)+95;
    		noeud = new Noeud(n.getX(), n.getY()-95, n.getCout()+95, n.getCout()+95 +h_Aleatoire );
    		noeud.setParent(n);
    		voisin.add(noeud);
    	}
    	
    	if(Sud(n.getX(), n.getY())) 
    	{
    		//System.out.println("Sud");
    		h_Aleatoire = rand.nextInt(95)+95;
    		noeud = new Noeud(n.getX(), n.getY()+95, n.getCout()+95, n.getCout()+95 +h_Aleatoire);
    		noeud.setParent(n);
    		voisin.add(noeud);
    	}
    	
    	if(Est(n.getX(), n.getY())) 	
    	{
    		//System.out.println("Est");
    		h_Aleatoire = rand.nextInt(95)+95;
    		noeud = new Noeud(n.getX()+95, n.getY(), n.getCout()+95, n.getCout()+95 +h_Aleatoire);
    		noeud.setParent(n);
    		voisin.add(noeud);    		
    	}
    	
    	if(Ouest(n.getX(), n.getY())) 
    	{
    		//System.out.println("Oeust");
    		h_Aleatoire = rand.nextInt(95)+95;
    		noeud = new Noeud(n.getX()-95, n.getY(), n.getCout()+95, n.getCout()+95 +h_Aleatoire);
    		noeud.setParent(n);
    		voisin.add(noeud);	
    	}

    	return voisin;
    }
    
    
    boolean Nord(int x , int y) throws FileNotFoundException
    {
    	return chemin_libre(x, y-95);
    }
    
    boolean Sud(int x , int y) throws FileNotFoundException
    {
    	return chemin_libre(x, y+95);
    }
    
    boolean Est(int x , int y) throws FileNotFoundException
    {
    	return chemin_libre(x+95, y);
    }
    
    boolean Ouest(int x , int y) throws FileNotFoundException
    {
    	return chemin_libre(x-95, y);
    }
    
}