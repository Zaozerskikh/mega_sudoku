package mega_sudoku.backend.dlx;

/**
 * Класс отвечающий за работу "танцующих" узлов.
 */
public class DancingNode {
    /**
     * Поля для доступа к соседним узлам.
     */
    DancingNode left, right, up, down;

    ColumnNode column;

    /**
     * Метод для "отката" вниз данного узла.
     * @param node новый узел.
     */
    DancingNode hookDown(DancingNode node) {
        assert (this.column == node.column);
        node.down = this.down;
        node.down.up = node;
        node.up = this;
        this.down = node;
        return node;
    }

    DancingNode hookRight(DancingNode node) {
        node.right = this.right;
        node.right.left = node;
        node.left = this;
        this.right = node;
        return node;
    }

    void unlinkLR() {
        this.left.right = this.right;
        this.right.left = this.left;
    }

    void relinkLR() {
        this.left.right = this.right.left = this;
    }

    void unlinkUD() {
        this.up.down = this.down;
        this.down.up = this.up;
    }

    void relinkUD() {
        this.up.down = this.down.up = this;
    }

    DancingNode() {
        left = right = up = down = this;
    }

    DancingNode(ColumnNode column) {
        this();
        this.column = column;
    }
}
