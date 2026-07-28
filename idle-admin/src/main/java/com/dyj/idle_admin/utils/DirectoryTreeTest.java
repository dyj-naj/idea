package com.dyj.idle_admin.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 目录节点类：封装目录属性，维护父目录和子目录链表（兄弟目录通过链表组织）
 */
class DirectoryNode {
    private String dirName;          // 目录名
    private DirectoryNode parent;    // 父目录引用
    private LinkedList<DirectoryNode> children; // 子目录链表（兄弟目录组成该链表）
    private boolean isRoot;          // 是否为根目录

    // 根目录构造方法
    public DirectoryNode(String dirName) {
        this.dirName = dirName;
        this.isRoot = true;
        this.parent = null;
        this.children = new LinkedList<>();
    }

    // 非根目录构造方法
    public DirectoryNode(String dirName, DirectoryNode parent) {
        this.dirName = dirName;
        this.isRoot = false;
        this.parent = parent;
        this.children = new LinkedList<>();
    }

    // 获取目录名
    public String getDirName() {
        return dirName;
    }

    // 获取父目录
    public DirectoryNode getParent() {
        return parent;
    }

    // 获取子目录链表
    public LinkedList<DirectoryNode> getChildren() {
        return children;
    }

    // 判断是否为根目录
    public boolean isRoot() {
        return isRoot;
    }

    // 添加子目录到链表
    public void addChild(DirectoryNode child) {
        this.children.add(child);
    }

    // 从子链表移除指定目录
    public void removeChild(DirectoryNode child) {
        this.children.remove(child);
    }

    // 判断当前目录是否为空（无子目录）
    public boolean isEmpty() {
        return this.children.isEmpty();
    }
}

/**
 * 目录管理器：封装目录的创建、删除、查找、遍历、兄弟目录查询等核心功能
 */
class DirectoryManager {
    private DirectoryNode root; // 根目录节点

    // 初始化管理器，创建根目录
    public DirectoryManager() {
        this.root = new DirectoryNode("root"); // 默认根目录名为root
    }

    /**
     * 创建目录
     * @param dirName 新目录名
     * @param parentDirName 父目录名（null则默认根目录为父目录）
     * @return 创建成功返回目录节点，失败返回null
     */
    public DirectoryNode createDirectory(String dirName, String parentDirName) {
        // 校验目录名非空
        if (dirName == null || dirName.trim().isEmpty()) {
            System.out.println("错误：目录名不能为空！");
            return null;
        }

        // 确定父目录节点
        DirectoryNode parentNode;
        if (parentDirName == null || parentDirName.trim().isEmpty()) {
            // 未指定父目录，默认根目录为父目录
            parentNode = root;
        } else {
            // 查找指定的父目录
            parentNode = findDirectory(parentDirName, root);
            if (parentNode == null) {
                System.out.println("错误：父目录[" + parentDirName + "]不存在！");
                return null;
            }
        }

        // 校验父目录下是否已存在同名子目录
        Optional<DirectoryNode> existChild = parentNode.getChildren().stream()
                .filter(child -> child.getDirName().equals(dirName))
                .findFirst();
        if (existChild.isPresent()) {
            System.out.println("错误：父目录[" + parentNode.getDirName() + "]下已存在同名目录[" + dirName + "]！");
            return null;
        }

        // 创建新目录节点，加入父目录的子链表
        DirectoryNode newDir = new DirectoryNode(dirName, parentNode);
        parentNode.addChild(newDir);
        System.out.println("成功：在父目录[" + parentNode.getDirName() + "]下创建目录[" + dirName + "]");
        return newDir;
    }

    /**
     * 删除目录（仅当目录为空时可删除）
     * @param dirName 要删除的目录名
     * @return 删除成功返回true，失败返回false
     */
    public boolean deleteDirectory(String dirName) {
        // 禁止删除根目录
        if (dirName.equals("root")) {
            System.out.println("错误：根目录不允许删除！");
            return false;
        }

        // 查找要删除的目录节点
        DirectoryNode targetDir = findDirectory(dirName, root);
        if (targetDir == null) {
            System.out.println("错误：要删除的目录[" + dirName + "]不存在！");
            return false;
        }

        // 校验目录是否为空（无子目录）
        if (!targetDir.isEmpty()) {
            System.out.println("错误：目录[" + dirName + "]非空（包含子目录），无法删除！");
            return false;
        }

        // 从父目录的子链表中移除该目录
        DirectoryNode parentNode = targetDir.getParent();
        parentNode.removeChild(targetDir);
        System.out.println("成功：删除目录[" + dirName + "]");
        return true;
    }

