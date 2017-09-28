package com.minsoura.findit;



/**
 * Created by min on 2016-01-14.
 */
public class ProfileStorage {

    public ProfileStorage(String userID){

    this.userID = userID;
    }
    //to add later: region, university, sayhi, picfive
    String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }



    String userHeight;
    String userJob;
    String userAge;
    String userPicMain;

    public String getUserPicMain() {
        return userPicMain;
    }

    public void setUserPicMain(String userPicMain) {
        this.userPicMain = userPicMain;
    }

    public String getUserPicTwo() {
        return userPicTwo;
    }

    public void setUserPicTwo(String userPicTwo) {
        this.userPicTwo = userPicTwo;
    }

    public String getUserPicThree() {
        return userPicThree;
    }

    public void setUserPicThree(String userPicThree) {
        this.userPicThree = userPicThree;
    }

    public String getUserPicFour() {
        return userPicFour;
    }

    public void setUserPicFour(String userPicFour) {
        this.userPicFour = userPicFour;
    }

    String userPicTwo;
    String userPicThree;
    String userPicFour;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    String userEmail;

    String SayHI;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSayHI() {
        return SayHI;
    }

    public void setSayHI(String sayHI) {
        SayHI = sayHI;
    }

    String message;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

    public String getUserCell() {
        return userCell;
    }

    public void setUserCell(String userCell) {
        this.userCell = userCell;
    }

    String userCell;
    String rating;

    public String getUserRegion() {
        return userRegion;
    }

    public void setUserRegion(String userRegion) {
        this.userRegion = userRegion;
    }

    public String getUserRegion2() {
        return userRegion2;
    }

    public void setUserRegion2(String userRegion2) {
        this.userRegion2 = userRegion2;
    }

    public String getUserUniversity() {
        return userUniversity;
    }

    public void setUserUniversity(String userUniversity) {
        this.userUniversity = userUniversity;
    }

    String userRegion;
    String userRegion2;
    String userUniversity;

    public String getUserSayHi() {
        return userSayHi;
    }

    public void setUserSayHi(String userSayHi) {
        this.userSayHi = userSayHi;
    }

    String userSayHi;



    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKeys(String userKey) {
        this.userKey = userKey;
    }

    String userKey;

    public String getUserdDay() {
        return userdDay;
    }

    public void setUserdDay(String userdDay) {
        this.userdDay = userdDay;
    }

    String userdDay;

    public String getUserRating() {
        return userRating;
    }

    String userRating;
    public ProfileStorage(String userID, String userHeight, String userJob, String userAge, String userEmail,String userRegion, String userRegion2, String userUniversity,String userSayhi) {
        this.userID = userID;
        this.userHeight = userHeight;
        this.userJob = userJob;
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userRegion = userRegion;
        this.userRegion2 = userRegion2;
        this.userUniversity = userUniversity;
        this.userSayHi = userSayhi;
    }

    public ProfileStorage(String userID, String userHeight, String userJob, String userAge, String userEmail,String userRegion, String userRegion2, String userUniversity,String userSayhi, String userRating) {
        this.userID = userID;
        this.userHeight = userHeight;
        this.userJob = userJob;
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userRegion = userRegion;
        this.userRegion2 = userRegion2;
        this.userUniversity = userUniversity;
        this.userSayHi = userSayhi;
        this.userRating =userRating;
    }
    public ProfileStorage(String userID, String userHeight, String userJob, String userAge, String userEmail,String userRegion, String userRegion2, String userUniversity,String userSayhi,String userKey, String userPicMain, String userPicTwo, String userPicThree, String userPicFour) {
        this.userID = userID;
        this.userHeight = userHeight;
        this.userJob = userJob;
        this.userAge = userAge;
        this.userEmail = userEmail;
        this.userPicMain = userPicMain;
        this.userPicTwo = userPicTwo;
        this.userPicThree = userPicThree;
        this.userPicFour = userPicFour;
        this.userRegion = userRegion;
        this.userRegion2 = userRegion2;
        this.userUniversity = userUniversity;
        this.userSayHi = userSayhi;
        this.userKey = userKey;
    }



    public ProfileStorage(String userID,String userEmail, String message, String type, String userCell, String userdDay){
        this.userID = userID;
        this.message = message;
        this.userEmail = userEmail;
        this.userdDay =userdDay;
        this.type = type;
        this.userCell = userCell;

    }
    public ProfileStorage(String userID,String userEmail, String userPicMain, String message, String type, String userCell, String userKey){
        this.userID = userID;
        this.message = message;
        this.userEmail = userEmail;
        this.userPicMain = userPicMain;
        this.type = type;
        this.userCell = userCell;
        this.userKey = userKey;

    }



}
