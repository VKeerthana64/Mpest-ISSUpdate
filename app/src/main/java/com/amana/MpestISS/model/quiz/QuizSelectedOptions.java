package com.amana.MpestISS.model.quiz;

public class QuizSelectedOptions {

    public QuizSelectedOptions(String selectedText, int pos, int SelectedPos) {
        SelectedText = selectedText;
        this.pos = pos;
        this.SelectedPos = SelectedPos;
    }

    private String SelectedText="";

    private int pos;

    private int SelectedPos;

    public String getSelectedText() {
        return SelectedText;
    }

    public void setSelectedText(String selectedText) {
        SelectedText = selectedText;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getSelectedPos() {
        return SelectedPos;
    }

    public void setSelectedPos(int selectedPos) {
        SelectedPos = selectedPos;
    }
}
