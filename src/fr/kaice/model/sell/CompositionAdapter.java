package fr.kaice.model.sell;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by merkling on 11/08/16.
 */
public class CompositionAdapter implements Serializable, Iterable<CompositionAdapter.Element> {
    
    private static final long serialVersionUID = -4704593307846210023L;
    private ArrayList<Element> list;
    
    public CompositionAdapter() {
        list = new ArrayList<>();
    }
    
    public void remove(int id) {
        Element toRemove = null;
        for (Element s :
                list) {
            if (s.getId() == id) {
                toRemove = s;
            }
        }
        list.remove(toRemove);
    }
    
    public void add(int id, int qty) {
        list.add(new Element(id, qty));
    }
    
    public ArrayList<Element> getAll() {
        return list;
    }
    
    public int size() {
        return list.size();
    }

    @Override
    public Iterator<Element> iterator() {
        return list.iterator();
    }

    public class Element implements Serializable {
        
        private static final long serialVersionUID = -5591738933022922716L;
        private int id, qty;
        
        Element(int id, int qty) {
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
