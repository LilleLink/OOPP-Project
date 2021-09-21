package model;

public interface IPRMVisitable {

    <E, T>T accept(IPRMVisitor<E, T> visitor, E env);

}
