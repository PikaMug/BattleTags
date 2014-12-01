package me.happypikachu.BattleTags.managers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;

/**
 * Project Arena<br/>
 * Class net.brord.plugins.arena.managers.Manager.java<br/>
 * @author Brord
 * @since 2 jul. 2014, 17:19:40
 */
public class Manager<T extends Managable> implements Managable, Iterable<T>{
	
	private Class<T> type;
	protected List<T> data;
	
	/**
	 * 
	 */
	public Manager(Class<T> clazz) {
		this.type = clazz;
		data = new LinkedList<>();
	}

	/**
	 * @return
	 */
	public Class<T> getType() {
		return type;
	}
	
	public boolean add(T o){
		if (o == null) return false;
		if (data.contains(o)) return false;
		
		return data.add(o);
	}
	
	public void remove(T o){
		if (o == null) return;
		if (!data.contains(o)) return;
		data.remove(o);
	}
	
	/**
	 * Checks if this value exists in the manager
	 * @param name
	 * @return
	 */
	public boolean exists(String name){
		return get(name) != null;
	}
	
	/**
	 * 
	 * @param name
	 * @return the name, or null if it doesnt exist
	 */
	public T get(String name){
		for (T o : data){
			if (o.name().equalsIgnoreCase(name)) return o;
		}
		for (T o : data){
			if (o.name().startsWith(name)) return o;
		}
		
		for (T o : data){
			if (ChatColor.stripColor(o.name()).equalsIgnoreCase(name)) return o;
		}
		
		for (T o : data){
			if (ChatColor.stripColor(o.name()).startsWith(name)) return o;
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	public T getRandom() {
		return data.isEmpty() ? null : data.get((new Random().nextInt(data.size())));
	}
	
	public void clear(){
		data.clear();
	}
	
	/**
	 * @return 
	 * 
	 */
	public List<T> getAll() {
		return data;
	}
	
	/**
	  * @see java.lang.Object#toString()
	  */
	@Override
	public String name() {
		return type.toString();
	}

	/**
	 * @return
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/**
	  * @see java.lang.Iterable#iterator()
	  */
	@Override
	public Iterator<T> iterator() {
		return data.iterator();
	}
}
