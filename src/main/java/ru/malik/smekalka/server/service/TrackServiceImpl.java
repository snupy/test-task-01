package ru.malik.smekalka.server.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.malik.smekalka.server.domain.Track;
import ru.malik.smekalka.server.repository.CarRepository;
import ru.malik.smekalka.server.repository.TrackRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TrackServiceImpl implements TrackService {
    private final CarRepository carRep;
    private final TrackRepository trackRep;

    @Autowired
    public TrackServiceImpl(CarRepository carRep, TrackRepository trackRep) {
        this.carRep = carRep;
        this.trackRep = trackRep;
    }

    @Override
    public void add(Track track) {
        log.trace("Try add track");
        track.getCars().forEach(c -> c.setNew(true));
        track.setNew(true);
        carRep.saveAll(track.getCars());
        trackRep.save(track);
    }

    @Override
    public void update(Track track) {
        log.trace("Try update track");
        track.getCars().forEach(c -> c.setNew(false));
        carRep.saveAll(track.getCars());
        track.setNew(false);
        trackRep.save(track);
    }

    @Override
    public List<Track> getAll() {
        log.trace("Try get all track");
        return trackRep.findAll();
    }

    @Override
    public Optional<Track> get(String id) {
        log.trace("Try get one track");
        return trackRep.findById(id);
    }
}
