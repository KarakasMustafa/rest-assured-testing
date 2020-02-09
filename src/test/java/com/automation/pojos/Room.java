package com.automation.pojos;

public class Room {
    // if we don't wanna serialize some property
    // from POJO --> to JSON it will not have id
    private int id;
    private String name;
    private String description;
    private boolean withTV;
    private boolean withWhiteBoard;

    public Room() {
    }

    public Room(String name, String description, boolean withTV, boolean withWhiteBoard) {
        this.name = name;
        this.description = description;
        this.withTV = withTV;
        this.withWhiteBoard = withWhiteBoard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isWithTV() {
        return withTV;
    }

    public void setWithTV(boolean withTV) {
        this.withTV = withTV;
    }

    public boolean isWithWhiteBoard() {
        return withWhiteBoard;
    }

    public void setWithWhiteBoard(boolean withWhiteBoard) {
        this.withWhiteBoard = withWhiteBoard;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", withTV=" + withTV +
                ", withWhiteBoard=" + withWhiteBoard +
                '}';
    }
}
