package com.kopytko.model;

import java.util.*;
import org.apache.logging.log4j.*;

public class BinaryTreeMap<K extends Comparable, V>  implements Map<K, V> {
    private static Logger log = LogManager.getLogger(BinaryTreeMap.class);

    private int size = 0;
    private Node<K, V> root;


    // Клас для вузлів
    private static class Node<K, V> implements Map.Entry<K, V> {
        private Node<K, V> left;
        private Node<K, V> right;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        public void print() {
            log.info("Key: " + key + " Value: " + value);
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0 ? true : false;
    }

    @Override
    public V get(Object key) {
        Comparable<? super K> c = (Comparable<? super K>) key;
        Node<K, V> node = root;
        while (node != null) {
            int cmp = c.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return  node.value;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Node<K, V> parent = root;
        Node<K, V> temp = root;
        if (root == null) {                  // перевіряємо чи є корень дерева,
            root = new Node<>(key, value);   // якщо немає, то вставляємо наші дані на місце кореня
        } else {
            int cmp;                         //зміна для порівняння ключів
            do {
                parent = temp;
                cmp = key.compareTo(temp.key);
                if (cmp < 0) {               // якщо ключ нового елемента менший за попередній,
                    temp = temp.left;        // то переходимо в ліву гілку
                } else if (cmp > 0) {        // якщо ключ нового елемента більший за попередній,
                    temp = temp.right;       // то переходимо в праву гілку
                } else {
                    V oldValue = temp.value;  // якщо ключі співпадають
                    temp.value = value;       // то замінюємо значення в даному вузлі
                    return oldValue;
                }
            } while (temp != null);           // виконуємо даний цикл доки не дійдемо до листа
            if (cmp < 0 ) {                             // якщо ключ нового елемента менший за ключ попереднього
                parent.left = new Node<>(key, value);   // то записуємо у ліву комірку
            } else if (cmp > 0) {                       // якщо ключ нового елемента більший за ключ попереднього
                parent.right = new Node<>(key, value);  // то записуємо у ліву гілку
            }
            size++;                                     //збільшуємо розмір дерева
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        K k = (K) key;
        Node<K, V> removingNode = root;
        Node<K, V> parent = root;
        Node<K, V> successor = null;
        if (root == null) {
            return null;
        } else {
            int cmp;
            do {                                                  // пошук вузла
                cmp = k.compareTo(removingNode.key);
                if (cmp < 0) {                                    // якщо заданий ключ менший за ключ попереднього вузла
                    if (removingNode.left == null) return null;   // то рухаємося ліворуч
                    parent = removingNode;
                    removingNode = removingNode.left;
                } else if (cmp > 0) {                             // якщо заданий ключ більший за ключ попереднього вузла
                    if (removingNode.right == null) return null;  // то рухаємося праворуч
                    parent = removingNode;
                    removingNode = removingNode.right;
                } else {                                          // якщо ключі рівні
                    break;                                        // то виходимо з циклу і переходимо до видалення
                }
            } while (true);

            // видалення для випадку коли у нашого елемента немає нащадків
            if (removingNode.right == null &&                 // перевіряємо чи у заданого вузла є нащадки
                    removingNode.left == null) {              //якщо немає, то перевіряємо чи вузол не є коренем
                if (root == removingNode) root = null;        // якщо це корінь, то просто присвоюємо йому null
                else if (parent.left == removingNode) parent.left = null;    //якщо вузол є правим або лівим нащадком
                else if (parent.right == removingNode) parent.right = null;  //просто присвоюємо йому null
            } else if (removingNode.right == null) {

            /*видалення для випадку коли у вузла є один нащадок
             якщо вузол має одного нащадка
            то ми переносимо нащадок на місце вузла*/
            if (parent.left == removingNode) parent.left = removingNode.left;
                else parent.right = removingNode.left;
            } else if (removingNode.left == null) {
                if (parent.left == removingNode) parent.left = removingNode.right;
                else parent.right = removingNode.right;
            } else {

            //видалення для випадку коли у вузла є 2 нащадки

             /*  знаходимо у нашому дереві вузол (наступник), ключ якого є менший
                 за ключ вузла, який ми хочемо видалити,
                 але більший за ключ правого нащадка даного вузла,
                 і переносимо його на місце вузла, який хочемо видалити.
                 Для цього спочатку рухаємося від вузла праворуч,
                 а далі ліворуч, поки не знайдемо елемент,
                 що має лише одного нащадка або взагалі немає.
                 У випадку якщо у правого вузла немає лівих нащадків,
                 то переносимо даний правий вузол на місце вузла, що видаляється
             */
             successor = getSuccessor(removingNode);

                if (removingNode == root) {
                    root = successor;
                } else if (parent.left == removingNode) {
                    parent.left = successor;
                } else {
                    parent.right = successor;
                    successor.left = removingNode.left;
                }
            }
            size--;
            return removingNode.getValue();
        }

    }

    // метод для пошуку наступника при видаленні вузла, що має 2-ох нащадків
    private Node<K, V> getSuccessor(Node<K, V> removingNode) {
        Node<K, V> successorParent = removingNode;
        Node<K, V> successor = removingNode;
        Node<K, V> current = removingNode.right;

        while (current != null) {        // якщо є ліві нащадки
            successorParent = successor;
            successor = current;                // то рухаємося ліворуч поки не знайдемо
            current = current.left;          // вузол без лівого нащадка
        }
        if (successor != removingNode.right) {      // якщо наступник не є правим нащадком видаляємого вузла
            successorParent.left = successor.right;    // переносимо праву гілку від наступника на місце наступника
            successor.right = removingNode.right;   // а на місце правого вузла наступника - праву гілку від вузла,
        }                                          // що видаляється
        return successor;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        runTreeForKey(root, set);
        return set;
    }

    public void runTreeForKey(Node<K, V> localRoot, Set<K> set) {
        if (localRoot != null) {
            runTreeForKey(localRoot.left, set);
            set.add(localRoot.getKey());
            runTreeForKey(localRoot.right, set);
        }
    }

    @Override
    public Collection<V> values() {
        Collection<V> array = new ArrayList<>();
        runTreeForValue(root, array);
        return array;
    }

    public void runTreeForValue(Node<K, V> localRoot, Collection<V> array) {
        if (localRoot != null) {
            runTreeForValue(localRoot.left, array);
            array.add(localRoot.getValue());
            runTreeForValue(localRoot.right, array);
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>();
        runTreeForNode(root, set);
        return set;
    }

    public void runTreeForNode(Node<K, V> localRoot, Set<Entry<K,V>> set) {
        if (localRoot != null) {
            runTreeForNode(localRoot.left, set);
            set.add(localRoot);
            runTreeForNode(localRoot.right, set);
        }
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    public void displayTree()
    {
        Stack globalStack = new Stack();
        globalStack.push(root);
        int nBlanks = 35;
        boolean isRowEmpty = false;
        log.info(
                "................................................................................");
        while(isRowEmpty==false)
        {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for(int j=0; j<nBlanks; j++)
                System.out.print(' ');
            while(globalStack.isEmpty()==false)
            {
                Node temp = (Node)globalStack.pop();
                if(temp != null)
                {
                    System.out.print(temp.key);
                    localStack.push(temp.left);
                    localStack.push(temp.right);
                    if(temp.left != null ||
                            temp.right != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }
                for(int j=0; j<nBlanks*2-2; j++)
                    System.out.print(' ');
            }
            log.info("\n");
            nBlanks /= 2;
            while(localStack.isEmpty()==false)
                globalStack.push( localStack.pop() );
        }
        log.info(
                "................................................................................");
    }

    public void print() {
            inOrder(root);
    }

    private void inOrder(Node localRoot)
    {
        if(localRoot != null)
        {
            inOrder(localRoot.left);
            log.info("Key: " + localRoot.key + " Value: " + localRoot.value);
            inOrder(localRoot.right);
        }
    }

}

