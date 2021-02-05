package com.cdqckj.gmis.utils;


import com.cdqckj.gmis.base.entity.TreeEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * list列表 转换成tree列表
 * Created by Ace on 2017/6/12.
 *
 * @author gmis
 */
@Log4j2
public class TreeUtil {

//    /**
//     * 复杂度O(N^2)<br/>
//     * 建立多个根节点树
//     * 保证构造后的树中，同一级的节点间的相互顺序与在传入数据中的相互顺序保持一致。
//     *
//     * @param treeNodes
//     * @return
//     */
//    @Deprecated
//    public static <T extends ITreeNode<T, ? extends Serializable>> List<T> build(List<T> treeNodes) {
//        if (CollectionUtils.isEmpty(treeNodes)) {
//            return treeNodes;
//        }
//        //记录自己是自己的父节点的id集合
//        List<Serializable> selfIdEqSelfParent = new ArrayList<>();
//        // 为每一个节点找到子节点集合
//        for (T parent : treeNodes) {
//            Serializable id = parent.getId();
//            for (T children : treeNodes) {
//                if (parent != children) {
//                    //parent != children 这个来判断自己的孩子不允许是自己，因为有时候，根节点的parent会被设置成为自己
//                    if (id.equals(children.getParentId())) {
////                        if (parent.getChildren() == null) {
////                            parent.setChildren(new ArrayList<T>());
////                        }
//                        parent.initChildren();
//                        parent.getChildren().add(children);
//                    }
//                } else if (id.equals(parent.getParentId())) {
//
//                    selfIdEqSelfParent.add(id);
//                }
//            }
//        }
//        // 找出根节点集合
//        List<T> trees = new ArrayList<>();
//
////        List<Serializable> allIds = new ArrayList<>();
////        for (T baseNode : treeNodes) {
////            allIds.add(baseNode.getId());
////        }
//
//        List<? extends Serializable> allIds = treeNodes.stream().map(node -> node.getId()).collect(Collectors.toList());
//        for (T baseNode : treeNodes) {
//            if (!allIds.contains(baseNode.getParentId()) || selfIdEqSelfParent.contains(baseNode.getParentId())) {
//                trees.add(baseNode);
//            }
//        }
//        return trees;
//    }


    /**
     * 构建Tree结构
     *
     * @param treeList
     * @param <E>      实体
     * @param <?>      主键
     * @return
     */
    public static <E extends TreeEntity<E, ? extends Serializable>> List<E> buildTree(List<E> treeList) {
        if (CollectionUtils.isEmpty(treeList)) {
            return treeList;
        }
        //记录自己是自己的父节点的id集合
        List<Serializable> selfIdEqSelfParent = new ArrayList<>();
        // 为每一个节点找到子节点集合
        for (E parent : treeList) {
            Serializable id = parent.getId();
            for (E children : treeList) {
                if (parent != children) {
                    //parent != children 这个来判断自己的孩子不允许是自己，因为有时候，根节点的parent会被设置成为自己
                    if (id.equals(children.getParentId())) {
                        parent.initChildren();
                        children.setParentName(parent.getLabel());
                        parent.getChildren().add(children);

                    }
                } else if (id.equals(parent.getParentId())) {
                    selfIdEqSelfParent.add(id);
                }
            }
        }
        // 找出根节点集合
        List<E> trees = new ArrayList<>();

        List<? extends Serializable> allIds = treeList.stream().map(node -> node.getId()).collect(Collectors.toList());
        for (E baseNode : treeList) {
            if (!allIds.contains(baseNode.getParentId()) || selfIdEqSelfParent.contains(baseNode.getParentId())) {
                trees.add(baseNode);
            }
        }
        return trees;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 11:01
     * @remark
     */
    public static <E extends TreeNodeInterface> List<TreeNodeInterface> buildTreeForInterface(List<E> treeList) {

        List<E> copyList  = new ArrayList<>(treeList);
        List<TreeNodeInterface> resultList = new ArrayList<>();
        buildTreeForInterface(copyList, resultList, 1);
        return resultList;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/12 11:19
     * @remark 递归处理
     */
    public static <E extends TreeNodeInterface> void buildTreeForInterface(List<E> treeList, List<TreeNodeInterface> resultList, int layer) {

        if (treeList.size() <= 0 || layer > 10) {
            return;
        }
        // 第一层节点
        if (layer == 1) {
            Iterator iterator = treeList.iterator();
            while(iterator.hasNext()) {
                E node = (E) iterator.next();
                if (node.parentNode()) {
                    resultList.add(node);
                    iterator.remove();
                }
            }
        }

        for (TreeNodeInterface node: resultList) {
            Iterator iterator = treeList.iterator();
            while(iterator.hasNext()) {
                TreeNodeInterface treeNode = (TreeNodeInterface) iterator.next();
                String treeId = node.getTreeId();
                String patentId = treeNode.getTreeParentId();
                if (treeId.equals(patentId)) {
                    node.setTreeSonNode(treeNode);
                    iterator.remove();
                }
            }
            buildTreeForInterface(treeList, node.children(), ++layer);
        }
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/30 14:46
    * @remark 获取这个用户的子节点
    */
    public static <E extends TreeEntity<E, ? extends Serializable>> void setUserTreeEntity(List<E> treeList, Long id, List<E> resultList){

        for (E e: treeList){
            if (e.getId().equals(id)){
                resultList.add(e);
                return;
            }else {
                List<E> childList = e.getChildren();
                if (childList != null && childList.size() != 0){
                    setUserTreeEntity(childList, id, resultList);
                }
            }
        }
    }


    /**
    * @Author: lijiangguo
    * @Date: 2021/1/30 15:52
    * @remark 设置节点
    */
    public static <E extends TreeNodeInterface> void getTreeNodeInterface(List<E> treeList, String id, List<E> resultList) {

        for (E e: treeList){
            String theId = e.getTreeId();
            if (theId.equals(id)){
                resultList.add(e);
                return;
            }else {
                List<E> childList = (List<E>) e.children();
                if (childList != null && childList.size() != 0){
                    getTreeNodeInterface(childList, id, resultList);
                }
            }
        }
    }
}
