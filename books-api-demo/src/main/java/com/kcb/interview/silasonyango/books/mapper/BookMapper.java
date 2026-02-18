package com.kcb.interview.silasonyango.books.mapper;

import com.kcb.interview.silasonyango.books.dto.BookDto;
import com.kcb.interview.silasonyango.books.entity.BookEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapper {

  BookEntity toEntity(BookDto dto);

  BookDto toDto(BookEntity entity);

  List<BookDto> toDtoList(List<BookEntity> entities);

  List<BookEntity> toEntityList(List<BookDto> dtos);
}
