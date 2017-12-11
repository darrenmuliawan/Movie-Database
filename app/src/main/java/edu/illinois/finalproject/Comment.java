package edu.illinois.finalproject;

/**
 * Created by darrenalexander on 12/10/17.
 */

public class Comment {
    private String name;
    private String comment;
    private String time;

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getTime() {
        return time;
    }

    public Comment(String name, String comment, String time) {

        this.name = name;
        this.comment = comment;
        this.time = time;
    }

}
