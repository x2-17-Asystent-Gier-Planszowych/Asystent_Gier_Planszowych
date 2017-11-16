package pwcdma.asystentgierplanszowych.model;


import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Table {
    private ArrayList<String> titles;
    private int[][] score;

    public Table(ArrayList<String> titles, int[][] score) {
        this.titles = titles;
        this.score = score;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public int[][] getScore() {
        return score;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public void setScore(int[][] score) {
        this.score = score;
    }

    public void setScoreCell(int rowPosition, int colPosition, int score){
        this.score[rowPosition][colPosition] = score;
    }

    public void setTitle(int colPosition, String title){
        this.titles.add(colPosition, title);
    }
}
