package gmbh.conteco.seminarverwaltung.mapper;

import gmbh.conteco.seminarverwaltung.domain.Seminar;
import gmbh.conteco.seminarverwaltung.domain.Seminartag;
import gmbh.conteco.seminarverwaltung.dto.SeminarDto;
import gmbh.conteco.seminarverwaltung.dto.SeminarMitTagenDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeminarMapper {
    @Mapping(source="dozent.username", target="dozentUsername")
    SeminarDto toDto(Seminar seminar);

    @Mapping(source = "seminar.dozent.username", target = "dozentUsername")
    @Mapping(source = "seminartage", target = "seminartage")
    SeminarMitTagenDto toDtoMitTagen(Seminar seminar, List<Seminartag> seminartage);

    @InheritInverseConfiguration
    Seminar toEntity(SeminarDto dto);
}
