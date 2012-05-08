package org.portfolio.client.controller.render.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.portfolio.util.LogService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TreeNodeImpl implements TreeNode {
	private JSONObject JSON_value;
	private List<TreeNode> children;
	private TreeNode parent;
	
	@SuppressWarnings("unused")
	private static final LogService logService = new LogService(TreeNodeImpl.class);
	
	public TreeNodeImpl(String json) {
		JSON_value = JSONObject.fromObject(json);
		children = new ArrayList<TreeNode>();
	}
	
	public JSONObject getValue() {
		
		return JSON_value;
	}
	
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	public TreeNode getParent() {
		return parent;
	}
	
	public List<TreeNode> getChildren() {
		return children;
	}
	
	public void appendChild(TreeNode child) {
		child.setParent(this);
		children.add(child);
	}
	
	public void insertChild(int pos, TreeNode child) {
		child.setParent(this);
		if(pos >= children.size()) {
			appendChild(child);
		}
		else {
			children.add(pos, child);
		}
	}

	public boolean hasChildren() {
		return children.size() > 0;
	}
	
	static public void insertChildAt(TreeNode tree, List<Integer> pos, TreeNode child) {
		
		TreeNode branch = tree;
		
		List<Integer> parentPos = new ArrayList<Integer>();
		
		while(pos.size() > 0) {
			
			Integer p = pos.remove(0);
			
			if(pos.size() > 0) {
				parentPos.add(p);
			}

			if(p > (branch.getChildren().size() - 1) || pos.size() == 0) {
				// must be the place?
				
				// logService.debug("==> Adding child '" + child.getValue().toString() + "'");
				branch.insertChild(p, child);
			}
			else {
				// logService.debug("moving down the tree...");
				branch = branch.getChildren().get(p);
			}
			
		}
		
	}
	
	public String toString() {
		JSONArray values = _toString(this);

		return values.toString();
	}

	static private JSONArray _toString(TreeNode node) {
		
		JSONArray values = new JSONArray();
		JSONArray subValues = new JSONArray();
		
		for(Iterator<TreeNode> i = node.getChildren().iterator(); i.hasNext(); ) {
			TreeNode childNode = i.next();
			subValues.addAll(_toString(childNode));
		}
		
		values.add(node.getValue());
		
		if(subValues.size() > 0) {
			values.add(subValues);
		}
		
		return values;
		
	}
}