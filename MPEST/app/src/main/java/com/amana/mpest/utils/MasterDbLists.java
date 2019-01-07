package com.amana.mpest.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.abdeveloper.library.MultiSelectModel;
import com.amana.mpest.model.AdhocRequest;
import com.amana.mpest.model.RemarskModel;
import com.amana.mpest.model.masterdetails.MaterialData;
import com.amana.mpest.model.masterdetails.MaterialsResponse;
import com.amana.mpest.model.masterdetails.PaymentModeData;
import com.amana.mpest.model.masterdetails.PaymentModeResponse;
import com.amana.mpest.model.masterdetails.ServiceTypeData;
import com.amana.mpest.model.masterdetails.ServiceTypeResponse;
import com.amana.mpest.model.masterdetails.SettingRemarksData;
import com.amana.mpest.model.masterdetails.SettingRemarksResponse;
import com.amana.mpest.model.masterdetails.TeamData;
import com.amana.mpest.model.masterdetails.TeamResponse;
import com.amana.mpest.model.realm.AdhocRm.AdhocRequestRm;
import com.amana.mpest.model.realm.LogsTable;
import com.amana.mpest.model.realm.Materials;
import com.amana.mpest.model.realm.PaymentMode;
import com.amana.mpest.model.realm.ServiceType;
import com.amana.mpest.model.realm.SettingRemarks;
import com.amana.mpest.model.realm.Teams;
import com.amana.mpest.model.realm.jobdetails.FeedbackCaptureRmModel;
import com.amana.mpest.model.realm.jobdetails.MaterialsCapturesRmModel;
import com.amana.mpest.model.realm.jobdetails.PaymentCaptureRmModel;
import com.amana.mpest.model.realm.jobdetails.PhotoRemarkRMModel;
import com.amana.mpest.model.realm.jobdetails.ServiceMaterialRMModel;
import com.amana.mpest.model.realm.jobdetails.ServicesCapturesRmModel;
import com.amana.mpest.model.realm.jobdetails.TeamCaptureRmModel;
import com.amana.mpest.model.realm.logdetails.LogFeedbackCaptureRmModel;
import com.amana.mpest.model.realm.logdetails.LogMaterialsCapturesRmModel;
import com.amana.mpest.model.realm.logdetails.LogPaymentCaptureRmModel;
import com.amana.mpest.model.realm.logdetails.LogPhotoRemarkRMModel;
import com.amana.mpest.model.realm.logdetails.LogServiceMaterialRMModel;
import com.amana.mpest.model.realm.logdetails.LogServicesCapturesRmModel;
import com.amana.mpest.model.realm.logdetails.LogTeamCaptureRmModel;
import com.amana.mpest.model.realm.logdetails.LogsServiceDetails;
import com.amana.mpest.model.realm.taskdetail.Datum;
import com.amana.mpest.restApi.ApiClient;
import com.amana.mpest.restApi.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MasterDbLists {
    static ArrayList<MaterialsResponse> materialsResponse = new ArrayList<>();
    static ArrayList<ServiceTypeResponse> serviceTypeResponses = new ArrayList<>();
    static ArrayList<SettingRemarksResponse> settingRemarksResponses = new ArrayList<>();
    static ArrayList<PaymentModeResponse> paymentModeResponses = new ArrayList<>();
    static ArrayList<TeamResponse> teamResponses = new ArrayList<>();

    /**
     * gets Materials
     */
    public static void getMaterialList(final Context context, final String Client_id) {

        Utils.showCustomDialog(context);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<MaterialData> call = apiService.getMaterials(Client_id);
        call.enqueue(new Callback<MaterialData>() {
            @Override
            public void onResponse(Call<MaterialData> call, Response<MaterialData> response) {

                try {
                    if (response.body().getStatusCode() == 200) {
                        materialsResponse = response.body().getData();

                        if (materialsResponse.size() > 0) {
                            // File realmFile = new File(getFilesDir(), "default.realm");
                            final Realm realm = Realm.getDefaultInstance(); // opens db
                            realm.beginTransaction();
                            realm.where(Materials.class).findAll().deleteAllFromRealm();
                            realm.commitTransaction();

                            for (int i = 0; i < materialsResponse.size(); i++) {

                                final int finalI = i;
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        //Create a user if there isn't one
                                        final Materials materials = realm.createObject(Materials.class); // Create a new String
                                        materials.setId(materialsResponse.get(finalI).getId());
                                        materials.setMaterialsName(materialsResponse.get(finalI).getMaterialsName());
                                        materials.setMaterialscode(materialsResponse.get(finalI).getMaterialscode());
                                        materials.setDescription(materialsResponse.get(finalI).getDescription());
                                        materials.setIsActive(materialsResponse.get(finalI).getIsActive());
                                        materials.setCreatedBy(materialsResponse.get(finalI).getCreatedBy());
                                        materials.setCreateDate(materialsResponse.get(finalI).getCreateDate());
                                        materials.setPestType(materialsResponse.get(finalI).getPestType());
                                    }
                                });

                            }
                        }
                        Utils.dismissDialog();
                        getServiceTypes(context, Client_id);
                    }


                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();
                    AppLogger.verbose("Materials", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<MaterialData> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("Materials", "unable to get details");

            }

        });
    }

    /**
     * get Service Type list
     */
    public static void getServiceTypes(final Context context, final String Client_id) {
        Utils.showCustomDialog(context);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<ServiceTypeData> call = apiService.getServiceTypes(Client_id);
        call.enqueue(new Callback<ServiceTypeData>() {
            @Override
            public void onResponse(Call<ServiceTypeData> call, Response<ServiceTypeData> response) {

                try {
                    if (response.body().getStatusCode() == 200) {
                        serviceTypeResponses = response.body().getData();

                        if (serviceTypeResponses.size() > 0) {
                            // File realmFile = new File(getFilesDir(), "default.realm");
                            final Realm realm = Realm.getDefaultInstance(); // opens db
                            realm.beginTransaction();
                            realm.where(ServiceType.class).findAll().deleteAllFromRealm();
                            realm.commitTransaction();

                            for (int i = 0; i < serviceTypeResponses.size(); i++) {

                                final int finalI = i;
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        //Create a user if there isn't one
                                        final ServiceType serviceType = realm.createObject(ServiceType.class); // Create a new String
                                        serviceType.setId(serviceTypeResponses.get(finalI).getId());
                                        serviceType.setServicetype(serviceTypeResponses.get(finalI).getServicetype());
                                        serviceType.setServicecode(serviceTypeResponses.get(finalI).getServicecode());
                                        serviceType.setDescription(serviceTypeResponses.get(finalI).getDescription());
                                        serviceType.setIsActive(serviceTypeResponses.get(finalI).getIsActive());
                                        serviceType.setCreatedBy(serviceTypeResponses.get(finalI).getCreatedBy());
                                        serviceType.setCreateDate(serviceTypeResponses.get(finalI).getCreateDate());
                                        serviceType.setPestType(serviceTypeResponses.get(finalI).getPestType());
                                    }
                                });

                            }
                        }
                        Utils.dismissDialog();
                        getSettingsRemarks(context, Client_id);
                    }

                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();
                    AppLogger.verbose("ServiceTypeData", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<ServiceTypeData> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("ServiceTypeData", "unable to get details");

            }

        });
    }

    /**
     * gets settingRemarks
     */
    public static void getSettingsRemarks(final Context context, final String Client_id) {

        Utils.showCustomDialog(context);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<SettingRemarksData> call = apiService.getSettingRemarks(Client_id);
        call.enqueue(new Callback<SettingRemarksData>() {
            @Override
            public void onResponse(Call<SettingRemarksData> call, Response<SettingRemarksData> response) {

                try {
                    if (response.body().getStatusCode() == 200) {
                        settingRemarksResponses = response.body().getData();

                        if (settingRemarksResponses.size() > 0) {
                            // File realmFile = new File(getFilesDir(), "default.realm");
                            final Realm realm = Realm.getDefaultInstance(); // opens db
                            realm.beginTransaction();
                            realm.where(SettingRemarks.class).findAll().deleteAllFromRealm();
                            realm.commitTransaction();

                            for (int i = 0; i < settingRemarksResponses.size(); i++) {

                                final int finalI = i;
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        //Create a user if there isn't one
                                        final SettingRemarks settingRemarks = realm.createObject(SettingRemarks.class); // Create a new String
                                        settingRemarks.setId(settingRemarksResponses.get(finalI).getId());
                                        settingRemarks.setClientId(settingRemarksResponses.get(finalI).getClientId());
                                        settingRemarks.setName(settingRemarksResponses.get(finalI).getName());
                                        settingRemarks.setDescription(settingRemarksResponses.get(finalI).getDescription());
                                        settingRemarks.setIsActive(settingRemarksResponses.get(finalI).getIsActive());

                                    }
                                });

                            }
                        }
                        Utils.dismissDialog();
                        getPaymentModes(context, Client_id);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.dismissDialog();
                    AppLogger.verbose("SettingRemarksData", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<SettingRemarksData> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("SettingRemarksData", "unable to get details");

            }

        });
    }


    /**
     * gets PaymentModes
     */
    public static void getPaymentModes(final Context context, final String Client_id) {

        Utils.showCustomDialog(context);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<PaymentModeData> call = apiService.getPaymentModes(Client_id);
        call.enqueue(new Callback<PaymentModeData>() {
            @Override
            public void onResponse(Call<PaymentModeData> call, Response<PaymentModeData> response) {

                try {
                    if (response.body().getStatusCode() == 200) {
                        paymentModeResponses = response.body().getData();

                        if (paymentModeResponses.size() > 0) {
                            // File realmFile = new File(getFilesDir(), "default.realm");
                            final Realm realm = Realm.getDefaultInstance(); // opens db
                            realm.beginTransaction();
                            realm.where(PaymentMode.class).findAll().deleteAllFromRealm();
                            realm.commitTransaction();


                            for (int i = 0; i < paymentModeResponses.size(); i++) {

                                final int finalI = i;
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        //Create a user if there isn't one
                                        final PaymentMode paymentMode = realm.createObject(PaymentMode.class); // Create a new String
                                        paymentMode.setId(paymentModeResponses.get(finalI).getId());
                                        paymentMode.setClientId(paymentModeResponses.get(finalI).getClientId());
                                        paymentMode.setName(paymentModeResponses.get(finalI).getName());
                                        paymentMode.setDescription(paymentModeResponses.get(finalI).getDescription());
                                        paymentMode.setIsActive(paymentModeResponses.get(finalI).getIsActive());

                                    }
                                });

                            }
                            Utils.dismissDialog();
                            getTeamList(context, Client_id);
                        }
                    }

                } catch (Exception e) {
                    Utils.dismissDialog();
                    e.printStackTrace();
                    AppLogger.verbose("PaymentModeData", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<PaymentModeData> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("PaymentModeData", "unable to get details");

            }

        });
    }

    /**
     * gets TeamList
     */
    public static void getTeamList(Context context, String Client_id) {

        Utils.showCustomDialog(context);
        final ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<TeamData> call = apiService.getTeamList(Client_id);
        call.enqueue(new Callback<TeamData>() {
            @Override
            public void onResponse(Call<TeamData> call, Response<TeamData> response) {

                try {
                    if (response.body().getStatusCode() == 200) {
                        teamResponses = response.body().getData();

                        if (teamResponses.size() > 0) {
                            // File realmFile = new File(getFilesDir(), "default.realm");
                            final Realm realm = Realm.getDefaultInstance(); // opens db
                            realm.beginTransaction();
                            realm.where(Teams.class).findAll().deleteAllFromRealm();
                            realm.commitTransaction();

                            for (int i = 0; i < teamResponses.size(); i++) {

                                final int finalI = i;
                                realm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {

                                        //Create a user if there isn't one
                                        final Teams teams = realm.createObject(Teams.class); // Create a new String
                                        teams.setId(teamResponses.get(finalI).getId());
                                        teams.setTeamName(teamResponses.get(finalI).getTeamName());
                                        teams.setTeamCode(teamResponses.get(finalI).getTeamCode());
                                        teams.setTeamLead(teamResponses.get(finalI).getTeamLead());
                                        teams.setTeamMembers(teamResponses.get(finalI).getTeamMembers());
                                        teams.setFromDate(teamResponses.get(finalI).getFromDate());
                                        teams.setToDate(teamResponses.get(finalI).getToDate());
                                        teams.setIsActive(teamResponses.get(finalI).getIsActive());
                                        teams.setCreatedBy(teamResponses.get(finalI).getCreatedBy());
                                        teams.setZone(teamResponses.get(finalI).getZone());
                                        teams.setUpdatedBy(teamResponses.get(finalI).getUpdatedBy());


                                    }
                                });

                            }

                        }
                    }
                    Utils.dismissDialog();

                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.dismissDialog();
                    AppLogger.verbose("TeamData", "Something Went wrong");
                }

            }

            @Override
            public void onFailure(Call<TeamData> call, Throwable t) {
                Utils.dismissDialog();
                AppLogger.verbose("TeamData", "unable to get details");

            }

        });
    }


    /**
     * fetch Task list from Datum Table and filters based on Status and Search
     *
     * @param mStatus
     * @param mSearch
     * @return
     */
    public static ArrayList<Datum> getMytaskListFromdb(String mStatus, String mSearch) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmQuery<Datum> query;
        if (mSearch.length() > 0) {
            //  mSearch = "?"+mSearch+"*";
            query = realm.where(Datum.class).equalTo("Status", "" + mStatus)
                    .and()
                    .contains("ServiceID", mSearch, Case.INSENSITIVE)
                    .or()
                    .contains("Location", mSearch, Case.INSENSITIVE)
                    .or()
                    .contains("ContactPerson", mSearch, Case.INSENSITIVE);


        } else {
            query = realm.where(Datum.class).equalTo("Status", "" + mStatus);

        }
        // Execute the query:
        RealmResults<Datum> result1 = query.sort("Startsat").findAll();
        realm.commitTransaction();


        return (ArrayList<Datum>) realm.copyFromRealm(result1);
    }



    /**
     * Gets Total Task List
     *
     * @return
     */
    public static ArrayList<Datum> getMytaskListFromdb() {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmQuery<Datum> query;
        //  mSearch = "?"+mSearch+"*";
        query = realm.where(Datum.class);

        // Execute the query:
        RealmResults<Datum> result1 = query.findAll();
        realm.commitTransaction();
        return (ArrayList<Datum>) realm.copyFromRealm(result1);
    }


    /**
     * Update Comments count in Mys Task List
     *
     * @param Count
     * @param mServiceID
     */
    public static void UpdateMyTaskList(final int Count, final String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Datum obj = realm.where(Datum.class).equalTo("ServiceID", mServiceID).findFirst();
                if (obj == null) {
                    obj = realm.createObject(Datum.class);
                }
                obj.setCommentCount(Count);

            }
        });
    }


    /**
     * Get Service details from Datum table by passing Service id
     *
     * @param mServiceId
     * @return
     */
    public static ArrayList<Datum> getServiceDetailsfromDb(String mServiceId) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<Datum> result1 = realm.where(Datum.class).equalTo("ServiceID", mServiceId).findAll();
        realm.commitTransaction();
        return (ArrayList<Datum>) realm.copyFromRealm(result1);
    }

    public static LogsServiceDetails getLOGServiceDetailsfromDb(String mServiceId) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        LogsServiceDetails result1 = realm.where(LogsServiceDetails.class)
                .equalTo("ServiceId", mServiceId)
                .findFirst();


        realm.commitTransaction();
        return result1;
    }

    /**
     * get all materials based on PestType
     *
     * @param mPestType
     * @return
     */
    public static ArrayList<MultiSelectModel> getAllMaterialList(String mPestType) {

        ArrayList<MultiSelectModel> multiSelectModels = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();

        RealmQuery<Materials> query;
        if (mPestType.length() > 0) {
            query = realm.where(Materials.class).contains("pestType", mPestType).sort("materialsName");
        } else {
            query = realm.where(Materials.class).sort("materialsName");
        }

        RealmResults<Materials> result1 = query.findAll();
        realm.commitTransaction();

        ArrayList<Materials> settingRemarks = (ArrayList<Materials>) realm.copyFromRealm(result1);

        for (int i = 0; i < settingRemarks.size(); i++) {
            multiSelectModels.add(new MultiSelectModel(i, settingRemarks.get(i).getMaterialsName().toString()));
        }

        return multiSelectModels;
    }


    /**
     * get all materials based on PestType
     *
     * @param mPestType
     * @return
     */
    public static ArrayList<MultiSelectModel> getAllServicesList(String mPestType) {

        ArrayList<MultiSelectModel> multiSelectModels = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();

        RealmQuery<ServiceType> query;
        if (mPestType.length() > 0) {
            query = realm.where(ServiceType.class).contains("pestType", mPestType).sort("servicetype");
        } else {
            query = realm.where(ServiceType.class).sort("servicetype");
        }

        RealmResults<ServiceType> result1 = query.findAll();
        realm.commitTransaction();

        ArrayList<ServiceType> settingRemarks = (ArrayList<ServiceType>) realm.copyFromRealm(result1);

        for (int i = 0; i < settingRemarks.size(); i++) {
            multiSelectModels.add(new MultiSelectModel(i, settingRemarks.get(i).getServicetype().toString()));
        }

        return multiSelectModels;
    }

    /**
     * Get Photo BEfore and after details from db by passing pesttype n type
     *
     * @param mType
     * @param mPestType
     * @return
     */
    public static ArrayList<PhotoRemarkRMModel> getPhotoRemarksDetails(String mType, String mPestType, String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<PhotoRemarkRMModel> result1 = realm.where(PhotoRemarkRMModel.class)
                .equalTo("BeforeAfter", mType)
                .equalTo("PestType", mPestType)
                .equalTo("ServiceID", mServiceID)
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<PhotoRemarkRMModel> photoWithRemarks = (ArrayList<PhotoRemarkRMModel>) realm.copyFromRealm(result1);

        return photoWithRemarks;
    }

    /**
     * Delete Row from PhotoRemarks Table
     *
     * @param mPosition
     */
    public static void DeletefromPhotos(final int mPosition) {
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<PhotoRemarkRMModel> result = realm.where(PhotoRemarkRMModel.class).equalTo("id", mPosition).findAll();
                result.deleteAllFromRealm();
            }
        });

    }

    /**
     * Update Remarks in PhotoTable
     *
     * @param mPositon
     */
    public static void UpdatePhotoRemarks(final int mPositon, final String mData, final ArrayList<Integer> arrayList, final String mOthers) {
        final RealmList<Integer> arr_list = new RealmList<>();
        arr_list.addAll(arrayList);

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                PhotoRemarkRMModel obj = realm.where(PhotoRemarkRMModel.class).equalTo("id", mPositon).findFirst();
                if (obj == null) {
                    obj = realm.createObject(PhotoRemarkRMModel.class, mPositon);
                }
                obj.setRemarks(mData);
                obj.setSelectedPositions(arr_list);
                obj.setOthers(mOthers);
            }
        });
    }

    /**
     * Fetches All remarks from db
     *
     * @return
     */
    public static ArrayList<RemarskModel> RemarksList() {

        ArrayList<RemarskModel> multiSelectModels = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmQuery<SettingRemarks> query = realm.where(SettingRemarks.class).sort("name");
        // Execute the query:
        RealmResults<SettingRemarks> result1 = query.findAll();
        realm.commitTransaction();

        ArrayList<SettingRemarks> settingRemarks = (ArrayList<SettingRemarks>) realm.copyFromRealm(result1);

        for (int i = 0; i < settingRemarks.size(); i++) {
            multiSelectModels.add(new RemarskModel(i, settingRemarks.get(i).getName().toString()));
        }

        return multiSelectModels;
    }


    /**
     * Fetches total TeamMembers
     *
     * @return
     */
    public static ArrayList<MultiSelectModel> TeamMemberList(String mServiceid) {

        ArrayList<MultiSelectModel> multiSelectModels = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmQuery<Datum> query1 = realm.where(Datum.class).equalTo("ServiceID", mServiceid);
        // Execute the query:
        Datum datum = query1.findFirst();
        realm.commitTransaction();
        try {
            String[] TeamList = datum.getTeamdetails().get(0).getTeamMembers().split(",");

            for (int i = 0; i < TeamList.length; i++) {
                multiSelectModels.add(new MultiSelectModel(i, TeamList[i].toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return multiSelectModels;
    }


    /**
     * Fetches total TeamMembers
     *
     * @return
     */
    public static ArrayList<MultiSelectModel> TeamAdhocMemberList(String mid) {

        ArrayList<MultiSelectModel> multiSelectModels = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmQuery<Teams> query1 = realm.where(Teams.class).equalTo("id", mid);
        // Execute the query:
        Teams datum = query1.findFirst();
        realm.commitTransaction();
        try {
            String[] TeamList = datum.getTeamMembers().split(",");

            for (int i = 0; i < TeamList.length; i++) {
                multiSelectModels.add(new MultiSelectModel(i, TeamList[i].toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return multiSelectModels;
    }



    /**
     * Fetches TeamMembers based on ServiceID
     *
     * @return
     */
    public static Datum GetTeamDetails(String mServiceid) {

        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmQuery<Datum> query1 = realm.where(Datum.class).equalTo("ServiceID", mServiceid);
        // Execute the query:
        Datum datum = query1.findFirst();
        realm.commitTransaction();

        return datum;
    }

    /**
     * Fetches total TeamMembers
     *
     * @return
     */
    public static ArrayList<MultiSelectModel> PaymentModeList() {

        ArrayList<MultiSelectModel> multiSelectModels = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmQuery<PaymentMode> query = realm.where(PaymentMode.class).sort("name");
        // Execute the query:
        RealmResults<PaymentMode> result1 = query.findAll();
        realm.commitTransaction();

        ArrayList<PaymentMode> settingRemarks = (ArrayList<PaymentMode>) realm.copyFromRealm(result1);

        for (int i = 0; i < settingRemarks.size(); i++) {
            multiSelectModels.add(new MultiSelectModel(i, settingRemarks.get(i).getName().toString()));
        }

        return multiSelectModels;
    }


    /**
     * Get Photo BEfore and after details from db by passing pesttype n type
     *
     * @param mType
     * @param mPestType
     * @return
     */
    public static ServiceMaterialRMModel getServiceMaterislDetails(String mType, String mPestType, String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        ServiceMaterialRMModel result1 = realm.where(ServiceMaterialRMModel.class)
                .equalTo("SM_Type", mType)
                .and()
                .equalTo("PestType", mPestType)
                .and()
                .equalTo("ServiceID", mServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }

    /**
     * Update Remarks in Service/Materials DB
     *
     * @param mType
     * @param mPest
     * @param mRemarks
     * @param arrayList
     * @param mOthers
     */
    public static void UpdateServiceMaterialRemarks(final String mServiceId, final String mType, final String mPest, final String mRemarks, final ArrayList<Integer> arrayList, final String mOthers) {
        final RealmList<Integer> arr_list = new RealmList<>();
        arr_list.addAll(arrayList);

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ServiceMaterialRMModel obj = realm.where(ServiceMaterialRMModel.class)
                        .equalTo("ServiceID", mServiceId)
                        .and()
                        .equalTo("SM_Type", mType)
                        .and()
                        .equalTo("PestType", mPest)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(ServiceMaterialRMModel.class);
                }
                obj.setSM_remarks(mRemarks);
                obj.setSelectedPositions(arr_list);
                obj.setOthers(mOthers);
            }
        });
    }

    // insert others based on type, PestType
    public static void UpdateServiceMaterialRemarks(final String mServiceId, final String mType, final String mPest, final String mOthers) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ServiceMaterialRMModel obj = realm.where(ServiceMaterialRMModel.class)
                        .equalTo("ServiceID", mServiceId)
                        .and()
                        .equalTo("SM_Type", mType)
                        .and()
                        .equalTo("PestType", mPest)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(ServiceMaterialRMModel.class);
                }

                obj.setOthers(mOthers);
            }
        });
    }

    // Insert service Details based on type, PestType
    public static void UpdateServiceMaterialRemarks(final String mServiceId, final String mType, final String mPest, final ArrayList<ServicesCapturesRmModel> servicesCapturesRmModel) {

        final RealmList<ServicesCapturesRmModel> arr_list = new RealmList<>();
        arr_list.addAll(servicesCapturesRmModel);

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ServiceMaterialRMModel obj = realm.where(ServiceMaterialRMModel.class)
                        .equalTo("ServiceID", mServiceId)
                        .and()
                        .equalTo("SM_Type", mType)
                        .and()
                        .equalTo("PestType", mPest)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(ServiceMaterialRMModel.class);

                }
                // obj.setServcieCaptures(arr_list);
                obj.getServcieCaptures().deleteAllFromRealm();
                obj.getServcieCaptures().addAll(realm.copyToRealm(arr_list));

            /*    //obj.setServicesCapturesRmModel(servicesCapturesRmModel);
                obj.getServicesCapturesRmModel().(servicesCapturesRmModel);*/

            }
        });
    }


    // Insert Materials Details based on type, PestType
    public static void UpdateMaterialListRemarks(final String mServiceId, final String mType, final String mPest, final ArrayList<MaterialsCapturesRmModel> materialsCapturesRmModels) {

        final RealmList<MaterialsCapturesRmModel> arr_list = new RealmList<>();
        arr_list.addAll(materialsCapturesRmModels);

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ServiceMaterialRMModel obj = realm.where(ServiceMaterialRMModel.class)
                        .equalTo("ServiceID", mServiceId)
                        .and()
                        .equalTo("SM_Type", mType)
                        .and()
                        .equalTo("PestType", mPest)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(ServiceMaterialRMModel.class);

                }
                // obj.setServcieCaptures(arr_list);
                obj.getMaterialsCaptures().deleteAllFromRealm();
                obj.getMaterialsCaptures().addAll(realm.copyToRealm(arr_list));


            }
        });
    }


    /**
     * Added data SERVICE/MATERIAL Db
     *
     * @param context
     * @param mPest
     * @param mType
     * @param mServiceID
     */
    public static void saveServiceDetailsInDb(Context context, final String mPest, final String mType, final String mServiceID) {

        Utils.showCustomDialog(context);
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmResults<ServiceMaterialRMModel> result1 = realm.where(ServiceMaterialRMModel.class)
                .equalTo("SM_Type", mType)
                .and()
                .equalTo("PestType", mPest)
                .and()
                .equalTo("ServiceID", mServiceID)
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<ServiceMaterialRMModel> serviceMaterialRMModels = (ArrayList<ServiceMaterialRMModel>) realm.copyFromRealm(result1);

        if (serviceMaterialRMModels.size() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    // Get the current max id in the users table
                    Number maxId = realm.where(ServiceMaterialRMModel.class).max("id");
                    // If there are no rows, currentId is null, so the next id must be 1
                    // If currentId is not null, increment it by 1
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                    //Create a user if there isn't one
                    final ServiceMaterialRMModel serviceMaterialRMModel = realm.createObject(ServiceMaterialRMModel.class, nextId);
                    serviceMaterialRMModel.setSM_Type(mType);
                    serviceMaterialRMModel.setPestType(mPest);
                    serviceMaterialRMModel.setSM_remarks("");
                    serviceMaterialRMModel.setOthers("");
                    serviceMaterialRMModel.setServiceID(mServiceID);

                }
            });
        }

        Utils.dismissDialog();
    }

