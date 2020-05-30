package jag.game;

import jag.RSProvider;

public interface RSWorld extends RSProvider {

    String getActivity();

    String getDomain();

    int getLocation();

    int getId();

    int getMask();

    int getPopulation();

}