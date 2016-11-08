package com.drink.cornerstone.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 作用：生成tree的工具类
 * author:邵天强
 * date:2014/11/15
 */
public class TreeUtil {
    /**
     * 获取tree的顶级节点<br/>
     *   实际构成tree的行为在createTree()方法中进行
     * @param top  tree的顶级节点
     * @param list  tree的所有节点
     * @return  返回构建成功的tree的顶级节点
     */
    public static TreeNode getTopTreeNode(TreeNode top,List<TreeNode>list){
        if(list.size()>0){
            top = createTree(top,list,0);
        }
        return top;
    }

    /**
     * 构造一棵树tree
     * <ul>
     *     <li>首先遍历tree的所有节点，找出父节点的所有子节点，并设置到父节点的属性中</li>
     *     <li>如果一个节点下没有子节点，则说明是叶子节点，则设置noLeaf=false,否则noLeaf=true</li>
     *     <li>进行递归，找到每一个节点下的子节点，最终形成tree</li>
     * </ul>
     * 注意：进入子节点的构造时层次+1，递归调用后-1,因为接下来要构造此子节点的兄弟节点，层次要和此子节点相同,
     *      因为之前+1，要保持相同需要-1
     * @param top  tree的顶级节点
     * @param list tree的所有节点
     * @param rootLevel 层次
     * @return 返回构建成功的tree的顶级节点
     */
    private static TreeNode createTree(TreeNode top, List<TreeNode> list, int rootLevel) {
        List<TreeNode> childTreeNode = new ArrayList<>();
        for(TreeNode tn:list){
            if(tn==null){
                continue;
            }
            if(tn.getpId().equals(top.getId())){
                childTreeNode.add(tn);
            }
        }
        for(TreeNode ctn:childTreeNode){
            if(ctn==null){
                continue;
            }
            createTree(ctn,list,++rootLevel);
        }
        --rootLevel;
        return top;

    }

}

