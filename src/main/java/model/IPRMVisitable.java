package model;

public interface IPRMVisitable {

    <E>void accept(IPRMVisitor<E> visitor, E env);

}
