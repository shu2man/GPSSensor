package com.example.yellow.gpssensor;

import cn.bmob.v3.BmobObject;

/**
 * Created by Yellow on 2018-2-11.
 */

public class BmobTable {
    public class friends extends BmobObject {
        private String id1;
        private String id2;
        private String lastword;

        public void setId1(String id1) {
            this.id1 = id1;
        }
        public void setId2(String id2) {
            this.id2 = id2;
        }
        public void setLastword(String lastword) {
            this.lastword = lastword;
        }

        public String getId1() {
            return id1;
        }
        public String getId2() {
            return id2;
        }
        public String getLastword() {
            return lastword;
        }
    }

    public class user extends BmobObject{
        private String sign;
        private String psd;
        private String profile;
        private String name;
        //private String objectId;

        /*        public void setObjectId(String objectId) {
                    this.objectId = objectId;
                }*/
        public void setName(String name) {
            this.name = name;
        }
        public void setProfile(String profile) {
            this.profile = profile;
        }
        public void setPsd(String psd) {
            this.psd = psd;
        }
        public void setSign(String sign) {
            this.sign = sign;
        }

        /*public String getObjectId() {
            return objectId;
        }*/
        public String getName() {
            return name;
        }
        public String getProfile() {
            return profile;
        }
        public String getPsd() {
            return psd;
        }
        public String getSign() {
            return sign;
        }

    }
}