    /**
     * 递归查找指定名称的目录
     * @param targetName 要查找的目录名
     * @param currentNode 当前遍历的节点
     * @return 找到返回节点，未找到返回null
     */
    public DirectoryNode findDirectory(String targetName, DirectoryNode currentNode) {
        // 基准条件：当前节点匹配目标名，返回
        if (currentNode.getDirName().equals(targetName)) {
            return currentNode;
        }

        // 递归遍历子目录链表
        for (DirectoryNode child : currentNode.getChildren()) {
            DirectoryNode found = findDirectory(targetName, child);
            if (found != null) {
                return found;
            }
        }

        // 未找到
        return null;
    }

    /**
     * 显示指定目录下的所有子目录
     * @param dirName 目标目录名
     */
    public void showChildrenDirectories(String dirName) {
        // 查找目标目录
        DirectoryNode targetDir = findDirectory(dirName, root);
        if (targetDir == null) {
            System.out.println("错误：目录[" + dirName + "]不存在！");
            return;
        }

        // 获取子目录链表
        LinkedList<DirectoryNode> children = targetDir.getChildren();
        if (children.isEmpty()) {
            System.out.println("目录[" + dirName + "]下无子目录");
            return;
        }

        // 输出子目录列表
        System.out.print("目录[" + dirName + "]下的子目录：");
        for (int i = 0; i < children.size(); i++) {
            DirectoryNode child = children.get(i);
            if (i == children.size() - 1) {
                System.out.println(child.getDirName());
            } else {
                System.out.print(child.getDirName() + " -> "); // 链表形式展示兄弟目录
            }
        }
    }

    /**
     * 获取指定目录的所有兄弟目录（同一父目录下的其他目录）
     * @param dirName 目标目录名
     * @return 兄弟目录列表，无则返回空列表
     */
    public List<DirectoryNode> getSiblingDirectories(String dirName) {
        // 查找目标目录
        DirectoryNode targetDir = findDirectory(dirName, root);
        if (targetDir == null) {
            System.out.println("错误：目录[" + dirName + "]不存在！");
            return new LinkedList<>();
        }

        // 根目录无父目录，因此无兄弟目录
        if (targetDir.isRoot()) {
            System.out.println("根目录无兄弟目录");
            return new LinkedList<>();
        }

        // 从父目录的子链表中过滤出除自身外的所有节点
        DirectoryNode parentNode = targetDir.getParent();
        List<DirectoryNode> siblings = new LinkedList<>();
        for (DirectoryNode child : parentNode.getChildren()) {
            if (!child.getDirName().equals(dirName)) {
                siblings.add(child);
            }
        }

        // 输出兄弟目录
        if (siblings.isEmpty()) {
            System.out.println("目录[" + dirName + "]无兄弟目录");
        } else {
            System.out.print("目录[" + dirName + "]的兄弟目录：");
            for (int i = 0; i < siblings.size(); i++) {
                DirectoryNode sibling = siblings.get(i);
                if (i == siblings.size() - 1) {
                    System.out.println(sibling.getDirName());
                } else {
                    System.out.print(sibling.getDirName() + " -> ");
                }
            }
        }
        return siblings;
    }

    // 获取根目录（测试用）
    public DirectoryNode getRoot() {
        return root;
    }
}

/**
 * 测试类：演示目录管理的所有核心功能
 */
public class DirectoryTreeTest {
    public static void main(String[] args) {
        // 1. 初始化目录管理器（自动创建根目录root）
        DirectoryManager manager = new DirectoryManager();
        System.out.println("===== 初始化完成，创建根目录root =====");

        // 2. 创建目录：未指定父目录（默认根目录）
        manager.createDirectory("学习资料", null);
        manager.createDirectory("工作文档", null);
        manager.createDirectory("娱乐文件", null);
        System.out.println();

        // 3. 显示根目录下的子目录（验证兄弟目录链表）
        manager.showChildrenDirectories("root");
        System.out.println();

        // 4. 创建子目录：指定父目录为“学习资料”
        manager.createDirectory("Java学习", "学习资料");
        manager.createDirectory("Python学习", "学习资料");
        System.out.println();

        // 5. 显示“学习资料”下的子目录
        manager.showChildrenDirectories("学习资料");
        System.out.println();

        // 6. 获取“Java学习”的兄弟目录
        manager.getSiblingDirectories("Java学习");
        System.out.println();

        // 7. 尝试删除非空目录（学习资料）→ 失败
        manager.deleteDirectory("学习资料");
        System.out.println();

        // 8. 删除空目录（Python学习）→ 成功
        manager.deleteDirectory("Python学习");
        System.out.println();

        // 9. 再次显示“学习资料”下的子目录
        manager.showChildrenDirectories("学习资料");
        System.out.println();

        // 10. 尝试创建同名目录→ 失败
        manager.createDirectory("工作文档", null);
        System.out.println();

        // 11. 尝试删除不存在的目录→ 失败
        manager.deleteDirectory("不存在的目录");
    }
}