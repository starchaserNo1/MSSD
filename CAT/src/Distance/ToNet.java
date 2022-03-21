package Distance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ToNet {  //将字符串转换为网络，节点及其之间的关系
	public static ArrayList<Node> net=null; //用于存储即将生成的网络节点
	public static Map<String,Node> record=null;  //用于记录网络节点与其标识符的对应关系
	public static ArrayList<Node> toNet(String info){  
		String pattern = ":[0-9.]*";
		info = info.replaceAll(pattern, "");
		net=new ArrayList<Node>();
		record=new HashMap<String,Node>(); 
		net.add(new Node());
		mkNod(net.get(0),info.substring(0, info.length()));
		return net;
	}
	public static void mkNod(Node node,String value){ //此方法用于创建节点，并确定父子节点关系和确定value值
		node.value=value;
		if(record.containsKey(value)){  //检查value值是否是另一个网络节点的标识符
			Node newNode=record.get(value); //取出对应的节点
			for(int i=0;i<newNode.parents.size();i++){
				newNode.parents.get(i).children.remove(record.get(value));
			}
			newNode.parents.addAll(node.parents);
			for(int i=0;i<newNode.parents.size();i++){
				newNode.parents.get(i).children.add(newNode);
				newNode.parents.get(i).children.remove(node);
			}//让父亲知道自己的孩子
			net.remove(record.get(value));
			net.remove(node);  //从网络中删除原来的节点
			net.add(newNode);   //添加更新后的节点
			record.put(value, newNode);//更新键值对，键不变，值节点新增了父节点
		}
		if((!(record.containsKey(value)))&&(!(value.contains("(")))&&(!(value.contains(",")))&&(!(value.contains(")")))){
			//假如没有(,),则该节点应为叶子节点
			LeafNode newLeafNode=new LeafNode(); //若满足条件，说明是叶子节点
			newLeafNode.parents.addAll(node.parents);  //添加父节点
			newLeafNode.value = value ;  //赋值
			for(int i=0;i<newLeafNode.parents.size();i++){
				newLeafNode.parents.get(i).children.add(newLeafNode);
				newLeafNode.parents.get(i).children.remove(node);
			}
			net.remove(node);  //从net数组移除node节点
			net.add(newLeafNode); //将叶子节点添加进去
		}
		if(value.startsWith("(") && value.endsWith(")")){
			Stack<Character> stk = new Stack<Character>();
			ArrayList<Integer> comma=new ArrayList<Integer>();
			for(int i=0;i<value.length();i++){  
				if(value.charAt(i)=='('){
					stk.push(value.charAt(i));  //如果字符是'（'就入栈
				}
				if(value.charAt(i)==')'){
					stk.pop();    //如果遇到‘）’，就出栈一个‘（’与之匹配
				}
				if(value.charAt(i)==','&&stk.size()==1){
					comma.add(i);   //记录逗号的位置，这些逗号区分了他的子节点
				}
			}
			comma.add(0,0);  //添加左括号位置
			comma.add((value.length()-1));    //添加右括号位置
			for(int j=0;j<(comma.size()-1);j++){
				net.add(new Node());
				net.get(net.size()-1).value=value.substring(comma.get(j)+1,comma.get(j+1));	
				node.children.add(net.get(net.size()-1));
				net.get(net.size()-1).parents.add(node);
				mkNod(net.get(net.size()-1),net.get(net.size()-1).value);
				}
		}
		if(value.startsWith("(")&&value.contains(")")&&(!(value.endsWith(")")))){
			Stack<Character> stk = new Stack<Character>();
			ArrayList<Integer> comma=new ArrayList<Integer>();
			for(int i=0;i<value.length();i++){  
				if(value.charAt(i)=='('){
					stk.push(value.charAt(i));  //如果字符是'（'就入栈
				}
				if(value.charAt(i)==')'){
					if(stk.size()==1){comma.add(i);}
					stk.pop();    //如果遇到‘）’，就出栈一个‘（’与之匹配
				}
				if(value.charAt(i)==','&&stk.size()==1){
					comma.add(i);   //记录逗号的位置，这些逗号区分了他的子节点
				}
			}
			comma.add(0,0);  //添加左括号位置
				record.put(value.substring(comma.get(comma.size()-1)+1,value.length()), node);
				for(int j=0;j<(comma.size()-1);j++){
					net.add(new Node());
					net.get(net.size()-1).value=value.substring(comma.get(j)+1,comma.get(j+1));	
					node.children.add(net.get(net.size()-1));
					net.get(net.size()-1).parents.add(node);
					mkNod(net.get(net.size()-1),net.get(net.size()-1).value);
			}
				node.value=value.substring(0,comma.get(comma.size()-1)+1);
		}
    }
}