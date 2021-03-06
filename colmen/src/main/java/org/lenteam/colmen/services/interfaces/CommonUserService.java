package org.lenteam.colmen.services.interfaces;

import org.lenteam.colmen.models.DailyStatistic;
import org.lenteam.colmen.models.Person;
import org.lenteam.colmen.models.Site;
import org.lenteam.colmen.models.StatisticPerson;

import java.time.LocalDate;

/**
 * @author Anton_Solovev
 * @since 9/1/2016
 */
public interface CommonUserService {

    Iterable<Person> getAllPersons();

    Iterable<Site> getAllSites();

    Iterable<StatisticPerson> getPersonsOnSite(Integer siteId);

    DailyStatistic getPersonStatisticOnSite(Integer personId, Integer siteId);

    DailyStatistic getPersonStatisticOnSite(Integer personId, Integer siteId, LocalDate fromDate, LocalDate toDate);
}

