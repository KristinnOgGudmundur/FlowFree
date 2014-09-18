package com.example.FlowFree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yngvi on 5.9.2014.
 */
public class Cellpath {

    private ArrayList<Coordinate> m_path = new ArrayList<Coordinate>();

    public void append( Coordinate co ) {
        int idx = m_path.indexOf(  co );
        if ( idx >= 0 ) {
            for ( int i=m_path.size()-1; i > idx; --i ) {
                m_path.remove(i);
            }
        }
        else {
            m_path.add(co);
        }
    }

    public List<Coordinate> getCoordinates() {
        return m_path;
    }

    public void reset() {
        m_path.clear();
    }

    public boolean isEmpty() {
        return m_path.isEmpty();
    }

	public void setEndAt(Coordinate c){
		if(m_path.isEmpty()){
			m_path.add(c);
		}
		else{
			boolean seen = false;
			for(int i = 0; i < m_path.size(); i++){
				if(seen){
					m_path.remove(i);
					i--;
				}
				else{
					if(m_path.get(i).equals(c)){
						seen = true;
					}
				}
			}
		}

	}

	public void removeLast(){
		m_path.remove(m_path.size() - 1);
	}

	public boolean contains(Coordinate theCoordinate){
		for(Coordinate c : m_path){
			if(c.equals(theCoordinate)){
				return true;
			}
		}
		return false;
	}

	public boolean contains(Coordinate a, Coordinate b){
		boolean containsA = false;
		boolean containsB = false;
		for(Coordinate c : m_path){
			if(c.equals(a)){
				containsA = true;
			}
			if(c.equals(b)){
				containsB = true;
			}
		}

		return containsA && containsB;
	}

	@Override
	public String toString(){
		String returnValue = "";
		for(Coordinate c : m_path){
			returnValue += c.toString();
		}

		return returnValue;
	}
}
