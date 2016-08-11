package fr.kaice.model.sell;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by merkling on 11/08/16.
 */
public class ListRawMaterial implements Serializable {
    
    private static final long serialVersionUID = -4704593307846210023L;
    private final ArrayList<Sample> list;
    
    public ListRawMaterial() {
        list = new ArrayList<>();
    }
    
    public void remove(int id) {
        Sample toRemove = null;
        for (Sample s :
                list) {
            if (s.getId() == id) {
                toRemove = s;
            }
        }
        list.remove(toRemove);
    }
    
    public void put(int id, int qty) {
        list.add(new Sample(id, qty));
    }
    
    public ArrayList<Sample> getAll() {
        return list;
    }
    
    public int size() {
        return list.size();
    }
    
    public class Sample implements Serializable {
        
        private int id, qty;
        
        Sample(int id, int qty) {
            this.id = id;
            this.qty = qty;
        }
        
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
        public int getQty() {
            return qty;
        }
        
        public void setQty(int qty) {
            this.qty = qty;
        }
    }
}
