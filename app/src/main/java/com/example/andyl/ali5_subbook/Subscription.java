package com.example.andyl.ali5_subbook;

public class Subscription implements subscriptable{

    private String subscription;
    private String date;
    private Double cost;
    private String comment;

    public Subscription(String subscription, String date, Double cost){ // Constructor that doesn't contain comment
        this.subscription = subscription;
        this.date = date;
        this.cost = cost;
        this.comment = new String();
    }

    public Subscription(String subscription, String date, Double cost, String comment){ // Constructor that contains comment
        this.subscription = subscription;
        this.date = date;
        this.cost = cost;
        this.comment = comment;
    }

    public String getDate(){
        return date;
    }

    public String getSubscription(){
        return subscription;
    }

    public Double getCost(){
        return cost;
    }

    public String getComment(){
        return comment;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setSubscription(String subscription) throws SubscriptionTooLongException{
        if(subscription.length() > 20)
            throw new SubscriptionTooLongException();
        else
            this.subscription = subscription;
    }

    public void setCost(Double cost) throws NegativeCostException{
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

    public String toString() {
        return subscription +"\n" + date + " | $" + cost.toString();
    }
}
