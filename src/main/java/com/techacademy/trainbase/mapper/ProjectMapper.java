package com.techacademy.trainbase.mapper;

import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {DateMapper.class}
)
public interface ProjectMapper {
    // Placeholder mapper for project entities and nested fields.
}
