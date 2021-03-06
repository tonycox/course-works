package com.epam.homework_9.dao.impl.xml;

import com.epam.homework_9.dao.exceptions.DaoException;
import com.epam.homework_9.dao.exceptions.ModelException;
import com.epam.homework_9.dao.interfaces.Dao;
import com.epam.homework_9.dao.impl.xml.utils.saxhandlers.ArtistHandler;
import com.epam.homework_9.dao.impl.xml.utils.saxhandlers.ArtistsIdHandler;
import com.epam.homework_9.models.Album;
import com.epam.homework_9.models.Artist;
import com.epam.homework_9.models.MusicGuide;
import com.epam.homework_9.models.Track;
import com.epam.homework_9.validators.ModelValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ArtistXmlDao implements Dao<Artist> {

    private static final Logger logger = LogManager.getLogger("com.epam.homework_9.fullOutLog");
    private final String xmlPath;
    private SAXParser parser;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;

    public ArtistXmlDao(String xmlPath, SAXParserFactory factory) {
        logger.info("initialization - " + ArtistXmlDao.class);
        this.xmlPath = xmlPath;
        try {
            parser = factory.newSAXParser();
            logger.debug("start init JAXB");
            JAXBContext context = JAXBContext.newInstance(MusicGuide.class, Artist.class, Album.class, Track.class);
            initMarshaller(context);
            initUnmarshaller(context);
            logger.debug("successful initialization");
        } catch (ParserConfigurationException | SAXException | JAXBException e) {
            logger.error("unsuccessful initialization - " + e.getMessage());
            throw new DaoException("init parser fallen at the beginning", e);
        }
    }

    private void initUnmarshaller(JAXBContext context) throws JAXBException {
        unmarshaller = context.createUnmarshaller();

    }

    private void initMarshaller(JAXBContext context) throws JAXBException {
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    }

    @Override
    public List<Artist> getAll() {
        logger.debug("getAll method");
        ArtistsIdHandler countHandler = new ArtistsIdHandler();
        parse(countHandler);
        Set<Long> idSet = countHandler.getIds();
        List<Artist> artists = new ArrayList<>();
        idSet.forEach(aLong -> artists.add(getById(aLong)));
        return artists;
    }

    @Override
    public Artist getById(Long id) {
        logger.debug("getAll method");
        ArtistHandler handler = new ArtistHandler(id);
        parse(handler);
        Artist artist = handler.get();
        try {
            ModelValidator.validate(artist);
        } catch (ModelException e) {
            throw new DaoException(e);
        }
        return artist;
    }

    private void parse(DefaultHandler handler) {
        try {
            parser.parse(xmlPath, handler);
        } catch (SAXException | IOException e) {
            logger.error("parsing was fallen" + e.getMessage());
            throw new DaoException("parsing lol", e);
        }
    }

    @Override
    public void add(Artist artist) {
        try {
            ModelValidator.validate(artist);
        } catch (ModelException e) {
            throw new DaoException(e);
        }
        marshalling(musicGuide -> musicGuide.addArtist(artist));
    }

    public void marshalling(Consumer<MusicGuide> operator) {
        try {
            MusicGuide guide = (MusicGuide) unmarshaller.unmarshal(new FileReader(xmlPath));
            operator.accept(guide);
            marshaller.marshal(guide, new FileWriter(xmlPath));
        } catch (JAXBException | IOException e) {
            logger.error("marshalling was fallen " + e.getMessage());
            throw new DaoException("marshalling lol", e);
        }
    }

    @Override
    public boolean delete(Artist artist) {
        if (checkArtist(artist)) {
            marshalling(musicGuide -> musicGuide.getAllArtists().remove(artist));
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Artist artist) {
        try {
            ModelValidator.validate(artist);
        } catch (ModelException e) {
            throw new DaoException(e);
        }
        Artist artistFromXml = getById(artist.getId());
        marshalling(musicGuide -> {
            List<Artist> allArtists = musicGuide.getAllArtists();
            int i = allArtists.indexOf(artistFromXml);
            allArtists.remove(i);
            allArtists.add(i, artist);
        });
        return true;
    }

    private boolean checkArtist(Artist artist) {
        try {
            ModelValidator.validate(artist);
        } catch (ModelException e) {
            throw new DaoException(e);
        }
        Long id = artist.getId();
        Artist artistInXml = getById(id);
        return artistInXml.equals(artist);
    }

}
