package com.ramez.driver.Classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class State {

        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("name")
        @Expose
        private String name;

        public int getUserId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
