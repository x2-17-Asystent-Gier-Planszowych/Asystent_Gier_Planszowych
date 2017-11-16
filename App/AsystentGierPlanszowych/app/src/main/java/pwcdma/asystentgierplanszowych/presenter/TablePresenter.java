package pwcdma.asystentgierplanszowych.presenter;


import java.util.ArrayList;

import pwcdma.asystentgierplanszowych.model.Table;

public class TablePresenter {
    Table table;

    public TablePresenter() {
    }

    public int fetchScoreFromTable(int row, int col) {
        int tempScore[][] = table.getScore();
        return tempScore[row][col];
    }

    public String fetchColumnTitle(int row) {
        return table.getTitles().get(row);
    }

    public void createTable(int numberOfRows, int numberOfCols) {
        ArrayList<String> titles = new ArrayList<>(numberOfCols);
        int[][] scores = new int[numberOfRows][numberOfCols];
        Table table = new Table(titles, scores);
    }

    public void editTitle(int rowPosition, int colPosition,int score){
        table.setScoreCell(rowPosition, colPosition, score);
    }

    public void editTitle(int colPosition, String title){
        table.setTitle(colPosition, title);
    }
}
