package org.utils;

import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author erokshark
 */
public final class RussianRoulette {
    
    private final Player[] players = new Player[2];
    private Cylinder cylinder = null;

    public RussianRoulette() {
        resetCylinder();
    }
    
    public static class Player {
        private int id = 0;
        private String name = "";
        private boolean hasSpinned;
        
        public Player(int id, String name) {
            this.name = name;            
            this.id = id;
        }
        public String getName() {
            return this.name;
        } 
        public int getId() {
            return this.id;
        } 
        public boolean getHasSpinned() {
            return this.hasSpinned;
        } 
        public void setHasSpinned(boolean param) {
            this.hasSpinned = param;
        }
    }
    
    public static class Cylinder {
        private int bulletChamber;
        
        public Cylinder(int chamber) {
            bulletChamber = chamber;
        }  
        public int getBulletChamber() {
            return bulletChamber;
        }    
        public void decreaseCylinder() {
            if (bulletChamber != 1) {
                bulletChamber--;
            }
        }
    }
    
    public void clearPlayers() {
        players[0] = null;
        players[1] = null;
    }
    
    public Player getCurrentPlayer() {
        return getPlayer(0);
    }
    
    public Player getNextPlayer() {
        return getPlayer(1);
    }
    
    public Player getPlayer(int id) {
        return players[id];
    }
    
    public Player[] getPlayers() {
        return players;
    }
        
    public void setPlayer(int id, Player player) {
        players[id] = player;
    }
        
    public void changePlayer() {
        Collections.reverse(Arrays.asList(players));
    }
        
    public void randomizePlayerOrder(int i) {
        i = getRandomNumber(i);
        for (int j = 0; j < i; j++) {
            changePlayer();
        }
    }
 
    public Cylinder getCylinder() {
        return this.cylinder;
    }
    
    public void resetCylinder() {
        this.cylinder = new Cylinder(getRandomNumber(6));
    }
        
    public boolean ready() {
        return players[0] != null && players[1] != null;
    }
    
    public void resetGame() {
        clearPlayers();
        resetCylinder();
    }        

    public boolean bulletFired() {
        return getCylinder().getBulletChamber() == getRandomNumber(getCylinder().getBulletChamber());
    }

    public boolean pullTrigger(Player player) {
        if (bulletFired()) {
            return true;
        } else {            
            getCylinder().decreaseCylinder();        
        }
        return false;
    }

    public static Integer getRandomNumber(int maxVal) {
        return (int) (Math.random() * maxVal) + 1;
    }

}
