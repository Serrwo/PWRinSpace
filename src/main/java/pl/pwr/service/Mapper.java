package pl.pwr.service;

public interface Mapper<E, D> {
     D map(E entity);
}
