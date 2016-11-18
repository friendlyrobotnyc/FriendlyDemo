package nyc.friendlyrobot.demo.interaction;

import javax.inject.Inject;

import nyc.friendlyrobot.demo.data.remote.Api;

//Base class for any 1 reactive task
// such as saving or reading from db or saving to network.
public class Interaction {
    @Inject
    Api api;



}
