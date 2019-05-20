
package com.amana.MpestISS.model.quiz;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizAnswerDetail {

    @SerializedName("QuizQuestion_Id")
    @Expose
    private String quizQuestionId;
    @SerializedName("ChosenAnswer")
    @Expose
    private String chosenAnswer;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;

    public String getQuizQuestionId(String id) {
        return quizQuestionId;
    }

    public void setQuizQuestionId(String quizQuestionId) {
        this.quizQuestionId = quizQuestionId;
    }

    public String getChosenAnswer(String seletedText) {
        return chosenAnswer;
    }

    public void setChosenAnswer(String chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

}
