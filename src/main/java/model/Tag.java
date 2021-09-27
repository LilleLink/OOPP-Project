package model;

class Tag implements ITag {

    private String name;

    private String color;

    Tag(String name){
        this.name = name;
        color = "CDCDCD";
    }

    Tag(Tag other) {
        this.name = other.name;
        this.color = other.color;
    }

    Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }

    /**
     *
     * @return the name of a Tag
     */
    public String getName(){
        return name;
    }

    /**
     *
     * @return the color code of a Tag
     */
    public String getColor(){
        return color;
    }

    void setName(String name) {
        this.name = name;
    }

    void setColor(String color) {
        this.color = color;
    }



}
