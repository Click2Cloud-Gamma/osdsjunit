package com.opensds.jsonmodels.addbackendrequests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBackendRequestPayload {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("endpoint")
        @Expose
        private String endpoint;
        @SerializedName("bucketName")
        @Expose
        private String bucketName;
        @SerializedName("security")
        @Expose
        private String security;
        @SerializedName("access")
        @Expose
        private String access;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }

        public String getSecurity() {
            return security;
        }

        public void setSecurity(String security) {
            this.security = security;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }

    @Override
    public String toString() {
        return "AddBackendRequestPayload{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", region='" + region + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", security='" + security + '\'' +
                ", access='" + access + '\'' +
                '}';
    }
}
