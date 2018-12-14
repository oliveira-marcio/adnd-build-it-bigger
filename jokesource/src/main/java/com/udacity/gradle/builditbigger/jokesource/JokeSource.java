package com.udacity.gradle.builditbigger.jokesource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JokeSource {

    private Random mRandomGen;
    private List<String> mJokes;

    public JokeSource() {
        mRandomGen = new Random();

        mJokes = new ArrayList<>();
        mJokes.add("Eight bytes walk into a bar. The bartender asks, \"Can I get you anything?\"\n" +
                "\"Yeah,\" reply the bytes.  \"Make us a double.\"");
        mJokes.add("How many programmers does it take to change a light bulb?\n" +
                "None, it's a hardware problem");
        mJokes.add("Programming is 10% science, 20% ingenuity, and 70% getting the ingenuity " +
                "to work with the science.");
        mJokes.add("The generation of random numbers is too important to be left to chance.");
        mJokes.add("A SQL query goes into a bar, walks up to two tables and asks, \"Can I join you?\"");
    }

    public String getJoke() {
        return mJokes.get(mRandomGen.nextInt(mJokes.size()));
    }
}
