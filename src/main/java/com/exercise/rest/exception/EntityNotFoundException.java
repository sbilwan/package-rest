package com.exercise.rest.exception;

public class EntityNotFoundException extends RuntimeException {

    private String id;

    private String message;

    public EntityNotFoundException(){
        super();
    }
    public EntityNotFoundException(String id){
        super();
        this.id = id;
    }
    public EntityNotFoundException(String id, String message ){
        super();
        this.id = id;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
