package com.magic.akash.magicbox;

/**
 * Created by AKASH on 2/15/2017.
 */
public class DataModel_star {

    String currency;
    String amount;
    String place;
    String date;


    public DataModel_star(String currency, String amount,  String place,String date  ) {
        this.currency=currency;
        this.amount=amount;
        this.place=place;
        this.date=date;

    }

    public String getcurrency() {
        return currency;
    }

    public String getamount() {
        return amount;
    }

    public String getplace() {
        return place;
    }

    public String getdate() {
        return date;
    }
}
