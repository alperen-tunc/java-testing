package gmbh.conteco.seminarverwaltung.mapper;

import gmbh.conteco.seminarverwaltung.domain.Seminar;
import gmbh.conteco.seminarverwaltung.domain.SeminarArt;
import gmbh.conteco.seminarverwaltung.domain.SeminarStatus;
import gmbh.conteco.seminarverwaltung.domain.Seminartag;
import gmbh.conteco.seminarverwaltung.domain.User;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;
import gmbh.conteco.seminarverwaltung.dto.SeminartagDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-13T00:42:59+0200",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.0.z20250331-1358, environment: Java 21.0.6 (Eclipse Adoptium)"
)
@Component
public class SeminarMapperImpl implements SeminarMapper {

    @Override
    public SeminarDto toDto(Seminar seminar) {
        if ( seminar == null ) {
            return null;
        }

        String dozentUsername = null;
        UUID id = null;
        String seminarId = null;
        String titel = null;
        SeminarStatus status = null;
        SeminarArt art = null;
        int teilnehmerPraesenz = 0;
        int teilnehmerOnline = 0;
        String location = null;
        String link = null;

        dozentUsername = seminarDozentUsername( seminar );
        id = seminar.getId();
        seminarId = seminar.getSeminarId();
        titel = seminar.getTitel();
        status = seminar.getStatus();
        art = seminar.getArt();
        teilnehmerPraesenz = seminar.getTeilnehmerPraesenz();
        teilnehmerOnline = seminar.getTeilnehmerOnline();
        location = seminar.getLocation();
        link = seminar.getLink();

        LocalDateTime startzeitpunkt = null;
        LocalDateTime endzeitpunkt = null;

        SeminarDto seminarDto = new SeminarDto( id, seminarId, titel, dozentUsername, status, art, teilnehmerPraesenz, teilnehmerOnline, location, link, startzeitpunkt, endzeitpunkt );

        return seminarDto;
    }

    @Override
    public SeminarMitTagenDto toDtoMitTagen(Seminar seminar, List<Seminartag> seminartage) {
        if ( seminar == null && seminartage == null ) {
            return null;
        }

        String dozentUsername = null;
        UUID id = null;
        String seminarId = null;
        String titel = null;
        SeminarStatus status = null;
        SeminarArt art = null;
        int teilnehmerPraesenz = 0;
        int teilnehmerOnline = 0;
        String location = null;
        String link = null;
        if ( seminar != null ) {
            dozentUsername = seminarDozentUsername( seminar );
            id = seminar.getId();
            seminarId = seminar.getSeminarId();
            titel = seminar.getTitel();
            status = seminar.getStatus();
            art = seminar.getArt();
            teilnehmerPraesenz = seminar.getTeilnehmerPraesenz();
            teilnehmerOnline = seminar.getTeilnehmerOnline();
            location = seminar.getLocation();
            link = seminar.getLink();
        }
        List<SeminartagDto> seminartage1 = null;
        seminartage1 = seminartagListToSeminartagDtoList( seminartage );

        SeminarMitTagenDto seminarMitTagenDto = new SeminarMitTagenDto( id, seminarId, titel, dozentUsername, status, art, teilnehmerPraesenz, teilnehmerOnline, location, link, seminartage1 );

        return seminarMitTagenDto;
    }

    @Override
    public Seminar toEntity(SeminarDto dto) {
        if ( dto == null ) {
            return null;
        }

        Seminar.SeminarBuilder seminar = Seminar.builder();

        seminar.dozent( seminarDtoToUser( dto ) );
        seminar.art( dto.art() );
        seminar.id( dto.id() );
        seminar.link( dto.link() );
        seminar.location( dto.location() );
        seminar.seminarId( dto.seminarId() );
        seminar.status( dto.status() );
        seminar.teilnehmerOnline( dto.teilnehmerOnline() );
        seminar.teilnehmerPraesenz( dto.teilnehmerPraesenz() );
        seminar.titel( dto.titel() );

        return seminar.build();
    }

    private String seminarDozentUsername(Seminar seminar) {
        User dozent = seminar.getDozent();
        if ( dozent == null ) {
            return null;
        }
        return dozent.getUsername();
    }

    protected SeminartagDto seminartagToSeminartagDto(Seminartag seminartag) {
        if ( seminartag == null ) {
            return null;
        }

        LocalDate tagDatum = null;
        LocalTime startUhrzeit = null;
        LocalTime endUhrzeit = null;

        tagDatum = seminartag.getTagDatum();
        startUhrzeit = seminartag.getStartUhrzeit();
        endUhrzeit = seminartag.getEndUhrzeit();

        SeminartagDto seminartagDto = new SeminartagDto( tagDatum, startUhrzeit, endUhrzeit );

        return seminartagDto;
    }

    protected List<SeminartagDto> seminartagListToSeminartagDtoList(List<Seminartag> list) {
        if ( list == null ) {
            return null;
        }

        List<SeminartagDto> list1 = new ArrayList<SeminartagDto>( list.size() );
        for ( Seminartag seminartag : list ) {
            list1.add( seminartagToSeminartagDto( seminartag ) );
        }

        return list1;
    }

    protected User seminarDtoToUser(SeminarDto seminarDto) {
        if ( seminarDto == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( seminarDto.dozentUsername() );

        return user;
    }
}
