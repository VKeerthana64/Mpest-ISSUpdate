
package com.amana.MpestISS.model.realm.taskdetail;

import io.realm.RealmObject;

public class Team extends RealmObject{

    public String Teams_Id;

    public String TeamLead_Id;

    public String _id;

    public String getTeams_Id() {
        return Teams_Id;
    }

    public void setTeams_Id(String teams_Id) {
        Teams_Id = teams_Id;
    }

    public String getTeamLead_Id() {
        return TeamLead_Id;
    }

    public void setTeamLead_Id(String teamLead_Id) {
        TeamLead_Id = teamLead_Id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
