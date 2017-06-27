package io.github.riwcwt.tree;

/**
 * Created by michael on 2017-06-20.
 */
public class BTree<T extends Object> {

    private Node<T> root;

    public BTree() {
    }

    public Node<T> add(T data) {
        if (data == null) {
            return null;
        }
        if (root == null) {
            root = new Node<>(data);
            return root;
        } else {
            Node<T> current = root;
            int n;
            Node<T> node = new Node<>(data);
            while (true) {
                n = compare(data, current.getData());
                if (n == 0) {
                    return current;
                } else if (n > 0) {
                    if (current.getRight() == null) {
                        current.setRight(node);
                    } else {
                        current = current.getRight();
                    }
                } else {
                    if (current.getLeft() == null) {
                        current.setLeft(node);
                    } else {
                        current = current.getLeft();
                    }
                }
            }
        }
    }

    public Node<T> delete(T data) {
        return delete(data, this.root);
    }

    private Node<T> delete(T data, Node<T> node) {
        if (node == null) {
            return null;
        }
        Node<T> parent = null;
        Node<T> current = node;
        int n;
        while (true) {
            n = compare(data, current.getData());
            if (n == 0) {
                if (current.getLeft() == null && current.getRight() == null) {
                    if (parent != null) {
                        if (current.equals(parent.getLeft())) {
                            parent.setLeft(null);
                        } else if (current.equals(parent.getRight())) {
                            parent.setRight(null);
                        }
                    } else {
                        this.root = null;
                    }
                } else if (current.getLeft() == null || current.getRight() == null) {
                    if (parent != null) {
                        if (current.equals(parent.getLeft())) {
                            if (current.getLeft() != null) {
                                parent.setLeft(current.getLeft());
                            } else if (current.getRight() != null) {
                                parent.setLeft(current.getRight());
                            }
                        } else if (current.equals(parent.getRight())) {
                            if (current.getLeft() != null) {
                                parent.setRight(current.getLeft());
                            } else if (current.getRight() != null) {
                                parent.setRight(current.getRight());
                            }
                        }
                    } else {
                        if (current.getLeft() != null) {
                            this.root = current.getLeft();
                        } else if (current.getRight() != null) {
                            this.root = current.getRight();
                        }
                    }
                } else {
                    Node<T> min = min(current.getRight());

                    min.setLeft(current.getLeft());
                    min.setRight(current.getRight());

                    if (parent == null) {
                        this.root = min;
                    } else {
                        if (current.equals(parent.getLeft())) {
                            parent.setLeft(min);
                        } else if (current.equals(parent.getRight())) {
                            parent.setRight(min);
                        }
                    }

                    delete(min.getData(), current.getRight());
                }
                return current;
            } else if (n > 0) {
                if (current.getRight() != null) {
                    parent = current;
                    current = current.getRight();
                } else {
                    break;
                }
            } else {
                if (current.getLeft() != null) {
                    parent = current;
                    current = current.getLeft();
                } else {
                    break;
                }
            }
        }
        return null;
    }

    private Node<T> min(Node<T> node) {
        Node<T> current = node;
        while (true) {
            if (current.getLeft() == null) {
                break;
            }
            current = current.getLeft();
        }
        return current;
    }

    public void print() {
        this.print(this.root);
    }

    private void print(Node<T> node) {
        if (node == null) {
            return;
        }
        print(node.getLeft());
        System.out.println(node.getData());
        print(node.getRight());
    }

    public Node<T> find(T data) {
        return this.find(data, this.root);
    }

    public int deep() {
        return this.deep(this.root);
    }

    private int deep(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(deep(node.getLeft()), deep(node.getRight()));
    }

    private Node find(T data, Node<T> root) {
        if (root == null) {
            return null;
        }
        System.out.println("经过节点 ： " + root.getData());
        int n = compare(data, root.getData());
        if (n == 0) {
            return root;
        } else if (n > 0) {
            return find(data, root.getRight());
        } else {
            return find(data, root.getLeft());
        }
    }

    private int compare(T from, T to) {
        int result;
        if (from.equals(to)) {
            result = 0;
        } else if (from.hashCode() > to.hashCode()) {
            result = 1;
        } else {
            result = -1;
        }
        return result;
    }

    public static class Node<T> {
        private T data;
        private Node left;
        private Node right;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<?> node = (Node<?>) o;

            return data != null ? data.equals(node.data) : node.data == null;
        }

        @Override
        public int hashCode() {
            return data != null ? data.hashCode() : 0;
        }
    }

}