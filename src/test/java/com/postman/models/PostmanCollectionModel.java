package com.postman.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostmanCollectionModel {

    public Map<String, Object> getCollection() {
        return collection;
    }

    public void setCollection(Map<String, Object> collection) {
        this.collection = collection;
    }

    private Map<String, Object> collection;

}
