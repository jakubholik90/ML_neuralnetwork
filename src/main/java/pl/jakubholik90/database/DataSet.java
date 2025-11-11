package pl.jakubholik90.database;

public class DataSet {

    private final String name;
    private Integer size;

    public DataSet(String name, Integer size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
