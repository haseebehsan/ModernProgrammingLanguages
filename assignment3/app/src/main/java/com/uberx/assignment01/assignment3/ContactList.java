package com.uberx.assignment01.assignment3;

/**
 * Created by Haseeb on 3/15/2017.
 */
public class ContactList {
    String name;
    String number;
    public ContactList(String n, String num){
        this.name = n;
        this.number = num;
        // System.out.println(n + " " + num );
    }
    public String toString(){
        return name;
    }
    // static ArrayList<ContactList> list = new ArrayList<ContactList>();
}
