package laundry_tracker;

import java.util.Collection;

/**

 * Interface to search an underlying inventory of items and return a collection of found items. 
 * @author G. Cope
 * @param <TypeFound> The type of items that are found.
 * @param <TypeBase> The type of items to be searched
 */

public interface Searchable<TypeFound, TypeBase>{

	/**
	 * Searches an underlying inventory of items consisting of type E
	 * @param value A searchable value of type V
	 * @return A Collection of items of type E.
	 */

	public Collection<TypeFound> search(TypeBase value);

	

}

