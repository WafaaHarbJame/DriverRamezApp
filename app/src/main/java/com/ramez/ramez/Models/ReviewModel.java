package com.ramez.ramez.Models;

 public class ReviewModel {
   private int id;
    private String reviewText;

     public ReviewModel(int id, String reviewText) {
         this.id = id;
         this.reviewText = reviewText;
     }

     public int getId() {
         return id;
     }

     public void setId(int id) {
         this.id = id;
     }

     public String getReviewText() {
         return reviewText;
     }

     public void setReviewText(String reviewText) {
         this.reviewText = reviewText;
     }
 }
