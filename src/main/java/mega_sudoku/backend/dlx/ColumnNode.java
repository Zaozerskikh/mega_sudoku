package mega_sudoku.backend.dlx;

public class ColumnNode extends DancingNode {
    int size;

    ColumnNode() {
        super();
        size = 0;
        column = this;
    }

    void cover() {
        unlinkLR();
        for (DancingNode i = this.down; i != this; i = i.down) {
            for (DancingNode j = i.right; j != i; j = j.right) {
                j.unlinkUD();
                j.column.size--;
            }
        }
    }

    void uncover() {
        for (DancingNode i = this.up; i != this; i = i.up) {
            for (DancingNode j = i.left; j != i; j = j.left) {
                j.column.size++;
                j.relinkUD();
            }
        }
        relinkLR();
    }
}
