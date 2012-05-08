package org.portfolio.client.controller.render.utils;

import java.util.List;

import net.sf.json.JSONObject;

public interface TreeNode {
	void setParent(TreeNode parent);
	void insertChild(int pos, TreeNode child);
	void appendChild(TreeNode child);
	TreeNode getParent();
	JSONObject getValue();
	boolean hasChildren();
	List<TreeNode> getChildren();
	String toString();
	
}