
public class ThreadedNode {

	private int _key;
	private String _value;
	private ThreadedNode _parent;
	private ThreadedNode _left;
	private ThreadedNode _right;
	private boolean _threadedLeft;
	private boolean _threadedRight;

	public ThreadedNode() {
		this._key = Integer.MIN_VALUE;
		this._value = "";
		this._parent = null;
		this._left = this;
		this._right = this;
		this._threadedLeft = true;
		this._threadedRight = true;
	}

	public ThreadedNode(int key, String value) {
		this._key = key;
		this._value = value;
		this._parent = null;
		this._left = this;
		this._right = this;
		this._threadedLeft = true;
		this._threadedRight = true;
	}

	public int getKey() {
		return this._key;
	}

	public String getValue() {
		return this._value;
	}

	public void setLeft(ThreadedNode node) {
		this._left = node;
	}
	public void setValue(String value)
	{
		this._value = value;
	}

	public void setRight(ThreadedNode node) {
		this._right = node;
	}

	public ThreadedNode getLeft() {
		return this._left;
	}

	public ThreadedNode getRight() {
		return this._right;
	}
	public ThreadedNode getParent()
	{
		return this._parent;
	}
	public void setParent(ThreadedNode parent)
	{
		this._parent = parent;
	}

	public boolean isThreadedLeft() {
		return this._threadedLeft;
	}

	public boolean isThreadedRight() {
		return this._threadedRight;
	}
	public void setThreadedLeft(boolean b)
	{
		this._threadedLeft = b;
	}
	public void setThreadedRight(boolean b)
	{
		this._threadedRight = b;
	}
	public boolean isLeaf()
	{
		return this.isThreadedLeft() && this.isThreadedRight();
	}
	public String toString()
	{
		return this.getKey() + " " +this.getValue();
	}
	public void setKey(int key)
	{
		this._key = key;
	}
	
	public void copy(ThreadedNode node) {
		
		this._value = node._value;
		this._key = node._key;
		
		
	}
}