package com.devrabbit.picstarcelebrity.mvvm.model;

import androidx.annotation.Nullable;

public class CommentsModel {
    @Nullable
    String comments;

    public CommentsModel(@Nullable String comments) {
        this.comments = comments;
    }

    @Nullable
    public String getComments() {
        return comments;
    }

    public void setComments(@Nullable String comments) {
        this.comments = comments;
    }
}
