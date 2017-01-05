package com.artisans.code.movimento1euro.models;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents an election among various causes, in which users vote.
 */

public class Election implements Serializable{

    private final String dateFormat = "dd-MM-yyy HH:mm:ss";
    private SimpleDateFormat readingFormat = new SimpleDateFormat(dateFormat);
    private Calendar cal = Calendar.getInstance();

    /**
     * Causes that won the election
     */
    private ArrayList<Cause> winningCauses;
    /**
     * All the causes voted on this election
     */
    private ArrayList<Cause> causes;
    private int id;
    private int year;
    private String title;
    private Date startDate;
    private Date endDate;
    /**
     * Ammount M1E has available to reward the winners of this specific election
     */
    private double availableAmmount;
    private int totalVotes;

    public Election() {
    }

    /**
     * Constructor. Parses a JSON object to construct the election.
     * @param election JSON Object containing information for the election
     */
    public Election(JSONObject election) {
        try {
            id = election.getInt("id");
            title = election.getString("titulo");
            startDate = readingFormat.parse(election.getString("data_de_inicio"));
            endDate = readingFormat.parse(election.getString("data_de_fim"));

            cal.setTime(endDate);
            year = cal.get(Calendar.YEAR);

            availableAmmount = election.getDouble("montante_disponivel");

            try {
                if (election.getJSONObject("total_votos") == null)
                    totalVotes = 0;
                //totalVotes = election.getInt("total_votos");
            }catch(JSONException e){
                totalVotes = 0;
            }
        }catch(JSONException e ){
            Log.e("past", e.getMessage());
            id = 999;
            title = "Default";
            year = 2000;
            availableAmmount = 0;
            totalVotes = 0;

        }catch(Exception e){

        }
    }

    public String toString(){
        return "ID: " + Integer.toString(id) + ", Year: " + Integer.toString(year) + ", Title: " + title + ", Start Date: " + startDate.toString() + ", End Date: " + endDate.toString() + ", Available Ammount: " + Double.toString(availableAmmount) + ", Total Votes: " + Integer.toString(totalVotes);
    }

    /*
    public Election parsePastElection(JSONObject election){
        try {
            id = election.getInt("id");
        }catch(JSONException e ){

        }catch(Exception e){

        }

    }

    public static ArrayList<Cause> parsePastElectionWinners (JSONObject election){

    }
    */

    public int getId(){
        return id;
    }

    public int getYear(){
        return year;
    }

    public String getTitle(){
        return title;
    }

    public Date getStartDate(){
        return startDate;
    }

    public Date getEndDate(){
        return endDate;
    }

    public double getAvailableAmmount(){
        return availableAmmount;
    }

    public int getTotalVotes(){
        return totalVotes;
    }

}



