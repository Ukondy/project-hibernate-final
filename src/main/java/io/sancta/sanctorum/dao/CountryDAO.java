package io.sancta.sanctorum.dao;

import io.sancta.sanctorum.domain.City;
import io.sancta.sanctorum.domain.Country;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CountryDAO {
    SessionFactory sessionFactory;

    public List<Country> getAll() {
        String hql = "select country from Country country join fetch country.languages";

        return sessionFactory.getCurrentSession().createQuery(hql, Country.class).list();
    }
}
