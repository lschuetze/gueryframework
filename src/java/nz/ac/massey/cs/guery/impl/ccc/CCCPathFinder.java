package nz.ac.massey.cs.guery.impl.ccc;

import java.util.Iterator;

import com.google.common.base.Predicate;

import nz.ac.massey.cs.guery.GraphAdapter;
import nz.ac.massey.cs.guery.Path;
import nz.ac.massey.cs.guery.PathFinder;

public class CCCPathFinder<V, E> implements PathFinder<V, E> {

	@Override
	public Iterator<Path<V, E>> findLinks(GraphAdapter<V, E> g, V start,int minLength, int maxLength, boolean outgoing, Predicate<E> filter, boolean computeAll) {
		// TODO Auto-generated method stub
		return null;
	}

}
