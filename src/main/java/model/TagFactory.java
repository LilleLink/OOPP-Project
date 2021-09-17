package model;

import java.util.UUID;

public class TagFactory {

    public UUID createTag(String name){
        return UUID.randomUUID();
    }

    private static class CommonTag{

    }
}