/*    public static void saveImageDetails(final String BeforeAfter, final String mPest, final String ServiceID, final Bitmap bitmap){
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // Get the current max id in the users table
                Number maxId = realm.where(PhotoRemarkRMModel.class).max("id");
                // If there are no rows, currentId is null, so the next id must be 1
                // If currentId is not null, increment it by 1
                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                //Create a user if there isn't one
                final PhotoRemarkRMModel photoRemarkRMModel = realm.createObject(PhotoRemarkRMModel.class, nextId);
                photoRemarkRMModel.setImgBase64(Utils.convertBitmapToBase64(bitmap));
                photoRemarkRMModel.setBeforeAfter(BeforeAfter);
                photoRemarkRMModel.setPestType(mPest);
                photoRemarkRMModel.setRemarks("");
                photoRemarkRMModel.setOthers("");
                photoRemarkRMModel.setServiceID(ServiceID);

            }
        });
    }*/

    public static void saveImageDetails(final String BeforeAfter, final String mPest, final String ServiceID, final String imagePath) {
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                // Get the current max id in the users table
                Number maxId = realm.where(PhotoRemarkRMModel.class).max("id");
                // If there are no rows, currentId is null, so the next id must be 1
                // If currentId is not null, increment it by 1
                int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                //Create a user if there isn't one
                final PhotoRemarkRMModel photoRemarkRMModel = realm.createObject(PhotoRemarkRMModel.class, nextId);
                photoRemarkRMModel.setImgBase64(imagePath);
                photoRemarkRMModel.setBeforeAfter(BeforeAfter);
                photoRemarkRMModel.setPestType(mPest);
                photoRemarkRMModel.setRemarks("");
                photoRemarkRMModel.setOthers("");
                photoRemarkRMModel.setServiceID(ServiceID);

            }
        });
    }

    /**
     * SAVE/UPDATE TEAM DETAILS IN DB
     *
     * @param context
     * @param mServiceID
     * @param TeamLead
     * @param TeamMember
     * @param Remarks
     * @param TechSign
     * @param arrayList
     */
    public static void saveUpdateTeamDetailsInDb(Context context, final String mServiceID, final String TeamLead, final String TeamMember, final String Remarks, final String TechSign, ArrayList<Integer> arrayList) {

        final RealmList<Integer> arr_list = new RealmList<>();
        arr_list.addAll(arrayList);

        Utils.showCustomDialog(context);
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmResults<TeamCaptureRmModel> result1 = realm.where(TeamCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<TeamCaptureRmModel> teamCaptureRmModels = (ArrayList<TeamCaptureRmModel>) realm.copyFromRealm(result1);

        if (teamCaptureRmModels.size() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // Get the current max id in the users table
                    Number maxId = realm.where(TeamCaptureRmModel.class).max("id");
                    // If there are no rows, currentId is null, so the next id must be 1
                    // If currentId is not null, increment it by 1
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                    //Create a user if there isn't one
                    final TeamCaptureRmModel teamCaptureRmModel = realm.createObject(TeamCaptureRmModel.class, nextId);
                    teamCaptureRmModel.setServiceId(mServiceID);
                    teamCaptureRmModel.setTeamLead(TeamLead);
                    teamCaptureRmModel.setTeamMembers(TeamMember);
                    teamCaptureRmModel.setSelectedPositions(arr_list);
                    teamCaptureRmModel.setRemarks(Remarks);
                    teamCaptureRmModel.setTechSign(TechSign);


                }
            });
        } else {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    TeamCaptureRmModel obj = realm.where(TeamCaptureRmModel.class)
                            .equalTo("ServiceId", mServiceID)
                            .findFirst();
                    if (obj == null) {
                        obj = realm.createObject(TeamCaptureRmModel.class);

                    }

                    obj.setTeamLead(TeamLead);
                    obj.setTeamMembers(TeamMember);
                    obj.setSelectedPositions(arr_list);
                    obj.setRemarks(Remarks);
                    obj.setTechSign(TechSign);

                }
            });
        }

        Utils.dismissDialog();
    }


    /**
     * Get Save Team List
     *
     * @param mServiceID
     * @return
     */
    public static TeamCaptureRmModel getTeamSavedDetails(String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        TeamCaptureRmModel result1 = realm.where(TeamCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }

    public static LogTeamCaptureRmModel getLOGTeamSavedDetails(String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        LogTeamCaptureRmModel result1 = realm.where(LogTeamCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }

    /**
     * Save/Update Feedback Details
     *
     * @param context
     * @param mServiceID
     * @param mCustomerName
     * @param mEmailId
     * @param mRating
     * @param mRemarks
     * @param mCustomerSign
     */
    public static void saveUpdateFeedbackDetailsInDb(Context context, final String mServiceID, final String mCustomerName, final String mEmailId, final String mRating, final String mRemarks, final String mCustomerSign) {

        Utils.showCustomDialog(context);
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmResults<FeedbackCaptureRmModel> result1 = realm.where(FeedbackCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<FeedbackCaptureRmModel> feedbackCaptureRmModels = (ArrayList<FeedbackCaptureRmModel>) realm.copyFromRealm(result1);

        if (feedbackCaptureRmModels.size() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // Get the current max id in the users table
                    Number maxId = realm.where(FeedbackCaptureRmModel.class).max("id");
                    // If there are no rows, currentId is null, so the next id must be 1
                    // If currentId is not null, increment it by 1
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                    //Create a user if there isn't one
                    final FeedbackCaptureRmModel feedbackCaptureRmModel = realm.createObject(FeedbackCaptureRmModel.class, nextId);
                    feedbackCaptureRmModel.setServiceId(mServiceID);
                    feedbackCaptureRmModel.setCustomerName(mCustomerName);
                    feedbackCaptureRmModel.setEmailID(mEmailId);
                    feedbackCaptureRmModel.setRating(mRating);
                    feedbackCaptureRmModel.setRemarks(mRemarks);
                    feedbackCaptureRmModel.setCustomerSign(mCustomerSign);

                }
            });
        } else {

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    FeedbackCaptureRmModel obj = realm.where(FeedbackCaptureRmModel.class)
                            .equalTo("ServiceId", mServiceID)
                            .findFirst();
                    if (obj == null) {
                        obj = realm.createObject(FeedbackCaptureRmModel.class);

                    }

                    obj.setServiceId(mServiceID);
                    obj.setCustomerName(mCustomerName);
                    obj.setEmailID(mEmailId);
                    obj.setRating(mRating);
                    obj.setRemarks(mRemarks);
                    obj.setCustomerSign(mCustomerSign);

                }
            });
        }

        Utils.dismissDialog();
    }


    /**
     * Get Save Team List
     *
     * @param mServiceID
     * @return
     */
    public static FeedbackCaptureRmModel getFeedbackSavedDetails(String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        FeedbackCaptureRmModel result1 = realm.where(FeedbackCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }

    public static LogFeedbackCaptureRmModel getLOGFeedbackSavedDetails(String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        LogFeedbackCaptureRmModel result1 = realm.where(LogFeedbackCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }

    /**
     * Save/Update Payment details in db
     *
     * @param context
     * @param mServiceID
     * @param mPaymentMode
     * @param mTotalPayment
     * @param mPaymentNote
     * @param PaymentRemarks
     * @param mAreaPlanned
     * @param mAreaCompleted
     */
    public static void saveUpdatePaymentDetailsInDb(Context context, final String mServiceID, final String mPaymentMode, final String mTotalPayment, final String mPaymentNote, final String PaymentRemarks, final String mAreaPlanned, final String mAreaCompleted, final String mSOR) {


        Utils.showCustomDialog(context);
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmResults<PaymentCaptureRmModel> result1 = realm.where(PaymentCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<PaymentCaptureRmModel> teamCaptureRmModels = (ArrayList<PaymentCaptureRmModel>) realm.copyFromRealm(result1);

        if (teamCaptureRmModels.size() == 0) {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    // Get the current max id in the users table
                    Number maxId = realm.where(PaymentCaptureRmModel.class).max("id");
                    // If there are no rows, currentId is null, so the next id must be 1
                    // If currentId is not null, increment it by 1
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                    //Create a user if there isn't one
                    final PaymentCaptureRmModel paymentCaptureRmModel = realm.createObject(PaymentCaptureRmModel.class, nextId);
                    paymentCaptureRmModel.setServiceId(mServiceID);
                    paymentCaptureRmModel.setPaymentMode(mPaymentMode);
                    paymentCaptureRmModel.setTotalPayment(mTotalPayment);
                    paymentCaptureRmModel.setPaymentNote_chequeNo(mPaymentNote);
                    paymentCaptureRmModel.setPaymentRemarks(PaymentRemarks);
                    paymentCaptureRmModel.setTotalAreaPlanned(mAreaPlanned);
                    paymentCaptureRmModel.setTotalAreaCompleted(mAreaCompleted);
                    paymentCaptureRmModel.setSOR(mSOR);

                }
            });
        } else {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    PaymentCaptureRmModel obj = realm.where(PaymentCaptureRmModel.class)
                            .equalTo("ServiceId", mServiceID)
                            .findFirst();
                    if (obj == null) {
                        obj = realm.createObject(PaymentCaptureRmModel.class);
                    }

                    obj.setPaymentMode(mPaymentMode);
                    obj.setTotalPayment(mTotalPayment);
                    obj.setPaymentNote_chequeNo(mPaymentNote);
                    obj.setPaymentRemarks(PaymentRemarks);
                    obj.setTotalAreaPlanned(mAreaPlanned);
                    obj.setTotalAreaCompleted(mAreaCompleted);
                    obj.setSOR(mSOR);

                }
            });
        }

        Utils.dismissDialog();
    }


    /**
     * Get Payment Details
     *
     * @param mServiceID
     * @return
     */
    public static PaymentCaptureRmModel getPaymentSavedDetails(String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        PaymentCaptureRmModel result1 = realm.where(PaymentCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }


    public static LogPaymentCaptureRmModel getLOGPaymentSavedDetails(String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        LogPaymentCaptureRmModel result1 = realm.where(LogPaymentCaptureRmModel.class)
                .equalTo("ServiceId", mServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }

    /**
     * get Service/Materials from DB to display preview
     *
     * @param mServiceID
     * @param mType
     * @return
     */
    public static ArrayList<ServiceMaterialRMModel> getServiceMaterialFromDB(String mServiceID, String mType) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<ServiceMaterialRMModel> result1 = realm.where(ServiceMaterialRMModel.class)
                .equalTo("ServiceID", mServiceID)
                .and()
                .equalTo("SM_Type", mType)
                // .and()
                //.notEqualTo("SM_remarks", "")
                .findAll();
        realm.commitTransaction();

        ArrayList<ServiceMaterialRMModel> serviceMaterialRMModels = (ArrayList<ServiceMaterialRMModel>) realm.copyFromRealm(result1);

        ArrayList<ServiceMaterialRMModel> newserviceMaterialRMModels = new ArrayList<>();
        for (ServiceMaterialRMModel serviceMaterialRMModel : serviceMaterialRMModels) {

            if (serviceMaterialRMModel.getOthers().length() != 0 || serviceMaterialRMModel.getSM_remarks().length() != 0) {
                newserviceMaterialRMModels.add(serviceMaterialRMModel);
            }

        }

        return newserviceMaterialRMModels;
    }


    /**
     * Get Photo BEfore and after details from db by passing pesttype n type
     *
     * @param mType
     * @return
     */
    public static ArrayList<PhotoRemarkRMModel> getPhotoRemarksDetails(String mType, String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<PhotoRemarkRMModel> result1 = realm.where(PhotoRemarkRMModel.class)
                .equalTo("BeforeAfter", mType)

                .equalTo("ServiceID", mServiceID)
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<PhotoRemarkRMModel> photoWithRemarks = (ArrayList<PhotoRemarkRMModel>) realm.copyFromRealm(result1);

        return photoWithRemarks;
    }


    public static ArrayList<LogPhotoRemarkRMModel> getLOGPhotoRemarksDetails(String mType, String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<LogPhotoRemarkRMModel> result1 = realm.where(LogPhotoRemarkRMModel.class)
                .equalTo("BeforeAfter", mType)

                .equalTo("ServiceID", mServiceID)
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<LogPhotoRemarkRMModel> photoWithRemarks = (ArrayList<LogPhotoRemarkRMModel>) realm.copyFromRealm(result1);

        return photoWithRemarks;
    }

    public static void ClearDbAfterUpload() {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        realm.where(FeedbackCaptureRmModel.class).findAll().deleteAllFromRealm();

        realm.where(PaymentCaptureRmModel.class).findAll().deleteAllFromRealm();
        realm.where(PhotoRemarkRMModel.class).findAll().deleteAllFromRealm();
        //realm.where(ServiceMaterialRMModel.class).findAll().deleteAllFromRealm();
        //realm.where(ServicesCapturesRmModel.class).findAll().deleteAllFromRealm();
        // realm.where(MaterialsCapturesRmModel.class).findAll().deleteAllFromRealm();
        realm.where(TeamCaptureRmModel.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();

    }

    public static void UploadJobStatus(final String mServiceID, final String mStatus) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Datum obj = realm.where(Datum.class)
                        .equalTo("ServiceID", mServiceID)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(Datum.class);
                }

                obj.setStatus(mStatus);

            }
        });

    }

    public static void UploadJobStatus(final String mServiceID, final String mStatus, final String mUploadStatus) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Datum obj = realm.where(Datum.class)
                        .equalTo("ServiceID", mServiceID)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(Datum.class);
                }

                obj.setStatus(mStatus);
                obj.setUploadStatus(mUploadStatus);

            }
        });

    }

    public static void UploadAdhocJob(final String mAdhocId,final String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                AdhocRequestRm obj = realm.where(AdhocRequestRm.class)
                        .equalTo("ADHOCServiceId", mAdhocId)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(AdhocRequestRm.class);
                }

                obj.setServiceId(mServiceID);

            }
        });

    }


    public static void UploadAdhocIdPrimaryKey(final String mAdhocId,final String mID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                AdhocRequestRm obj = realm.where(AdhocRequestRm.class)
                        .equalTo("ADHOCServiceId", mAdhocId)
                        .findFirst();
                if (obj == null) {
                    obj = realm.createObject(AdhocRequestRm.class);
                }

                obj.set_id(mID);

            }
        });

    }

    public static String getAdhocBasedonServiceID(final String mServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
                AdhocRequestRm result1 = realm.where(AdhocRequestRm.class)
                        .equalTo("ServiceId", mServiceID)
                        .findFirst();
                // Execute the query:
                realm.commitTransaction();


        return result1.getADHOCServiceId();

    }

    /**
     * Fetches total TeamName in Arraylist
     *
     * @return
     */
    public static AdhocRequestRm GetAhocData(String mAdhocServiceID) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();

        AdhocRequestRm result1 = realm.where(AdhocRequestRm.class)
                .equalTo("ADHOCServiceId", mAdhocServiceID)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1;
    }

    /**
     * Fetches total TeamName in Arraylist
     *
     * @return
     */
    public static ArrayList<AdhocRequestRm> GetAdhocList() {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<AdhocRequestRm> result1 = realm.where(AdhocRequestRm.class)
                .notEqualTo("ServiceId", "")
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<AdhocRequestRm> adhocRequestRms = (ArrayList<AdhocRequestRm>) realm.copyFromRealm(result1);

        return adhocRequestRms;
    }


    /**
     * Fetches total TeamName in Arraylist
     *
     * @return
     */
    public static ArrayList<String> GetTeamNameList() {

        ArrayList<Teams> multiSelectModels = new ArrayList<>();
        ArrayList<String> arr_teamName = new ArrayList<>();
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmQuery<Teams> query1 = realm.where(Teams.class);
        // Execute the query:
        RealmResults<Teams> result1 = query1.findAll();
        realm.commitTransaction();
        multiSelectModels = (ArrayList<Teams>) realm.copyFromRealm(result1);
        for (int i = 0; i < multiSelectModels.size(); i++) {
            arr_teamName.add(multiSelectModels.get(i).getTeamName());
        }

        return arr_teamName;
    }


    /**
     * Fetches total TeamName in Arraylist
     *
     * @return
     */
    public static String getTeamID(String TeamName) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        Teams result1 = realm.where(Teams.class)
                .equalTo("teamName", TeamName)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1.getId();
    }

    /**
     * Fetches total TeamName in Arraylist
     *
     * @return
     */
    public static String getTeamLeadName(String TeamName) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        Teams result1 = realm.where(Teams.class)
                .equalTo("teamName", TeamName)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1.getTeamLead();
    }

    /**
     * Fetches total TeamName in Arraylist
     *
     * @return
     */
    public static String getTeamMemberForAdhoc(String mTeamId) {

        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        Teams result1 = realm.where(Teams.class)
                .equalTo("id", mTeamId)
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1.getTeamMembers();
    }

    /**
     * Add LOG Details
     */
    public static void AddLog(final String _id, final String mServiceID, final String mStatus, final String mLogDate, final String mLogTime) {
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //Create a user if there isn't one
                final LogsTable logsTable = realm.createObject(LogsTable.class); // Create a new String
                logsTable.setId(_id);
                logsTable.setServiceId(mServiceID);
                logsTable.setStatus(mStatus);
                logsTable.setLogDate(mLogDate);
                logsTable.setLogTime(mLogTime);
            }
        });
    }

    public static ArrayList<LogsTable> getAllLogDetails() {
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmQuery<LogsTable> query1 = realm.where(LogsTable.class);
        // Execute the query:
        RealmResults<LogsTable> result1 = query1.findAll();
        realm.commitTransaction();

        return (ArrayList<LogsTable>) realm.copyFromRealm(result1);
    }

    public static void DeleteLogDetails() {
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        realm.where(LogsTable.class).findAll().deleteAllFromRealm();

        realm.where(ServiceMaterialRMModel.class).findAll().deleteAllFromRealm();
        realm.where(MaterialsCapturesRmModel.class).findAll().deleteAllFromRealm();
        realm.where(ServicesCapturesRmModel.class).findAll().deleteAllFromRealm();
        realm.where(LogsServiceDetails.class).findAll().deleteAllFromRealm();
        realm.where(LogFeedbackCaptureRmModel.class).findAll().deleteAllFromRealm();
        realm.where(LogPaymentCaptureRmModel.class).findAll().deleteAllFromRealm();
        realm.where(LogTeamCaptureRmModel.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();
    }

    public static void DeleteDuplicate(String mServiceID) {
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        realm.where(Datum.class)
                .equalTo("ServiceID", mServiceID)
                .and()
                .notEqualTo("UploadStatus", "Upload InProgress")
                .and()
                .notEqualTo("UploadStatus", "Upload Success")
                .findAll()
                .deleteAllFromRealm();


        realm.commitTransaction();
    }

    /**
     * Check whether In progress already exist
     *
     * @return
     */
    public static int checkInProgressExist() {
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        RealmResults<Datum> result1 = realm.where(Datum.class)
                .equalTo("Status", "In-Progress")
                .findAll();
        // Execute the query:
        realm.commitTransaction();

        ArrayList<Datum> teamCaptureRmModels = (ArrayList<Datum>) realm.copyFromRealm(result1);

        return teamCaptureRmModels.size();
    }

    /**
     * Check whether In progress already exist
     *
     * @return
     */
    public static String getCustomerName_InProgressExist() {
        final Realm realm = Realm.getDefaultInstance(); // opens db

        realm.beginTransaction();
        Datum result1 = realm.where(Datum.class)
                .equalTo("Status", "In-Progress")
                .findFirst();
        // Execute the query:
        realm.commitTransaction();

        return result1.getCustomerdetails().get(0).getCompanyName();
    }


    /**
     * Get Master details count
     *
     * @return
     */
    public static int getMasterCount() {
       /* int materialCount, servicesCount, pestTypeCount, teamsCount;
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        materialCount = (int) realm.where(Materials.class).count();
        servicesCount = (int) realm.where(ServiceType.class).count();
        pestTypeCount = (int) realm.where(SettingRemarks.class).count();
        teamsCount = (int) realm.where(PaymentMode.class).count();
        realm.commitTransaction();

        AppLogger.verbose("Master Count -- ", "" + materialCount + "," + servicesCount + "," + pestTypeCount + "," + teamsCount);
        if (materialCount == 0 || servicesCount == 0 || pestTypeCount == 0 || teamsCount == 0) {

            return 0;
        } else {

            return 1;
        }*/
        return 10;
    }


    public static int GetLogDuplicateCount(final String mServiceId) {
        int materialCount;
        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<LogsServiceDetails> logsServiceDetails = realm.where(LogsServiceDetails.class).equalTo("ServiceId", mServiceId).findAll();

        materialCount = logsServiceDetails.size();

        realm.commitTransaction();

        return materialCount;
    }





    /**
     * Get Service details from Datum table by passing Service id
     *
     * @param mServiceId
     * @return
     */
    public static void CopyTotalDetailToLog(String mServiceId) {


        final Realm realm = Realm.getDefaultInstance(); // opens db
        realm.beginTransaction();
        RealmResults<FeedbackCaptureRmModel> result1 = realm.where(FeedbackCaptureRmModel.class).equalTo("ServiceId", mServiceId).findAll();

        RealmResults<PhotoRemarkRMModel> result2 = realm.where(PhotoRemarkRMModel.class)
                .equalTo("ServiceID", mServiceId)
                .findAll();

        final PaymentCaptureRmModel Paymentresult = realm.where(PaymentCaptureRmModel.class)
                .equalTo("ServiceId", mServiceId)
                .findFirst();

       /* RealmResults<ServiceMaterialRMModel> SMresult = realm.where(ServiceMaterialRMModel.class)
                .equalTo("ServiceID", mServiceId)
                .findAll()*/
        ;

        final TeamCaptureRmModel Teamresult = realm.where(TeamCaptureRmModel.class)
                .equalTo("ServiceId", mServiceId)
                .findFirst();

        realm.commitTransaction();

        // Log Feedback
        final ArrayList<FeedbackCaptureRmModel> feedbackCapt = (ArrayList<FeedbackCaptureRmModel>) realm.copyFromRealm(result1);
        for (int i = 0; i < feedbackCapt.size(); i++) {

            final int finalI = i;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    Number maxId = realm.where(LogFeedbackCaptureRmModel.class).max("id");
                    // If there are no rows, currentId is null, so the next id must be 1
                    // If currentId is not null, increment it by 1
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;
                    //Create a user if there isn't one
                    final LogFeedbackCaptureRmModel feedbackCaptureRmModel = realm.createObject(LogFeedbackCaptureRmModel.class, nextId); // Create a new String
                    feedbackCaptureRmModel.setServiceId(feedbackCapt.get(finalI).getServiceId());
                    feedbackCaptureRmModel.setCustomerName(feedbackCapt.get(finalI).getCustomerName());
                    feedbackCaptureRmModel.setEmailID(feedbackCapt.get(finalI).getEmailID());
                    feedbackCaptureRmModel.setRating(feedbackCapt.get(finalI).getRating());
                    feedbackCaptureRmModel.setRemarks(feedbackCapt.get(finalI).getRemarks());
                    feedbackCaptureRmModel.setRating(feedbackCapt.get(finalI).getRating());
                    feedbackCaptureRmModel.setCustomerSign(feedbackCapt.get(finalI).getCustomerSign());
                    feedbackCaptureRmModel.setSelectedPositions(feedbackCapt.get(finalI).getSelectedPositions());

                }
            });

        }

        // Photo After and Before Details
        final ArrayList<PhotoRemarkRMModel> photoWithRemarks = (ArrayList<PhotoRemarkRMModel>) realm.copyFromRealm(result2);
        for (int i = 0; i < photoWithRemarks.size(); i++) {
            final int finalI = i;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    // Get the current max id in the users table
                    Number maxId = realm.where(LogPhotoRemarkRMModel.class).max("id");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                    //Create a user if there isn't one
                    final LogPhotoRemarkRMModel photoRemarkRMModel = realm.createObject(LogPhotoRemarkRMModel.class, nextId); // Create a new String
                    photoRemarkRMModel.setServiceID(photoWithRemarks.get(finalI).getServiceID());
                    photoRemarkRMModel.setBeforeAfter(photoWithRemarks.get(finalI).getBeforeAfter());
                    photoRemarkRMModel.setPestType(photoWithRemarks.get(finalI).getPestType());
                    photoRemarkRMModel.setRemarks(photoWithRemarks.get(finalI).getRemarks());
                    photoRemarkRMModel.setImgBase64(photoWithRemarks.get(finalI).getImgBase64());
                    photoRemarkRMModel.setOthers(photoWithRemarks.get(finalI).getOthers());
                    photoRemarkRMModel.setSelectedPositions(photoWithRemarks.get(finalI).getSelectedPositions());
                }
            });
        }


        if (Paymentresult != null) {
            // Log Payment Details
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Number maxId = realm.where(LogPaymentCaptureRmModel.class).max("id");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                    //Create a user if there isn't one
                    final LogPaymentCaptureRmModel logPaymentCaptureRmModel = realm.createObject(LogPaymentCaptureRmModel.class, nextId); // Create a new String
                    logPaymentCaptureRmModel.setServiceId(Paymentresult.getServiceId());
                    logPaymentCaptureRmModel.setPaymentMode(Paymentresult.getPaymentMode());
                    logPaymentCaptureRmModel.setTotalPayment(Paymentresult.getTotalPayment());
                    logPaymentCaptureRmModel.setPaymentNote_chequeNo(Paymentresult.getPaymentNote_chequeNo());
                    logPaymentCaptureRmModel.setPaymentRemarks(Paymentresult.getPaymentRemarks());
                    logPaymentCaptureRmModel.setTotalAreaPlanned(Paymentresult.getTotalAreaPlanned());
                    logPaymentCaptureRmModel.setTotalAreaCompleted(Paymentresult.getTotalAreaCompleted());
                    logPaymentCaptureRmModel.setSOR(Paymentresult.getSOR());
                }
            });
        }

        // Service n material details
        /*final ArrayList<ServiceMaterialRMModel> serviceMaterialData = (ArrayList<ServiceMaterialRMModel>) realm.copyFromRealm(SMresult);
        for (int i = 0; i < serviceMaterialData.size(); i++) {
            final int finalI = i;
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    Number maxId = realm.where(LogServiceMaterialRMModel.class).max("id");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                    final LogServiceMaterialRMModel serviceMaterialRMModel = realm.createObject(LogServiceMaterialRMModel.class, nextId); // Create a new String
                    serviceMaterialRMModel.setServiceID(serviceMaterialData.get(finalI).getServiceID());
                    serviceMaterialRMModel.setPestType(serviceMaterialData.get(finalI).getPestType());
                    serviceMaterialRMModel.setSM_Type(serviceMaterialData.get(finalI).getSM_Type());
                    serviceMaterialRMModel.setSM_remarks(serviceMaterialData.get(finalI).getSM_remarks());
                    serviceMaterialRMModel.setOthers(serviceMaterialData.get(finalI).getOthers());
                    serviceMaterialRMModel.setSelectedPositions(serviceMaterialData.get(finalI).getSelectedPositions());

                    RealmList<ServicesCapturesRmModel> servicesCapturesRmModels = (serviceMaterialData.get(finalI).getServcieCaptures());
                    RealmList<LogServicesCapturesRmModel> logServicesCapturesRmModels = new RealmList<>();

                    for(int i=0; i< servicesCapturesRmModels.size(); i++){

                        LogServicesCapturesRmModel logServicesCapturesRmModel = new LogServicesCapturesRmModel();
                        logServicesCapturesRmModel.setId(servicesCapturesRmModels.get(i).getId());
                        logServicesCapturesRmModel.setNoCulls(servicesCapturesRmModels.get(i).getNoCulls());
                        logServicesCapturesRmModel.setNoBorrows(servicesCapturesRmModels.get(i).getNoBorrows());
                        logServicesCapturesRmModel.setNoDead(servicesCapturesRmModels.get(i).getNoDead());
                        logServicesCapturesRmModel.setHabitat(servicesCapturesRmModels.get(i).getHabitat());
                        logServicesCapturesRmModel.setReason(servicesCapturesRmModels.get(i).getReason());
                        logServicesCapturesRmModel.setAction(servicesCapturesRmModels.get(i).getAction());
                        logServicesCapturesRmModel.setRecommendation(servicesCapturesRmModels.get(i).getRecommendation());
                        logServicesCapturesRmModel.setBreeding(servicesCapturesRmModels.get(i).getBreeding());
                        logServicesCapturesRmModel.setSpeices(servicesCapturesRmModels.get(i).getSpeices());
                        logServicesCapturesRmModel.setDensity(servicesCapturesRmModels.get(i).getDensity());
                        logServicesCapturesRmModel.setInstar(servicesCapturesRmModels.get(i).getInstar());
                        logServicesCapturesRmModel.setMistingCarriedOut(servicesCapturesRmModels.get(i).getMistingCarriedOut());
                        logServicesCapturesRmModel.setMistingCarriedIFNo(servicesCapturesRmModels.get(i).getMistingCarriedIFNo());
                        logServicesCapturesRmModel.setMistingCarriedOthers(servicesCapturesRmModels.get(i).getMistingCarriedOthers());

                        logServicesCapturesRmModels.add(logServicesCapturesRmModel);
                    }

                    serviceMaterialRMModel.setServcieCaptures(logServicesCapturesRmModels);

                    RealmList<MaterialsCapturesRmModel> materialsCapturesRmModels =  (serviceMaterialData.get(finalI).getMaterialsCaptures());

                    RealmList<LogMaterialsCapturesRmModel> logMaterialsCapturesRmModels = new RealmList<>();

                    for(int i=0; i< materialsCapturesRmModels.size(); i++){

                        LogMaterialsCapturesRmModel logMaterialsCapturesRmModel = new LogMaterialsCapturesRmModel();
                        logMaterialsCapturesRmModel.setMaterialName(materialsCapturesRmModels.get(i).getMaterialName());
                        logMaterialsCapturesRmModel.setQuantity(materialsCapturesRmModels.get(i).getQuantity());
                        logMaterialsCapturesRmModel.setUnit(materialsCapturesRmModels.get(i).getUnit());

                        logMaterialsCapturesRmModels.add(logMaterialsCapturesRmModel);
                    }

                    serviceMaterialRMModel.setMaterialsCaptures(logMaterialsCapturesRmModels);

                }
            });

        }*/

        if (Teamresult != null) {
            // Team Details
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    Number maxId = realm.where(LogTeamCaptureRmModel.class).max("id");
                    int nextId = (maxId == null) ? 1 : maxId.intValue() + 1;

                    //Create a user if there isn't one
                    final LogTeamCaptureRmModel logTeamCaptureRmModel = realm.createObject(LogTeamCaptureRmModel.class, nextId); // Create a new String
                    logTeamCaptureRmModel.setServiceId(Teamresult.getServiceId());
                    logTeamCaptureRmModel.setTeamLead(Teamresult.getTeamLead());
                    logTeamCaptureRmModel.setTeamMembers(Teamresult.getTeamMembers());
                    logTeamCaptureRmModel.setRemarks(Teamresult.getRemarks());
                    logTeamCaptureRmModel.setTechSign(Teamresult.getTechSign());
                    logTeamCaptureRmModel.setSelectedPositions(Teamresult.getSelectedPositions());

                }
            });
        }


    }// end

}




