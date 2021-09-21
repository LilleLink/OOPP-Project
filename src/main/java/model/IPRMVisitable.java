package model;

public interface IPRMVisitable {

    <T, E>void accept(IPRMVisitor<T, E> visitor, E env);

}
