package com.brichev.model;

import org.bson.Document;

public interface DocumentedModel {
    Document toDocument();
}
