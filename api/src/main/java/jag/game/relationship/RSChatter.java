package jag.game.relationship;

import jag.RSProvider;

public interface RSChatter extends RSProvider {

    RSNamePair getDisplayName();

    RSNamePair getPreviousName();

}