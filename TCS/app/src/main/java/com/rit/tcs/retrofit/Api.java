package com.rit.tcs.retrofit;


import com.google.gson.JsonElement;
import com.rit.tcs.bussinessobject.*;
import com.rit.tcs.bussinessobject.ConfirmModel.ConfirmModel;
import com.rit.tcs.bussinessobject.LoginFirstModel.LoginFirstModel;
import retrofit2.Call;
import retrofit2.http.*;

public interface Api {

    @FormUrlEncoded
    @POST(NetworkUtility.LOGIN)
    Call<LoginFirstModel> createlogin(
            @Field("username") String username,
            @Field("password") String password,
            @Field("deviceId") String deviceId,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(NetworkUtility.LOGIN)
    Call<LoginFirstModel> createlogin1(
            @Field("username") String username,
            @Field("password") String password,
            @Field("deviceId") String deviceId,
            @Field("type") String type
    );

    @POST(NetworkUtility.CHECK_PROXY)
    Call<LoginFirstModel> checkproxy();


    @FormUrlEncoded
    @POST(NetworkUtility.GET_LOCKER_BY_USERID)
    Call<LockerModel> fetchLockerByUserId(
            @Field("userid") String userId
    );

    @FormUrlEncoded
    @POST(NetworkUtility.GET_LOCKER_BY_COMPANYID)
    Call<LockerModel> fetchLockerByCompanyId(
            @Field("userid") String userId,
            @Field("cid") String cid
    );

    @FormUrlEncoded
    @POST(NetworkUtility.UPDATE_FCM_KEY)
    Call<GenericModel> updateDeviceFCM(
            @Field("userid") String userId,
            @Field("device_uid") String device_uid,
            @Field("key") String key,
            @Field("type") String type
    );

    @FormUrlEncoded
    @POST(NetworkUtility.PROFILE)
    Call<ProfileModel> fetchUserProfile(
            @Field("userid") String userid
    );

    @FormUrlEncoded
    @POST(NetworkUtility.BOOKING)
    Call<ConfirmModel> createBooking(
            @Field("userid") String userid,
            @Field("cat_id") String cat_id,
            @Field("asset_id") String asset_id,
            @Field("startTime") String startTime,
            @Field("endTime") String endTime
    );


    @GET(NetworkUtility.MQTTJS)
    Call<GenericModel> sendNotification(
            @Query("locid") String locid
    );


    @FormUrlEncoded
    @POST(NetworkUtility.RELEASE_LOCKER)
    Call<GenericModel> releaseLocker(
            @Field("locker_uid") String locker_uid,
            @Field("userid") String userid,
            @Field("booking_id") String booking_id
    );



    @FormUrlEncoded
    @POST(NetworkUtility.GET_LOCKER_RFID)
    Call<ProfileTabModel> getLockerByRfid(
            @Field("rfid") String rfid
    );

    @FormUrlEncoded
    @POST(NetworkUtility.SAVE_ABOUT)
    Call<GenericModel> saveAbout(
            @Field("userid") String userid,
            @Field("about") String about
    );

    @FormUrlEncoded
    @POST(NetworkUtility.CHECK_PASSCODE)
    Call<RFIDDataModel> checkPasscode(
            @Field("passcode") String rfid
    );

    @FormUrlEncoded
    @POST(NetworkUtility.FORGOT_PASSCODE)
    Call<GenericModel> forgotPasscode(
            @Field("username") String rfid
    );


    /*


    @GET(NetworkUtility.NOTIFICATIONS)
    Call<NotificationModel> fetchNotification(
            @Query("loggeduserid") String userid
    );

    @GET(NetworkUtility.GET_ALL_AD)
    Call<AdDataModel> fetchAdData(
    );
    @GET(NetworkUtility.USER_CREDIT)
    Call<ImageUserActionModel> fetchUserCredit(
            @Query("loggeduserid") String userid
    );


    @FormUrlEncoded
    @POST(NetworkUtility.RECOMENDED)
    Call<RecomendedModel> fetchRecomended(
            @Field("userid") String userid,
            @Field("username") String userName
    );

    @FormUrlEncoded
    @POST(NetworkUtility.FOLLOWING)
    Call<FollowingModel> fetchFollowing(
            @Field("userid") String userid,
            @Field("username") String userName
    );

    @FormUrlEncoded
    @POST(NetworkUtility.IMAGE_DETAILS)
    Call<ImageDetailModel> fetchImageDetail(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("photouserid") String photoUserid,
            @Field("photousername") String photoUserName
    );

    @FormUrlEncoded
    @POST(NetworkUtility.VIDEO_DETAILS)
    Call<VideoDetailModel> fetchVideoDetail(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("videouserid") String photoUserid,
            @Field("videousername") String photoUserName
    );

    @FormUrlEncoded
    @POST(NetworkUtility.IMAGE_USER_ACTIONS)
    Call<ImageUserActionModel> fetchImageUserAction(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("photoId") String photoUserid,
            @Field("operation") String photoUserName,
            @Field("comment") String comment,
            @Field("photo_name") String photoName,
            @Field("photo_user_id") String photoUserId,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("report") String report

    );

    @FormUrlEncoded
    @POST(NetworkUtility.CONTEST_ACTION)
    Call<ImageUserActionModel> fetchContestUserAction(
            @Field("contest_id") String contest_id,
            @Field("fullname") String userName,
            @Field("contest_name") String contest_name,
            @Field("email") String email,
            @Field("report") String report

    );


    @FormUrlEncoded
    @POST(NetworkUtility.VIDEO_USER_ACTIONS)
    Call<ImageUserActionModel> fetchVideoUserAction(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("videoid") String videoUserid,
            @Field("operation") String videoUserName,
            @Field("comment") String comment,
            @Field("video_name") String videoName,
            @Field("video_user_id") String videoUserId,
            @Field("fullname") String fullname,
            @Field("email") String email,
            @Field("report") String report,
            @Field("video_pic") String videoPic

    );

    @FormUrlEncoded
    @POST(NetworkUtility.UPDATE_PHOTO_UPLOAD)
    Call<ImageUserActionModel> updatePhotoUpload(
            @Field("title") String title,
            @Field("adult") String adult,
            @Field("tags") String tags,
            @Field("category") String category,
            @Field("description") String description,
            @Field("groupid") String groupid,
            @Field("operation") String operation

    );


    @FormUrlEncoded
    @POST(NetworkUtility.USER_FOLLOW)
    Call<ImageUserActionModel> updateUserfollow(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("followinguserid") String followinguserid,
            @Field("followingusername") String followingusername

    );

    @FormUrlEncoded
    @POST(NetworkUtility.CREATE_CHALLENGE)
    Call<BannerUploadModel> createChallenge(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("challenge_name") String challenge_name,
            @Field("category") String category,
            @Field("allow_no") String allow_no,
            @Field("voting_start") String voting_start,
            @Field("voting_ends") String voting_ends,
            @Field("challenge_type") String challenge_type,
            @Field("description") String description,
            @Field("winner_point") String winner_point,
            @Field("people_choice") String people_choice,
            @Field("biography") String biography,
            @Field("file_name") String file_name

    );


    @GET(NetworkUtility.GET_ALL_IMAGES)
    Call<AllImageModel> fetchAllImages();

    @GET(NetworkUtility.GET_ALL_IMAGES)
    Call<AllImageModel> fetchCategoryImages(@Query("category") String category);


    @GET(NetworkUtility.GET_ALL_VIDEOS)
    Call<AllVideoModel> fetchAllVideos();

    @GET(NetworkUtility.GET_ALL_VIDEOS)
    Call<AllVideoModel> fetchCategoryVideos(@Query("category") String category);

    @GET(NetworkUtility.MISCELLANEOUS)
    Call<CategoryModel> fetchMiscellaneous();


    @GET(NetworkUtility.GET_ALL_CONTESTCHALLENGES)
    Call<ContestChallengesModel> fetchCategoryContestsChallenges(@Query("category") String category);

    @GET(NetworkUtility.GET_ALL_ONGOING_VOTING)
    Call<ContestChallengesModel> fetchVoting();


    @GET(NetworkUtility.CONTEST_DETAILS)
    Call<ContestDetailModel> fetchContestDetails(@Query("contestid") String contestid);

    @GET(NetworkUtility.CHALLENGES_DETAILS)
    Call<ChallengeDetailModel> fetchChallengeDetails(@Query("challengeid") String challengeid);

    @FormUrlEncoded
    @POST(NetworkUtility.GET_USER_IMAGES)
    Call<AllImageModel> fetchAllUserImages(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName
    );

    @FormUrlEncoded
    @POST(NetworkUtility.CONTEST_VOTE_SUBMISSION)
    Call<ImageUserActionModel> contestVote(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("id") String id,
            @Field("photos") String photos,
            @Field("entry_type") String entryType

    );

    @FormUrlEncoded
    @POST(NetworkUtility.CHALLENGE_VOTE_SUBMISSION)
    Call<ImageUserActionModel> challengeVote(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("id") String id,
            @Field("photos") String photos,
            @Field("entry_type") String entryType

    );

    @FormUrlEncoded
    @POST(NetworkUtility.CONTEST_SUBMIT_ENTRY)
    Call<ImageUserActionModel> contestSubmitEntry(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("id") String id,
            @Field("media_id") String photos,
            @Field("entry_type") String entryType

    );

    @FormUrlEncoded
    @POST(NetworkUtility.CHALLENGE_SUBMIT_ENTRY)
    Call<ImageUserActionModel> challengeSubmitEntry(
            @Field("loggeduserid") String userid,
            @Field("loggedusername") String userName,
            @Field("id") String id,
            @Field("media_id") String photos,
            @Field("entry_type") String entryType
    );

    @Multipart
    @POST(NetworkUtility.FILE_UPLOAD_URL)
    Call<ImageUploadModel> upload(
            @Part("userid") RequestBody userid,
            @Part("username") RequestBody username,
            @Part("filename") RequestBody filename,
            @Part("groupid") RequestBody groupid,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST(NetworkUtility.VIDEO_UPLOAD_URL)
    Call<VideoUploadModel> uploadVideo(
            @Part("loggeduserid") RequestBody userid,
            @Part("loggedusername") RequestBody username,
            @Part("filename") RequestBody filename,
            @Part("groupid") RequestBody groupid,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST(NetworkUtility.BANNER_UPLOAD_URL)
    Call<BannerUploadModel> uploadBanner(
            @Part("filename") RequestBody filename,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST(NetworkUtility.PROFILE_IMAGE_UPLOAD_URL)
    Call<BannerUploadModel> uploadProfileImage(
            @Part("filename") RequestBody filename,
            @Part("loggeduserid") RequestBody loggeduserid,
            @Part("loggedusername") RequestBody loggedusername,
            @Part MultipartBody.Part file
    );

*/





    @FormUrlEncoded
    @POST(NetworkUtility.RFID_ACCESS)
    Call<RFIDDataModel> fetchDetailsByRFId(
            @Field("rfid") String rfid
    );


}
