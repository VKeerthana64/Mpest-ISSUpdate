
package com.amana.MpestISS.model.quiz;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizAnswersPojo {

    @SerializedName("Client_Id")
    @Expose
    private String clientId;
    @SerializedName("QuestionSet_Id")
    @Expose
    private String questionSetId;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("QuizAnswerDetails")
    @Expose
    private ArrayList<QuizAnswerDetail> quizAnswerDetails = null;

    public String getClientId(String s) {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getQuestionSetId(String s) {
        return questionSetId;
    }

    public void setQuestionSetId(String questionSetId) {
        this.questionSetId = questionSetId;
    }

    public String getCreatedBy(String s) {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ArrayList<QuizAnswerDetail> getQuizAnswerDetails() {
        return quizAnswerDetails;
    }

    public void setQuizAnswerDetails(ArrayList<QuizAnswerDetail> quizAnswerDetails) {
        this.quizAnswerDetails = quizAnswerDetails;
    }

}
