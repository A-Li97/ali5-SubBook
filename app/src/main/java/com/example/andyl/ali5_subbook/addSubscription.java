package com.example.andyl.ali5_subbook;
import java.util.Date;

/**
 * Created by Andy on 1/30/2018.
 */

public class addSubscription implements subscriptable{

    private String subscription;
    private Date date;
    private Integer cost;
    private String comment;

    public addSubscription(String subscription, /*Date date,*/ Integer cost){ // Constructor that doesn't contain comment
        this.subscription = subscription;
        //this.date = date;
        this.date = new Date();
        this.cost = cost;
        this.comment = new String();
    }

    public addSubscription(String subscription, /*Date date,*/ Integer cost, String comment){ // Constructor that contains comment
        this.subscription = subscription;
        //this.date = date;
        this.date = new Date();
        this.cost = cost;
        this.comment = comment;
    }

    public Date getDate(){
        return date;
    }

    public String getSubscription(){
        return subscription;
    }

    public Integer getCost(){
        return cost;
    }

    public String getComment(){
        return comment;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setSubscription(String subscription) throws SubscriptionTooLongException{
        if(subscription.length() > 20)
            throw new SubscriptionTooLongException();
        else
            this.subscription = subscription;
    }

    public void setCost(Integer cost) throws NegativeCostException{
        if(cost < 0)
            throw new NegativeCostException();
        else
            this.cost = cost;
    }

    public void setComment(String comment) throws CommentTooLongException{
        if(comment.length() > 30)
            throw new CommentTooLongException();
        else
            this.comment = comment;
    }
}
