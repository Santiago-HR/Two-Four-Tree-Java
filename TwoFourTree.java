public class TwoFourTree {
    private class TwoFourTreeItem {
        int values = 1;
        int value1 = 0; // always exists.
        int value2 = 0; // exists iff the node is a 3-
        // node or 4-node.
        int value3 = 0; // exists iff the node is a 4-
        // node.
        boolean isLeaf = true;
        TwoFourTreeItem parent = null; // parent exists iff the node
        // is not root.
        TwoFourTreeItem leftChild = null; // left and right child exist
        // iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;
        TwoFourTreeItem centerChild = null; // center child exists iff the
        // node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null; // center-left and center-right
        // children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        public boolean isTwoNode() {
            if (values == 1) return true;
            return false;
        }

        public boolean isThreeNode() {
            if (values == 2) return true;
            return false;
        }

        public boolean isFourNode() {
            if (values == 3) return true;
            return false;
        }

        public boolean isRoot() {
            if (this == root) return true;
            return false;
        }

        public TwoFourTreeItem(int value1) { // 2-node
            this.values = 1;
            this.value1 = value1;
            this.isLeaf = true;
        }

        public TwoFourTreeItem(int value1, int value2) { // 3-node
            this.values = 2;
            this.isLeaf = true;
            if (value1 < value2) {
                this.value1 = value1;
                this.value2 = value2;
            } else {
                this.value1 = value2;
                this.value2 = value1;
            }
        }

        public TwoFourTreeItem(int value1, int value2, int value3) { // 4-node
            this.values = 3;
            this.isLeaf = true;
            int[] vals = { value1, value2, value3 };
            java.util.Arrays.sort(vals);
            this.value1 = vals[0];
            this.value2 = vals[1];
            this.value3 = vals[2];
        }

        private void printIndents(int indent) {
            for (int i = 0; i < indent; i++) System.out.printf(" ");
        }

        public void printInOrder(int indent) {
            if (!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if (isThreeNode()) {
                if (!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if (isFourNode()) {
                if (!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if (!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if (!isLeaf) rightChild.printInOrder(indent + 1);
        }
    }

    TwoFourTreeItem root = null;

    public TwoFourTreeItem merge(TwoFourTreeItem node) {
        if (node == null) return null;
        if (node.isTwoNode()) {
            if (node.isRoot()) {
                if (node.leftChild.isTwoNode() && node.rightChild.isTwoNode()) {
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.leftChild.value1, node.value1, node.rightChild.value1);
                    if (!node.leftChild.isLeaf && !node.rightChild.isLeaf)
                        replacementNode.isLeaf = false;
                    replacementNode.leftChild = node.leftChild.leftChild;
                    replacementNode.leftChild.parent = replacementNode;
                    replacementNode.centerLeftChild = node.leftChild.rightChild;
                    replacementNode.centerLeftChild.parent = replacementNode;
                    replacementNode.centerRightChild = node.rightChild.leftChild;
                    replacementNode.centerRightChild.parent = replacementNode;
                    replacementNode.rightChild = node.rightChild.rightChild;
                    replacementNode.rightChild.parent = replacementNode;
                    node = replacementNode;
                    root = replacementNode;
                    return replacementNode;
                }
                return node;
            } else if (node == node.parent.leftChild) {
                if (node.parent.isTwoNode()) {
                    if (node.parent.rightChild.isThreeNode()) {
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1); //creation of replacement node
                        replacementNode.isLeaf = node.isLeaf;
                        if (!node.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.rightChild.leftChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.value1 = node.parent.rightChild.value1;
                        TwoFourTreeItem replacementNodeRight = new
                        TwoFourTreeItem(node.parent.rightChild.value2); // creation of new right child
                        replacementNodeRight.isLeaf =
                        node.parent.rightChild.isLeaf;
                        if (!node.parent.rightChild.isLeaf) {
                            replacementNodeRight.leftChild =
                            node.parent.rightChild.centerChild;
                            replacementNodeRight.leftChild.parent =
                            replacementNodeRight;
                            replacementNodeRight.rightChild =
                            node.parent.rightChild.rightChild;
                            replacementNodeRight.rightChild.parent =
                            replacementNodeRight;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNodeRight.parent = replacementNode.parent;
                        replacementNodeRight.parent.rightChild =
                        replacementNodeRight;
                        return replacementNode;
                    } else {
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.rightChild.leftChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.value1 = node.parent.rightChild.value1;
                        TwoFourTreeItem replacementNodeRight = new
                        TwoFourTreeItem(node.parent.rightChild.value2, node.parent.rightChild.value3);
                        replacementNodeRight.isLeaf =
                        node.parent.rightChild.isLeaf;
                        if (!replacementNodeRight.isLeaf) {
                            replacementNodeRight.leftChild =
                            node.parent.rightChild.centerLeftChild;
                            replacementNodeRight.leftChild.parent =
                            replacementNodeRight;
                            replacementNodeRight.centerChild =
                            node.parent.rightChild.centerRightChild;
                            replacementNodeRight.centerChild.parent =
                            replacementNodeRight;
                            replacementNodeRight.rightChild =
                            node.parent.rightChild.rightChild;
                            replacementNodeRight.rightChild.parent =
                            replacementNodeRight;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNodeRight.parent = replacementNode.parent;
                        replacementNodeRight.parent.rightChild =
                        replacementNodeRight;
                        return replacementNode;
                    }
                } else if (node.parent.isThreeNode()) {
                    if (node.parent.centerChild.isTwoNode()) {
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1, node.parent.centerChild.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerLeftChild = node.rightChild;
                            replacementNode.centerLeftChild.parent =
                            replacementNode;
                            replacementNode.centerRightChild =
                            node.parent.centerChild.leftChild;
                            replacementNode.centerRightChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.centerChild.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.values--;
                        node.parent.value1 = node.parent.value2;
                        node.parent.value2 = 0;
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNode.parent.centerChild = null;
                        return replacementNode;
                    } else if (node.parent.centerChild.isThreeNode()) {
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.centerChild.leftChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.value1 = node.parent.centerChild.value1;
                        //Creates a new center node
                        TwoFourTreeItem replacementNodeCenter = new
                        TwoFourTreeItem(node.parent.centerChild.value2);
                        replacementNodeCenter.isLeaf =
                        node.parent.centerChild.isLeaf;
                        if (!replacementNodeCenter.isLeaf) {
                            replacementNodeCenter.leftChild =
                            node.parent.centerChild.centerChild;
                            replacementNodeCenter.leftChild.parent =
                            replacementNodeCenter;
                            replacementNodeCenter.rightChild =
                            node.parent.centerChild.rightChild;
                            replacementNodeCenter.rightChild.parent =
                            replacementNodeCenter;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNodeCenter.parent = replacementNode.parent;
                        replacementNodeCenter.parent.centerChild =
                        replacementNodeCenter;
                        return replacementNode;
                    } else {
                        //Creates a new left node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.centerChild.leftChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.value1 = node.parent.centerChild.value1;
                        //Creates a new center node
                        TwoFourTreeItem replacementNodeCenter = new
                        TwoFourTreeItem(node.parent.centerChild.value2, node.parent.centerChild.value3);
                        replacementNodeCenter.isLeaf =
                        node.parent.centerChild.isLeaf;
                        if (!replacementNodeCenter.isLeaf) {
                            replacementNodeCenter.leftChild =
                            node.parent.centerChild.centerLeftChild;
                            replacementNodeCenter.leftChild.parent =
                            replacementNodeCenter;
                            replacementNodeCenter.centerChild =
                            node.parent.centerChild.centerRightChild;
                            replacementNodeCenter.centerChild.parent =
                            replacementNodeCenter;
                            replacementNodeCenter.rightChild =
                            node.parent.centerChild.rightChild;
                            replacementNodeCenter.rightChild.parent =
                            replacementNodeCenter;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNodeCenter.parent = replacementNode.parent;
                        replacementNodeCenter.parent.centerChild =
                        replacementNodeCenter;
                        return replacementNode;
                    }
                } else {
                    if (node.parent.centerLeftChild.isTwoNode()) {
                        //Creates a new left node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1,
                        node.parent.centerLeftChild.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerLeftChild = node.rightChild;
                            replacementNode.centerLeftChild.parent =
                            replacementNode;
                            replacementNode.centerRightChild =
                            node.parent.centerLeftChild.leftChild;
                            replacementNode.centerRightChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.centerLeftChild.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.values = 2;
                        node.parent.value1 = node.parent.value2;
                        node.parent.value2 = node.parent.value3;
                        node.parent.value3 = 0;
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNode.parent.centerChild =
                        node.parent.centerRightChild;
                        replacementNode.parent.centerLeftChild = null;
                        replacementNode.parent.centerRightChild = null;
                        return replacementNode;
                    } else if (node.parent.centerLeftChild.isThreeNode()) {
                        //Creates a new left node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.centerLeftChild.leftChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.value1 =
                        node.parent.centerLeftChild.value1;
                        //Creates a new center left node
                        TwoFourTreeItem replacementNodeCenterLeft = new
                        TwoFourTreeItem(node.parent.centerLeftChild.value2);
                        replacementNodeCenterLeft.isLeaf =
                        node.parent.centerLeftChild.isLeaf;
                        if (!replacementNodeCenterLeft.isLeaf) {
                            replacementNodeCenterLeft.leftChild =
                            node.parent.centerLeftChild.centerChild;
                            replacementNodeCenterLeft.leftChild.parent =
                            replacementNodeCenterLeft;
                            replacementNodeCenterLeft.rightChild =
                            node.parent.centerLeftChild.rightChild;
                            replacementNodeCenterLeft.rightChild.parent =
                            replacementNodeCenterLeft;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNodeCenterLeft.parent =
                        replacementNode.parent;
                        replacementNodeCenterLeft.parent.centerLeftChild =
                        replacementNodeCenterLeft;
                        return replacementNode;
                    } else {
                        //Creates a new left node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.value1, node.parent.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.leftChild = node.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                            replacementNode.centerChild = node.rightChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.rightChild =
                            node.parent.centerLeftChild.leftChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                        }
                        node.parent.value1 =
                        node.parent.centerLeftChild.value1;
                        //Creates a new center left node
                        TwoFourTreeItem replacementNodeCenterLeft = new
                        TwoFourTreeItem(node.parent.centerLeftChild.value2,
                        node.parent.centerLeftChild.value3);
                        replacementNodeCenterLeft.isLeaf =
                        node.parent.centerLeftChild.isLeaf;
                        if (!replacementNodeCenterLeft.isLeaf) {
                            replacementNodeCenterLeft.leftChild =
                            node.parent.centerLeftChild.centerLeftChild;
                            replacementNodeCenterLeft.leftChild.parent =
                            replacementNodeCenterLeft;
                            replacementNodeCenterLeft.centerChild =
                            node.parent.centerLeftChild.centerRightChild;
                            replacementNodeCenterLeft.centerChild.parent =
                            replacementNodeCenterLeft;
                            replacementNodeCenterLeft.rightChild =
                            node.parent.centerLeftChild.rightChild;
                            replacementNodeCenterLeft.rightChild.parent =
                            replacementNodeCenterLeft;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.leftChild = replacementNode;
                        replacementNodeCenterLeft.parent =
                        replacementNode.parent;
                        replacementNodeCenterLeft.parent.centerLeftChild =
                        replacementNodeCenterLeft;
                        return replacementNode;
                    }
                }
            } else if (node == node.parent.centerLeftChild) {
                if (node.parent.centerRightChild.isTwoNode()) {
                    //Creates a new center left child
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value2,
                    node.parent.centerRightChild.value1);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerLeftChild = node.rightChild;
                        replacementNode.centerLeftChild.parent =
                        replacementNode;
                        replacementNode.centerRightChild =
                        node.parent.centerRightChild.leftChild;
                        replacementNode.centerRightChild.parent =
                        replacementNode;
                        replacementNode.rightChild =
                        node.parent.centerRightChild.rightChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.values = 2;
                    node.parent.value2 = node.parent.value3;
                    node.parent.value3 = 0;
                    replacementNode.parent = node.parent;
                    replacementNode.parent.centerChild = replacementNode;
                    replacementNode.parent.centerRightChild = null;
                    replacementNode.parent.centerLeftChild = null;
                    return replacementNode;
                } else if (node.parent.centerRightChild.isThreeNode()) {
                    //Creates a new center left node
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value2);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerChild = node.rightChild;
                        replacementNode.centerChild.parent = replacementNode;
                        replacementNode.rightChild =
                        node.parent.centerRightChild.leftChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.value2 = node.parent.centerRightChild.value1;
                    //Creates a new center right node
                    TwoFourTreeItem replacementNodeCenterRight = new
                    TwoFourTreeItem(node.parent.centerRightChild.value2);
                    replacementNodeCenterRight.isLeaf =
                    node.parent.centerRightChild.isLeaf;
                    if (!replacementNodeCenterRight.isLeaf) {
                        replacementNodeCenterRight.leftChild =
                        node.parent.centerRightChild.centerChild;
                        replacementNodeCenterRight.leftChild.parent =
                        replacementNodeCenterRight;
                        replacementNodeCenterRight.rightChild =
                        node.parent.centerRightChild.rightChild;
                        replacementNodeCenterRight.rightChild.parent =
                        replacementNodeCenterRight;
                    }
                    replacementNode.parent = node.parent;
                    replacementNode.parent.centerLeftChild = replacementNode;
                    replacementNodeCenterRight.parent = replacementNode.parent;
                    replacementNodeCenterRight.parent.centerRightChild =
                    replacementNodeCenterRight;
                    return replacementNode;
                } else {
                    //Creates a new center left node
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value2);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerChild = node.rightChild;
                        replacementNode.centerChild.parent = replacementNode;
                        replacementNode.rightChild =
                        node.parent.centerRightChild.leftChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.value2 = node.parent.centerRightChild.value1;
                    //Creates a new center right node
                    TwoFourTreeItem replacementNodeCenterRight = new
                    TwoFourTreeItem(node.parent.centerLeftChild.value2,
                    node.parent.centerRightChild.value3);
                    replacementNodeCenterRight.isLeaf =
                    node.parent.centerRightChild.isLeaf;
                    if (!replacementNodeCenterRight.isLeaf) {
                        replacementNodeCenterRight.leftChild =
                        node.parent.centerRightChild.centerLeftChild;
                        replacementNodeCenterRight.leftChild.parent =
                        replacementNodeCenterRight;
                        replacementNodeCenterRight.centerChild =
                        node.parent.centerRightChild.centerRightChild;
                        replacementNodeCenterRight.centerChild.parent =
                        replacementNodeCenterRight;
                        replacementNodeCenterRight.rightChild =
                        node.parent.centerRightChild.rightChild;
                        replacementNodeCenterRight.rightChild.parent =
                        replacementNodeCenterRight;
                    }
                    replacementNode.parent = node.parent;
                    replacementNode.parent.centerLeftChild = replacementNode;
                    replacementNodeCenterRight.parent = replacementNode.parent;
                    replacementNodeCenterRight.parent.centerRightChild =
                    replacementNodeCenterRight;
                    return replacementNode;
                }
            } else if (node == node.parent.centerChild) {
                if (node.parent.rightChild.isTwoNode()) {
                    //Creates a new right node
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value2, node.parent.rightChild.value1);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerLeftChild = node.rightChild;
                        replacementNode.centerLeftChild.parent =
                        replacementNode;
                        replacementNode.centerRightChild =
                        node.parent.rightChild.leftChild;
                        replacementNode.centerRightChild.parent =
                        replacementNode;
                        replacementNode.rightChild =
                        node.parent.rightChild.rightChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.values = 1;
                    node.parent.value2 = 0;
                    replacementNode.parent = node.parent;
                    replacementNode.parent.rightChild = replacementNode;
                    replacementNode.parent.centerChild = null;
                    return replacementNode;
                } else if (node.parent.rightChild.isThreeNode()) {
                    //Creates a new center node
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value2);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerChild = node.rightChild;
                        replacementNode.centerChild.parent = replacementNode;
                        replacementNode.rightChild =
                        node.parent.rightChild.leftChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.value2 = node.parent.rightChild.value1;
                    //Creates a new right node
                    TwoFourTreeItem replacementNodeRight = new
                    TwoFourTreeItem(node.parent.rightChild.value2);
                    replacementNodeRight.isLeaf =
                    node.parent.rightChild.isLeaf;
                    if (!replacementNodeRight.isLeaf) {
                        replacementNodeRight.leftChild =
                        node.parent.rightChild.centerChild;
                        replacementNodeRight.leftChild.parent =
                        replacementNodeRight;
                        replacementNodeRight.rightChild =
                        node.parent.rightChild.rightChild;
                        replacementNodeRight.rightChild.parent =
                        replacementNodeRight;
                    }
                    replacementNode.parent = node.parent;
                    replacementNode.parent.centerChild = replacementNode;
                    replacementNodeRight.parent = replacementNode.parent;
                    replacementNodeRight.parent.rightChild =
                    replacementNodeRight;
                    return replacementNode;
                } else {
                    //Creates a new center node
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value2);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerChild = node.rightChild;
                        replacementNode.centerChild.parent = replacementNode;
                        replacementNode.rightChild =
                        node.parent.rightChild.leftChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.value2 = node.parent.rightChild.value1;
                    //Creates a new right node
                    TwoFourTreeItem replacementNodeRight = new
                    TwoFourTreeItem(node.parent.rightChild.value2, node.parent.rightChild.value3);
                    replacementNodeRight.isLeaf =
                    node.parent.rightChild.isLeaf;
                    if (!replacementNodeRight.isLeaf) {
                        replacementNodeRight.leftChild =
                        node.parent.rightChild.centerLeftChild;
                        replacementNodeRight.leftChild.parent =
                        replacementNodeRight;
                        replacementNodeRight.centerChild =
                        node.parent.rightChild.centerRightChild;
                        replacementNodeRight.centerChild.parent =
                        replacementNodeRight;
                        replacementNodeRight.rightChild =
                        node.parent.rightChild.rightChild;
                        replacementNodeRight.rightChild.parent =
                        replacementNodeRight;
                    }
                    replacementNode.parent = node.parent;
                    replacementNode.parent.centerChild = replacementNode;
                    replacementNodeRight.parent = replacementNode.parent;
                    replacementNodeRight.parent.rightChild =
                    replacementNodeRight;
                    return replacementNode;
                }
            } else if (node == node.parent.centerRightChild) {
                if (node.parent.rightChild.isTwoNode()) {
                    //Creates a new right node
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value3, node.parent.rightChild.value1);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerLeftChild = node.rightChild;
                        replacementNode.centerLeftChild.parent =
                        replacementNode;
                        replacementNode.centerRightChild =
                        node.parent.rightChild.leftChild;
                        replacementNode.centerRightChild.parent =
                        replacementNode;
                        replacementNode.rightChild =
                        node.parent.rightChild.rightChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.values = 2;
                    node.parent.value3 = 0;
                    replacementNode.parent = node.parent;
                    replacementNode.parent.rightChild = replacementNode;
                    replacementNode.parent.centerChild =
                    replacementNode.parent.centerLeftChild;
                    replacementNode.parent.centerLeftChild = null;
                    replacementNode.parent.centerRightChild = null;
                    return replacementNode;
                } else if (node.parent.rightChild.isThreeNode()) {
                    //Creates a new center right child
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value3);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerChild = node.rightChild;
                        replacementNode.centerChild.parent = replacementNode;
                        replacementNode.rightChild =
                        node.parent.rightChild.leftChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.value3 = node.parent.rightChild.value1;
                    //Creates a new right child node
                    TwoFourTreeItem replacementNodeRight = new
                    TwoFourTreeItem(node.parent.rightChild.value2);
                    replacementNodeRight.isLeaf =
                    node.parent.rightChild.isLeaf;
                    if (!replacementNodeRight.isLeaf) {
                        replacementNodeRight.leftChild =
                        node.parent.rightChild.centerChild;
                        replacementNodeRight.leftChild.parent =
                        replacementNodeRight;
                        replacementNodeRight.rightChild =
                        node.parent.rightChild.rightChild;
                        replacementNodeRight.rightChild.parent =
                        replacementNodeRight;
                    }
                    replacementNode.parent = node.parent;
                    replacementNode.parent.centerRightChild = replacementNode;
                    replacementNodeRight.parent = replacementNode.parent;
                    replacementNodeRight.parent.rightChild =
                    replacementNodeRight;
                    return replacementNode;
                } else {
                    //Creates a new center right node
                    TwoFourTreeItem replacementNode = new
                    TwoFourTreeItem(node.value1, node.parent.value3);
                    replacementNode.isLeaf = node.isLeaf;
                    if (!replacementNode.isLeaf) {
                        replacementNode.leftChild = node.leftChild;
                        replacementNode.leftChild.parent = replacementNode;
                        replacementNode.centerChild = node.rightChild;
                        replacementNode.centerChild.parent = replacementNode;
                        replacementNode.rightChild =
                        node.parent.rightChild.leftChild;
                        replacementNode.rightChild.parent = replacementNode;
                    }
                    node.parent.value3 = node.rightChild.value1;
                    //Creates a new right node
                    TwoFourTreeItem replacementNodeRight = new
                    TwoFourTreeItem(node.parent.rightChild.value2, node.parent.rightChild.value3);
                    replacementNodeRight.isLeaf = node.isLeaf;
                    if (!replacementNodeRight.isLeaf) {
                        replacementNodeRight.leftChild =
                        node.parent.rightChild.centerLeftChild;
                        replacementNodeRight.leftChild.parent =
                        replacementNodeRight;
                        replacementNodeRight.centerChild =
                        node.parent.rightChild.centerRightChild;
                        replacementNodeRight.centerChild.parent =
                        replacementNodeRight;
                        replacementNodeRight.rightChild =
                        node.parent.rightChild.rightChild;
                        replacementNodeRight.rightChild.parent =
                        replacementNodeRight;
                    }
                    replacementNode.parent = node.parent;
                    replacementNode.parent.centerRightChild = replacementNode;
                    replacementNodeRight.parent = replacementNode.parent;
                    replacementNodeRight.parent.rightChild =
                    replacementNodeRight;
                    return replacementNode;
                }
            } else {
                if (node.parent.isTwoNode()) {
                    if (node.parent.leftChild.isThreeNode()) {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.value1, node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerChild = node.leftChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.leftChild.rightChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.value1 = node.parent.leftChild.value2;
                        //Creates a new left node
                        TwoFourTreeItem replacementNodeLeft = new
                        TwoFourTreeItem(node.parent.leftChild.value1);
                        replacementNodeLeft.isLeaf =
                        node.parent.leftChild.isLeaf;
                        if (!replacementNodeLeft.isLeaf) {
                            replacementNodeLeft.rightChild =
                            node.parent.leftChild.centerChild;
                            replacementNodeLeft.rightChild.parent =
                            replacementNodeLeft;
                            replacementNodeLeft.leftChild =
                            node.parent.leftChild.leftChild;
                            replacementNodeLeft.leftChild.parent =
                            replacementNodeLeft;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNodeLeft.parent = replacementNode.parent;
                        replacementNodeLeft.parent.leftChild =
                        replacementNodeLeft;
                        return replacementNode;
                    } else {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.value1, node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerChild = node.leftChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.leftChild.rightChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.value1 = node.parent.leftChild.value3;
                        //Creates a new left node
                        TwoFourTreeItem replacementNodeLeft = new
                        TwoFourTreeItem(node.parent.leftChild.value1, node.parent.leftChild.value2);
                        replacementNodeLeft.isLeaf =
                        node.parent.leftChild.isLeaf;
                        if (!replacementNodeLeft.isLeaf) {
                            replacementNodeLeft.rightChild =
                            node.parent.leftChild.centerRightChild;
                            replacementNodeLeft.rightChild.parent =
                            replacementNodeLeft;
                            replacementNodeLeft.centerChild =
                            node.parent.leftChild.centerLeftChild;
                            replacementNodeLeft.centerChild.parent =
                            replacementNodeLeft;
                            replacementNodeLeft.leftChild =
                            node.parent.leftChild.leftChild;
                            replacementNodeLeft.leftChild.parent =
                            replacementNodeLeft;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNodeLeft.parent = replacementNode.parent;
                        replacementNodeLeft.parent.leftChild =
                        replacementNodeLeft;
                        return replacementNode;
                    }
                } else if (node.parent.isThreeNode()) {
                    if (node.parent.centerChild.isTwoNode()) {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.centerChild.value1, node.parent.value2, node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerRightChild = node.leftChild;
                            replacementNode.centerRightChild.parent =
                            replacementNode;
                            replacementNode.centerLeftChild =
                            node.parent.centerChild.rightChild;
                            replacementNode.centerLeftChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.centerChild.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.values = 1;
                        node.parent.value2 = 0;
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNode.parent.centerChild = null;
                        return replacementNode;
                    } else if (node.parent.centerChild.isThreeNode()) {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.value2, node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerChild = node.leftChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.centerChild.rightChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.value2 = node.parent.centerChild.value2;
                        //Creates a new center node
                        TwoFourTreeItem replacementNodeCenter = new
                        TwoFourTreeItem(node.parent.centerChild.value1);
                        replacementNodeCenter.isLeaf =
                        node.parent.centerChild.isLeaf;
                        if (!replacementNodeCenter.isLeaf) {
                            replacementNodeCenter.rightChild =
                            node.parent.centerChild.centerChild;
                            replacementNodeCenter.rightChild.parent =
                            replacementNodeCenter;
                            replacementNodeCenter.leftChild =
                            node.parent.centerChild.leftChild;
                            replacementNodeCenter.leftChild.parent =
                            replacementNodeCenter;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNodeCenter.parent = replacementNode.parent;
                        replacementNodeCenter.parent.centerChild =
                        replacementNodeCenter;
                        return replacementNode;
                    } else {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.value2, node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerChild = node.leftChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.centerChild.rightChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.value2 = node.parent.centerChild.value3;
                        //Creates a new center node
                        TwoFourTreeItem replacementNodeCenter = new
                        TwoFourTreeItem(node.parent.centerChild.value1, node.parent.centerChild.value2);
                        replacementNodeCenter.isLeaf =
                        node.parent.centerChild.isLeaf;
                        if (!replacementNodeCenter.isLeaf) {
                            replacementNodeCenter.rightChild =
                            node.parent.centerChild.centerRightChild;
                            replacementNodeCenter.rightChild.parent =
                            replacementNodeCenter;
                            replacementNodeCenter.centerChild =
                            node.parent.centerChild.centerLeftChild;
                            replacementNodeCenter.centerChild.parent =
                            replacementNodeCenter;
                            replacementNodeCenter.leftChild =
                            node.parent.centerChild.leftChild;
                            replacementNodeCenter.leftChild.parent =
                            replacementNodeCenter;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNodeCenter.parent = replacementNode.parent;
                        replacementNodeCenter.parent.centerChild =
                        replacementNodeCenter;
                        return replacementNode;
                    }
                } else {
                    if (node.parent.centerRightChild.isTwoNode()) {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.centerRightChild.value1, node.parent.value3,
                        node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerRightChild = node.leftChild;
                            replacementNode.centerRightChild.parent =
                            replacementNode;
                            replacementNode.centerLeftChild =
                            node.parent.centerRightChild.rightChild;
                            replacementNode.centerLeftChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.centerRightChild.leftChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.values = 2;
                        node.parent.value3 = 0;
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNode.parent.centerChild =
                        replacementNode.parent.centerLeftChild;
                        replacementNode.parent.centerLeftChild = null;
                        replacementNode.parent.centerRightChild = null;
                        return replacementNode;
                    } else if (node.parent.centerRightChild.isThreeNode()) {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.value3, node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerChild = node.leftChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.centerRightChild.rightChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.value3 =
                        node.parent.centerRightChild.value2;
                        //Creates a new center right node
                        TwoFourTreeItem replacementNodeCenterRight = new
                        TwoFourTreeItem(node.parent.centerRightChild.value1);
                        replacementNodeCenterRight.isLeaf =
                        node.parent.centerRightChild.isLeaf;
                        if (!replacementNodeCenterRight.isLeaf) {
                            replacementNodeCenterRight.rightChild =
                            node.parent.centerRightChild.centerChild;
                            replacementNodeCenterRight.rightChild.parent =
                            replacementNodeCenterRight;
                            replacementNodeCenterRight.leftChild =
                            node.parent.centerRightChild.leftChild;
                            replacementNodeCenterRight.leftChild.parent =
                            replacementNodeCenterRight;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNodeCenterRight.parent =
                        replacementNode.parent;
                        replacementNodeCenterRight.parent.centerRightChild =
                        replacementNodeCenterRight;
                        return replacementNode;
                    } else {
                        //Creates a new right node
                        TwoFourTreeItem replacementNode = new
                        TwoFourTreeItem(node.parent.value3, node.value1);
                        replacementNode.isLeaf = node.isLeaf;
                        if (!replacementNode.isLeaf) {
                            replacementNode.rightChild = node.rightChild;
                            replacementNode.rightChild.parent =
                            replacementNode;
                            replacementNode.centerChild = node.leftChild;
                            replacementNode.centerChild.parent =
                            replacementNode;
                            replacementNode.leftChild =
                            node.parent.centerRightChild.rightChild;
                            replacementNode.leftChild.parent = replacementNode;
                        }
                        node.parent.value3 =
                        node.parent.centerRightChild.value3;
                        //Creates a new center right node
                        TwoFourTreeItem replacementNodeCenterRight = new
                        TwoFourTreeItem(node.parent.centerRightChild.value1,
                        node.parent.centerRightChild.value2);
                        replacementNodeCenterRight.isLeaf =
                        node.parent.centerRightChild.isLeaf;
                        if (!replacementNodeCenterRight.isLeaf) {
                            replacementNodeCenterRight.rightChild =
                            node.parent.centerRightChild.centerRightChild;
                            replacementNodeCenterRight.rightChild.parent =
                            replacementNodeCenterRight;
                            replacementNodeCenterRight.centerChild =
                            node.parent.centerRightChild.centerLeftChild;
                            replacementNodeCenterRight.centerChild.parent =
                            replacementNodeCenterRight;
                            replacementNodeCenterRight.leftChild =
                            node.parent.centerRightChild.leftChild;
                            replacementNodeCenterRight.leftChild.parent =
                            replacementNodeCenterRight;
                        }
                        replacementNode.parent = node.parent;
                        replacementNode.parent.rightChild = replacementNode;
                        replacementNodeCenterRight.parent =
                        replacementNode.parent;
                        replacementNodeCenterRight.parent.centerRightChild =
                        replacementNodeCenterRight;
                        return replacementNode;
                    }
                }
            }
        }
        return node;
    }

    public boolean addValue(int value) { // insert value into tree
        if (hasValue(value)) return true;
        if (root == null) { // if no values within tree
            root = new TwoFourTreeItem(value);
            return true;
        }
        if (root.isLeaf) { //if node is a leaf
            if (root.isTwoNode()) { // if 2-node
                root.values = 2;
                if (root.value1 > value) {
                    root.value2 = root.value1;
                    root.value1 = value;
                } else {
                    root.value2 = value;
                }
                return true;
            } else if (root.isThreeNode()) { //if 3-node
                root.values = 3;
                if (root.value1 > value) {
                    root.value3 = root.value2;
                    root.value2 = root.value1;
                    root.value1 = value;
                } else if (root.value2 > value) {
                    root.value3 = root.value2;
                    root.value2 = value;
                } else {
                    root.value3 = value;
                }
                return true;
            } else if (root.isFourNode()) { // if 4-node
                TwoFourTreeItem newRoot = new TwoFourTreeItem(root.value2);
                newRoot.isLeaf = false;
                newRoot.leftChild = new TwoFourTreeItem(root.value1);
                newRoot.leftChild.parent = newRoot;
                newRoot.rightChild = new TwoFourTreeItem(root.value3);
                newRoot.rightChild.parent = newRoot;
                root = newRoot;
            }
        }
        TwoFourTreeItem node = root;
        while (!node.isLeaf) { //loop to guide through tree to insert
            if (node.isTwoNode()) {
                if (node.value1 > value) {
                    node = node.leftChild;
                    continue;
                } else {
                    node = node.rightChild;
                    continue;
                }
            } else if (node.isThreeNode()) {
                if (node.value1 > value) {
                    node = node.leftChild;
                    continue;
                } else if (node.value2 > value) {
                    node = node.centerChild;
                    continue;
                } else {
                    node = node.rightChild;
                    continue;
                }
            } else if (node.isFourNode()) { // if there is 4-node split
                int liftValue = node.value2; // value to be lifted to parent
                if (node.isRoot()) { //if root
                    TwoFourTreeItem newRoot = new TwoFourTreeItem(node.value2);
                    newRoot.isLeaf = false;
                    newRoot.leftChild = new TwoFourTreeItem(node.value1); //
                    // create left child with first value
                    newRoot.leftChild.isLeaf = false;
                    newRoot.leftChild.parent = newRoot;
                    newRoot.rightChild = new TwoFourTreeItem(node.value3); //
                    // create right child with third value
                    newRoot.rightChild.isLeaf = false;
                    newRoot.rightChild.parent = newRoot;
                    newRoot.leftChild.leftChild = node.leftChild; // assign
                    // child to the new root's left child
                    newRoot.leftChild.leftChild.parent = newRoot.leftChild;
                    newRoot.leftChild.rightChild = node.centerLeftChild;
                    newRoot.leftChild.rightChild.parent = newRoot.leftChild;
                    newRoot.rightChild.leftChild = node.centerRightChild; //
                    // assign child to the new root's right child
                    newRoot.rightChild.leftChild.parent = newRoot.rightChild;
                    newRoot.rightChild.rightChild = node.rightChild;
                    newRoot.rightChild.rightChild.parent = newRoot.rightChild;
                    root = newRoot;
                    node = root;
                    continue;
                } else if (node == node.parent.leftChild) {
                    if (node.parent.isTwoNode()) { //if parent is 2-node
                        node.parent.values = 2;
                        node.parent.value2 = node.parent.value1;
                        node.parent.value1 = liftValue;
                        node.parent.leftChild = new
                        TwoFourTreeItem(node.value1);
                        node.parent.leftChild.isLeaf = false;
                        node.parent.leftChild.parent = node.parent;
                        node.parent.leftChild.leftChild = node.leftChild;
                        node.parent.leftChild.rightChild =
                        node.centerLeftChild;
                        node.parent.leftChild.leftChild.parent =
                        node.parent.leftChild;
                        node.parent.leftChild.rightChild.parent =
                        node.parent.leftChild;
                        node.parent.centerChild = new
                        TwoFourTreeItem(node.value3);
                        node.parent.centerChild.isLeaf = false;
                        node.parent.centerChild.parent = node.parent;
                        node.parent.centerChild.leftChild =
                        node.centerRightChild;
                        node.parent.centerChild.rightChild = node.rightChild;
                        node.parent.centerChild.leftChild.parent =
                        node.parent.centerChild;
                        node.parent.centerChild.rightChild.parent =
                        node.parent.centerChild;
                        node = node.parent;
                        continue;
                    } else {
                        node.parent.values = 3;
                        node.parent.value3 = node.parent.value2;
                        node.parent.value2 = node.parent.value1;
                        node.parent.value1 = liftValue;
                        node.parent.leftChild = new
                        TwoFourTreeItem(node.value1);
                        node.parent.leftChild.isLeaf = false;
                        node.parent.leftChild.parent = node.parent;
                        node.parent.leftChild.leftChild = node.leftChild;
                        node.parent.leftChild.rightChild =
                        node.centerLeftChild;
                        node.parent.leftChild.leftChild.parent =
                        node.parent.leftChild;
                        node.parent.leftChild.rightChild.parent =
                        node.parent.rightChild;
                        node.parent.centerLeftChild = new
                        TwoFourTreeItem(node.value3);
                        node.parent.centerLeftChild.isLeaf = false;
                        node.parent.centerLeftChild.parent = node.parent;
                        node.parent.centerLeftChild.leftChild =
                        node.centerRightChild;
                        node.parent.centerLeftChild.rightChild =
                        node.rightChild;
                        node.parent.centerLeftChild.leftChild.parent =
                        node.parent.centerLeftChild;
                        node.parent.centerLeftChild.rightChild.parent =
                        node.parent.centerLeftChild;
                        node.parent.centerRightChild = node.parent.centerChild;
                        node.parent.centerChild = null;
                        node = node.parent;
                        continue;
                    }
                } else if (node == node.parent.centerChild) {
                    node.parent.values = 3;
                    node.parent.value3 = node.parent.value2;
                    node.parent.value2 = liftValue;
                    node.parent.centerLeftChild = new
                    TwoFourTreeItem(node.value1);
                    node.parent.centerLeftChild.isLeaf = false;
                    node.parent.centerLeftChild.parent = node.parent;
                    node.parent.centerLeftChild.leftChild = node.leftChild;
                    node.parent.centerLeftChild.rightChild =
                    node.centerRightChild;
                    node.parent.centerLeftChild.leftChild.parent =
                    node.parent.centerLeftChild;
                    node.parent.centerLeftChild.rightChild.parent =
                    node.parent.centerLeftChild;
                    node.parent.centerRightChild = new
                    TwoFourTreeItem(node.value3);
                    node.parent.centerRightChild.isLeaf = false;
                    node.parent.centerRightChild.parent = node.parent;
                    node.parent.centerRightChild.leftChild =
                    node.centerRightChild;
                    node.parent.centerRightChild.rightChild = node.rightChild;
                    node.parent.centerRightChild.leftChild.parent =
                    node.parent.centerRightChild;
                    node.parent.centerRightChild.rightChild.parent =
                    node.parent.centerRightChild;
                    node.parent.centerChild = null;
                    node = node.parent;
                    continue;
                } else {
                    if (node.parent.isTwoNode()) {
                        node.parent.values = 2;
                        node.parent.value2 = liftValue;
                        node.parent.centerChild = new
                        TwoFourTreeItem(node.value1);
                        node.parent.centerChild.isLeaf = false;
                        node.parent.centerChild.parent = node.parent;
                        node.parent.centerChild.leftChild = node.leftChild;
                        node.parent.centerChild.leftChild.parent =
                        node.parent.centerChild;
                        node.parent.centerChild.rightChild =
                        node.centerLeftChild;
                        node.parent.centerChild.rightChild.parent =
                        node.centerChild;
                        node.parent.rightChild = new
                        TwoFourTreeItem(node.value3);
                        node.parent.rightChild.isLeaf = false;
                        node.parent.rightChild.parent = node.parent;
                        node.parent.rightChild.leftChild =
                        node.centerRightChild;
                        node.parent.rightChild.leftChild.parent =
                        node.parent.rightChild;
                        node.parent.rightChild.rightChild = node.rightChild;
                        node.parent.rightChild.rightChild.parent =
                        node.parent.rightChild;
                        node = node.parent;
                        continue;
                    } else {
                        node.parent.values = 3;
                        node.parent.value3 = liftValue;
                        node.parent.centerLeftChild = node.parent.centerChild;
                        node.parent.centerChild = null;
                        node.parent.centerRightChild = new
                        TwoFourTreeItem(node.value1);
                        node.parent.centerRightChild.isLeaf = false;
                        node.parent.centerRightChild.leftChild =
                        node.leftChild;
                        node.parent.centerRightChild.leftChild.parent =
                        node.parent.centerRightChild;
                        node.parent.centerRightChild.rightChild =
                        node.centerLeftChild;
                        node.parent.centerRightChild.rightChild.parent =
                        node.parent.centerRightChild;
                        node.parent.centerRightChild.parent = node.parent;
                        node.parent.rightChild = new
                        TwoFourTreeItem(node.value3);
                        node.parent.rightChild.isLeaf = false;
                        node.parent.rightChild.leftChild =
                        node.centerRightChild;
                        node.parent.rightChild.rightChild = node.rightChild;
                        node.parent.rightChild.leftChild.parent =
                        node.parent.rightChild;
                        node.parent.rightChild.rightChild.parent =
                        node.parent.rightChild;
                        node.parent.rightChild.parent = node.parent;
                        node = node.parent;
                        continue;
                    }
                }
            }
        }
        if (node.isTwoNode()) {
            node.values = 2;
            if (value > node.value1) {
                node.value2 = value;
            } else {
                node.value2 = node.value1;
                node.value1 = value;
            }
            return true;
        } else if (node.isThreeNode()) {
            node.values = 3;
            if (value > node.value2) {
                node.value3 = value;
            } else if (value > node.value1) {
                node.value3 = node.value2;
                node.value2 = value;
            } else {
                node.value3 = node.value2;
                node.value2 = node.value1;
                node.value1 = value;
            }
            return true;
        } else {
            int liftValue = node.value2;
            if (node == node.parent.leftChild) {
                //Checking what type of node the parent is
                if (node.parent.isTwoNode()) { // if parent is 2-node
                    node.parent.values = 2;
                    node.parent.value2 = node.parent.value1;
                    node.parent.value1 = liftValue;
                    node.parent.leftChild = new
                    TwoFourTreeItem(node.value1);
                    node.parent.leftChild.parent = node.parent;
                    node.parent.centerChild = new
                    TwoFourTreeItem(node.value3);
                    node.parent.centerChild.parent = node.parent;
                    node = node.parent;
                    if (node.value1 > value) {
                        node = node.leftChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    } else {
                        node = node.centerChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    }
                    return true;
                } else {
                    node.parent.values = 3;
                    node.parent.value3 = node.parent.value2;
                    node.parent.value2 = node.parent.value1;
                    node.parent.value1 = liftValue;
                    node.parent.leftChild = new
                    TwoFourTreeItem(node.value1);
                    node.parent.leftChild.parent = node.parent;
                    node.parent.centerLeftChild = new
                    TwoFourTreeItem(node.value3);
                    node.parent.centerLeftChild.parent = node.parent;
                    node.parent.centerRightChild = node.parent.centerChild;
                    node.parent.centerRightChild.parent = node.parent;
                    node.parent.centerChild = null;
                    node = node.parent;
                    if (node.value1 > value) {
                        node = node.leftChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    } else {
                        node = node.centerLeftChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    }
                    return true;
                }
            } else if (node == node.parent.centerChild) {
                node.parent.values = 3;
                node.parent.value3 = node.parent.value2;
                node.parent.value2 = liftValue;
                node.parent.centerLeftChild = new
                TwoFourTreeItem(node.value1);
                node.parent.centerLeftChild.parent = node.parent;
                node.parent.centerRightChild = new
                TwoFourTreeItem(node.value3);
                node.parent.centerRightChild.parent = node.parent;
                node.parent.centerChild = null;
                node = node.parent;
                if (node.value2 > value) {
                    node = node.centerLeftChild;
                    node.values = 2;
                    if (node.value1 < value) {
                        node.value2 = value;
                    } else {
                        node.value2 = node.value1;
                        node.value1 = value;
                    }
                } else {
                    node = node.centerRightChild;
                    node.values = 2;
                    if (node.value1 < value) {
                        node.value2 = value;
                    } else {
                        node.value2 = node.value1;
                        node.value1 = value;
                    }
                }
                return true;
            } else {
                if (node.parent.isTwoNode()) {
                    node.parent.values = 2;
                    node.parent.value2 = liftValue;
                    node.parent.centerChild = new
                    TwoFourTreeItem(node.value1);
                    node.parent.centerChild.parent = node.parent;
                    node.parent.rightChild = new
                    TwoFourTreeItem(node.value3);
                    node.parent.rightChild.parent = node.parent;
                    node = node.parent;
                    if (node.value2 > value) {
                        node = node.centerChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    } else {
                        node = node.rightChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    }
                    return true;
                } else {
                    //Is a three node
                    node.parent.values = 3;
                    node.parent.value3 = liftValue;
                    node.parent.centerLeftChild = node.parent.centerChild;
                    node.parent.centerLeftChild.parent = node.parent;
                    node.parent.centerChild = null;
                    node.parent.centerRightChild = new
                    TwoFourTreeItem(node.value1);
                    node.parent.centerRightChild.parent = node.parent;
                    node.parent.rightChild = new
                    TwoFourTreeItem(node.value3);
                    node.parent.rightChild.parent = node.parent;
                    node = node.parent;
                    if (node.value3 > value) {
                        node = node.centerRightChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    } else {
                        node = node.rightChild;
                        node.values = 2;
                        if (node.value1 < value) {
                            node.value2 = value;
                        } else {
                            node.value2 = node.value1;
                            node.value1 = value;
                        }
                    }
                    return true;
                }
            }
        }
    }

    public boolean hasValue(int value) {
        return hasValueRecursive(root, value);
    }

    private boolean hasValueRecursive(TwoFourTreeItem node, int value) {
        if (node == null) {
            return false;
        }
        if (node.isTwoNode()) {
            if (node.value1 == value) {
                return true;
            } else if (value < node.value1) {
                return hasValueRecursive(node.leftChild, value);
            } else {
                return hasValueRecursive(node.rightChild, value);
            }
        } else if (node.isThreeNode()) {
            if (node.value1 == value || node.value2 == value) {
                return true;
            } else if (value < node.value1) {
                return hasValueRecursive(node.leftChild, value);
            } else if (value < node.value2) {
                return hasValueRecursive(node.centerChild, value);
            } else {
                return hasValueRecursive(node.rightChild, value);
            }
        } else { // Four-node
            if (node.value1 == value || node.value2 == value || node.value3 ==
            value) {
                return true;
            } else if (value < node.value1) {
                return hasValueRecursive(node.leftChild, value);
            } else if (value < node.value2) {
                return hasValueRecursive(node.centerLeftChild, value);
            } else if (value < node.value3) {
                return hasValueRecursive(node.centerRightChild, value);
            } else {
                return hasValueRecursive(node.rightChild, value);
            }
        }
    }

    public boolean deleteValue(int value) {
        if (!hasValue(value)) return true;
        TwoFourTreeItem succesorNode, replacementNode;
        TwoFourTreeItem node = root;
        while (node != null) {
            if (node.isTwoNode()) {
                node = merge(node); // if node is two node then merge
            }
            if ((node.value1 == value || node.value2 == value || node.value3 ==
            value) && node.isLeaf) { //if contains value and is leaf
                if (node.value1 == value) {
                    node.values--;
                    node.value1 = node.value2;
                    node.value2 = node.value3;
                    node.value3 = 0;
                    return true;
                } else if (node.value2 == value) {
                    node.values--;
                    node.value2 = node.value3;
                    node.value3 = 0;
                    return true;
                } else {
                    node.values--;
                    node.value3 = 0;
                    return true;
                }
            } else if ((node.value1 == value || node.value2 == value || node.value3
            == value)) { // if node has value but is not a leaf
                replacementNode = node;
                if (replacementNode.isThreeNode()) {
                    if (replacementNode.value1 == value) {
                        succesorNode = node.centerChild;
                        succesorNode = merge(succesorNode);
                        if (succesorNode.value1 == value || succesorNode.value2 ==
                        value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }
                        while (succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if (succesorNode.value1 == value || succesorNode.value2
                            == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    } else {
                        succesorNode = node.rightChild;
                        succesorNode = merge(succesorNode);
                        if (succesorNode.value1 == value || succesorNode.value2 ==
                        value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }
                        while (succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if (succesorNode.value1 == value || succesorNode.value2
                            == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    }
                } else if (replacementNode.isTwoNode()) {
                    succesorNode = node.rightChild;
                    succesorNode = merge(succesorNode);
                    if (succesorNode.value1 == value || succesorNode.value2 == value
                    || succesorNode.value3 == value) {
                        replacementNode = succesorNode;
                    }
                    while (succesorNode.leftChild != null) {
                        succesorNode = succesorNode.leftChild;
                        succesorNode = merge(succesorNode);
                        if (succesorNode.value1 == value || succesorNode.value2 ==
                        value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }
                    }
                } else {
                    if (replacementNode.value1 == value) {
                        succesorNode = node.centerLeftChild;
                        succesorNode = merge(succesorNode);
                        if (succesorNode.value1 == value || succesorNode.value2 ==
                        value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }
                        while (succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if (succesorNode.value1 == value || succesorNode.value2
                            == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    } else if (replacementNode.value2 == value) {
                        succesorNode = node.centerRightChild;
                        succesorNode = merge(succesorNode);
                        if (succesorNode.value1 == value || succesorNode.value2 ==
                        value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }
                        while (succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if (succesorNode.value1 == value || succesorNode.value2
                            == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    } else {
                        succesorNode = node.rightChild;
                        succesorNode = merge(succesorNode);
                        if (succesorNode.value1 == value || succesorNode.value2 ==
                        value || succesorNode.value3 == value) {
                            replacementNode = succesorNode;
                        }
                        while (succesorNode.leftChild != null) {
                            succesorNode = succesorNode.leftChild;
                            succesorNode = merge(succesorNode);
                            if (succesorNode.value1 == value || succesorNode.value2
                            == value || succesorNode.value3 == value) {
                                replacementNode = succesorNode;
                            }
                        }
                    }
                }
                // replace value with succesor
                if (replacementNode.value1 == value) {
                    replacementNode.value1 = succesorNode.value1;
                    succesorNode.values--;
                    succesorNode.value1 = succesorNode.value2;
                    succesorNode.value2 = succesorNode.value3;
                    succesorNode.value3 = 0;
                    return true;
                } else if (replacementNode.value2 == value) {
                    replacementNode.value2 = succesorNode.value1;
                    succesorNode.values--;
                    succesorNode.value1 = succesorNode.value2;
                    succesorNode.value2 = succesorNode.value3;
                    succesorNode.value3 = 0;
                    return true;
                } else {
                    replacementNode.value3 = succesorNode.value1;
                    succesorNode.values--;
                    succesorNode.value1 = succesorNode.value2;
                    succesorNode.value2 = succesorNode.value3;
                    succesorNode.value3 = 0;
                    return true;
                }
            } else { // if it doesn't contain value
                if (node.isThreeNode()) {
                    if (node.value1 > value) {
                        node = node.leftChild;
                        continue;
                    } else if (node.value2 > value) {
                        node = node.centerChild;
                        continue;
                    } else {
                        node = node.rightChild;
                        continue;
                    }
                } else { // if 4-node
                    if (node.value1 > value) {
                        node = node.leftChild;
                        continue;
                    } else if (node.value2 > value) {
                        node = node.centerLeftChild;
                        continue;
                    } else if (node.value3 > value) {
                        node = node.centerRightChild;
                        continue;
                    } else {
                        node = node.rightChild;
                        continue;
                    }
                }
            }
        }
        return false;
    }

    public void printInOrder() {
        if (root != null) root.printInOrder(0);
    }

    public TwoFourTree() {
    }
}
