package model;

public interface IPRMDatabase {

    User load(String name);
    void save(User user);

}
