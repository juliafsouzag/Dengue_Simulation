import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adult extends Mosquito{
	
	private static final int MAX_AGE = 40;
	private static final int MAX_LITTER_SIZE = 15;
	private static final int MIN_LITTER_SIZE = 2;
	private int female = 1;
	private int totalBirths = 0;
	private int copulate = 0;

	
	public Adult(Enviroment enviroment, Position position) {

		super(enviroment, position);
		
		Random rand = new Random();
    	int female = rand.nextInt(2);
    	setLocomotion(10);
	}
	
	 public void act(ArrayList<Being> being) {
	        if (isAlive()) {
	        	
	        	incrementAge();
	         		
	        	boolean copulated = copulate();
	        	
	        	if(copulated) {
	        		//LOOK FOR BLOOD
	        		reproduce(being);
	        	}
	        	
	        	move();	
	        	
	        }
		
	    }
	 boolean copulate() {
		 
		 Random rand = new Random();
		 copulate = rand.nextInt(2);
		 
		 if(copulate == 1) {
			 return true;
		 }
		 
		 return false;
	 }
	 public int breed() {
	        int births = 0;
	        
	        if (canBreed()) {
	        	
	           Random rand = new Random();
	           births = rand.nextInt(((MAX_LITTER_SIZE) - MIN_LITTER_SIZE )+1) + MIN_LITTER_SIZE;
	        }
	        
	        return births;
	}
	 
	 private boolean canBreed() {
		 
		if(female == 1 && totalBirths < MAX_LITTER_SIZE) {
			return true;
		}
		else if(female == 1 && totalBirths >= MAX_LITTER_SIZE) {
			
			female = 2;
			return false;
		}
		
		return false;
	}

	private void reproduce(ArrayList<Being> newEggs) {
		Enviroment enviroment = getEnviroment();
		
		
		 
	     List<Position> free = enviroment.getFreePositions(getPosition());
	     
	     int births = breed();
	        
	     for (int b = 0; b < births && free.size() > 0; b++) {
	    	 
	            Position newPosition = free.remove(0);
	            
	            Egg egg = new Egg(enviroment, newPosition);
	            
	            newEggs.add(egg);
	            
	            enviroment.setBeingAt(egg, newPosition.getRow(), newPosition.getCol());
	            
	            totalBirths++;
	        }
	}

	public void move() {
		
		 for(int i = 0; i< getLocomotion(); i++) {
			 moveOnePosition();
		 }
		  
	} 
	public void moveOnePosition() {
		  
		Position newPosition = getEnviroment().freeAdjacentPosition(getPosition());
			 
		  Position oldPosition = getPosition();
		  
		  if (newPosition != null) {
			    
              setPosition(newPosition);
              this.getEnviroment().setBeingAt(this, newPosition.getRow(), newPosition.getCol());
              getEnviroment().clear(oldPosition);
          }
		  
	} 	
	public void incrementAge() {
	       
	    	setAge(getAge() + 1);
	        
	        if (getAge() > MAX_AGE) {
	     
	            setDead();
	    
	        }
	    
	    }
}
