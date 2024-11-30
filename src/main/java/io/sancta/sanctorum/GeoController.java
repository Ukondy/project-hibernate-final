package io.sancta.sanctorum;

import io.sancta.sanctorum.dao.CityDAO;
import io.sancta.sanctorum.dao.CountryDAO;
import io.sancta.sanctorum.domain.City;
import io.sancta.sanctorum.domain.Country;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeoController {
    SessionFactory sessionFactory;
    CityDAO cityDAO;
    CountryDAO countryDAO;

    public GeoController() {
        sessionFactory = setupRelationalDataBase();
        cityDAO = new CityDAO(sessionFactory);
        countryDAO = new CountryDAO(sessionFactory);
    }
    public void run() {
        List<City> cities = fetchData();

    }

    private SessionFactory setupRelationalDataBase() {
        return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    private List<City> fetchData() {
        try(Session session = sessionFactory.getCurrentSession()) {
            List<City> allCities;
            session.beginTransaction();

            List<Country> countries = countryDAO.getAll();
            int totalCount = cityDAO.getTotalCount();
            int step = 500;

            allCities = IntStream.iterate(0, hasNext -> hasNext < totalCount, next -> next + step)
                    .mapToObj(i -> cityDAO.getItems(i, step))
                    .flatMap(Collection::stream)
                    .toList();

            session.getTransaction().commit();
            return allCities;
        }
    }
}
