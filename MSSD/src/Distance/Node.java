

package Distance;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Node implements Serializable{
	public ArrayList<Node> parents=new ArrayList<Node>();//节点的父节点
	public ArrayList<Node> children=new ArrayList<Node>();//节点的子节点
	public String value=null;//存储该节点的值
	
	public boolean isRet = false;//是否为网络节点
	public int d;//节点的深度
	public boolean visited = false;
	public String strflag;
	public int label;
	public boolean isNewRoot = false;
	public String newRootFlag = "";
}
