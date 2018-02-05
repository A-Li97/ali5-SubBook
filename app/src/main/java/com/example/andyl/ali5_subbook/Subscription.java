package com.example.andyl.ali5_subbook;

/**
 * @author Andy Li
 * @version 1
 * @see MainActivity
 */

public class Subscription implements subscriptable{

    private String subscription;
    private String date;
    private Double cost;
    private String comment;

    /**
     * This is a constructor method that takes in 3 parameters.
     *
     * @param subscription  name of the subscription
     * @param date          date subscription added
     * @param cost          monthly cost
     */

    public Subscription(String subscription, String date, Double cost){ // Constructor that doesn't contain comment
        this.subscription = subscription;
        this.date = date;
        this.cost = cost;
        this.comment = new String();
    }

    /**
     * This is a constructor method that takes in 4 parameters.
     *
     * @param subscription  name of the subscription
     * @param date          date subscription added
     * @param cost          monthly cost
     * @param comment       additional info
     */

    public Subscription(String subscription, String date, Double cost, String comment){ // Constructor that contains comment
        this.subscription = subscription;
        this.date = date;
        this.cost = cost;
        this.comment = comment;
    }

    /**
     * Returns the date of the subscription in string form.
     * <p>
     * This method gets the date of the subscription.
     *
     * @return  date
     */

    public String getDate(){
        return date;
    }

    /**
     * Returns subscription name
     * <p>
     * This method gets the name of the subscription.
     *
     * @return  name
     */

    public String getSubscription(){
        return subscription;
    }

    /**
     * Returns cost as a double.
     * <P>
     * This method returns the monthly cost of the subscription.
     *
     * @return cost
     */

    public Double getCost(){
        return cost;
    }

    /**
     * Returns additional info user enter in (if any)
     * <p>
     * This method returns additonal info entered by the user during creation of the object,
     * null if not available.
     *
     * @return comment
     */

    public String getComment(){
        return comment;
    }

    /**
     * This method takes a date as input and sets the object date to it.
     *
     * @param date  date of subscription
     */

    public void setDate(String date){
        this.date = date;
    }

    /**
     * This method takes in a string as input and sets the object name to it.
     *
     * @param subscription                      name of subscription
     * @throws SubscriptionTooLongException     error if subscription name exceeds 20 characters
     */

    public void setSubscription(String subscription) throws SubscriptionTooLongException{
        if(subscription.length() > 20)
            throw new SubscriptionTooLongException();
        else
            this.subscription = subscription;
    }

    /**
     * This method takes in a double as input and sets the object monthly cost to it.
     *
     * @param cost                      monthly cost of the subsciption
     * @throws NegativeCostException    error if cost is negative
     */

    public void setCost(Double cost) throws NegativeCostException{
        if(cost < 0)
            throw new NegativeCostException();
        else
            this.cost = cost;
    }

    /**
     * This method takes in a string as input and sets the object comment to it.
     *
     * @param comment                       additional info
     * @throws CommentTooLongException      error if comment exceeds 30 characters
     */

    public void setComment(String comment) throws CommentTooLongException{
        if(comment.length() > 30)
            throw new CommentTooLongException();
        else
            this.comment = comment;
    }

    /**
     * Returns a string in proper format
     * <p>
     * This method is called after changing the file format of json back to gson,
     * it prints the proper format of the string.
     *
     * @return string
     */

    public String toString() {
        return subscription +"\n" + date + " | $" + cost.toString();
    }
}
