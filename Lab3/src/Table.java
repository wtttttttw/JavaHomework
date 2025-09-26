public class Table {
    private int[][] data;
    private int rows;
    private int cols;

    public Table(int rows, int cols){
        this.rows=rows;
        this.cols=cols;
        this.data = new int[rows][cols];
    }

    public int rows(){
        return rows;
    }

    public int cols(){
        return cols;
    }

    public int getValue(int row, int col){
        return data[row][col];
    } 

    public void setValue(int row, int col, int value){
        data[row][col] = value;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]);
                if (j < cols - 1) {
                    sb.append(" ");
                }
            }
            if (i < rows - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public double average(){
        if (rows == 0 || cols == 0) {
            return 0.0;
        }

        int sum = 0;
        int count = rows*  cols;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sum+=data[i][j];
            }
        }

        return (double)sum/count;
    }
}
