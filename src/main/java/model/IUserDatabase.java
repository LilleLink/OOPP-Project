package model;

public interface IUserDatabase {

    User load(String name);
    void save(User user);

}
