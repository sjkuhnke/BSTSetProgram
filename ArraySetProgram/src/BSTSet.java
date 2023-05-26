import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.io.Serializable;

public class BSTSet implements Set<String>, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4022434240979354040L;

	// Nested Node class
	class Node implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5451532909307792189L;
		String data;
		Node left, right;
		
		public Node(String data) {
			this.data = data;
		}
	}
	
	// Fields
	Node root;
	private int count;
	
	public BSTSet() {	}
	
	@Override
	public int size() {
		return doSize(root);
	}
	
	public int count() {
		return count;
	}

	private int doSize(Node n) {
		if (n == null) return 0;
		return doSize(n.left) + doSize(n.right) + 1;
	}

	@Override
	public boolean isEmpty() {
		return doSize(root) == 0;
	}

	@Override
	public boolean contains(Object o) {
		if (!(o instanceof String)) {
			return false;
		}
		String target = (String) o;
		target = target.toLowerCase();
		return doContains(root, target);
	}
	
	private boolean doContains(Node n, String target) {
		if (n == null) return false;
		int c = target.compareTo(n.data);
		
		if (c == 0) {
			return true;
		} else if (c < 0) {
			return doContains(n.left, target);
		} else {
			return doContains(n.right, target);
		}
	}

	@Override
	public Iterator<String> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		Object[] result = new Object[size()];
		
		doOrder(root, result, 0);
		return result;
	}

	private int doOrder(Node n, Object[] r, int i) {
		if (n == null) return i;
		
		i = doOrder(n.left, r, i);
		r[i++] = n.data;
		return doOrder(n.right, r, i);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) {
		if (a.length < size()) {
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size());
		} else if (a.length > size()) {
			a[size()] = null;
		}
		
		doOrder(root, a, 0);
	    return a;
	}

	@Override
	public boolean add(String e) {
		if (contains(e)) return false;
		
		e = e.toLowerCase();
		Node elem = new Node(e);
		return doAdd(root, elem);
	}
	
	private boolean doAdd(Node n, Node e) {
		if (n == null) {
			root = e;
			doCount();
			return true;
		}
		
		int c = e.data.compareTo(n.data);
		
		if (c < 0) {
			if (n.left == null) {
				n.left = e;
				doCount();
				return true;
			} else {
				return doAdd(n.left, e);
			}
		} else if (c > 0) {
			if (n.right == null) {
				n.right = e;
				doCount();
				return true;
			} else {
				return doAdd(n.right, e);
			}
		}
		return true;
	}

	private void doCount() {
		count++;
		if (count % 10 == 0) {
			if (count != size()) {
				System.out.println("Count was off\nWas: " + count + "\nExpected: " + size());
				count = size();
			}
		}
		
	}

	@Override
	public boolean remove(Object o) {
		if (!(o instanceof String)) {
			return false;
		}
		
		String e = (String) o;
		e = e.toLowerCase();
		if (!contains(e)) return false;
		
		Node target = new Node(e);
		root = doRemove(root, target);
		count--;
		return true;
	}
	
	private Node doRemove(Node n, Node e) {
		if (n == null) return null;
		
		int c = e.data.compareTo(n.data);
		
		if (c < 0) {
			n.left = doRemove(n.left, e);
		} else if (c > 0) {
			n.right = doRemove(n.right, e);
		} else {
			if (n.left == null) {
				return n.right;
			} else if (n.right == null) {
				return n.left;
			} else {
				Node pre = findPre(n.left);
				n.data = pre.data;
				n.left = doRemove(n.left, pre);
			}
		}
		
		return n;
	}
	
	private Node findPre(Node n) {
		if (n == null) return n;
		
		return findPre(n.right);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addAll(Collection<? extends String> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		root = null;
		count = 0;
	}
	
	public String toString() {
	    Object[] objectArray = toArray();
	    String[] stringArray = new String[objectArray.length];

	    for (int i = 0; i < objectArray.length; i++) {
	        String s = (String) objectArray[i];
	        stringArray[i] = toProperCase(s);
	    }

	    return String.join(",", stringArray);
	}
	
	public String toProperCase(String s) {
	    if (s == null || s.isEmpty()) {
	        return s;
	    }

	    StringBuilder sb = new StringBuilder();
	    boolean capitalizeNext = true;

	    for (char c : s.toCharArray()) {
	        if (Character.isWhitespace(c) || c == '-') {
	            capitalizeNext = true;
	            sb.append(c);
	        } else {
	            if (capitalizeNext) {
	                sb.append(Character.toUpperCase(c));
	                capitalizeNext = false;
	            } else {
	                sb.append(Character.toLowerCase(c));
	            }
	        }
	    }
	    
	    return sb.toString();
	}

	
}
